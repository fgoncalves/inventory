package com.fred.inventory.domain.usecases;

import android.support.annotation.NonNull;
import com.fred.inventory.data.firebase.models.SuppliesList;
import rx.Observable;

public interface GetSuppliesListUseCase {
  /**
   * Get the supplies list with the given uuid. Empty if none exists
   *
   * @param uuid The supplies list uuid
   * @return An observable for the supplies list
   */
  Observable<SuppliesList> get(@NonNull final String uuid);
}
