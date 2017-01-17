package com.fred.inventory.presentation.productlist.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
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
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import timber.log.Timber;

public class ProductListViewModelImpl
    implements ProductListViewModel, ProductListRecyclerViewAdapter.OnProductDeletedListener,
    ProductListRecyclerViewAdapter.OnItemClickListener {
  private static final int PRODUCT_LIST_NAME_VIEW = 0;
  private static final int SEARCH_EDIT_TEXT_VIEW = 1;
  private final ObservableField<String> productListName = new ObservableField<>();
  private final OneTimeTextWatcher productListNameTextWatcher =
      new OneTimeTextWatcher(productListName);
  private final ObservableInt toolbarDisplayedChild = new ObservableInt(0);
  private final GetProductListUseCase getProductListUseCase;
  private final SaveProductListInLocalStorageUseCase saveProductListInLocalStorageUseCase;
  private final SchedulerTransformer transformer;
  private final RxSubscriptionPool rxSubscriptionPool;
  private final ObservableInt listVisibility = new ObservableInt(View.GONE);
  private final ObservableInt emptyListVisibility = new ObservableInt(View.GONE);
  private final ObservableInt progressBarVisibility = new ObservableInt(View.GONE);
  private final ObservableInt recyclerViewScrollPosition = new ObservableInt(0);
  private final ObservableField<String> searchQuery = new ObservableField<>();
  private final Observable<Long> productListIdObservable = Observable.create();
  private final PathManager pathManager;
  private final ProductListRecyclerViewAdapter adapter;
  private final GetProductInfoFromCodeUseCase getProductInfoFromCodeUseCase;
  private final TextWatcher searchQueryTextWatcher = new TextWatcher() {
    @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override public void afterTextChanged(Editable editable) {
      String query = editable.toString();
      List<Product> filteredProducts = filter(productList.getProducts(), query);
      adapter.replaceAll(filteredProducts);
      recyclerViewScrollPosition.set(0);
    }
  };

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

  @Override public void forProductList(Long id) {
    productListIdObservable.set(id);
  }

  @Override public void onActivityCreated() {
    adapter.setOnProductDeletedListener(this);
    adapter.setOnItemClickListener(this);

    if (productListIdObservable.get() == null) {
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

  @Override public TextWatcher productListTextWatcher() {
    return productListNameTextWatcher;
  }

  @Override public ObservableInt progressBarVisibility() {
    return progressBarVisibility;
  }

  private void queryForProductList() {
    Subscription subscription = getProductListUseCase.get(productListIdObservable.get())
        .compose(transformer.applySchedulers())
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
        .compose(transformer.applySchedulers())
        .subscribe(value -> pathManager.back(), e -> Timber.d(e, "Failed to save product list"));

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
        .compose(transformer.applySchedulers())
        .subscribe(value -> {
          ProductListViewModelImpl.this.productList = value;
          goToItemScreen(value, null);
        }, e -> Timber.d(e, "Failed to save product list"));

    rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
  }

  @Override public ObservableInt emptyListVisibility() {
    return emptyListVisibility;
  }

  @Override public ObservableInt itemListVisibility() {
    return listVisibility;
  }

  @Override public ObservableInt toolBarDisplayedChild() {
    return toolbarDisplayedChild;
  }

  @Override public ObservableInt recyclerViewScrollPosition() {
    return recyclerViewScrollPosition;
  }

  @Override public void unbindProductListIdObserver(Observer<Long> observer) {
    productListIdObservable.unbind(observer);
  }

  @Override public void bindProductListIdObserver(Observer<Long> observer) {
    productListIdObservable.bind(observer);
  }

  @Override public RecyclerView.Adapter<?> productListRecyclerViewAdapter() {
    return (RecyclerView.Adapter<?>) adapter;
  }

  @Override public boolean onHomeButtonPressed() {
    if (toolbarDisplayedChild.get() == PRODUCT_LIST_NAME_VIEW) return false;
    searchQuery.set(""); // Clear search so entire list is shown
    toolbarDisplayedChild.set(PRODUCT_LIST_NAME_VIEW);
    return true;
  }

  @Override public ObservableField<String> searchQuery() {
    return searchQuery;
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

  @Override public TextWatcher searchQueryTextWatcher() {
    return searchQueryTextWatcher;
  }

  @Override public void onItemClicked(Product product) {
    goToItemScreen(productList, product);
  }

  @Override public void onCodeScanned(String barcode) {
    Subscription subscription = getProductInfoFromCodeUseCase.info(barcode)
        .flatMap(product -> {
            ProductList productList = createFromInput();
            productList.setProducts(adapter.getItems());
            productList.getProducts().add(product);
            return saveProductListInLocalStorageUseCase.save(productList);
        })
        .compose(transformer.applySchedulers())
        .subscribe(productList -> {
          ProductListViewModelImpl.this.productList = productList;
          goToItemScreen(productList,
              productList.getProducts().get(productList.getProducts().size() - 1));
        }, e -> Timber.e(e, "Failed to get the product info"));

    rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
  }

  @Override public void onSearchButtonClicked(View actionView) {
    toolbarDisplayedChild.set(SEARCH_EDIT_TEXT_VIEW);
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

  private List<Product> filter(List<Product> products, String query) {
    if (StringUtils.isBlank(query)) return products;

    List<Product> filteredProducts = new ArrayList<>();
    for (Product product : products) {
      if (product.getName().toLowerCase().contains(query.toLowerCase())) {
        filteredProducts.add(product);
      }
    }
    return filteredProducts;
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
      adapter.add(productList.getProducts());
    }
  }
}
