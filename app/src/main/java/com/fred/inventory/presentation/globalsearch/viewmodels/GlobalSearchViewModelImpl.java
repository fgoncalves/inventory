package com.fred.inventory.presentation.globalsearch.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.View;
import com.fred.inventory.presentation.globalsearch.adapters.GlobalSearchRecyclerViewAdapter;
import com.fred.inventory.utils.binding.widgets.OneTimeTextWatcher;
import javax.inject.Inject;

public class GlobalSearchViewModelImpl implements GlobalSearchViewModel {
  private final ObservableField<String> searchQuery = new ObservableField<>("");
  private final TextWatcher searchQueryTextWatcher = new OneTimeTextWatcher(searchQuery);
  private final ObservableInt progressBarVisibility = new ObservableInt(View.VISIBLE);
  private final GlobalSearchRecyclerViewAdapter adapter;

  @Inject public GlobalSearchViewModelImpl(GlobalSearchRecyclerViewAdapter adapter) {
    this.adapter = adapter;
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

  @Override public ObservableInt progressBarVisibility() {
    return progressBarVisibility;
  }
}
