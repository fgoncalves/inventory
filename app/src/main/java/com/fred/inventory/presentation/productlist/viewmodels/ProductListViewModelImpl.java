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
import com.fred.inventory.presentation.productlist.models.ImmutableProductListScreenState;
import com.fred.inventory.presentation.productlist.models.ProductListScreenState;
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
import timber.log.Timber;

public class ProductListViewModelImpl implements ProductListViewModel {
  private final Observable<ProductListScreenState> screenStateObservable = Observable.create();
  private final Observable<String> showAddProductScreenObservable = Observable.create();
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
      ProductListScreenState newState = ImmutableProductListScreenState.builder()
          .emptyViewVisibility(View.VISIBLE)
          .showKeyboard(true)
          .productList(new ProductList())
          .build();
      screenStateObservable.set(newState);
      return;
    }

    queryForProductList();
  }

  @Override public void onDetachedFromWindow() {
    rxSubscriptionPool.unsubscribeFrom(getClass().getCanonicalName());
  }

  @Override
  public void bindProductListScreenStateObserver(Observer<ProductListScreenState> observer) {
    screenStateObservable.bind(observer);
  }

  @Override
  public void unbindProductListScreenStateObserver(Observer<ProductListScreenState> observer) {
    screenStateObservable.unbind(observer);
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

    ProductListScreenState currentState = screenStateObservable.get();
    currentState.productList().setName(name);
    Subscription subscription =
        saveProductListInLocalStorageUseCase.save(currentState.productList())
            .compose(transformer.<ProductList>applySchedulers())
            .subscribe(new Subscriber<ProductList>() {
              @Override public void onCompleted() {

              }

              @Override public void onError(Throwable e) {
                Timber.d(e, "Failed to save product list");
                // TODO: Update state with error
              }

              @Override public void onNext(ProductList productList) {
                ProductListScreenState newState = ImmutableProductListScreenState.builder()
                    .dismissed(true)
                    .productList(productList)
                    .build();
                screenStateObservable.set(newState);
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

    // TODO: Obviously current list is null
    ProductListScreenState currentState = screenStateObservable.get();
    currentState.productList().setName(name);
    ProductListScreenState newState = ImmutableProductListScreenState.builder()
        .showKeyboard(false)
        .productList(currentState.productList())
        .build();
    screenStateObservable.set(newState);

    Subscription subscription =
        saveProductListInLocalStorageUseCase.save(currentState.productList())
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
