package com.fred.inventory.data.firebase.models;

import android.support.annotation.Nullable;
import com.fred.inventory.data.firebase.models.adapters.FirebaseDateAdapter;
import com.google.auto.value.AutoValue;
import com.google.firebase.database.DataSnapshot;
import java.util.Date;
import me.mattlogan.auto.value.firebase.adapter.FirebaseAdapter;
import me.mattlogan.auto.value.firebase.annotation.FirebaseValue;

@AutoValue @FirebaseValue public abstract class SupplyItem {
  public abstract String uuid();

  public abstract String name();

  /**
   * Internal quantity intended only for display purposes
   */
  public abstract int quantity();

  public abstract boolean unit();

  @Nullable public abstract String barcode();

  @Nullable public abstract String quantityLabel();

  @Nullable @FirebaseAdapter(FirebaseDateAdapter.class) public abstract Date expirationDate();

  public static SupplyItem create(String uuid, String name, int quantity, boolean unit,
      String barcode, String quantityLabel, Date expirationDate) {
    return new AutoValue_SupplyItem(uuid, name, quantity, unit, barcode, quantityLabel,
        expirationDate);
  }

  public static SupplyItem create(DataSnapshot dataSnapshot) {
    AutoValue_SupplyItem.FirebaseValue value =
        dataSnapshot.getValue(AutoValue_SupplyItem.FirebaseValue.class);
    if (value == null) return null;
    return value.toAutoValue();
  }

  public Object toFirebaseValue() {
    return new AutoValue_SupplyItem.FirebaseValue(this);
  }
}
