package com.fred.inventory.presentation.supplies.viewmodels;

import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.fred.inventory.R;
import com.fred.inventory.data.firebase.models.SuppliesList;
import com.fred.inventory.domain.usecases.GetUserSuppliesListDatabaseReferenceUseCase;
import com.fred.inventory.presentation.supplies.adapters.SuppliesAdapter;
import com.fred.inventory.presentation.supplieslist.SuppliesListScreen;
import com.fred.inventory.utils.path.PathManager;
import com.fred.inventory.utils.rx.RxSubscriptionPool;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

public class SuppliesViewModelImpl
    implements SuppliesViewModel, SuppliesAdapter.OnItemClickListener {
  private final ObservableInt emptyViewVisibility = new ObservableInt(View.GONE);
  private final ObservableInt listViewVisibility = new ObservableInt(View.GONE);
  private final ObservableInt loadingVisibility = new ObservableInt(View.GONE);
  private final View.OnClickListener addButtonClickListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      final SuppliesListScreen screen = SuppliesListScreen.newInstance();
      pathManager.go(screen, R.id.main_container);
    }
  };
  private final GetUserSuppliesListDatabaseReferenceUseCase
      getUserSuppliesListDatabaseReferenceUseCase;
  private final SchedulerTransformer ioToUiSchedulerTransformer;
  private final RxSubscriptionPool rxSubscriptionPool;
  private final SuppliesAdapter adapter;
  private final PathManager pathManager;
  private final ChildEventListener supplyListsChildEventListener = new ChildEventListener() {
    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
      emptyViewVisibility.set(View.GONE);
      listViewVisibility.set(View.VISIBLE);

      SuppliesList suppliesList = SuppliesList.create(dataSnapshot);
      adapter.add(suppliesList);
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
      SuppliesList suppliesList = SuppliesList.create(dataSnapshot);
      adapter.remove(suppliesList);

      if (adapter.getItemCount() == 0) {
        emptyViewVisibility.set(View.VISIBLE);
        listViewVisibility.set(View.GONE);
      } else {
        emptyViewVisibility.set(View.GONE);
        listViewVisibility.set(View.VISIBLE);
      }
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override public void onCancelled(DatabaseError databaseError) {

    }
  };
  private DatabaseReference databaseReference;

  @Inject public SuppliesViewModelImpl(
      GetUserSuppliesListDatabaseReferenceUseCase getUserSuppliesListDatabaseReferenceUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer ioToUiSchedulerTransformer,
      RxSubscriptionPool rxSubscriptionPool, SuppliesAdapter adapter, PathManager pathManager) {
    this.getUserSuppliesListDatabaseReferenceUseCase = getUserSuppliesListDatabaseReferenceUseCase;
    this.ioToUiSchedulerTransformer = ioToUiSchedulerTransformer;
    this.rxSubscriptionPool = rxSubscriptionPool;
    this.adapter = adapter;
    this.pathManager = pathManager;
  }

  @Override public void onResume() {
    adapter.setOnItemClickListener(this);

    loadingVisibility.set(View.VISIBLE);
    Subscription subscription = retrieveData();
    rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
  }

  @Override public void onPause() {
    rxSubscriptionPool.unsubscribeFrom(getClass().getCanonicalName());
    if (databaseReference != null) {
      databaseReference.removeEventListener(supplyListsChildEventListener);
    }
  }

  @Override public RecyclerView.Adapter<?> adapter() {
    return (RecyclerView.Adapter<?>) adapter;
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

  @Override public void onItemClicked(SuppliesList suppliesList) {
    final SuppliesListScreen screen = SuppliesListScreen.newInstance(suppliesList.uuid());
    pathManager.go(screen, R.id.main_container);
  }

  @Override public ObservableInt loadingVisibility() {
    return loadingVisibility;
  }

  /**
   * Get the data for the view
   *
   * @return The subscription for the rx operation
   */
  private Subscription retrieveData() {
    return getUserSuppliesListDatabaseReferenceUseCase.getUserSuppliesListDatabaseReference()
        .compose(ioToUiSchedulerTransformer.applySchedulers())
        .subscribe(new ListOfProductListsSubscriber());
  }

  /**
   * Subscriber for the list of product lists
   */
  private class ListOfProductListsSubscriber extends Subscriber<DatabaseReference> {
    @Override public void onCompleted() {
    }

    @Override public void onError(Throwable e) {
      loadingVisibility.set(View.GONE);
      Timber.d(e, "Failed to list all product lists from the app");
      // TODO: Show error
    }

    @Override public void onNext(DatabaseReference databaseReference) {
      SuppliesViewModelImpl.this.databaseReference = databaseReference;
      databaseReference.addChildEventListener(supplyListsChildEventListener);
      databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override public void onDataChange(DataSnapshot dataSnapshot) {
          boolean isEmpty = dataSnapshot.getChildrenCount() == 0;
          loadingVisibility.set(View.GONE);
          emptyViewVisibility.set(isEmpty ? View.VISIBLE : View.GONE);
          listViewVisibility.set(isEmpty ? View.GONE : View.VISIBLE);
        }

        @Override public void onCancelled(DatabaseError databaseError) {

        }
      });
    }
  }
}
