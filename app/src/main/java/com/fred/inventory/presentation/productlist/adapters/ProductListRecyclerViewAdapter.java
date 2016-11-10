package com.fred.inventory.presentation.productlist.adapters;

import com.fred.inventory.domain.models.Product;
import java.util.List;

/**
 * Adapter for the recycler view holding the product list items
 * <p/>
 * Created by fred on 10.11.16.
 */

public interface ProductListRecyclerViewAdapter {
  void setData(List<Product> products);
}
