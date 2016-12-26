package com.fred.inventory.presentation.widgets.clicktoedittext;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.fred.inventory.MainActivity;
import javax.inject.Inject;

public class OldClickToEditTextViewImpl extends ViewSwitcher implements OldClickToEditTextView {
  private TextView text;
  private EditText editText;
  private TextInputLayout editTextInputLayout;

  @Inject OldClickToEditViewModel viewModel;

  public OldClickToEditTextViewImpl(Context context) {
    super(context);
    createChildren(context, null);
  }

  public OldClickToEditTextViewImpl(Context context, AttributeSet attrs) {
    super(context, attrs);
    createChildren(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    addChildren();

    if (isInEditMode()) return;

    inject();
    bindToViewModel();
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (isInEditMode()) return;
    viewModel.onAttachToWindow();
  }

  @Override public void addTextChangedListener(TextWatcher textWatcher) {
    editText.addTextChangedListener(textWatcher);
  }

  @Override public void setState(ClickToEditTextViewState state) {
    viewModel.setState(state);
  }

  private void dismissKeyboard() {
    editText.requestFocus();
    InputMethodManager imm =
        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
  }

  private void showKeyboard() {
    editText.requestFocus();
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

  @Override public void onShowKeyboardRequest() {
    showNext();
    showKeyboard();
  }

  @Override public void setText(@NonNull String text) {
    viewModel.attachModel(text);
  }

  @Override public String getText() {
    return editText.getText().toString();
  }

  @Override public void onHideKeyboardRequest() {
    showPrevious();
    dismissKeyboard();
  }

  @Override public void setError(String error) {
    editTextInputLayout.setError(error);
    if (editTextInputLayout.getVisibility() != VISIBLE) showNext();
  }

  private void createChildren(Context context, AttributeSet attrs) {
    if (attrs == null) {
      text = new TextView(context);
      editText = new EditText(context);
      editTextInputLayout = new TextInputLayout(context);
    } else {
      text = new TextView(context, attrs);
      editText = new EditText(context, attrs);
      editTextInputLayout = new TextInputLayout(context, attrs);
    }

    text.setId(View.generateViewId());
    editText.setId(View.generateViewId());
    editTextInputLayout.setId(View.generateViewId());

    text.setSingleLine();
    editText.setSingleLine();
    editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
    editTextInputLayout.addView(editText);
  }

  private void addChildren() {
    addView(text);
    addView(editTextInputLayout);
  }

  private void inject() {
    MainActivity.scoped(new ClickToEditTextModule()).inject(this);
  }

  private void bindToViewModel() {
    viewModel.bindEditableTextObserver(value -> {
      editText.setText(value);
      editText.setSelection(editText.length());
    });
    viewModel.bindTextObserver(value -> text.setText(value));
    viewModel.bindShowKeyboardObserver(value -> {
      editText.requestFocus();
      if (value) {
        showKeyboard();
      } else {
        dismissKeyboard();
      }
    });
    viewModel.bindSwitchToEditTextViewObserver(value -> showNext());
    viewModel.bindSwitchToTextViewObserver(value -> showPrevious());
    editText.addTextChangedListener(viewModel.textWatcher());
    editText.setOnEditorActionListener(viewModel.editorActionListener());
    text.setOnClickListener(viewModel.textViewClickListener());
  }
}
