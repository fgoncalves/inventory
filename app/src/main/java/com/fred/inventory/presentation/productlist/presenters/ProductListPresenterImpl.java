package com.fred.inventory.presentation.productlist.presenters;

import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.GetProductListUseCase;
import com.fred.inventory.domain.usecases.SaveProductListInLocalStorageUseCase;
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
  private final SaveProductListInLocalStorageUseCase saveProductListInLocalStorageUseCase;
  private final SchedulerTransformer transformer;
  private final RxSubscriptionPool rxSubscriptionPool;
  private String productListId;
  private ProductList productList;

  @Inject
  public ProductListPresenterImpl(ProductListView view, GetProductListUseCase getProductListUseCase,
      SaveProductListInLocalStorageUseCase saveProductListInLocalStorageUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer transformer,
      RxSubscriptionPool rxSubscriptionPool) {
    this.view = view;
    this.getProductListUseCase = getProductListUseCase;
    this.saveProductListInLocalStorageUseCase = saveProductListInLocalStorageUseCase;
    this.transformer = transformer;
    this.rxSubscriptionPool = rxSubscriptionPool;
  }

  @Override public void onAttachedToWindow() {
    initializeModel();

    if (StringUtils.isBlank(productListId)) {
      view.showEmptyProductList();
      view.showKeyboardOnProductListName();
      return;
    }

    Subscription subscription = getProductListUseCase.get(productListId)
        .compose(transformer.<ProductList>applySchedulers())
        .subscribe(new GetProductListSubscriber());

    rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
  }

  @Override public void onDetachedFromWindow() {
    rxSubscriptionPool.unsubscribeFrom(getClass().getCanonicalName());
  }

  @Override public void forProductList(String productListId) {
    this.productListId = productListId;
  }

  @Override public void onDoneButtonClicked() {
    String name = view.getProductListName();
    if (StringUtils.isBlank(name)) {
      view.showEmptyProductListNameErrorMessage();
      return;
    }

    productList.setName(name);
    Subscription subscription = saveProductListInLocalStorageUseCase.save(productList)
        .compose(transformer.<ProductList>applySchedulers())
        .subscribe(new Subscriber<ProductList>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {

          }

          @Override public void onNext(ProductList productList) {
            view.hideKeyboard();
            view.doDismiss();
          }
        });

    rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
  }

  private void initializeModel() {
    productList = new ProductList();
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
      ProductListPresenterImpl.this.productList = productList;
      view.hideEmptyProductList();
      displayProductList(productList);
    }
  }
}
