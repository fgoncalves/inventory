package com.fred.inventory.presentation.globalsearch.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;

/**
 * The view model for the global search screen
 */

public interface GlobalSearchViewModel {
  ObservableField<String> searchQuery();

  TextWatcher searchQueryTextWatcher();

  RecyclerView.Adapter<?> globalSearchRecyclerViewAdapter();

  ObservableInt progressBarVisibility();
}
