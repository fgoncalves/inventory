package com.fred.inventory.data.db;

import android.content.Context;
import io.realm.Realm;
import io.realm.RealmObject;
import java.util.List;
import javax.inject.Inject;

public class RealmWrapperImpl implements RealmWrapper {
  private final Context context;

  @Inject public RealmWrapperImpl(Context context) {
    this.context = context;
  }

  @Override public <T extends RealmObject> List<T> all(Class<T> clazz) {
    Realm realm = Realm.getInstance(context);
    try {
      return realm.allObjects(clazz);
    } finally {
      realm.close();
    }
  }
}
