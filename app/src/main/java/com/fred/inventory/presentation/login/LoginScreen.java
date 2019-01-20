package com.fred.inventory.presentation.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.databinding.LoginScreenBinding;
import com.fred.inventory.di.LoginModule;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.presentation.login.viewmodels.LoginViewModel;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import javax.inject.Inject;

public class LoginScreen extends BaseScreen
    implements LoginViewModel.OnStartGoogleSignInActivityListener,
    LoginViewModel.OnLoginErrorListener {
  private static final int RC_SIGN_IN = 0xbeef;

  @Inject LoginViewModel viewModel;

  public static LoginScreen newInstance() {
    return new LoginScreen();
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    viewModel.onActivityCreated();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    LoginScreenBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.login_screen, container, false);

    MainActivity.scoped(new LoginModule(this)).inject(this);
    binding.setViewModel(viewModel);

    viewModel.setOnStartGoogleSignInActivityListener(this);
    viewModel.setOnLoginErrorListener(this);

    return binding.getRoot();
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == RC_SIGN_IN) {
      viewModel.onGoogleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
    }
  }

  @Override public void onStart() {
    super.onStart();
    viewModel.onStart();
  }

  @Override public void onStop() {
    super.onStop();
    viewModel.onStop();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    viewModel.onDestroy();
  }

  @Override protected int getMenuResource() {
    return NO_RESOURCE_ID;
  }

  @Override protected boolean isHomeButtonSupported() {
    return false;
  }

  @Override protected boolean handleBackPress() {
    return false;
  }

  @Override protected Toolbar getToolbar() {
    return null;
  }

  @Override protected String getToolbarTitle() {
    return null;
  }

  @Override public void onStartGoogleSignIn(GoogleApiClient googleApiClient) {
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
    startActivityForResult(signInIntent, RC_SIGN_IN);
  }

  @Override public void onFailedToLoginToFirebase() {
    new AlertDialog.Builder(getActivity()).setMessage(R.string.failed_to_login_to_firebase)
        .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());
  }

  @Override public void onFailedToLoginToGoogle() {
    new AlertDialog.Builder(getActivity()).setMessage(R.string.failed_to_login_to_google)
        .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());
  }

  @Override protected boolean supportsDrawer() {
    return false;
  }
}
