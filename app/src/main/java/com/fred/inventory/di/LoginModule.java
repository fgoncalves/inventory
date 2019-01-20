package com.fred.inventory.di;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import com.fred.inventory.R;
import com.fred.inventory.RootModule;
import com.fred.inventory.presentation.login.LoginScreen;
import com.fred.inventory.presentation.login.utils.GoogleApiClientFactory;
import com.fred.inventory.presentation.login.utils.GoogleApiClientFactoryImpl;
import com.fred.inventory.presentation.login.viewmodels.LoginViewModel;
import com.fred.inventory.presentation.login.viewmodels.LoginViewModelImpl;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(injects = LoginScreen.class, addsTo = RootModule.class) public class LoginModule {
  private final LoginScreen loginScreen;

  public LoginModule(LoginScreen loginScreen) {
    this.loginScreen = loginScreen;
  }

  @Provides @Singleton public FragmentActivity providesFragmentActivity() {
    return (FragmentActivity) loginScreen.getActivity();
  }

  @Provides @Singleton public LoginViewModel providesLoginViewModel(LoginViewModelImpl viewModel) {
    return viewModel;
  }

  @Provides @Singleton public GoogleSignInOptions providesGoogleSignInOptions(Context context) {
    return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
        context.getString(R.string.default_web_client_id)).requestEmail().build();
  }

  @Provides @Singleton public GoogleApiClientFactory providesGoogleApiClientFactory(
      GoogleApiClientFactoryImpl googleApiClientFactory) {
    return googleApiClientFactory;
  }
}
