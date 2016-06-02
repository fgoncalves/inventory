package com.fred.inventory.presentation.widgets.clicktoedittext;

import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import com.fred.inventory.utils.binding.Observable;
import com.fred.inventory.utils.binding.Observer;
import com.fred.inventory.utils.binding.widgets.ObservableTextWatcher;
import javax.inject.Inject;

public class ClickToEditViewModelImpl implements ClickToEditViewModel {
  private final Observable<String> editableTextObservable = Observable.create();
  private final Observable<String> textObservable = Observable.create();
  private final Observable<Boolean> showKeyBoardObservable = Observable.create();
  private final Observable<Void> switchToEditTextView = Observable.create();
  private final Observable<Void> switchToTextView = Observable.create();
  private final ObservableTextWatcher textWatcher =
      (ObservableTextWatcher) ObservableTextWatcher.create().bind(new Observer<String>() {
        @Override public void update(String value) {
          editableTextObservable.set(value);
          textObservable.set(value);
        }
      });
  private final TextView.OnEditorActionListener editorActionListener =
      new TextView.OnEditorActionListener() {
        @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
          if (actionId == EditorInfo.IME_ACTION_DONE) {
            switchToTextView.set(null);
            showKeyBoardObservable.set(false);
            return true;
          }
          return false;
        }
      };
  private final View.OnClickListener textClickListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      switchToEditTextView.set(null);
      showKeyBoardObservable.set(true);
    }
  };

  @Inject public ClickToEditViewModelImpl() {
  }

  @Override public void bindEditableTextObserver(Observer<String> observer) {
    editableTextObservable.bind(observer);
  }

  @Override public void bindTextObserver(Observer<String> observer) {
    textObservable.bind(observer);
  }

  @Override public TextWatcher textWatcher() {
    return textWatcher;
  }

  @Override public void unbindEditableTextObserver(Observer<String> observer) {
    editableTextObservable.unbind(observer);
  }

  @Override public void unbindTextObserver(Observer<String> observer) {
    textObservable.unbind(observer);
  }

  @Override public void bindShowKeyboardObserver(Observer<Boolean> observer) {
    showKeyBoardObservable.bind(observer);
  }

  @Override public TextView.OnEditorActionListener editorActionListener() {
    return editorActionListener;
  }

  @Override public void bindSwitchToEditTextViewObserver(Observer<Void> observer) {
    switchToEditTextView.bind(observer);
  }

  @Override public View.OnClickListener textViewClickListener() {
    return textClickListener;
  }

  @Override public void bindSwitchToTextViewObserver(Observer<Void> observer) {
    switchToTextView.bind(observer);
  }
}
