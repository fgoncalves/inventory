package com.fred.inventory.domain.usecases;

import android.support.annotation.NonNull;
import com.fred.inventory.data.firebase.models.SupplyItem;
import rx.Observable;

/**
 * Creates or updates a given supply item.
 */

public interface CreateOrUpdateSupplyItemUseCase {
  /**
   * Create or update a given supply item
   *
   * @param suppliesListUuid The supply list uuid
   * @param item The item with a uuid already set
   * @return An observable for the updated or created item
   */
  Observable<SupplyItem> createOrUpdate(@NonNull final String suppliesListUuid,
      @NonNull final SupplyItem item);
}
