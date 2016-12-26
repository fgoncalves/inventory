package com.fred.inventory.presentation.widgets.clicktoedittext;

import android.databinding.ObservableField;
import android.text.TextWatcher;
import android.view.View;

/**
 * Click to edit text view view model.
 * <p/>
 * Created by fred on 26.12.16.
 */

public interface ClickToEditTextViewModel {
  View.OnFocusChangeListener editTextFocusChangeListener();

  View.OnClickListener clickListener();

  TextWatcher textWatcher();

  ObservableField<String> text();

  ObservableField<ClickToEditTextViewMode> mode();

  ClickToEditTextView.OnModeChangedListener modeChangedListener();

  void enableEditMode();

  void disableEditMode();
}
