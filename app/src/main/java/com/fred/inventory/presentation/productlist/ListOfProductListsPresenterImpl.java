package com.fred.inventory.presentation.productlist;

import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.ListAllProductListsUseCase;
import com.fred.inventory.presentation.productlist.adapters.ListOfProductListsAdapter;
import com.fred.inventory.utils.rx.RxSubscriptionPool;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

public class ListOfProductListsPresenterImpl implements ListOfProductListsPresenter {
  private final ListOfProductListsView view;
  private final ListAllProductListsUseCase listAllProductListsUseCase;
  private final SchedulerTransformer ioToUiSchedulerTransformer;
  private final RxSubscriptionPool rxSubscriptionPool;
  private final ListOfProductListsAdapter adapter;

  @Inject public ListOfProductListsPresenterImpl(ListOfProductListsView view,
      ListAllProductListsUseCase listAllProductListsUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer ioToUiSchedulerTransformer,
      RxSubscriptionPool rxSubscriptionPool, ListOfProductListsAdapter adapter) {
    this.view = view;
    this.listAllProductListsUseCase = listAllProductListsUseCase;
    this.ioToUiSchedulerTransformer = ioToUiSchedulerTransformer;
    this.rxSubscriptionPool = rxSubscriptionPool;
    this.adapter = adapter;
  }

  @Override public void onAttachedToWindow() {
    Subscription subscription = listAllProductListsUseCase.list()
        .compose(ioToUiSchedulerTransformer.<List<ProductList>>applySchedulers())
        .subscribe(new ListOfProductListsSubscriber());

    rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
  }

  @Override public void onDetachedFromWindow() {
    rxSubscriptionPool.unsubscribeFrom(getClass().getCanonicalName());
  }

  @Override public void onAddButtonClicked() {
    view.notifyListenersOfAddButtonClick();
  }

  /**
   * Subscriber for the list of product lists
   */
  private class ListOfProductListsSubscriber extends Subscriber<List<ProductList>> {
    private List<ProductList> productLists;

    @Override public void onCompleted() {
      if (productLists == null || productLists.isEmpty()) {
        view.showEmptyView();
      } else {
        view.hideEmptyView();
      }
    }

    @Override public void onError(Throwable e) {
      Timber.d(e, "Failed to list all product lists from the app");
      view.displayListAllProductListsError();
    }

    @Override public void onNext(List<ProductList> productLists) {
      this.productLists = productLists;
      adapter.attachModel(productLists);
      view.setAdapter(adapter);
    }
  }
}
