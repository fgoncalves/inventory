package com.fred.inventory.presentation.login.viewmodels;

import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.fred.inventory.R;
import com.fred.inventory.domain.usecases.GetOrCreateUserUseCase;
import com.fred.inventory.presentation.login.utils.GoogleApiClientFactory;
import com.fred.inventory.presentation.supplies.SuppliesScreen;
import com.fred.inventory.utils.path.PathManager;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import javax.inject.Inject;
import timber.log.Timber;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class LoginViewModelImpl implements LoginViewModel {
  private final ObservableInt signInButtonVisibility = new ObservableInt(VISIBLE);
  private final ObservableInt progressBarVisibility = new ObservableInt(GONE);
  private final PathManager pathManager;
  private final GoogleApiClientFactory googleApiClientFactory;
  private final FragmentActivity fragmentActivity;
  private final FirebaseAuth firebaseAuth;
  private final GetOrCreateUserUseCase getOrCreateUserUseCase;
  private final SchedulerTransformer transformer;
  private final FirebaseAuth.AuthStateListener mAuthListener =
      new FirebaseAuth.AuthStateListener() {
        @Override public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
          FirebaseUser user = firebaseAuth.getCurrentUser();
          if (user != null) {
            getOrCreateUserInFirebase();
          } else {
            displaySignInButton();
            if (onLoginErrorListener != null) onLoginErrorListener.onFailedToLoginToFirebase();
          }
        }
      };
  private final View.OnClickListener onGoogleSignInClickListener = new View.OnClickListener() {
    @Override public void onClick(View view) {
      if (onStartGoogleSignInActivityListener == null) return;
      hideSignInButton();
      onStartGoogleSignInActivityListener.onStartGoogleSignIn(googleApiClient);
    }
  };

  private GoogleApiClient googleApiClient;
  private OnStartGoogleSignInActivityListener onStartGoogleSignInActivityListener;
  private OnLoginErrorListener onLoginErrorListener;

  @Inject
  public LoginViewModelImpl(PathManager pathManager, GoogleApiClientFactory googleApiClientFactory,
      FragmentActivity fragmentActivity, FirebaseAuth firebaseAuth,
      GetOrCreateUserUseCase getOrCreateUserUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer transformer) {
    this.pathManager = pathManager;
    this.googleApiClientFactory = googleApiClientFactory;
    this.fragmentActivity = fragmentActivity;
    this.firebaseAuth = firebaseAuth;
    this.getOrCreateUserUseCase = getOrCreateUserUseCase;
    this.transformer = transformer;
  }

  @Override public void onStart() {
    hideSignInButton();
    firebaseAuth.addAuthStateListener(mAuthListener);
  }

  @Override public void onStop() {
    firebaseAuth.removeAuthStateListener(mAuthListener);
  }

  @Override public void onActivityCreated() {
    googleApiClient = googleApiClientFactory.createGoogleApiClient(connectionResult -> {
      if (!connectionResult.isSuccess()) {
        displaySignInButton();
        if (onLoginErrorListener != null) onLoginErrorListener.onFailedToLoginToGoogle();
      }
    });
  }

  @Override public void onDestroy() {
    googleApiClient.stopAutoManage(fragmentActivity);
    googleApiClient.disconnect();
  }

  @Override public void setOnStartGoogleSignInActivityListener(
      OnStartGoogleSignInActivityListener onStartGoogleSignInActivityListener) {
    this.onStartGoogleSignInActivityListener = onStartGoogleSignInActivityListener;
  }

  @Override public View.OnClickListener onGoogleSignInClickListener() {
    return onGoogleSignInClickListener;
  }

  @Override public void onGoogleSignInResult(GoogleSignInResult result) {
    if (result.isSuccess()) {
      GoogleSignInAccount account = result.getSignInAccount();
      loginToFireBaseWithGoogle(account);
    } else {
      if (onLoginErrorListener != null) onLoginErrorListener.onFailedToLoginToGoogle();
    }
  }

  @Override public ObservableInt signInButtonVisibility() {
    return signInButtonVisibility;
  }

  @Override public ObservableInt progressBarVisibility() {
    return progressBarVisibility;
  }

  @Override public void setOnLoginErrorListener(OnLoginErrorListener onLoginErrorListener) {
    this.onLoginErrorListener = onLoginErrorListener;
  }

  private void loginToFireBaseWithGoogle(GoogleSignInAccount account) {
    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
    firebaseAuth.signInWithCredential(credential).addOnCompleteListener(fragmentActivity, task -> {
      if (task.isSuccessful()) {
        getOrCreateUserInFirebase();
        return;
      }
      if (onLoginErrorListener != null) onLoginErrorListener.onFailedToLoginToFirebase();
    });
  }

  private void goToSuppliesLists() {
    final SuppliesScreen screen = SuppliesScreen.newInstance();
    pathManager.single(screen, R.id.main_container);
  }

  private void getOrCreateUserInFirebase() {
    getOrCreateUserUseCase.getOrCreateUser()
        .compose(transformer.applySchedulers())
        .subscribe(user1 -> {
          displaySignInButton();
          goToSuppliesLists();
        }, throwable -> {
          displaySignInButton();
          Timber.e(throwable, "Failed to get or create user");
          if (onLoginErrorListener != null) onLoginErrorListener.onFailedToLoginToFirebase();
        });
  }

  private void hideSignInButton() {
    progressBarVisibility.set(VISIBLE);
    signInButtonVisibility.set(GONE);
  }

  private void displaySignInButton() {
    progressBarVisibility.set(GONE);
    signInButtonVisibility.set(VISIBLE);
  }
}
