package com.fred.inventory.domain.usecases;

import android.support.annotation.NonNull;
import rx.Observable;

public interface DeleteSuppliesListUseCase {
  /**
   * Delete the supplies' list identified by the uuid
   *
   * @param uuid The supplies' list uuid
   * @return An observable for the call
   */
  Observable<Void> delete(@NonNull final String uuid);
}
