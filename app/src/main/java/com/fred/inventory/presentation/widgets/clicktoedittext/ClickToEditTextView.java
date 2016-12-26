package com.fred.inventory.presentation.widgets.clicktoedittext;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ViewSwitcher;
import com.fred.inventory.utils.KeyboardUtil;

/**
 * The view for the click to edit text view
 * <p/>
 * This assumes that the view is composed of 2 views: A TextView and an EditText.
 * The first one must be a text view and the second the edit text view.
 * <p/>
 * This view mu
 * <p/>
 * Created by fred on 26.12.16.
 */

public class ClickToEditTextView extends ViewSwitcher {
  /**
   * Implement this to receive notifications about the changed mode
   */
  public interface OnModeChangedListener {
    /**
     * Called when the mode changed in the view
     *
     * @param mode The mode that was set
     */
    void onModeChanged(@NonNull final ClickToEditTextViewMode mode);
  }

  private OnModeChangedListener onModeChangedListener;

  public ClickToEditTextView(Context context) {
    super(context);
  }

  public ClickToEditTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void setMode(ClickToEditTextViewMode mode) {
    switch (mode) {
      case EDIT:
        if (getCurrentView() == getChildAt(0)) {
          showNext();
          KeyboardUtil.showKeyboard(getCurrentView());
        }
        break;
      case TEXT:
        if (getCurrentView() == getChildAt(1)) {
          KeyboardUtil.hideKeyboard(getCurrentView());
          showPrevious();
        }
        break;
    }
    if (onModeChangedListener != null) onModeChangedListener.onModeChanged(mode);
  }

  public void setOnModeChangedListener(OnModeChangedListener onModeChangedListener) {
    this.onModeChangedListener = onModeChangedListener;
  }
}
