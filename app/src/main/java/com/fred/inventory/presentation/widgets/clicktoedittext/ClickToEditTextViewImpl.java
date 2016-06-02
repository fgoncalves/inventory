package com.fred.inventory.presentation.widgets.clicktoedittext;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.fred.inventory.MainActivity;
import com.fred.inventory.utils.binding.Observer;
import javax.inject.Inject;

public class ClickToEditTextViewImpl extends ViewSwitcher implements ClickToEditTextView {
  private TextView text;
  private EditText editText;

  @Inject ClickToEditViewModel viewModel;

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

    if (isInEditMode()) return;

    inject();
    bindToViewModel();
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

  public void dismissKeyboard() {
    InputMethodManager imm =
        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
  }

  public void showKeyboard() {
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
    //presenter.onShowKeyboardRequest();
  }

  @Override public boolean isEditable() {
    return getCurrentView() == editText;
  }

  @Override public void setText(@NonNull String text) {
    //presenter.setText(text);
  }

  @Override public String getText() {
    return editText.getText().toString();
  }

  @Override public void onHideKeyboardRequest() {
    editText.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override public void onGlobalLayout() {
            editText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            InputMethodManager imm =
                (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
          }
        });
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
    MainActivity.scoped(new ClickToEditTextModule()).inject(this);
  }

  private void bindToViewModel() {
    viewModel.bindEditableTextObserver(new Observer<String>() {
      @Override public void update(String value) {
        editText.setText(value);
        editText.setSelection(editText.length());
      }
    });
    viewModel.bindTextObserver(new Observer<String>() {
      @Override public void update(String value) {
        text.setText(value);
      }
    });
    viewModel.bindShowKeyboardObserver(new Observer<Boolean>() {
      @Override public void update(Boolean value) {
        editText.requestFocus();
        if (value) {
          showKeyboard();
        } else {
          dismissKeyboard();
        }
      }
    });
    viewModel.bindSwitchToEditTextViewObserver(new Observer<Void>() {
      @Override public void update(Void value) {
        showNext();
      }
    });
    viewModel.bindSwitchToTextViewObserver(new Observer<Void>() {
      @Override public void update(Void value) {
        showPrevious();
      }
    });
    editText.addTextChangedListener(viewModel.textWatcher());
    editText.setOnEditorActionListener(viewModel.editorActionListener());
    text.setOnClickListener(viewModel.textViewClickListener());
  }
}
