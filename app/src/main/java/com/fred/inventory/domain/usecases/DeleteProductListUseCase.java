package com.fred.inventory.domain.usecases;

import com.fred.inventory.domain.models.ProductList;
import rx.Observable;

/**
 * Delete an entire product list
 * <p/>
 * Created by fred on 04.12.16.
 */

public interface DeleteProductListUseCase {
  /**
   * Delete the given product list from the database
   *
   * @param productList The product list to delete
   * @return An observable for the operation
   */
  Observable<Void> delete(ProductList productList);
}
