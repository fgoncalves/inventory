package com.fred.inventory.presentation.listofproducts.viewmodels;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.ListAllProductListsUseCase;
import com.fred.inventory.presentation.listofproducts.adapters.ListOfProductListsAdapter;
import com.fred.inventory.presentation.listofproducts.views.ListOfProductListsView;
import com.fred.inventory.utils.binding.Observable;
import com.fred.inventory.utils.binding.Observer;
import com.fred.inventory.utils.rx.RxSubscriptionPool;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import rx.subjects.PublishSubject;
import timber.log.Timber;

public class ListOfProductListsViewModelImpl implements ListOfProductListsViewModel {
  private final Observable<Integer> emptyViewVisibilityObservable = Observable.create();
  private final Observable<Void> showErrorMessageObservable = Observable.create();
  private final View.OnClickListener addButtonClickListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      interactions.onNext(ListOfProductListsView.ListOfProductListsEvent.ADD_BUTTON_CLICKED);
      interactions.onCompleted();
    }
  };
  private final ListAllProductListsUseCase listAllProductListsUseCase;
  private final SchedulerTransformer ioToUiSchedulerTransformer;
  private final RxSubscriptionPool rxSubscriptionPool;
  private final ListOfProductListsAdapter adapter;
  private final PublishSubject<ListOfProductListsView.ListOfProductListsEvent> interactions =
      PublishSubject.create();

  @Inject
  public ListOfProductListsViewModelImpl(ListAllProductListsUseCase listAllProductListsUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer ioToUiSchedulerTransformer,
      RxSubscriptionPool rxSubscriptionPool, ListOfProductListsAdapter adapter) {
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

  @Override public void bindEmptyViewVisibilityObserver(Observer<Integer> observer) {
    emptyViewVisibilityObservable.bind(observer);
  }

  @Override public void unbindEmptyViewVisibilityObserver(Observer<Integer> observer) {
    emptyViewVisibilityObservable.unbind(observer);
  }

  @Override public void bindShowErrorMessageObserver(Observer<Void> observer) {
    showErrorMessageObservable.bind(observer);
  }

  @Override public void unbindShowErrorMessageObserver(Observer<Void> observer) {
    showErrorMessageObservable.unbind(observer);
  }

  @Override public RecyclerView.Adapter adapter() {
    return (RecyclerView.Adapter) adapter;
  }

  @Override public rx.Observable<ListOfProductListsView.ListOfProductListsEvent> interactions() {
    return interactions.asObservable();
  }

  @Override public View.OnClickListener addButtonClickListener() {
    return addButtonClickListener;
  }

  /**
   * Subscriber for the list of product lists
   */
  private class ListOfProductListsSubscriber extends Subscriber<List<ProductList>> {
    private List<ProductList> productLists;

    @Override public void onCompleted() {
      if (productLists == null || productLists.isEmpty()) {
        emptyViewVisibilityObservable.set(View.VISIBLE);
      } else {
        emptyViewVisibilityObservable.set(View.GONE);
      }
    }

    @Override public void onError(Throwable e) {
      Timber.d(e, "Failed to list all product lists from the app");
      showErrorMessageObservable.set(null);
    }

    @Override public void onNext(List<ProductList> productLists) {
      this.productLists = productLists;
      adapter.attachModel(productLists);
      adapter.onNewData();
    }
  }
}
