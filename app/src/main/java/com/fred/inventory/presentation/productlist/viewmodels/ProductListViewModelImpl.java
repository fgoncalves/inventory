package com.fred.inventory.presentation.productlist.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.View;
import com.fred.inventory.R;
import com.fred.inventory.domain.models.Product;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.GetProductInfoFromCodeUseCase;
import com.fred.inventory.domain.usecases.GetProductListUseCase;
import com.fred.inventory.domain.usecases.SaveProductListInLocalStorageUseCase;
import com.fred.inventory.presentation.items.ItemScreen;
import com.fred.inventory.presentation.productlist.adapters.ProductListRecyclerViewAdapter;
import com.fred.inventory.utils.StringUtils;
import com.fred.inventory.utils.binding.Observable;
import com.fred.inventory.utils.binding.Observer;
import com.fred.inventory.utils.binding.widgets.OneTimeTextWatcher;
import com.fred.inventory.utils.path.PathManager;
import com.fred.inventory.utils.rx.RxSubscriptionPool;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;
import timber.log.Timber;

public class ProductListViewModelImpl
    implements ProductListViewModel, ProductListRecyclerViewAdapter.OnProductDeletedListener,
    ProductListRecyclerViewAdapter.OnItemClickListener {
  private final ObservableField<String> productListName = new ObservableField<>();
  private final OneTimeTextWatcher productNameTextWatcher = new OneTimeTextWatcher(productListName);
  private final GetProductListUseCase getProductListUseCase;
  private final SaveProductListInLocalStorageUseCase saveProductListInLocalStorageUseCase;
  private final SchedulerTransformer transformer;
  private final RxSubscriptionPool rxSubscriptionPool;
  private final ObservableInt listVisibility = new ObservableInt(View.GONE);
  private final ObservableInt emptyListVisibility = new ObservableInt(View.GONE);
  private final ObservableInt progressBarVisibility = new ObservableInt(View.GONE);
  private final Observable<String> productListIdObservable = Observable.create();
  private final PathManager pathManager;
  private final ProductListRecyclerViewAdapter adapter;
  private final GetProductInfoFromCodeUseCase getProductInfoFromCodeUseCase;

  private ProductList productList;
  private OnScanBarCodeButtonClickListener scanBarCodeButtonClickListener;

  @Inject public ProductListViewModelImpl(GetProductListUseCase getProductListUseCase,
      SaveProductListInLocalStorageUseCase saveProductListInLocalStorageUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer transformer,
      RxSubscriptionPool rxSubscriptionPool, PathManager pathManager,
      ProductListRecyclerViewAdapter adapter,
      GetProductInfoFromCodeUseCase getProductInfoFromCodeUseCase) {
    this.getProductListUseCase = getProductListUseCase;
    this.saveProductListInLocalStorageUseCase = saveProductListInLocalStorageUseCase;
    this.transformer = transformer;
    this.rxSubscriptionPool = rxSubscriptionPool;
    this.pathManager = pathManager;
    this.adapter = adapter;
    this.getProductInfoFromCodeUseCase = getProductInfoFromCodeUseCase;
  }

  @Override public void forProductList(String id) {
    productListIdObservable.set(id);
  }

  @Override public void onActivityCreated() {
    adapter.setOnProductDeletedListener(this);
    adapter.setOnItemClickListener(this);

    if (StringUtils.isBlank(productListIdObservable.get())) {
      emptyListVisibility.set(View.VISIBLE);
      // TODO: put input in text view
      return;
    }

    progressBarVisibility.set(View.VISIBLE);
    queryForProductList();
  }

  @Override public void onDestroyView() {
    rxSubscriptionPool.unsubscribeFrom(getClass().getCanonicalName());
  }

  @Override public ObservableField<String> productListName() {
    return productListName;
  }

  @Override public TextWatcher productNameTextWatcher() {
    return productNameTextWatcher;
  }

  @Override public ObservableInt progressBarVisibility() {
    return progressBarVisibility;
  }

  private void queryForProductList() {
    Subscription subscription = getProductListUseCase.get(productListIdObservable.get())
        .compose(transformer.<ProductList>applySchedulers())
        .subscribe(new ProductListSubscriber());

    rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
  }

  @Override public void onDoneButtonClick(View view) {
    final String name = productListName.get();
    if (StringUtils.isBlank(name)) {
      // TODO: Set error
      return;
    }

    ProductList productList = createFromInput();
    productList.setProducts(adapter.getItems());

    Subscription subscription = saveProductListInLocalStorageUseCase.save(productList)
        .compose(transformer.<ProductList>applySchedulers())
        .subscribe(new Subscriber<ProductList>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {
            Timber.d(e, "Failed to save product list");
            // TODO: Update state with error
          }

          @Override public void onNext(ProductList productList) {
            pathManager.back();
          }
        });

    rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
  }

  @Override
  public void setOnScanBarCodeButtonClickListener(OnScanBarCodeButtonClickListener listener) {
    this.scanBarCodeButtonClickListener = listener;
  }

  @Override public void onScanBarCodeButtonClick(View view) {
    String name = productListName.get();
    if (StringUtils.isBlank(name)) {
      // TODO: Show error here
      return;
    }

    if (scanBarCodeButtonClickListener != null) {
      scanBarCodeButtonClickListener.onScanBarCodeButtonClicked();
    }
  }

  @Override public void onAddButtonClick(View view) {
    String name = productListName.get();
    if (StringUtils.isBlank(name)) {
      // TODO: Show error here
      return;
    }

    ProductList productList = createFromInput();
    productList.setProducts(adapter.getItems());

    Subscription subscription = saveProductListInLocalStorageUseCase.save(productList)
        .compose(transformer.<ProductList>applySchedulers())
        .subscribe(new Subscriber<ProductList>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {
            Timber.d(e, "Failed to save product list");
            // TODO: Update state with error
          }

          @Override public void onNext(ProductList productList) {
            ProductListViewModelImpl.this.productList = productList;
            goToItemScreen(productList, null);
          }
        });

    rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
  }

  @Override public ObservableInt emptyListVisibility() {
    return emptyListVisibility;
  }

  @Override public ObservableInt itemListVisibility() {
    return listVisibility;
  }

  @Override public void unbindProductListIdObserver(Observer<String> observer) {
    productListIdObservable.unbind(observer);
  }

  @Override public void bindProductListIdObserver(Observer<String> observer) {
    productListIdObservable.bind(observer);
  }

  @Override public RecyclerView.Adapter productListRecyclerViewAdapter() {
    return (RecyclerView.Adapter) adapter;
  }

  private ProductList createFromInput() {
    ProductList productList = new ProductList();
    productList.setId(productListIdObservable.get());
    productList.setName(productListName.get());
    return productList;
  }

  @Override public void onProductDeleted(Product product) {
    boolean hasItems = !adapter.getItems().isEmpty();
    emptyListVisibility.set(hasItems ? View.GONE : View.VISIBLE);
    listVisibility.set(hasItems ? View.VISIBLE : View.GONE);
  }

  @Override public void onItemClicked(Product product) {
    goToItemScreen(productList, product);
  }

  @Override public void onCodeScanned(String barcode) {
    Subscription subscription = getProductInfoFromCodeUseCase.info(barcode)
        .flatMap(new ZipProductListAndProduct())
        .compose(transformer.<ProductListProductCombo>applySchedulers())
        .subscribe(new Subscriber<ProductListProductCombo>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {
            Timber.e(e, "Failed to get the product info");
          }

          @Override public void onNext(ProductListProductCombo productListProductCombo) {
            ProductListViewModelImpl.this.productList = productListProductCombo.list;
            goToItemScreen(productListProductCombo.list, productListProductCombo.list.getProducts()
                .get(productListProductCombo.list.getProducts().size() - 1));
          }
        });
    rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
  }

  private void goToItemScreen(ProductList productList, Product product) {
    ItemScreen itemScreen;
    if (product == null) {
      itemScreen = ItemScreen.newInstance(productList.getId());
    } else {
      itemScreen = ItemScreen.newInstance(productList.getId(), product.getId());
    }
    productListIdObservable.set(productList.getId());
    pathManager.go(itemScreen, R.id.main_container);
  }

  private class ProductListSubscriber extends Subscriber<ProductList> {
    @Override public void onCompleted() {
      progressBarVisibility.set(View.GONE);
    }

    @Override public void onError(Throwable e) {
      progressBarVisibility.set(View.GONE);
      Timber.e(e, "Failed to get the product list");
    }

    @Override public void onNext(ProductList productList) {
      ProductListViewModelImpl.this.productList = productList;
      boolean hasItems = !productList.getProducts().isEmpty();
      emptyListVisibility.set(hasItems ? View.GONE : View.VISIBLE);
      listVisibility.set(hasItems ? View.VISIBLE : View.GONE);
      productListName.set(productList.getName());
      productListIdObservable.set(productList.getId());
      adapter.setData(productList.getProducts());
    }
  }

  private class ZipProductListAndProduct
      implements Func1<Product, rx.Observable<ProductListProductCombo>> {
    @Override public rx.Observable<ProductListProductCombo> call(Product product) {
      ProductList productList = createFromInput();
      productList.setProducts(adapter.getItems());
      productList.getProducts().add(product);
      return saveProductListInLocalStorageUseCase.save(productList)
          .zipWith(rx.Observable.just(product),
              new Func2<ProductList, Product, ProductListProductCombo>() {
                @Override
                public ProductListProductCombo call(ProductList productList, Product product) {
                  return new ProductListProductCombo(productList, product);
                }
              });
    }
  }

  private static class ProductListProductCombo {
    ProductList list;
    Product product;

    public ProductListProductCombo(ProductList list, Product product) {
      this.list = list;
      this.product = product;
    }
  }
}
