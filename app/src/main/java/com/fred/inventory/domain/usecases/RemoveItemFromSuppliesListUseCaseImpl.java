package com.fred.inventory.domain.usecases;

import android.support.annotation.NonNull;
import com.fred.inventory.data.firebase.models.SupplyItem;
import com.fred.inventory.data.firebase.services.SuppliesItemService;
import javax.inject.Inject;
import rx.Observable;

public class RemoveItemFromSuppliesListUseCaseImpl implements RemoveItemFromSuppliesListUseCase {
  private final SuppliesItemService suppliesItemService;

  @Inject public RemoveItemFromSuppliesListUseCaseImpl(SuppliesItemService suppliesItemService) {
    this.suppliesItemService = suppliesItemService;
  }

  @Override public Observable<SupplyItem> remove(@NonNull String suppliesListUuid,
      @NonNull SupplyItem supplyItem) {
    return suppliesItemService.delete(suppliesListUuid, supplyItem.uuid()).map(value -> supplyItem);
  }
}
