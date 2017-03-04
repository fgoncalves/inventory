package com.fred.inventory.presentation;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import com.fred.inventory.utils.KeyboardUtil;

/**
 * Didn't manage to find another way to force show a keyboard when the focus was requested
 */

public class ForceKeyboardEditText extends AppCompatEditText {

  public ForceKeyboardEditText(Context context) {
    super(context);
  }

  public ForceKeyboardEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ForceKeyboardEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
    super.onFocusChanged(focused, direction, previouslyFocusedRect);
    if (focused) {
      KeyboardUtil.showKeyboard(this);
    } else {
      KeyboardUtil.hideKeyboard(this);
    }
  }
}
