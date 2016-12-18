package com.fred.inventory.domain.usecases;

import com.fred.inventory.domain.models.ProductList;
import rx.Observable;

/**
 * Get a product list
 * <p/>
 * Created by fred on 29.03.16.
 */
public interface GetProductListUseCase {
  /**
   * Get the product list for the given id.
   *
   * @param id The product list id
   * @return An observable for the product list. Empty if there is no product list with the given id
   */
  Observable<ProductList> get(Long id);
}
