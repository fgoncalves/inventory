package com.fred.inventory.presentation.widgets.clicktoedittext;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.fred.inventory.MainActivity;
import javax.inject.Inject;

public class ClickToEditTextViewImpl extends ViewSwitcher implements ClickToEditTextView {
  private TextView text;
  private EditText editText;

  @Inject ClickToEditTextPresenter presenter;

  public ClickToEditTextViewImpl(Context context) {
    super(context);
    createChildren(context, null);
  }

  public ClickToEditTextViewImpl(Context context, AttributeSet attrs) {
    super(context, attrs);
    createChildren(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    addChildren();
    setOnClickListener(this);
    setOnEditorActionListener();
    setTextChangeListeners();

    if (isInEditMode()) return;

    inject();
  }

  @Override public void onClick(View v) {
    if (getCurrentView() == text) presenter.onTextViewClicked();
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
    editText.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override public void onGlobalLayout() {
            editText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            InputMethodManager imm =
                (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
          }
        });
  }

  @Override public void requestFocusOnEditText() {
    editText.requestFocus();
  }

  @Override public void setEditTextText(@NonNull String text) {
    editText.setText(text);
  }

  @Override public void onShowKeyboardRequest() {
    presenter.onShowKeyboardRequest();
  }

  @Override public boolean isEditable() {
    return getCurrentView() == editText;
  }

  @Override public void setText(@NonNull String text) {
    presenter.setText(text);
  }

  @Override public String getText() {
    return editText.getText().toString();
  }

  private void createChildren(Context context, AttributeSet attrs) {
    if (attrs == null) {
      text = new TextView(context);
      editText = new EditText(context);
    } else {
      text = new TextView(context, attrs);
      editText = new EditText(context, attrs);
    }

    text.setSingleLine();
    editText.setSingleLine();
    editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
  }

  private void addChildren() {
    addView(text);
    addView(editText);
  }

  private void inject() {
    MainActivity.scoped(new ClickToEditTextModule(this)).inject(this);
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
