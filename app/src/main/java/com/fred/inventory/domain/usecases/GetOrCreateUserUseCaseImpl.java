package com.fred.inventory.domain.usecases;

import com.fred.inventory.data.firebase.models.User;
import com.fred.inventory.data.firebase.services.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.HashMap;
import javax.inject.Inject;
import rx.Observable;

public class GetOrCreateUserUseCaseImpl implements GetOrCreateUserUseCase {
  private final FirebaseAuth firebaseAuth;
  private final UserService userService;

  @Inject public GetOrCreateUserUseCaseImpl(FirebaseAuth firebaseAuth, UserService userService) {
    this.firebaseAuth = firebaseAuth;
    this.userService = userService;
  }

  @Override public Observable<User> getOrCreateUser() {
    FirebaseUser user = firebaseAuth.getCurrentUser();
    return userService.get(user.getUid())
        .switchIfEmpty(userService.createOrUpdate(
            User.create(user.getUid(), user.getEmail(), new HashMap<>())));
  }
}

