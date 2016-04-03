package com.fred.inventory.domain.usecases;

import com.fred.inventory.domain.models.ProductList;
import rx.Observable;

/**
 * Use case to save a product list in the local storage
 * <p/>
 * Created by fred on 03.04.16.
 */
public interface SaveProductListInLocalStorageUseCase {
  /**
   * Store the given list in the local storage. If no id is provided then the list is treated as new
   * and created. Otherwise an update will be performed.
   *
   * @param productList The product list to save into the local storage
   * @return An observable for the saved list
   */
  Observable<ProductList> save(ProductList productList);
}
