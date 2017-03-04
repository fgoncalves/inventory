package com.fred.inventory.presentation.login.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.fred.inventory.R;
import com.fred.inventory.domain.usecases.GetOrCreateUserUseCase;
import com.fred.inventory.presentation.listofproducts.ListOfProductListsScreen;
import com.fred.inventory.utils.path.PathManager;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
  private final Context context;
  private final PathManager pathManager;
  private final GoogleSignInOptions googleSignInOptions;
  private final FragmentActivity fragmentActivity;
  private final FirebaseAuth firebaseAuth;
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
  private final GetOrCreateUserUseCase getOrCreateUserUseCase;
  private final SchedulerTransformer transformer;

  private GoogleApiClient googleApiClient;
  private OnStartGoogleSignInActivityListener onStartGoogleSignInActivityListener;
  private OnLoginErrorListener onLoginErrorListener;

  @Inject public LoginViewModelImpl(Context context, PathManager pathManager,
      GoogleSignInOptions googleSignInOptions, FragmentActivity fragmentActivity,
      FirebaseAuth firebaseAuth, GetOrCreateUserUseCase getOrCreateUserUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer transformer) {
    this.context = context;
    this.pathManager = pathManager;
    this.googleSignInOptions = googleSignInOptions;
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
    googleApiClient = new GoogleApiClient.Builder(context).enableAutoManage(fragmentActivity,
        connectionResult -> {
          if (!connectionResult.isSuccess()) {
            displaySignInButton();
            if (onLoginErrorListener != null) onLoginErrorListener.onFailedToLoginToGoogle();
          }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();
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

  @Override public void onGoogleSignInResult(Intent data) {
    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
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
    final ListOfProductListsScreen screen = ListOfProductListsScreen.newInstance();
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
