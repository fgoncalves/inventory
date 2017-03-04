package com.fred.inventory.domain.usecases;

import android.support.annotation.NonNull;
import com.fred.inventory.data.firebase.models.SupplyItem;
import com.fred.inventory.data.firebase.services.SuppliesItemService;
import javax.inject.Inject;
import rx.Observable;

public class CreateOrUpdateSupplyItemUseCaseImpl implements CreateOrUpdateSupplyItemUseCase {
  private final SuppliesItemService suppliesItemService;

  @Inject public CreateOrUpdateSupplyItemUseCaseImpl(SuppliesItemService suppliesItemService) {
    this.suppliesItemService = suppliesItemService;
  }

  @Override public Observable<SupplyItem> createOrUpdate(@NonNull String suppliesListUuid,
      @NonNull SupplyItem item) {
    return suppliesItemService.update(suppliesListUuid, item)
        .onErrorResumeNext(suppliesItemService.create(suppliesListUuid, item));
  }
}
