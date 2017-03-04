package com.fred.inventory.di;

import com.fred.inventory.di.qualifiers.UsersDatabaseReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(complete = false, library = true) public class FirebaseModule {
  @Provides @Singleton public FirebaseAuth providesFirebaseAuth() {
    return FirebaseAuth.getInstance();
  }

  @Provides @Singleton public FirebaseDatabase providesFirebaseDatabase() {
    return FirebaseDatabase.getInstance();
  }

  @Provides @Singleton @UsersDatabaseReference
  public DatabaseReference providesUsersDatabaseReference(FirebaseDatabase database) {
    return database.getReference("users");
  }
}
