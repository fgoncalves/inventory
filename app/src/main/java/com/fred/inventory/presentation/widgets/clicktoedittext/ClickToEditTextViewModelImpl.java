package com.fred.inventory.presentation.widgets.clicktoedittext;

import android.databinding.ObservableField;
import android.text.TextWatcher;
import android.view.View;
import com.fred.inventory.utils.binding.widgets.OneTimeTextWatcher;
import javax.inject.Inject;

public class ClickToEditTextViewModelImpl implements ClickToEditTextViewModel {
  private final ObservableField<ClickToEditTextViewMode> mode =
      new ObservableField<>(ClickToEditTextViewMode.TEXT);
  private final ObservableField<String> text = new ObservableField<>("");
  private final TextWatcher textWatcher = new OneTimeTextWatcher(text);
  private final View.OnFocusChangeListener onFocusChangeListener = (view, hasFocus) -> {
    if (hasFocus) mode.set(ClickToEditTextViewMode.EDIT);
  };
  private final View.OnClickListener clickListener = (view) -> {
    if (!isInEditMode()) enableEditMode();
  };
  private final ClickToEditTextView.OnModeChangedListener onModeChangedListener = (newMode) -> {
    if (newMode != mode.get()) mode.set(newMode);
  };

  @Inject public ClickToEditTextViewModelImpl() {
  }

  @Override public View.OnFocusChangeListener editTextFocusChangeListener() {
    return onFocusChangeListener;
  }

  @Override public View.OnClickListener clickListener() {
    return clickListener;
  }

  @Override public TextWatcher textWatcher() {
    return textWatcher;
  }

  @Override public ObservableField<String> text() {
    return text;
  }

  @Override public ObservableField<ClickToEditTextViewMode> mode() {
    return mode;
  }

  @Override public void enableEditMode() {
    mode.set(ClickToEditTextViewMode.EDIT);
  }

  @Override public void disableEditMode() {
    mode.set(ClickToEditTextViewMode.TEXT);
  }

  @Override public ClickToEditTextView.OnModeChangedListener modeChangedListener() {
    return onModeChangedListener;
  }

  private boolean isInEditMode() {
    return mode.get() == ClickToEditTextViewMode.EDIT;
  }
}
