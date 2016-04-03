package com.fred.inventory.presentation.widgets.clicktoedittext;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * A text view that will turn editable on click and back uneditable upon loosing focus.
 * <p/>
 * Created by fred on 20.03.16.
 */
public interface ClickToEditTextView extends View.OnClickListener {
  void becomeEditable();

  void becomeUneditable();

  void dismissKeyboard();

  void setTextViewText(@NonNull String text);

  void showKeyboard();

  void requestFocusOnEditText();

  void setText(@NonNull String text);

  void setEditTextText(@NonNull String text);

  void onShowKeyboardRequest();

  boolean isEditable();
}
