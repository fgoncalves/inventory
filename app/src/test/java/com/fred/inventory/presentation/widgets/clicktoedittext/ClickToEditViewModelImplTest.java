package com.fred.inventory.presentation.widgets.clicktoedittext;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import com.fred.inventory.utils.binding.Observer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClickToEditViewModelImplTest {
  @Mock Observer<String> editableObserver;
  @Mock Observer<String> textObserver;
  @Mock Observer<Boolean> keyboardObserver;
  @Mock Observer<Void> switchToEditTextViewObserver;
  @Mock Observer<Void> switchToTextViewObserver;

  private ClickToEditViewModelImpl clickToEditViewModel;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    clickToEditViewModel = new ClickToEditViewModelImpl();
    clickToEditViewModel.bindEditableTextObserver(editableObserver);
    clickToEditViewModel.bindTextObserver(textObserver);
    clickToEditViewModel.bindShowKeyboardObserver(keyboardObserver);
    clickToEditViewModel.bindSwitchToEditTextViewObserver(switchToEditTextViewObserver);
    clickToEditViewModel.bindSwitchToTextViewObserver(switchToTextViewObserver);
  }

  @Test public void bindEditableTextObserver_shouldChangeUpdateTheTextObserverIfTextChanges() {
    TextWatcher textWatcher = clickToEditViewModel.textWatcher();
    String expected = "This would be the text on both views";
    Editable editable = mock(Editable.class);
    when(editable.toString()).thenReturn(expected);

    textWatcher.afterTextChanged(editable);

    verify(editableObserver).update(expected);
    verify(textObserver).update(expected);
  }

  @Test
  public void unbindEditableTextObserver_shouldUnbindObserversInAWayThatUpdatesAreNoLongerReceived() {
    TextWatcher textWatcher = clickToEditViewModel.textWatcher();
    String value = "This would be the text on both views";
    Editable editable = mock(Editable.class);
    when(editable.toString()).thenReturn(value);

    clickToEditViewModel.unbindEditableTextObserver(editableObserver);
    clickToEditViewModel.unbindTextObserver(textObserver);
    textWatcher.afterTextChanged(editable);

    verify(editableObserver, never()).update(value);
    verify(textObserver, never()).update(value);
  }

  @Test
  public void bindKeyboardObserver_shouldBindObservablesInAWayThatPressingDoneWouldHideTheKeyboard() {
    TextView.OnEditorActionListener onEditorActionListener =
        clickToEditViewModel.editorActionListener();

    onEditorActionListener.onEditorAction(null, EditorInfo.IME_ACTION_DONE, null);

    verify(keyboardObserver).update(false);
  }

  @Test
  public void bindKeyboardObserver_shouldBindObservablesInAWayThatPressingAnotherKeyThanDoneShouldNotHideTheKeyboard() {
    TextView.OnEditorActionListener onEditorActionListener =
        clickToEditViewModel.editorActionListener();

    onEditorActionListener.onEditorAction(null, EditorInfo.IME_ACTION_GO, null);

    verify(keyboardObserver, never()).update(anyBoolean());
  }

  @Test
  public void bindSwitchToEditTextViewObserver_shouldBindObservablesInAWayThatClickingOnTheTextViewWillSwitchToEditTextView() {
    View.OnClickListener clickListener = clickToEditViewModel.textViewClickListener();

    clickListener.onClick(mock(View.class));

    verify(switchToEditTextViewObserver).update(null);
  }

  @Test public void textViewClickListener_shouldShowKeyboardWhenClicked() {
    View.OnClickListener clickListener = clickToEditViewModel.textViewClickListener();

    clickListener.onClick(mock(View.class));

    verify(keyboardObserver).update(true);
  }

  @Test
  public void bindSwitchToTextViewObserver_shouldBindObservablesInAWayThatClickingTheDoneButtonShouldSwitchToTheTextView() {
    TextView.OnEditorActionListener onEditorActionListener =
        clickToEditViewModel.editorActionListener();

    onEditorActionListener.onEditorAction(null, EditorInfo.IME_ACTION_DONE, null);

    verify(switchToTextViewObserver).update(null);
  }
}
