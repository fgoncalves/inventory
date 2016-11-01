package com.fred.inventory.utils.binding.widgets;

import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;
import com.fred.inventory.utils.Objects;

/**
 * This is a text watcher that will only update the observable field when the text is different
 * from
 * what it holds.
 */
public class OneTimeTextWatcher implements TextWatcher {
  private final ObservableField<String> observableField;

  public OneTimeTextWatcher(ObservableField<String> observableField) {
    this.observableField = observableField;
  }

  @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override public void afterTextChanged(Editable editable) {
    String newText = editable.toString();
    String oldText = observableField.get();
    if (Objects.equals(newText, oldText)) return;
    observableField.set(newText);
  }
}
