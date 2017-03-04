package com.fred.inventory.domain.usecases;

import android.support.annotation.NonNull;
import com.fred.inventory.data.firebase.models.SuppliesList;
import com.fred.inventory.data.firebase.services.SupplyListsService;
import javax.inject.Inject;
import rx.Observable;

public class GetSuppliesListUseCaseImpl implements GetSuppliesListUseCase {
  private final SupplyListsService service;

  @Inject public GetSuppliesListUseCaseImpl(SupplyListsService service) {
    this.service = service;
  }

  @Override public Observable<SuppliesList> get(@NonNull String uuid) {
    return service.get(uuid);
  }
}
