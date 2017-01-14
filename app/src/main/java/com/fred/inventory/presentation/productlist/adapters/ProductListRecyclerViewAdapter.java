package com.fred.inventory.presentation.productlist.adapters;

import com.fred.inventory.domain.models.Product;
import java.util.List;

/**
 * Adapter for the recycler view holding the product list items
 * <p/>
 * Created by fred on 10.11.16.
 */

public interface ProductListRecyclerViewAdapter {
  void replaceAll(List<Product> models);

  interface OnProductDeletedListener {
    void onProductDeleted(Product product);
  }

  interface OnItemClickListener {
    void onItemClicked(Product product);
  }

  void add(Product model);

  void remove(Product model);

  void add(List<Product> models);

  void remove(List<Product> models);

  List<Product> getItems();

  void setOnProductDeletedListener(OnProductDeletedListener onProductDeletedListener);

  void setOnItemClickListener(OnItemClickListener onItemClickListener);
}
