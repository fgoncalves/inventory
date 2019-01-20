package com.fred.inventory.presentation.supplies.viewmodels;

import android.databinding.ObservableField;
import android.view.View;
import com.fred.inventory.data.firebase.models.SuppliesList;

/**
 * View model for each item in the first list of products
 * <p/>
 * Created by fred on 04.06.16.
 */
public interface SuppliesItemViewModel {
  interface OnItemClickListener {
    void onItemClicked();
  }

  void onBindViewHolder(SuppliesList suppliesList);

  View.OnClickListener itemClickListener();

  ObservableField<String> itemNameObservable();

  ObservableField<String> infoTextObservable();

  ObservableField<String> description();

  View.OnClickListener deleteButtonClickListener();

  void setOnItemClickListener(OnItemClickListener onItemClickListener);
}
