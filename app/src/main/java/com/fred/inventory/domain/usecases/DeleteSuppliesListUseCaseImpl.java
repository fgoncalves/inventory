package com.fred.inventory.domain.usecases;

import android.support.annotation.NonNull;
import com.fred.inventory.data.firebase.services.SupplyListsService;
import javax.inject.Inject;
import rx.Observable;

public class DeleteSuppliesListUseCaseImpl implements DeleteSuppliesListUseCase {
  private final SupplyListsService supplyListsService;

  @Inject public DeleteSuppliesListUseCaseImpl(SupplyListsService supplyListsService) {
    this.supplyListsService = supplyListsService;
  }

  @Override public Observable<Void> delete(@NonNull String uuid) {
    return supplyListsService.delete(uuid);
  }
}
