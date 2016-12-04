package com.fred.inventory.presentation.listofproducts.viewmodels;

import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.fred.inventory.R;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.ListAllProductListsUseCase;
import com.fred.inventory.presentation.listofproducts.adapters.ListOfProductListsAdapter;
import com.fred.inventory.presentation.productlist.ProductListScreen;
import com.fred.inventory.utils.path.PathManager;
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
      final ProductListScreen screen = ProductListScreen.newInstance();
      pathManager.go(screen, R.id.main_container);
    }
  };
  private final ListAllProductListsUseCase listAllProductListsUseCase;
  private final SchedulerTransformer ioToUiSchedulerTransformer;
  private final RxSubscriptionPool rxSubscriptionPool;
  private final ListOfProductListsAdapter adapter;
  private final PathManager pathManager;

  @Inject
  public ListOfProductListsViewModelImpl(ListAllProductListsUseCase listAllProductListsUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer ioToUiSchedulerTransformer,
      RxSubscriptionPool rxSubscriptionPool, ListOfProductListsAdapter adapter,
      PathManager pathManager) {
    this.listAllProductListsUseCase = listAllProductListsUseCase;
    this.ioToUiSchedulerTransformer = ioToUiSchedulerTransformer;
    this.rxSubscriptionPool = rxSubscriptionPool;
    this.adapter = adapter;
    this.pathManager = pathManager;
  }

  @Override public void onResume() {
    Subscription subscription = retrieveData();
    rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
  }

  @Override public void onPause() {
    rxSubscriptionPool.unsubscribeFrom(getClass().getCanonicalName());
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
   * Get the data for the view
   *
   * @return The subscription for the rx operation
   */
  public Subscription retrieveData() {
    return listAllProductListsUseCase.list()
        .compose(ioToUiSchedulerTransformer.<List<ProductList>>applySchedulers())
        .subscribe(new ListOfProductListsSubscriber());
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
      adapter.setData(productLists);
    }
  }
}
