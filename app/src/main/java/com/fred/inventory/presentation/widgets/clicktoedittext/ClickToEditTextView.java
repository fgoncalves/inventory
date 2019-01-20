package com.fred.inventory.presentation.widgets.clicktoedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.fred.inventory.R;
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
  public enum Mode {
    EDIT, TEXT
  }

  private final TextWatcher textWatcher = new TextWatcher() {
    @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override public void afterTextChanged(Editable editable) {
      if (textView.getText().equals(editable.toString())) return;
      textView.setText(editable.toString());
    }
  };
  private int editTextStyle;
  private int textViewStyle;
  private EditText editText;
  private TextView textView;

  public ClickToEditTextView(Context context) {
    this(context, null);
  }

  public ClickToEditTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypedArray a =
        context.getTheme().obtainStyledAttributes(attrs, R.styleable.ClickToEditTextView, 0, 0);

    if (attrs != null) {
      try {
        textViewStyle = a.getResourceId(R.styleable.ClickToEditTextView_textViewStyle, -1);
        editTextStyle = a.getResourceId(R.styleable.ClickToEditTextView_editTextStyle, -1);
      } finally {
        a.recycle();
      }
    }

    createChildren(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    if (textViewStyle != -1) setTextAppearance(textView, textViewStyle);
    if (editTextStyle != -1) setTextAppearance(editText, editTextStyle);
    addChildren();
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    addClickListener();
    setOnFocusChangeListener((view, hasFocus) -> {
      if (hasFocus) {
        setMode(Mode.EDIT);
      } else {
        setMode(Mode.TEXT);
      }
    });
    editText.addTextChangedListener(textWatcher);
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    editText.removeTextChangedListener(textWatcher);
  }

  @Override public void setOnClickListener(OnClickListener l) {
    textView.setOnClickListener(l);
  }

  public void setText(CharSequence text) {
    int selection = editText.getSelectionEnd();
    textView.setText(text);
    editText.setText(text);
    editText.setSelection(selection);
  }

  public void addTextChangedListener(TextWatcher textWatcher) {
    editText.addTextChangedListener(textWatcher);
  }

  public void setMode(Mode mode) {
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
  }

  public CharSequence getText() {
    return textView.getText();
  }

  @SuppressWarnings("deprecation") private void setTextAppearance(TextView textView, int style) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      textView.setTextAppearance(getContext(), style);
    } else {
      textView.setTextAppearance(style);
    }
  }

  private void createChildren(Context context, AttributeSet attrs) {
    if (attrs == null) {
      textView = new TextView(context);
      editText = new EditText(context);
    } else {
      textView = new TextView(context, attrs);
      editText = new EditText(context, attrs);
    }

    textView.setId(View.generateViewId());
    editText.setId(View.generateViewId());
  }

  private void addChildren() {
    addView(textView);
    addView(editText);
  }

  private void addClickListener() {
    textView.setOnClickListener((view) -> {
      if (!isInEditMode()) setMode(Mode.EDIT);
    });
  }
}
