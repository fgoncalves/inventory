package com.fred.inventory.presentation.productlist.adapters;

import com.fred.inventory.data.firebase.models.SuppliesList;
import com.fred.inventory.data.firebase.models.SupplyItem;
import java.util.List;

/**
 * Adapter for the recycler view holding the product list items
 * <p/>
 * Created by fred on 10.11.16.
 */

public interface ProductListRecyclerViewAdapter {
  interface OnItemClickListener {
    void onItemClicked(SupplyItem supplyItem);
  }

  void replaceAll(List<SupplyItem> models);

  void add(SupplyItem model);

  void remove(SupplyItem model);

  int getItemCount();

  List<SupplyItem> getItems();

  void setSuppliesList(SuppliesList suppliesList);

  void setOnItemClickListener(OnItemClickListener onItemClickListener);
}
