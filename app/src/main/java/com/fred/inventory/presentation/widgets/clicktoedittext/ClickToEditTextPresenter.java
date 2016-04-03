package com.fred.inventory.presentation.widgets.clicktoedittext;

import android.support.annotation.NonNull;

/**
 * Presenter for {@link ClickToEditTextView}
 * <p/>
 * Created by fred on 20.03.16.
 */
public interface ClickToEditTextPresenter {

  void onTextViewClicked();

  boolean onKeyboardKeyPressed(int imeAction);

  void onTextEntered(@NonNull String text);

  void setText(@NonNull String text);

  void onShowKeyboardRequest();
}
