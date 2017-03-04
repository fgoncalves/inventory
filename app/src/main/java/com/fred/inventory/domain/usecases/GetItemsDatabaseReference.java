package com.fred.inventory.domain.usecases;

import android.support.annotation.NonNull;
import com.google.firebase.database.DatabaseReference;
import rx.Observable;

public interface GetItemsDatabaseReference {
  /**
   * Get the database reference for the items of the supplies' list identified by the given uuid.
   *
   * @param suppliesListUuid Supplies' list uuid
   * @return Observable for the database reference
   */
  Observable<DatabaseReference> get(@NonNull final String suppliesListUuid);
}
