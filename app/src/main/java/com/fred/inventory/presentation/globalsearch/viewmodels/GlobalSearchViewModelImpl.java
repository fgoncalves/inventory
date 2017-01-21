package com.fred.inventory.presentation.globalsearch.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import com.fred.inventory.R;
import com.fred.inventory.domain.usecases.SearchForProductUseCase;
import com.fred.inventory.presentation.globalsearch.adapters.GlobalSearchRecyclerViewAdapter;
import com.fred.inventory.presentation.productlist.ProductListScreen;
import com.fred.inventory.utils.binding.widgets.OneTimeTextWatcher;
import com.fred.inventory.utils.path.PathManager;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import javax.inject.Inject;

public class GlobalSearchViewModelImpl implements GlobalSearchViewModel {
  private final ObservableField<String> searchQuery = new ObservableField<>("");
  private final TextWatcher searchQueryTextWatcher = new OneTimeTextWatcher(searchQuery);
  private final ObservableInt progressBarVisibility = new ObservableInt(View.GONE);
  private final ObservableInt listVisibility = new ObservableInt(View.GONE);
  private final GlobalSearchRecyclerViewAdapter adapter;
  private final SearchForProductUseCase searchForProductUseCase;
  private final SchedulerTransformer transformer;
  private final PathManager pathManager;

  @Inject public GlobalSearchViewModelImpl(GlobalSearchRecyclerViewAdapter adapter,
      SearchForProductUseCase searchForProductUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer transformer, PathManager pathManager) {
    this.adapter = adapter;
    this.searchForProductUseCase = searchForProductUseCase;
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
    adapter.setOnItemClickedListener(result -> {
      final ProductListScreen screen = ProductListScreen.newInstance(result.getProductListId());
      pathManager.go(screen, R.id.main_container);
    });
  }

  @Override public void onPause() {
    adapter.setOnItemClickedListener(null);
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
    searchForProductUseCase.search(searchQuery.get())
        .compose(transformer.applySchedulers())
        .subscribe(results -> {
          progressBarVisibility.set(View.GONE);
          listVisibility.set(View.VISIBLE);
          adapter.replaceAll(results);
        }, throwable -> {
        });
  }
}
