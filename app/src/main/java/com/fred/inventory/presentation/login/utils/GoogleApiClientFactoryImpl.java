package com.fred.inventory.presentation.login.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import javax.inject.Inject;

public class GoogleApiClientFactoryImpl implements GoogleApiClientFactory {
  private final Context context;
  private final GoogleSignInOptions googleSignInOptions;
  private final FragmentActivity fragmentActivity;

  @Inject
  public GoogleApiClientFactoryImpl(Context context, GoogleSignInOptions googleSignInOptions,
      FragmentActivity fragmentActivity) {
    this.context = context;
    this.googleSignInOptions = googleSignInOptions;
    this.fragmentActivity = fragmentActivity;
  }

  @Override public GoogleApiClient createGoogleApiClient(
      @NonNull GoogleApiClient.OnConnectionFailedListener listener) {
    return new GoogleApiClient.Builder(context).enableAutoManage(fragmentActivity, listener)
        .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
        .build();
  }
}
