package com.fred.inventory.data.firebase.services;

import com.fred.inventory.data.firebase.models.User;
import com.fred.inventory.data.firebase.models.exceptions.FirebaseDatabaseException;
import com.fred.inventory.di.qualifiers.UsersDatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import javax.inject.Inject;
import rx.Observable;

public class UserServiceImpl implements UserService {
  private final DatabaseReference databaseReference;

  @Inject public UserServiceImpl(@UsersDatabaseReference DatabaseReference databaseReference) {
    this.databaseReference = databaseReference;
  }

  @Override public Observable<User> get(String uuid) {
    return Observable.create(subscriber -> {
      if (subscriber.isUnsubscribed()) return;
      databaseReference.child(uuid).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override public void onDataChange(DataSnapshot dataSnapshot) {
          User user = User.create(dataSnapshot);
          if (user != null) subscriber.onNext(user);
          subscriber.onCompleted();
        }

        @Override public void onCancelled(DatabaseError databaseError) {
          subscriber.onError(new FirebaseDatabaseException(databaseError));
        }
      });
    });
  }

  @Override public Observable<User> createOrUpdate(User user) {
    return Observable.create(subscriber -> {
      if (subscriber.isUnsubscribed()) return;
      DatabaseReference userDataBaseReference = databaseReference.child(user.uuid());
      userDataBaseReference.setValue(user.toFirebaseValue());
      userDataBaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override public void onDataChange(DataSnapshot dataSnapshot) {
          User user = User.create(dataSnapshot);
          if (user != null) subscriber.onNext(user);
          subscriber.onCompleted();
        }

        @Override public void onCancelled(DatabaseError databaseError) {
          subscriber.onError(new FirebaseDatabaseException(databaseError));
        }
      });
    });
  }
}
