package com.fred.inventory.domain.usecases;

import com.fred.inventory.data.firebase.models.SuppliesList;
import rx.Observable;

public interface CreateOrUpdateSuppliesListUserCase {
  /**
   * Create an empty supplies' list with the given name
   *
   * @param suppliesList Name for the supplies' list
   * @return An observable for the created supplies list
   */
  Observable<SuppliesList> createOrUpdate(SuppliesList suppliesList);
}
