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
   * @return An observable for all the product lists in the database
   */
  Observable<List<ProductList>> all();
}
