package com.fred.inventory.presentation.listofproducts.viewmodels;

import android.databinding.ObservableField;
import android.view.View;
import com.fred.inventory.domain.models.ProductList;

/**
 * View model for each item in the first list of products
 * <p/>
 * Created by fred on 04.06.16.
 */
public interface ListOfProductListsItemViewModel {
  interface OnDeleteButtonClick {
    void onDeleteClicked();
  }

  interface OnItemClickListener {
    void onItemClicked();
  }

  void onBindViewHolder(ProductList productList);

  View.OnClickListener itemClickListener();

  void setOnDeleteButtonClick(OnDeleteButtonClick onDeleteButtonClick);

  ObservableField<String> itemNameObservable();

  ObservableField<String> infoTextObservable();

  View.OnClickListener deleteButtonClickListener();

  void setOnItemClickListener(OnItemClickListener onItemClickListener);
}
