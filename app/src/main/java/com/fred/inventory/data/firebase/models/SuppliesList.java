package com.fred.inventory.data.firebase.models;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.google.firebase.database.DataSnapshot;
import java.util.Map;
import me.mattlogan.auto.value.firebase.annotation.FirebaseValue;

@AutoValue @FirebaseValue public abstract class SuppliesList {
  public abstract String uuid();

  public abstract String name();

  @Nullable public abstract Map<String, SupplyItem> items();

  public static SuppliesList create(String uuid, String name, Map<String, SupplyItem> supplyItems) {
    return new AutoValue_SuppliesList(uuid, name, supplyItems);
  }

  public static SuppliesList create(DataSnapshot dataSnapshot) {
    AutoValue_SuppliesList.FirebaseValue value =
        dataSnapshot.getValue(AutoValue_SuppliesList.FirebaseValue.class);
    if (value == null) return null;
    return value.toAutoValue();
  }

  public Object toFirebaseValue() {
    return new AutoValue_SuppliesList.FirebaseValue(this);
  }
}
