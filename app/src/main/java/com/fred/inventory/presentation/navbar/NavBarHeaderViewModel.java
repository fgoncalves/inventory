package com.fred.inventory.presentation.navbar;

import android.databinding.ObservableField;
import android.net.Uri;

/**
 * View model for the nav bar
 */

public interface NavBarHeaderViewModel {
  ObservableField<Uri> imageUrl();

  ObservableField<String> userName();

  ObservableField<String> userEmail();

  void onStart();

  void onStop();
}
