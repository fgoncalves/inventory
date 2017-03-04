package com.fred.inventory.data.firebase.services;

import android.support.annotation.NonNull;
import com.fred.inventory.data.firebase.models.SuppliesList;
import com.fred.inventory.data.firebase.models.exceptions.FirebaseDatabaseException;
import com.fred.inventory.di.qualifiers.UsersDatabaseReference;
import com.fred.inventory.utils.StringUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import javax.inject.Inject;
import rx.Observable;

public class SupplyListsServiceImpl implements SupplyListsService {
  private final DatabaseReference databaseReference;
  private final FirebaseAuth firebaseAuth;

  @Inject public SupplyListsServiceImpl(@UsersDatabaseReference DatabaseReference databaseReference,
      FirebaseAuth firebaseAuth) {
    this.databaseReference = databaseReference;
    this.firebaseAuth = firebaseAuth;
  }

  @Override public Observable<SuppliesList> create(@NonNull String name) {
    return Observable.create(subscriber -> {
      if (subscriber.isUnsubscribed()) return;

      DatabaseReference childReference = getSupplyListsFirebaseDatabaseReference().push();
      childReference.setValue(
          SuppliesList.create(childReference.getKey(), name, new HashMap<>()).toFirebaseValue());
      childReference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override public void onDataChange(DataSnapshot dataSnapshot) {
          SuppliesList result = SuppliesList.create(dataSnapshot);
          if (result != null) subscriber.onNext(result);
          subscriber.onCompleted();
        }

        @Override public void onCancelled(DatabaseError databaseError) {
          subscriber.onError(new FirebaseDatabaseException(databaseError));
        }
      });
    });
  }

  @Override public Observable<SuppliesList> update(@NonNull SuppliesList suppliesList) {
    return Observable.create(subscriber -> {
      if (subscriber.isUnsubscribed()) return;

      if (StringUtils.isBlank(suppliesList.uuid())) {
        subscriber.onError(
            new IllegalArgumentException("Cannot update supplies list without an uuid"));
        return;
      }

      getSupplyListsFirebaseDatabaseReference().child(suppliesList.uuid())
          .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {
              SuppliesList firebaseSuppliesList = SuppliesList.create(dataSnapshot);
              if (firebaseSuppliesList == null) {
                subscriber.onError(new IllegalArgumentException("No supplies list to update"));
                return;
              }

              SuppliesList updatedValue =
                  SuppliesList.create(firebaseSuppliesList.uuid(), suppliesList.name(),
                      firebaseSuppliesList.items());
              getSupplyListsFirebaseDatabaseReference().child(firebaseSuppliesList.uuid())
                  .setValue(updatedValue.toFirebaseValue(), (databaseError, databaseReference1) -> {
                    if (databaseError != null) {
                      subscriber.onError(new FirebaseDatabaseException(databaseError));
                      return;
                    }
                    subscriber.onNext(updatedValue);
                    subscriber.onCompleted();
                  });
            }

            @Override public void onCancelled(DatabaseError databaseError) {
              subscriber.onError(new FirebaseDatabaseException(databaseError));
            }
          });
    });
  }

  @Override public Observable<Void> delete(@NonNull String uuid) {
    return Observable.create(subscriber -> {
      if (subscriber.isUnsubscribed()) return;

      getSupplyListsFirebaseDatabaseReference().child(uuid)
          .removeValue((databaseError, databaseReference1) -> {
            if (databaseError != null) {
              subscriber.onError(new FirebaseDatabaseException(databaseError));
              return;
            }
            subscriber.onCompleted();
          });
    });
  }

  @Override public Observable<SuppliesList> get(@NonNull String uuid) {
    return Observable.create(subscriber -> {
      if (subscriber.isUnsubscribed()) return;

      getSupplyListsFirebaseDatabaseReference().child(uuid)
          .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {
              SuppliesList suppliesList = SuppliesList.create(dataSnapshot);
              if (suppliesList != null) subscriber.onNext(suppliesList);
            }

            @Override public void onCancelled(DatabaseError databaseError) {
              subscriber.onError(new FirebaseDatabaseException(databaseError));
            }
          });
    });
  }

  @Override public Observable<DatabaseReference> getSupplyListsDatabaseReference() {
    return Observable.fromCallable(() -> getSupplyListsFirebaseDatabaseReference());
  }

  private DatabaseReference getSupplyListsFirebaseDatabaseReference() {
    return databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("supplyLists");
  }
}
