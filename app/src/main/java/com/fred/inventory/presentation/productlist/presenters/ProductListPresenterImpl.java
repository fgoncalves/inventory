package com.fred.inventory.presentation.productlist.presenters;

import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.GetProductListUseCase;
import com.fred.inventory.presentation.productlist.views.ProductListView;
import com.fred.inventory.utils.StringUtils;
import com.fred.inventory.utils.rx.RxSubscriptionPool;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;

public class ProductListPresenterImpl implements ProductListPresenter {
  private final ProductListView view;
  private final GetProductListUseCase getProductListUseCase;
  private final SchedulerTransformer transformer;
  private final RxSubscriptionPool rxSubscriptionPool;

  @Inject
  public ProductListPresenterImpl(ProductListView view, GetProductListUseCase getProductListUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer transformer,
      RxSubscriptionPool rxSubscriptionPool) {
    this.view = view;
    this.getProductListUseCase = getProductListUseCase;
    this.transformer = transformer;
    this.rxSubscriptionPool = rxSubscriptionPool;
  }

  @Override public void onAttachedToWindow(String productListId) {
    if (StringUtils.isBlank(productListId)) return;

    Subscription subscription = getProductListUseCase.get(productListId)
        .compose(transformer.<ProductList>applySchedulers())
        .subscribe(new GetProductListSubscriber());

    rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
  }

  @Override public void onDetachedFromWindow() {
    rxSubscriptionPool.unsubscribeFrom(getClass().getCanonicalName());
  }

  private void displayProductList(ProductList productList) {
    view.displayProductListName(productList.getName());
  }

  private class GetProductListSubscriber extends Subscriber<ProductList> {

    private boolean isEmpty = true;

    @Override public void onCompleted() {
      if (isEmpty) view.showEmptyProductList();
    }

    @Override public void onError(Throwable e) {

    }

    @Override public void onNext(ProductList productList) {
      isEmpty = false;
      view.hideEmptyProductList();
      displayProductList(productList);
    }
  }
}
