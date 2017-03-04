package com.fred.inventory.domain.usecases;

import android.support.annotation.NonNull;
import com.fred.inventory.data.firebase.models.SupplyItem;
import rx.Observable;

/**
 * Use case to get a supply item
 */

public interface GetSupplyItemUseCase {
  Observable<SupplyItem> get(@NonNull final String suppliesListUuid,
      @NonNull final String itemUuid);
}
