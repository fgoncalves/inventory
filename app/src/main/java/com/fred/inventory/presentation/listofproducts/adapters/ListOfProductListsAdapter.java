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

  void setData(List<ProductList> productListList);

  void setOnProductListDeletedListener(OnProductListDeletedListener onProductListDeletedListener);

  int getItemCount();
}
