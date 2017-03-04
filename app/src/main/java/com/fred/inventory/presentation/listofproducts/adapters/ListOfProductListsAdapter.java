package com.fred.inventory.presentation.listofproducts.adapters;

import com.fred.inventory.data.firebase.models.SuppliesList;

/**
 * The adapter for the product lists
 */
public interface ListOfProductListsAdapter {
  interface OnItemClickListener {
    void onItemClicked(SuppliesList suppliesList);
  }

  void add(SuppliesList suppliesList);

  void remove(SuppliesList suppliesList);

  void setOnItemClickListener(OnItemClickListener onItemClickListener);

  int getItemCount();
}
