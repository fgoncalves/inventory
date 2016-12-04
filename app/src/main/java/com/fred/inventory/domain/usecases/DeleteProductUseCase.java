package com.fred.inventory.domain.usecases;

import com.fred.inventory.domain.models.Product;
import rx.Observable;

/**
 * Delete a product from the local storage
 * <p/>
 * Created by fred on 04.12.16.
 */

public interface DeleteProductUseCase {
  /**
   * Delete the given product from the local storage
   *
   * @param product The product to delete
   * @return An observable to the operation
   */
  Observable<Void> delete(Product product);
}
