package com.fred.inventory.presentation.listofproducts.viewmodels;

import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.ListAllProductListsUseCase;
import com.fred.inventory.presentation.listofproducts.adapters.ListOfProductListsAdapter;
import com.fred.inventory.utils.rx.RxSubscriptionPool;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

public class ListOfProductListsViewModelImpl implements ListOfProductListsViewModel {
  private final ObservableInt emptyViewVisibility = new ObservableInt(View.VISIBLE);
  private final ObservableInt listViewVisibility = new ObservableInt(View.GONE);
  private final View.OnClickListener addButtonClickListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      //TODO: start add product
    }
  };
  private final ListAllProductListsUseCase listAllProductListsUseCase;
  private final SchedulerTransformer ioToUiSchedulerTransformer;
  private final RxSubscriptionPool rxSubscriptionPool;
  private final ListOfProductListsAdapter adapter;

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

  @Override public RecyclerView.Adapter adapter() {
    return (RecyclerView.Adapter) adapter;
  }

  @Override public View.OnClickListener addButtonClickListener() {
    return addButtonClickListener;
  }

  @Override public ObservableInt emptyViewVisibilityObservable() {
    return emptyViewVisibility;
  }

  @Override public ObservableInt listVisibilityObservable() {
    return listViewVisibility;
  }

  /**
   * Subscriber for the list of product lists
   */
  private class ListOfProductListsSubscriber extends Subscriber<List<ProductList>> {
    private List<ProductList> productLists;

    @Override public void onCompleted() {
      if (productLists == null || productLists.isEmpty()) {
        emptyViewVisibility.set(View.VISIBLE);
        listViewVisibility.set(View.GONE);
      } else {
        emptyViewVisibility.set(View.GONE);
        listViewVisibility.set(View.VISIBLE);
      }
    }

    @Override public void onError(Throwable e) {
      Timber.d(e, "Failed to list all product lists from the app");
      // TODO: Show error
    }

    @Override public void onNext(List<ProductList> productLists) {
      this.productLists = productLists;
      adapter.attachModel(productLists);
      adapter.onNewData();
    }
  }
}
