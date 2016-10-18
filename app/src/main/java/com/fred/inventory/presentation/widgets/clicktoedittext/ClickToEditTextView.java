package com.fred.inventory.presentation.widgets.clicktoedittext;

import android.support.annotation.NonNull;
import android.text.TextWatcher;

/**
 * A text view that will turn editable on click and back uneditable upon loosing focus.
 * <p/>
 * Created by fred on 20.03.16.
 */
public interface ClickToEditTextView {
  enum ClickToEditTextViewState {
    EDITABLE,
    NON_EDITABLE
  }

  void setText(@NonNull String text);

  void setState(ClickToEditTextViewState state);

  String getText();

  void onShowKeyboardRequest();

  void onHideKeyboardRequest();

  void addTextChangedListener(TextWatcher textWatcher);
}
