package com.fred.inventory.presentation.globalsearch.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;
import javax.inject.Inject;

public class GlobalSearchListItemViewModelImpl implements GlobalSearchListItemViewModel {
  @Inject public GlobalSearchListItemViewModelImpl() {
  }

  @Override public ObservableField<String> productName() {
    return null;
  }

  @Override public ObservableField<String> productListName() {
    return null;
  }

  @Override public ObservableField<String> productQuantityLabel() {
    return null;
  }

  @Override public ObservableInt quantity() {
    return null;
  }

  @Override public ObservableInt progressBarVisibility() {
    return null;
  }

  @Override public View.OnClickListener itemClickListener() {
    return null;
  }
}
