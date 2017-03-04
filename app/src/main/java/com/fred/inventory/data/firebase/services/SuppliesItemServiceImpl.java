package com.fred.inventory.data.firebase.services;

import android.support.annotation.NonNull;
import com.fred.inventory.data.firebase.models.SupplyItem;
import com.fred.inventory.data.firebase.models.exceptions.FirebaseDatabaseException;
import com.fred.inventory.di.qualifiers.UsersDatabaseReference;
import com.fred.inventory.utils.StringUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import javax.inject.Inject;
import rx.Observable;

public class SuppliesItemServiceImpl implements SuppliesItemService {
  private final FirebaseAuth firebaseAuth;
  private final DatabaseReference databaseReference;

  @Inject public SuppliesItemServiceImpl(FirebaseAuth firebaseAuth,
      @UsersDatabaseReference DatabaseReference databaseReference) {
    this.firebaseAuth = firebaseAuth;
    this.databaseReference = databaseReference;
  }

  @Override public Observable<DatabaseReference> getSuppliesItemDatabaseReference(
      @NonNull String suppliesListUuid) {
    return Observable.fromCallable(() -> getItemsDatabaseReference(suppliesListUuid));
  }

  @Override public Observable<Void> delete(@NonNull String suppliesListUuid, @NonNull String uuid) {
    return Observable.create(subscriber -> {
      getItemsDatabaseReference(suppliesListUuid).child(uuid)
          .removeValue((databaseError, databaseReference1) -> {
            if (subscriber.isUnsubscribed()) return;

            if (databaseError != null) {
              subscriber.onError(new FirebaseDatabaseException(databaseError));
              return;
            }
            subscriber.onCompleted();
          });
    });
  }

  @Override
  public Observable<SupplyItem> create(@NonNull String suppliesListUuid, @NonNull SupplyItem item) {
    return Observable.create(subscriber -> {
      if (subscriber.isUnsubscribed()) return;

      DatabaseReference dbReferenceToNewItem = getItemsDatabaseReference(suppliesListUuid).push();
      SupplyItem copy =
          SupplyItem.create(dbReferenceToNewItem.getKey(), item.name(), item.quantity(),
              item.unit(), item.barcode(), item.quantityLabel(), item.expirationDate());
      dbReferenceToNewItem.setValue(copy.toFirebaseValue());
      dbReferenceToNewItem.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override public void onDataChange(DataSnapshot dataSnapshot) {
          SupplyItem newItem = SupplyItem.create(dataSnapshot);
          if (newItem != null) subscriber.onNext(newItem);
          subscriber.onCompleted();
        }

        @Override public void onCancelled(DatabaseError databaseError) {
          subscriber.onError(new FirebaseDatabaseException(databaseError));
        }
      });
    });
  }

  @Override
  public Observable<SupplyItem> update(@NonNull String suppliesListUuid, @NonNull SupplyItem item) {
    return Observable.create(subscriber -> {
      if (subscriber.isUnsubscribed()) return;

      if (StringUtils.isBlank(item.uuid())) {
        subscriber.onError(new IllegalArgumentException("Cannot update item without uuid"));
        return;
      }

      getItemsDatabaseReference(suppliesListUuid).child(item.uuid())
          .setValue(item.toFirebaseValue(), (databaseError, databaseReference1) -> {
            if (databaseError != null) {
              subscriber.onError(new FirebaseDatabaseException(databaseError));
              return;
            }
            subscriber.onNext(item);
            subscriber.onCompleted();
          });
    });
  }

  @Override public Observable<SupplyItem> get(@NonNull String suppliesListUuid,
      @NonNull String supplyItemUuid) {
    return Observable.create(subscriber -> {
      if (subscriber.isUnsubscribed()) return;

      getItemsDatabaseReference(suppliesListUuid).child(supplyItemUuid)
          .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {
              SupplyItem supplyItem = SupplyItem.create(dataSnapshot);
              if (supplyItem != null) subscriber.onNext(supplyItem);
              subscriber.onCompleted();
            }

            @Override public void onCancelled(DatabaseError databaseError) {
              subscriber.onError(new FirebaseDatabaseException(databaseError));
            }
          });
    });
  }

  private DatabaseReference getItemsDatabaseReference(@NonNull String suppliesListUuid) {
    return databaseReference.child(firebaseAuth.getCurrentUser().getUid())
        .child("supplyLists")
        .child(suppliesListUuid)
        .child("items");
  }
}
