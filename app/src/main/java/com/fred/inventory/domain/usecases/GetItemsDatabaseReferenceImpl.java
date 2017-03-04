package com.fred.inventory.domain.usecases;

import android.support.annotation.NonNull;
import com.fred.inventory.data.firebase.services.SuppliesItemService;
import com.google.firebase.database.DatabaseReference;
import javax.inject.Inject;
import rx.Observable;

public class GetItemsDatabaseReferenceImpl implements GetItemsDatabaseReference {
  private final SuppliesItemService service;

  @Inject public GetItemsDatabaseReferenceImpl(SuppliesItemService service) {
    this.service = service;
  }

  @Override public Observable<DatabaseReference> get(@NonNull String suppliesListUuid) {
    return service.getSuppliesItemDatabaseReference(suppliesListUuid);
  }
}
