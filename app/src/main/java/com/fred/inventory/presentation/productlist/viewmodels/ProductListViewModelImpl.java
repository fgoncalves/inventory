package com.fred.inventory.presentation.productlist.viewmodels;

import android.view.View;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.GetProductListUseCase;
import com.fred.inventory.domain.usecases.SaveProductListInLocalStorageUseCase;
import com.fred.inventory.utils.StringUtils;
import com.fred.inventory.utils.binding.Observable;
import com.fred.inventory.utils.binding.Observer;
import com.fred.inventory.utils.rx.RxSubscriptionPool;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;

public class ProductListViewModelImpl implements ProductListViewModel {
  private final Observable<Integer> emptyListVisibilityObservable = Observable.create();
  private final Observable<Boolean> showKeyboardObservable = Observable.create();
  private final GetProductListUseCase getProductListUseCase;
  private final SaveProductListInLocalStorageUseCase saveProductListInLocalStorageUseCase;
  private final SchedulerTransformer transformer;
  private final RxSubscriptionPool rxSubscriptionPool;
  private String productListId;
  private ProductList productList;

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
      emptyListVisibilityObservable.set(View.VISIBLE);
      showKeyboardObservable.set(true);
      return;
    }

    queryForProductList();
  }

  @Override public void onDetachedFromWindow() {
    rxSubscriptionPool.unsubscribeFrom(getClass().getCanonicalName());
  }

  @Override public void bindEmptyViewVisibilityObserver(Observer<Integer> observer) {
    emptyListVisibilityObservable.bind(observer);
  }

  @Override public void bindShowKeyboardObserver(Observer<Boolean> observer) {
    showKeyboardObservable.bind(observer);
  }

  @Override public void unbindEmptyViewVisibilityObserver(Observer<Integer> observer) {
    emptyListVisibilityObservable.unbind(observer);
  }

  @Override public void unbindShowKeyboardObserver(Observer<Boolean> observer) {
    showKeyboardObservable.unbind(observer);
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

    }
  }
}
