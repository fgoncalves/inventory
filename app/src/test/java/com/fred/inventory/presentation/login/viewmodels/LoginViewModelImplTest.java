package com.fred.inventory.presentation.login.viewmodels;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.fred.inventory.domain.usecases.GetOrCreateUserUseCase;
import com.fred.inventory.presentation.login.utils.GoogleApiClientFactory;
import com.fred.inventory.testhelpers.TestSchedulerTransformer;
import com.fred.inventory.utils.path.PathManager;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginViewModelImplTest {
  @Mock PathManager pathManager;
  @Mock FragmentActivity fragmentActivity;
  @Mock FirebaseAuth firebaseAuth;
  @Mock GetOrCreateUserUseCase getOrCreateUserUseCase;
  @Mock GoogleApiClientFactory googleApiClientFactory;
  @Mock GoogleApiClient googleApiClient;
  @Mock LoginViewModel.OnLoginErrorListener onLoginErrorListener;
  @Mock LoginViewModel.OnStartGoogleSignInActivityListener startGoogleSignInActivityListener;

  private LoginViewModelImpl viewModel;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    viewModel =
        new LoginViewModelImpl(pathManager, googleApiClientFactory, fragmentActivity, firebaseAuth,
            getOrCreateUserUseCase, new TestSchedulerTransformer());
    viewModel.setOnLoginErrorListener(onLoginErrorListener);
    viewModel.setOnStartGoogleSignInActivityListener(startGoogleSignInActivityListener);

    when(googleApiClientFactory.createGoogleApiClient(
        any(GoogleApiClient.OnConnectionFailedListener.class))).thenReturn(googleApiClient);
  }

  @Test public void onStart_shouldHideSignInButton() {
    viewModel.onStart();

    assertThat(viewModel.signInButtonVisibility().get()).isEqualTo(GONE);
    assertThat(viewModel.progressBarVisibility().get()).isEqualTo(VISIBLE);
  }

  @Test public void onStart_shouldRegisterListenerWithFirebaseAuth() {
    viewModel.onStart();

    verify(firebaseAuth).addAuthStateListener(any(FirebaseAuth.AuthStateListener.class));
  }

  @Test public void onStop_shouldRemoveFirebaseAuthListener() {
    viewModel.onStart();
    reset(firebaseAuth);

    viewModel.onStop();

    verify(firebaseAuth).removeAuthStateListener(any(FirebaseAuth.AuthStateListener.class));
  }

  @Test public void onActivityCreated_shouldAskFactoryToCreateGoogleApiClient() {
    viewModel.onActivityCreated();

    verify(googleApiClientFactory).createGoogleApiClient(
        any(GoogleApiClient.OnConnectionFailedListener.class));
  }

  @Test public void onDestroy_shouldStopAutomanagingAndDisconnectGoogleClient() {
    viewModel.onActivityCreated();

    viewModel.onDestroy();

    verify(googleApiClient).stopAutoManage(any(FragmentActivity.class));
    verify(googleApiClient).disconnect();
  }

  @Test public void onGoogleSignInResult_shouldCallLoginErrorCallbacksWhenLoginFails() {
    GoogleSignInResult result = mock(GoogleSignInResult.class);
    when(result.isSuccess()).thenReturn(false);

    viewModel.onGoogleSignInResult(result);

    verify(onLoginErrorListener).onFailedToLoginToGoogle();
  }

  @Test public void onGoogleSignInResult_shouldNotCallLoginErrorCallbacksIfListenerIsNull() {
    viewModel.setOnLoginErrorListener(null);
    GoogleSignInResult result = mock(GoogleSignInResult.class);
    when(result.isSuccess()).thenReturn(false);

    viewModel.onGoogleSignInResult(result);

    verify(onLoginErrorListener, never()).onFailedToLoginToGoogle();
    verify(onLoginErrorListener, never()).onFailedToLoginToFirebase();
  }

  @Test public void onGoogleSignInClickListener_shouldCallListenerIfItIsSet() {
    viewModel.onGoogleSignInClickListener().onClick(mock(View.class));

    verify(startGoogleSignInActivityListener).onStartGoogleSignIn(any(GoogleApiClient.class));
  }

  @Test public void onGoogleSignInClickListener_shouldNotCallListenerIfNoneIsProvided() {
    viewModel.setOnStartGoogleSignInActivityListener(null);

    viewModel.onGoogleSignInClickListener().onClick(mock(View.class));

    verify(startGoogleSignInActivityListener, never()).onStartGoogleSignIn(
        any(GoogleApiClient.class));
  }
}
