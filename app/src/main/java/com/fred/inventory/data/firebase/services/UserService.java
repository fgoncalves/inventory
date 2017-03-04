package com.fred.inventory.data.firebase.services;

import com.fred.inventory.data.firebase.models.User;
import rx.Observable;

public interface UserService {
  /**
   * Get the user object from Firebase's database
   *
   * @param uuid User's uuid
   * @return An observable for the retrieved user. Empty if it doesn't exist.
   */
  Observable<User> get(String uuid);

  /**
   * Create or update the user with the given data. The uuid must be set.
   *
   * @param user The user to update
   * @return An observable to the created user
   */
  Observable<User> createOrUpdate(User user);
}
