package com.fred.inventory.presentation.listofproducts.adapters;

import com.fred.inventory.domain.models.ProductList;
import java.util.List;

/**
 * The adapter for the product lists
 */
public interface ListOfProductListsAdapter {
  void attachModel(List<ProductList> productListList);
}
