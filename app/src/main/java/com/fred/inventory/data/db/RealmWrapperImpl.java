package com.fred.inventory.data.db;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class RealmWrapperImpl implements RealmWrapper {
  private final RealmConfiguration realmConfiguration;

  @Inject public RealmWrapperImpl(RealmConfiguration realmConfiguration) {
    this.realmConfiguration = realmConfiguration;
  }

  @Override public <T extends RealmObject> List<T> all(Class<T> clazz) {
    Realm realm = Realm.getInstance(realmConfiguration);
    return new ArrayList<>(realm.where(clazz).findAll());
  }

  @Override public <T extends RealmObject> T get(Class<T> clazz, String id) {
    Realm realm = Realm.getInstance(realmConfiguration);
    return realm.where(clazz).equalTo("id", id).findFirst();
  }

  @Override public <T extends RealmObject> T store(T object) {
    Realm realm = Realm.getInstance(realmConfiguration);
    realm.beginTransaction();
    T result = realm.copyToRealmOrUpdate(object);
    realm.commitTransaction();
    return result;
  }

  @Override public <T extends RealmObject> void delete(Class<T> clazz, String id) {
    Realm realm = Realm.getInstance(realmConfiguration);
    realm.beginTransaction();
    realm.where(clazz).equalTo("id", id).findAll().deleteAllFromRealm();
    realm.commitTransaction();
  }
}
