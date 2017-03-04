package com.fred.inventory.domain.usecases;

import android.support.annotation.NonNull;
import com.fred.inventory.data.firebase.models.SupplyItem;
import com.fred.inventory.data.firebase.services.SuppliesItemService;
import javax.inject.Inject;
import rx.Observable;

public class GetSupplyItemUseCaseImpl implements GetSupplyItemUseCase {
  private final SuppliesItemService suppliesItemService;

  @Inject public GetSupplyItemUseCaseImpl(SuppliesItemService suppliesItemService) {
    this.suppliesItemService = suppliesItemService;
  }

  @Override
  public Observable<SupplyItem> get(@NonNull String suppliesListUuid, @NonNull String itemUuid) {
    return suppliesItemService.get(suppliesListUuid, itemUuid);
  }
}
