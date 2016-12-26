package com.fred.inventory.presentation.widgets.clicktoedittext;

import android.support.annotation.NonNull;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import com.fred.inventory.utils.binding.Observable;
import com.fred.inventory.utils.binding.Observer;
import com.fred.inventory.utils.binding.widgets.ObservableTextWatcher;
import javax.inject.Inject;

public class OldClickToEditViewModelImpl implements OldClickToEditViewModel {
  private final Observable<String> editableTextObservable = Observable.create();
  private final Observable<String> textObservable = Observable.create();
  private final Observable<Boolean> showKeyBoardObservable = Observable.create();
  private final Observable<Void> switchToEditTextView = Observable.create();
  private final Observable<Void> switchToTextView = Observable.create();
  private final Observable<OldClickToEditTextView.ClickToEditTextViewState> stateObservable =
      Observable.create();
  private final ObservableTextWatcher textWatcher =
      (ObservableTextWatcher) ObservableTextWatcher.create().bind(value -> {
        editableTextObservable.set(value);
        textObservable.set(value);
      });
  private final TextView.OnEditorActionListener editorActionListener = (v, actionId, event) -> {
    if (actionId == EditorInfo.IME_ACTION_DONE) {
      stateObservable.set(OldClickToEditTextView.ClickToEditTextViewState.NON_EDITABLE);
      showKeyBoardObservable.set(false);
      return true;
    }
    return false;
  };
  private final View.OnClickListener textClickListener = v -> {
    stateObservable.set(OldClickToEditTextView.ClickToEditTextViewState.EDITABLE);
    showKeyBoardObservable.set(true);
  };
  // This one is simply internal
  private final Observer<OldClickToEditTextView.ClickToEditTextViewState> stateObserver = value -> {
    switch (value) {
      case NON_EDITABLE:
        switchToTextView.set(null);
        break;
      case EDITABLE:
        switchToEditTextView.set(null);
    }
  };

  @Inject public OldClickToEditViewModelImpl() {
  }

  @Override public void onAttachToWindow() {
    editableTextObservable.set(textObservable.get());
    textObservable.set(textObservable.get());
    stateObservable.set(OldClickToEditTextView.ClickToEditTextViewState.NON_EDITABLE);
    stateObservable.bind(stateObserver);
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

  @Override public void attachModel(@NonNull String text) {
    textObservable.set(text);
  }

  @Override public void setState(OldClickToEditTextView.ClickToEditTextViewState state) {
    stateObservable.set(state);
  }
}
