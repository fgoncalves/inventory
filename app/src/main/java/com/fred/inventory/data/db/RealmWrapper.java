package com.fred.inventory.data.db;

import io.realm.RealmObject;
import java.util.List;

/**
 * Define a class that wraps around realm to make it easier to mock and isolate dependencies
 * <p/>
 * Created by fred on 13.03.16.
 */
public interface RealmWrapper {
  /**
   * Get all objects from Realm with the given class
   *
   * @param clazz The class of the objects to return
   * @return The list of objects in the realm
   */
  <T extends RealmObject> List<T> all(Class<T> clazz);
}