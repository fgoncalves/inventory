package com.fred.inventory.data.datakick.services;

import com.fred.inventory.data.datakick.models.Item;
import rx.Observable;

/**
 * Item service to use when calling the data kick backend
 */
public interface ItemService {
  /**
   * Get the product information from the barcode number.
   *
   * @param productId The barcode number
   * @return An observable for the retrieved Item
   */
  Observable<Item> get(String productId);
}
