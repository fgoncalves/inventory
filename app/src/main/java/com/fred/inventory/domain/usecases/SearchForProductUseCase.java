package com.fred.inventory.domain.usecases;

import android.support.annotation.NonNull;
import com.fred.inventory.domain.models.GlobalSearchResult;
import java.util.List;
import rx.Observable;

/**
 * Perform a search for a given product or all product that contain a given name
 */

public interface SearchForProductUseCase {
  /**
   * Perform a search for products that contain in their name the given query.
   *
   * @param query Whole or part of the name
   * @return Observable for the results
   */
  Observable<List<GlobalSearchResult>> search(@NonNull final String query);
}
