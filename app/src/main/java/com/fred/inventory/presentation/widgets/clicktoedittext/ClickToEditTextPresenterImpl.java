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
    makeEditableIfNotEditable();
    //view.requestFocusOnEditText();
    //view.showKeyboard();
  }

  @Override public boolean onKeyboardKeyPressed(int imeAction) {
    if (imeAction == EditorInfo.IME_ACTION_DONE) {
      makeUneditableIfEditable();
      //view.dismissKeyboard();
      return true;
    }
    return false;
  }

  @Override public void onTextEntered(@NonNull String text) {
    view.setTextViewText(text);
  }

  @Override public void setText(@NonNull String text) {
    // Right now the implementation of this sets automatically the text in the text view as well,
    // because there's a text listener in the edit text. However, this is not known to the presenter
    // and seems wrong to have it not set in both views. I'm leaving this here now and fix it later.
    //view.setEditTextText(text);
  }

  @Override public void onShowKeyboardRequest() {
    makeEditableIfNotEditable();
    //view.requestFocusOnEditText();
    //view.showKeyboard();
  }

  /**
   * Make the view editable if it wasn't already editable
   */
  private void makeEditableIfNotEditable() {
    if (!view.isEditable()) view.becomeEditable();
  }

  /**
   * Make the view uneditable if it was previously editable
   */
  private void makeUneditableIfEditable() {
    if (view.isEditable()) view.becomeUneditable();
  }
}
