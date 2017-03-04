package com.fred.inventory.domain.usecases;

import com.google.firebase.database.DatabaseReference;
import rx.Observable;

public interface GetUserSuppliesListDatabaseReferenceUseCase {
  /**
   * Get the database reference that points to the user's supply lists.
   *
   * @return An observable with the database reference
   */
  Observable<DatabaseReference> getUserSuppliesListDatabaseReference();
}
