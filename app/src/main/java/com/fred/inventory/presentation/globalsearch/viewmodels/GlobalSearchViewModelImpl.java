package com.fred.inventory.presentation.globalsearch.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import com.fred.inventory.R;
import com.fred.inventory.data.firebase.models.SuppliesList;
import com.fred.inventory.data.firebase.models.SupplyItem;
import com.fred.inventory.domain.models.GlobalSearchResult;
import com.fred.inventory.domain.usecases.GetUserSuppliesListDatabaseReferenceUseCase;
import com.fred.inventory.presentation.globalsearch.adapters.GlobalSearchRecyclerViewAdapter;
import com.fred.inventory.presentation.productlist.ProductListScreen;
import com.fred.inventory.utils.binding.widgets.OneTimeTextWatcher;
import com.fred.inventory.utils.path.PathManager;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import timber.log.Timber;

public class GlobalSearchViewModelImpl implements GlobalSearchViewModel {
  private final ObservableField<String> searchQuery = new ObservableField<>("");
  private final TextWatcher searchQueryTextWatcher = new OneTimeTextWatcher(searchQuery);
  private final ObservableInt progressBarVisibility = new ObservableInt(View.GONE);
  private final ObservableInt listVisibility = new ObservableInt(View.GONE);
  private final GlobalSearchRecyclerViewAdapter adapter;
  private final GetUserSuppliesListDatabaseReferenceUseCase
      getUserSuppliesListDatabaseReferenceUseCase;
  private final SchedulerTransformer transformer;
  private final PathManager pathManager;
  private DatabaseReference databaseReference;
  private ChildEventListener supplyListsEventListener = new ChildEventListener() {
    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
      SuppliesList suppliesList = SuppliesList.create(dataSnapshot);
      if (suppliesList != null) allSupplies.add(suppliesList);
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
      SuppliesList suppliesList = SuppliesList.create(dataSnapshot);
      if (suppliesList != null) allSupplies.remove(suppliesList);
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override public void onCancelled(DatabaseError databaseError) {

    }
  };

  private final List<SuppliesList> allSupplies = new ArrayList<>();

  @Inject public GlobalSearchViewModelImpl(GlobalSearchRecyclerViewAdapter adapter,
      GetUserSuppliesListDatabaseReferenceUseCase getUserSuppliesListDatabaseReferenceUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer transformer, PathManager pathManager) {
    this.adapter = adapter;
    this.getUserSuppliesListDatabaseReferenceUseCase = getUserSuppliesListDatabaseReferenceUseCase;
    this.transformer = transformer;
    this.pathManager = pathManager;
  }

  @Override public ObservableField<String> searchQuery() {
    return searchQuery;
  }

  @Override public TextWatcher searchQueryTextWatcher() {
    return searchQueryTextWatcher;
  }

  @Override public RecyclerView.Adapter<?> globalSearchRecyclerViewAdapter() {
    return (RecyclerView.Adapter<?>) adapter;
  }

  @Override public void onResume() {
    getUserSuppliesListDatabaseReferenceUseCase.getUserSuppliesListDatabaseReference()
        .compose(transformer.applySchedulers())
        .subscribe(databaseReference -> {
          this.databaseReference = databaseReference;
          databaseReference.addChildEventListener(supplyListsEventListener);
        }, throwable -> Timber.e(throwable, "Failed to get supply lists database reference"));
    adapter.setOnItemClickedListener(result -> {
      final ProductListScreen screen = ProductListScreen.newInstance(result.suppliesList().uuid());
      pathManager.back();
      pathManager.go(screen, R.id.main_container);
    });
  }

  @Override public void onPause() {
    adapter.setOnItemClickedListener(null);
    databaseReference.removeEventListener(supplyListsEventListener);
  }

  @Override public ObservableInt progressBarVisibility() {
    return progressBarVisibility;
  }

  @Override public ObservableInt listVisibility() {
    return listVisibility;
  }

  @Override public void onSearchButtonClicked(MenuItem item) {
    progressBarVisibility.set(View.VISIBLE);
    listVisibility.set(View.GONE);
    search(searchQuery.get()).compose(transformer.applySchedulers()).subscribe(results -> {
      progressBarVisibility.set(View.GONE);
      listVisibility.set(View.VISIBLE);
      adapter.replaceAll(results);
    }, throwable -> Timber.e(throwable, "Failed to search globally"));
  }

  /**
   * Go through all the suppplies and check if we have the given products
   *
   * @param query The query to check
   * @return An observable with the desired results
   */
  private Observable<List<GlobalSearchResult>> search(String query) {
    return Observable.fromCallable(() -> {
      List<GlobalSearchResult> results = new ArrayList<>();
      for (SuppliesList suppliesList : allSupplies) {
        if (suppliesList.items() == null) continue;
        for (SupplyItem supplyItem : suppliesList.items().values()) {
          if (supplyItem.name().toLowerCase().contains(query.toLowerCase())) {
            results.add(GlobalSearchResult.create(suppliesList, supplyItem));
          }
        }
      }
      return results;
    });
  }
}
