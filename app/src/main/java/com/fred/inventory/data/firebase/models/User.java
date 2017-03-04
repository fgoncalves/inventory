package com.fred.inventory.data.firebase.models;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.google.firebase.database.DataSnapshot;
import java.util.HashMap;
import java.util.Map;
import me.mattlogan.auto.value.firebase.annotation.FirebaseValue;

@AutoValue @FirebaseValue public abstract class User {
  public abstract String uuid();

  public abstract String email();

  @Nullable public abstract Map<String, SuppliesList> supplyLists();

  public static User create(String uuid, String email, Map<String, SuppliesList> supplyLists) {
    return new AutoValue_User(uuid, email, supplyLists == null ? new HashMap<>() : supplyLists);
  }

  public static User create(DataSnapshot dataSnapshot) {
    AutoValue_User.FirebaseValue value = dataSnapshot.getValue(AutoValue_User.FirebaseValue.class);
    if (value == null) return null;
    return value.toAutoValue();
  }

  public Object toFirebaseValue() {
    return new AutoValue_User.FirebaseValue(this);
  }
}
