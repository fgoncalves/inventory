package com.fred.inventory.domain.usecases;

import android.support.annotation.NonNull;
import com.fred.inventory.data.firebase.models.SupplyItem;
import rx.Observable;

/**
 * Remove an item from the given supplies list
 */

public interface RemoveItemFromSuppliesListUseCase {
  /**
   * Remove the given supply item from the given supply list
   *
   * @param suppliesListUuid The supplies list uuid
   * @param supplyItem The supply item to remove
   * @return An observable for the operation
   */
  Observable<SupplyItem> remove(@NonNull final String suppliesListUuid,
      @NonNull final SupplyItem supplyItem);
}
