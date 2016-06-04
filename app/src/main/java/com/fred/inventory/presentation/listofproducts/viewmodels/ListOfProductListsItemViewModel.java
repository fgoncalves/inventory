package com.fred.inventory.presentation.listofproducts.viewmodels;

import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.utils.binding.Observer;

/**
 * View model for each item in the first list of products
 * <p/>
 * Created by fred on 04.06.16.
 */
public interface ListOfProductListsItemViewModel {

  void bindProductListNameObserver(Observer<String> observer);

  void bindInfoTextObserver(Observer<String> observer);

  void bindProductList(ProductList productList);

  void unbindProductListNameObserver(Observer<String> observer);
}
