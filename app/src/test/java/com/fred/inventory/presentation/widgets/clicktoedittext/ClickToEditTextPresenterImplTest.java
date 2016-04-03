package com.fred.inventory.presentation.widgets.clicktoedittext;

import android.view.inputmethod.EditorInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClickToEditTextPresenterImplTest {
  @Mock ClickToEditTextView view;

  private ClickToEditTextPresenterImpl presenter;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    presenter = new ClickToEditTextPresenterImpl(view);
  }

  @Test public void onTextViewClicked_shouldTellViewToBecomeEditableIfItWasNotEditable() {
    when(view.isEditable()).thenReturn(false);

    presenter.onTextViewClicked();

    verify(view).becomeEditable();
  }

  @Test public void onTextViewClicked_shouldTellViewNotToBecomeEditableIfItWasAlreadyEditable() {
    when(view.isEditable()).thenReturn(true);

    presenter.onTextViewClicked();

    verify(view, never()).becomeEditable();
  }

  @Test public void onTextViewClicked_shouldTellViewRequestFocusForEditTextAndThenShowKeyboard() {
    presenter.onTextViewClicked();

    InOrder inOrder = inOrder(view);

    inOrder.verify(view).requestFocusOnEditText();
    inOrder.verify(view).showKeyboard();
  }

  @Test
  public void onActionDone_shouldTellViewToBecomeUneditableWhenKeyboardDoneIsPressedIfViewWasEditable() {
    when(view.isEditable()).thenReturn(true);

    boolean result = presenter.onKeyboardKeyPressed(EditorInfo.IME_ACTION_DONE);

    verify(view).becomeUneditable();
    assertThat(result).isTrue();
  }

  @Test
  public void onActionDone_shouldNotTellViewToBecomeUneditableWhenKeyboardDoneIsPressedIfViewWasAlreadyUneditable() {
    when(view.isEditable()).thenReturn(false);

    boolean result = presenter.onKeyboardKeyPressed(EditorInfo.IME_ACTION_DONE);

    verify(view, never()).becomeUneditable();
    assertThat(result).isTrue();
  }

  @Test public void onActionDone_shouldTellViewToDismissKeyboard() {
    boolean result = presenter.onKeyboardKeyPressed(EditorInfo.IME_ACTION_DONE);

    verify(view).dismissKeyboard();
    assertThat(result).isTrue();
  }

  @Test public void onActionDone_shouldNotTellViewToBecomeUneditableIfKeyboardDoneIsNotPressed() {
    boolean result = presenter.onKeyboardKeyPressed(EditorInfo.IME_ACTION_NEXT);

    verify(view, never()).becomeUneditable();
    assertThat(result).isFalse();
  }

  @Test public void onTextEntered_shouldTellViewToSetTheTextInTheTextView() {
    String expectedText = "This should be the text to display";

    presenter.onTextEntered(expectedText);

    verify(view).setTextViewText(expectedText);
  }

  @Test public void setText_shouldTellViewToSetTheTextInTheEditTextVew() {
    String expectedText = "some text";

    presenter.setText(expectedText);

    verify(view).setEditTextText(expectedText);
  }

  @Test
  public void onShowKeyboardRequest_shouldTellViewToBecomeEditableIfViewWasNotPreviouslyEditable() {
    when(view.isEditable()).thenReturn(false);

    presenter.onShowKeyboardRequest();

    verify(view).becomeEditable();
  }

  @Test
  public void onShowKeyboardRequest_shouldNotTellViewToBecomeEditableIfViewWasAlreadyEditableEditable() {
    when(view.isEditable()).thenReturn(true);

    presenter.onShowKeyboardRequest();

    verify(view, never()).becomeEditable();
  }

  @Test public void onShowKeyboardRequest_shouldRequestFocusOnEditText() {
    presenter.onShowKeyboardRequest();

    verify(view).requestFocusOnEditText();
  }

  @Test public void onShowKeyboardRequest_shouldShowKeyboard() {
    presenter.onShowKeyboardRequest();

    verify(view).showKeyboard();
  }
}