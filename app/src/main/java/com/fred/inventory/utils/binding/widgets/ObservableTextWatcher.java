package com.fred.inventory.utils.binding.widgets;

import android.text.Editable;
import android.text.TextWatcher;
import com.fred.inventory.utils.Objects;
import com.fred.inventory.utils.binding.Observable;

/**
 * A text watcher that fires it's events when the text changes. This will only happen if the text
 * is different than the current value.
 */
public class ObservableTextWatcher extends Observable<String> implements TextWatcher {

  private ObservableTextWatcher() {
  }

  public static ObservableTextWatcher create() {
    return new ObservableTextWatcher();
  }

  @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    // do nothing
  }

  @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
    // do nothing
  }

  @Override public void afterTextChanged(Editable s) {
    String newText = s.toString();
    if (Objects.equals(newText, value)) return;
    set(newText);
  }
}
