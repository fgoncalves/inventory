package com.fred.inventory.domain.usecases;

import com.fred.inventory.data.firebase.models.SuppliesList;
import com.fred.inventory.data.firebase.services.SupplyListsService;
import javax.inject.Inject;
import rx.Observable;

public class CreateOrUpdateSuppliesListUserCaseImpl implements CreateOrUpdateSuppliesListUserCase {
  private final SupplyListsService service;

  @Inject public CreateOrUpdateSuppliesListUserCaseImpl(SupplyListsService service) {
    this.service = service;
  }

  @Override public Observable<SuppliesList> createOrUpdate(SuppliesList suppliesList) {
    return service.update(suppliesList).onErrorResumeNext(service.create(suppliesList.name()));
  }
}
