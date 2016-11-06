package com.fred.inventory.presentation.productlist.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextWatcher;
import android.view.View;
import com.fred.inventory.R;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.GetProductListUseCase;
import com.fred.inventory.domain.usecases.SaveProductListInLocalStorageUseCase;
import com.fred.inventory.presentation.items.ItemScreen;
import com.fred.inventory.utils.StringUtils;
import com.fred.inventory.utils.binding.widgets.OneTimeTextWatcher;
import com.fred.inventory.utils.path.PathManager;
import com.fred.inventory.utils.rx.RxSubscriptionPool;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

public class ProductListViewModelImpl implements ProductListViewModel {
  private final ObservableField<String> productListName = new ObservableField<>();
  private final OneTimeTextWatcher productNameTextWatcher = new OneTimeTextWatcher(productListName);
  private final GetProductListUseCase getProductListUseCase;
  private final SaveProductListInLocalStorageUseCase saveProductListInLocalStorageUseCase;
  private final SchedulerTransformer transformer;
  private final RxSubscriptionPool rxSubscriptionPool;
  private final ObservableInt listVisibility = new ObservableInt(View.GONE);
  private final ObservableInt emptyListVisibility = new ObservableInt(View.VISIBLE);
  private final PathManager pathManager;
  private String productListId;

  @Inject public ProductListViewModelImpl(GetProductListUseCase getProductListUseCase,
      SaveProductListInLocalStorageUseCase saveProductListInLocalStorageUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer transformer,
      RxSubscriptionPool rxSubscriptionPool, PathManager pathManager) {
    this.getProductListUseCase = getProductListUseCase;
    this.saveProductListInLocalStorageUseCase = saveProductListInLocalStorageUseCase;
    this.transformer = transformer;
    this.rxSubscriptionPool = rxSubscriptionPool;
    this.pathManager = pathManager;
  }

  @Override public void forProductList(String id) {
    this.productListId = id;
  }

  @Override public void onAttachedToWindow() {
    if (StringUtils.isBlank(productListId)) {
      // TODO: put input in text view
      return;
    }

    queryForProductList();
  }

  @Override public void onDetachedFromWindow() {
    rxSubscriptionPool.unsubscribeFrom(getClass().getCanonicalName());
  }

  @Override public ObservableField<String> productListName() {
    return productListName;
  }

  @Override public TextWatcher productNameTextWatcher() {
    return productNameTextWatcher;
  }

  private void queryForProductList() {
    Subscription subscription = getProductListUseCase.get(productListId)
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

  @Override public void onAddButtonClick(View view) {
    String name = productListName.get();
    if (StringUtils.isBlank(name)) {
      // TODO: Show error here
      return;
    }

    ProductList productList = createFromInput();

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
            ItemScreen itemScreen = ItemScreen.newInstance(productList.getId());
            pathManager.go(itemScreen, R.id.main_container);
          }
        });

    rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
  }

  private ProductList createFromInput() {
    ProductList productList = new ProductList();
    productList.setId(productListId);
    productList.setName(productListName.get());
    return productList;
  }

  @Override public ObservableInt emptyListVisibility() {
    return emptyListVisibility;
  }

  @Override public ObservableInt itemListVisibility() {
    return listVisibility;
  }

  public class ProductListSubscriber extends Subscriber<ProductList> {
    @Override public void onCompleted() {

    }

    @Override public void onError(Throwable e) {

    }

    @Override public void onNext(ProductList productList) {
      ProductListViewModelImpl.this.productListId = productList.getId();

      emptyListVisibility.set(View.GONE);
      listVisibility.set(View.VISIBLE);

      //TODO: add list and adapter shit

      productListName.set(productList.getName());
    }
  }
}
