package com.fred.inventory.domain.usecases;

import android.support.annotation.NonNull;
import com.fred.inventory.domain.models.Product;
import rx.Observable;

/**
 * Get the product info from a scanned code
 * <p/>
 * Created by fred on 11.12.16.
 */

public interface GetProductInfoFromCodeUseCase {
  /**
   * Get the product info for the given code.
   * <p/>
   * Not all fields are guaranteed to be present
   *
   * @param code Code to get the info for
   * @return An observable for the retrieved result
   */
  Observable<Product> info(@NonNull final String code);
}
