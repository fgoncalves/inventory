package com.fred.inventory.domain.usecases;

import com.fred.inventory.data.firebase.services.SupplyListsService;
import com.google.firebase.database.DatabaseReference;
import javax.inject.Inject;
import rx.Observable;

public class GetUserSuppliesListDatabaseReferenceUseCaseImpl
    implements GetUserSuppliesListDatabaseReferenceUseCase {
  private final SupplyListsService service;

  @Inject public GetUserSuppliesListDatabaseReferenceUseCaseImpl(SupplyListsService service) {
    this.service = service;
  }

  @Override public Observable<DatabaseReference> getUserSuppliesListDatabaseReference() {
    return service.getSupplyListsDatabaseReference();
  }
}
