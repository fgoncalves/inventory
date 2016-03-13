package com.fred.inventory.domain.usecases;

import com.fred.inventory.domain.models.ProductList;
import java.util.List;
import rx.Observable;

/**
 * Use case that grabs all the product lists in the app
 * <p/>
 * Created by fred on 13.03.16.
 */
public interface ListAllProductListsUseCase {
  /**
   * Get all the product lists in the app
   *
   * @return An observable for the product lists in the app
   */
  Observable<List<ProductList>> list();
}
