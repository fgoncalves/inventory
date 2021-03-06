package com.fred.inventory.data.outpan.services;

import com.fred.inventory.data.outpan.models.Product;
import rx.Observable;

public interface SupplyItemWebService {
  /**
   * Get the product info from the outpan backend.
   *
   * @param productId The bar code of the product
   * @return An observable for the product
   */
  Observable<Product> get(String productId);
}
