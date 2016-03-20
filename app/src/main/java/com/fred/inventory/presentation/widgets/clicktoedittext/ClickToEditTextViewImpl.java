package com.fred.inventory.presentation.widgets.clicktoedittext;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.fred.inventory.SuppliesApplication;
import javax.inject.Inject;

public class ClickToEditTextViewImpl extends ViewSwitcher implements ClickToEditTextView {
  private TextView text;
  private EditText editText;

  @Inject ClickToEditTextPresenter presenter;

  public ClickToEditTextViewImpl(Context context) {
    super(context);
  }

  public ClickToEditTextViewImpl(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    createChildren();
    addChildren();
    setOnClickListener(this);
    setOnEditorActionListener();
    setTextChangeListeners();

    if (isInEditMode()) return;

    inject();
  }

  @Override public void onClick(View v) {
    if (v.getId() == text.getId()) presenter.onTextViewClicked();
  }

  @Override public void setTextViewText(@NonNull String text) {
    this.text.setText(text);
  }

  @Override public void becomeEditable() {
    showNext();
  }

  @Override public void becomeUneditable() {
    showPrevious();
  }

  @Override public void dismissKeyboard() {
    InputMethodManager imm =
        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
  }

  @Override public void showKeyboard() {
    InputMethodManager imm =
        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
  }

  @Override public void requestFocusOnEditText() {
    editText.requestFocus();
  }

  private void createChildren() {
    text = new TextView(getContext());
    editText = new EditText(getContext());

    text.setSingleLine();
    editText.setSingleLine();
  }

  private void addChildren() {
    addView(text);
    addView(editText);
  }

  private void inject() {
    SuppliesApplication.scoped(new ClickToEditTextModule(this)).inject(this);
  }

  private void setOnEditorActionListener() {
    editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return presenter.onKeyboardKeyPressed(actionId);
      }
    });
  }

  private void setTextChangeListeners() {
    editText.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {
        presenter.onTextEntered(s.toString());
      }
    });
  }
}
