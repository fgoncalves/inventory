package com.fred.inventory.domain.usecases;

import com.fred.inventory.data.firebase.models.User;
import rx.Observable;

public interface GetOrCreateUserUseCase {
  /**
   * Get or create the corresponding user in Firebase's backend
   *
   * @return an observable for the user
   */
  Observable<User> getOrCreateUser();
}
