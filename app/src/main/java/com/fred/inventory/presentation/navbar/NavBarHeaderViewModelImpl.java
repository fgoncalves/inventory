package com.fred.inventory.presentation.navbar;

import android.databinding.ObservableField;
import android.net.Uri;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import javax.inject.Inject;

public class NavBarHeaderViewModelImpl implements NavBarHeaderViewModel {
  private final ObservableField<Uri> imageUrl = new ObservableField<>();
  private final ObservableField<String> userName = new ObservableField<>("");
  private final ObservableField<String> userEmail = new ObservableField<>("");
  private final FirebaseAuth firebaseAuth;
  private final FirebaseAuth.AuthStateListener mAuthListener = firebaseAuth1 -> {
    FirebaseUser user = firebaseAuth1.getCurrentUser();
    if (user != null) {
      imageUrl.set(user.getPhotoUrl());
      userName.set(user.getDisplayName());
      userEmail.set(user.getEmail());
    }
  };

  @Inject public NavBarHeaderViewModelImpl(FirebaseAuth firebaseAuth) {
    this.firebaseAuth = firebaseAuth;
  }

  @Override public ObservableField<Uri> imageUrl() {
    return imageUrl;
  }

  @Override public ObservableField<String> userName() {
    return userName;
  }

  @Override public ObservableField<String> userEmail() {
    return userEmail;
  }

  @Override public void onStart() {
    firebaseAuth.addAuthStateListener(mAuthListener);
  }

  @Override public void onStop() {
    firebaseAuth.removeAuthStateListener(mAuthListener);
  }
}
