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

  /**
   * Get the first occurrence of the given object from realm with the given id.
   *
   * @param clazz The class of the model
   * @param id The id of the object to retrieve
   * @return The first occurrence of the object or null if none is found
   */
  <T extends RealmObject> T get(Class<T> clazz, String id);

  /**
   * Store the given object into realm. If the object exists it will be updated.
   * <p/>
   * The object must have a valid id.
   *
   * @param object The object to be stored
   * @return The created or updated object
   */
  <T extends RealmObject> T store(T object);
}
