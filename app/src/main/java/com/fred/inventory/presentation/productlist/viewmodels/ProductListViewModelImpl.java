package com.fred.inventory.presentation.productlist.viewmodels;

import android.text.TextWatcher;
import android.view.View;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.GetProductListUseCase;
import com.fred.inventory.domain.usecases.SaveProductListInLocalStorageUseCase;
import com.fred.inventory.presentation.productlist.models.Error;
import com.fred.inventory.presentation.productlist.models.ImmutableProductListScreenState;
import com.fred.inventory.presentation.productlist.models.ProductListScreenState;
import com.fred.inventory.utils.StringUtils;
import com.fred.inventory.utils.binding.Observable;
import com.fred.inventory.utils.binding.Observer;
import com.fred.inventory.utils.binding.widgets.ObservableTextWatcher;
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
  private final ObservableTextWatcher productNameTextWatcher = ObservableTextWatcher.create();
  private final GetProductListUseCase getProductListUseCase;
  private final SaveProductListInLocalStorageUseCase saveProductListInLocalStorageUseCase;
  private final SchedulerTransformer transformer;
  private final RxSubscriptionPool rxSubscriptionPool;
  private final View.OnClickListener doneButtonClickListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      final String name = productNameTextWatcher.get();
      if (StringUtils.isBlank(name)) {
        emptyListNameError();
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
  };

  private final View.OnClickListener addButtonClickListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      String name = productNameTextWatcher.get();
      if (StringUtils.isBlank(name)) {
        emptyListNameError();
        return;
      }

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
                  showAddProductScreenObservable.set(productList.getId());
                }
              });

      rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
    }
  };
  private String productListId;

  @Inject public ProductListViewModelImpl(GetProductListUseCase getProductListUseCase,
      SaveProductListInLocalStorageUseCase saveProductListInLocalStorageUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer transformer,
      RxSubscriptionPool rxSubscriptionPool) {
    this.getProductListUseCase = getProductListUseCase;
    this.saveProductListInLocalStorageUseCase = saveProductListInLocalStorageUseCase;
    this.transformer = transformer;
    this.rxSubscriptionPool = rxSubscriptionPool;
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

  @Override public void bindProductNameObserver(Observer<String> observer) {
    productNameTextWatcher.bind(observer);
  }

  @Override public void unbindProductNameObserver(Observer<String> observer) {
    productNameTextWatcher.unbind(observer);
  }

  @Override public void bindShowAddProductScreenObservable(Observer<String> observer) {
    showAddProductScreenObservable.bind(observer);
  }

  @Override public void unbindShowAddProductScreenObservable(Observer<String> observer) {
    showAddProductScreenObservable.unbind(observer);
  }

  @Override public View.OnClickListener doneButtonClickListener() {
    return doneButtonClickListener;
  }

  @Override public View.OnClickListener addButtonClickListener() {
    return addButtonClickListener;
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

  public class ProductListSubscriber extends Subscriber<ProductList> {
    @Override public void onCompleted() {

    }

    @Override public void onError(Throwable e) {

    }

    @Override public void onNext(ProductList productList) {
      ProductListViewModelImpl.this.productListId = productListId;

      ProductListScreenState newState =
          ImmutableProductListScreenState.builder().productList(productList).build();

      screenStateObservable.set(newState);
      productNameTextWatcher.set(productList.getName());
    }
  }

  private void emptyListNameError() {
    ProductListScreenState newState = ImmutableProductListScreenState.builder()
        .error(Error.EMPTY_PRODUCT_LIST_NAME)
        .productList(screenStateObservable.get().productList())
        .build();
    screenStateObservable.set(newState);
  }
}
