package com.fred.inventory.presentation.login.viewmodels;

import android.databinding.ObservableInt;
import android.view.View;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * View model for the login
 * <p/>
 * Created by fred on 18.02.17.
 */

public interface LoginViewModel {
  interface OnStartGoogleSignInActivityListener {
    void onStartGoogleSignIn(GoogleApiClient googleApiClient);
  }

  interface OnLoginErrorListener {
    void onFailedToLoginToFirebase();

    void onFailedToLoginToGoogle();
  }

  View.OnClickListener onGoogleSignInClickListener();

  void onStart();

  void onStop();

  void onActivityCreated();

  void onGoogleSignInResult(GoogleSignInResult result);

  ObservableInt signInButtonVisibility();

  ObservableInt progressBarVisibility();

  void setOnStartGoogleSignInActivityListener(
      OnStartGoogleSignInActivityListener onStartGoogleSignInActivityListener);

  void setOnLoginErrorListener(OnLoginErrorListener onLoginErrorListener);

  void onDestroy();
}
