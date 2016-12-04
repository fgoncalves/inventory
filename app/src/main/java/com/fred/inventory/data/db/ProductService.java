package com.fred.inventory.data.db;

import com.fred.inventory.data.db.models.Product;
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

  /**
   * Create or update the given product list. If the list has an id, then an update will be
   * attempted. If the list has no id, then a random one will be assigned and the list will be
   * created.
   *
   * @param productList The product list to create or update
   * @return The created or updated list
   */
  Observable<ProductList> createOrUpdate(ProductList productList);

  /**
   * Create or update the given product. If the product has an id, then an update will be
   * attempted.
   * If the product has no ide then a random one will be assigned and the product will be created.
   *
   * @param product The product to create or update
   * @return An observable for the created or updated product
   */
  Observable<Product> createOrUpdate(Product product);

  /**
   * Delete the given product.
   *
   * @param product The product to delete
   * @return An observable for the operation
   */
  Observable<Void> delete(Product product);
}
