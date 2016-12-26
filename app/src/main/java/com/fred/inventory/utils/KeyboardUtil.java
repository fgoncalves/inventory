package com.fred.inventory.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Boiler plate code to show and hide keyboard
 * <p/>
 * Created by fred on 26.12.16.
 */

public class KeyboardUtil {
  public static void showKeyboard(@NonNull final View view) {
    Context context = view.getContext();
    InputMethodManager inputManager =
        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    view.requestFocus();
    inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
  }

  public static void hideKeyboard(@NonNull final View view) {
    Context context = view.getContext();
    InputMethodManager inputManager =
        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    view.requestFocus();
    inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }
}
