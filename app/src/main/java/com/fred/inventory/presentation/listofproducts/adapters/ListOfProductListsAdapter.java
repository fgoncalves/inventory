package com.fred.inventory.presentation.listofproducts.adapters;

import com.fred.inventory.domain.models.ProductList;
import java.util.List;

/**
 * The adapter for the product lists
 */
public interface ListOfProductListsAdapter {
  interface OnProductListDeletedListener {
    void onProductListDeleted(ProductList productList);
  }

  interface OnItemClickListener {
    void onItemClicked(ProductList productList);
  }

  void setData(List<ProductList> productListList);

  void setOnProductListDeletedListener(OnProductListDeletedListener onProductListDeletedListener);

  void setOnItemClickListener(OnItemClickListener onItemClickListener);

  int getItemCount();
}
