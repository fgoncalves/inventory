package com.fred.inventory.data.firebase.services;

import android.support.annotation.NonNull;
import com.fred.inventory.data.firebase.models.SupplyItem;
import com.google.firebase.database.DatabaseReference;
import rx.Observable;

public interface SuppliesItemService {
  /**
   * Get the database reference for the list of item in the supplies list.
   *
   * @param suppliesListUuid The uuid of the supplies list
   * @return An observable for the database reference
   */
  Observable<DatabaseReference> getSuppliesItemDatabaseReference(
      @NonNull final String suppliesListUuid);

  /**
   * Delete the item identified by the given uuid in the list identified by the other uuid.
   *
   * @param suppliesListUuid List's uuid
   * @param uuid Item's uuid
   * @return An observable for the call
   */
  Observable<Void> delete(@NonNull final String suppliesListUuid, @NonNull final String uuid);

  /**
   * Create the given item in the given supplies list
   *
   * @param suppliesListUuid The list's uuid
   * @param item The item to create
   * @return The created item with a uuid
   */
  Observable<SupplyItem> create(@NonNull final String suppliesListUuid,
      @NonNull final SupplyItem item);

  /**
   * Update the given item in the given supplies list. This will not work if the item's id is not
   * set.
   *
   * @param suppliesListUuid The supplies list's uuid
   * @param item The item ready to be updated
   * @return An observable for the updated item
   */
  Observable<SupplyItem> update(@NonNull final String suppliesListUuid,
      @NonNull final SupplyItem item);

  /**
   * Get the item identified by the given uuid. Empty if there's none.
   *
   * @param suppliesListUuid The supplies list uuid
   * @param supplyItemUuid The item's uuid
   * @return An observable for the operation
   */
  Observable<SupplyItem> get(@NonNull final String suppliesListUuid,
      @NonNull final String supplyItemUuid);
}
