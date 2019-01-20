package com.fred.inventory.presentation.login.utils;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Api client factory for Google's shitty implementation of a class that requires dependencies on
 * the
 * android framework all over the place.
 */

public interface GoogleApiClientFactory {
  /**
   * Create a google api client for the app. The returned client is new. The Calling code should
   * make sure to cache the instance if further calls are needed.
   *
   * @param listener The listener to call when the connection fails
   * @return A google api client
   */
  GoogleApiClient createGoogleApiClient(
      @NonNull final GoogleApiClient.OnConnectionFailedListener listener);
}
