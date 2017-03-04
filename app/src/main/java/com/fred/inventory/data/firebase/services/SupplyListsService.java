package com.fred.inventory.data.firebase.services;

import android.support.annotation.NonNull;
import com.fred.inventory.data.firebase.models.SuppliesList;
import com.google.firebase.database.DatabaseReference;
import rx.Observable;

public interface SupplyListsService {
  /**
   * Create a new supplies list in the user's supplies' lists
   *
   * @param name The supplies' list to name to store
   * @return An observable for the created list
   */
  Observable<SuppliesList> create(@NonNull final String name);

  /**
   * Update the given list in firebase. This method does not do anything to the list's items.
   *
   * @param suppliesList Supplies list to update
   * @return Observable for the updated value
   */
  Observable<SuppliesList> update(@NonNull final SuppliesList suppliesList);

  /**
   * Delete the supplies' list identified with the given uuid
   *
   * @param uuid The supplies' list uuid
   * @return Observable for the deletion
   */
  Observable<Void> delete(@NonNull final String uuid);

  /**
   * Get the supplies' list identified with the given uuid
   *
   * @param uuid The supplies' list uuid
   * @return Observable for the list
   */
  Observable<SuppliesList> get(@NonNull final String uuid);

  /**
   * Get the database reference for the user's supply lists.
   *
   * @return An observable for the supply lists database reference
   */
  Observable<DatabaseReference> getSupplyListsDatabaseReference();
}
