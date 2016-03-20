package com.fred.inventory.presentation.widgets.clicktoedittext;

import android.support.annotation.NonNull;
import android.view.inputmethod.EditorInfo;
import javax.inject.Inject;

public class ClickToEditTextPresenterImpl implements ClickToEditTextPresenter {
  private final ClickToEditTextView view;

  @Inject public ClickToEditTextPresenterImpl(ClickToEditTextView view) {
    this.view = view;
  }

  @Override public void onTextViewClicked() {
    view.becomeEditable();
    view.requestFocusOnEditText();
    view.showKeyboard();
  }

  @Override public boolean onKeyboardKeyPressed(int imeAction) {
    if (imeAction == EditorInfo.IME_ACTION_DONE) {
      view.becomeUneditable();
      view.dismissKeyboard();
      return true;
    }
    return false;
  }

  @Override public void onTextEntered(@NonNull String text) {
    view.setTextViewText(text);
  }
}
