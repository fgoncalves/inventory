package com.fred.inventory.presentation.productlist.adapters;

import com.fred.inventory.domain.models.Product;
import java.util.List;

/**
 * Adapter for the recycler view holding the product list items
 * <p/>
 * Created by fred on 10.11.16.
 */

public interface ProductListRecyclerViewAdapter {
  interface OnProductDeletedListener {
    void onProductDeleted(Product product);
  }

  interface OnItemClickListener {
    void onItemClicked(Product product);
  }

  void setData(List<Product> products);

  List<Product> getItems();

  void setOnProductDeletedListener(OnProductDeletedListener onProductDeletedListener);

  void setOnItemClickListener(OnItemClickListener onItemClickListener);
}
