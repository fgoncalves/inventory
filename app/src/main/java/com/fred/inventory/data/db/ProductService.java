package com.fred.inventory.data.db;

import com.fred.inventory.data.db.models.ProductList;
import java.util.List;
import rx.Observable;

/**
 * Interact with the products' database
 * <p/>
 * Created by fred on 13.03.16.
 */
public interface ProductService {
  /**
   * Get all the product lists in the database
   *
   * @return An observable for all the product lists in the database. Empty if none found.
   */
  Observable<List<ProductList>> all();

  /**
   * Get the specified product list from the local storage
   *
   * @param id The list's id
   * @return An observable for the product list. Will be empty if none is found.
   */
  Observable<ProductList> productList(String id);
}
