package com.fred.inventory.presentation.widgets.clicktoedittext;

import android.support.annotation.NonNull;

/**
 * A text view that will turn editable on click and back uneditable upon loosing focus.
 * <p/>
 * Created by fred on 20.03.16.
 */
public interface ClickToEditTextView {
  void becomeEditable();

  void becomeUneditable();

  void setTextViewText(@NonNull String text);

  void setText(@NonNull String text);

  String getText();

  void onShowKeyboardRequest();

  boolean isEditable();

  void onHideKeyboardRequest();
}
