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

public class ClickToEditTextPresenterImplTest {
  @Mock ClickToEditTextView view;

  private ClickToEditTextPresenterImpl presenter;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    presenter = new ClickToEditTextPresenterImpl(view);
  }

  @Test public void onTextViewClicked_shouldTellViewToBecomeEditable() {
    presenter.onTextViewClicked();

    verify(view).becomeEditable();
  }

  @Test public void onTextViewClicked_shouldTellViewRequestFocusForEditTextAndThenShowKeyboard() {
    presenter.onTextViewClicked();

    InOrder inOrder = inOrder(view);

    inOrder.verify(view).requestFocusOnEditText();
    inOrder.verify(view).showKeyboard();
  }

  @Test public void onActionDone_shouldTellViewToBecomeUneditableWhenKeyboardDoneIsPressed() {
    boolean result = presenter.onKeyboardKeyPressed(EditorInfo.IME_ACTION_DONE);

    verify(view).becomeUneditable();
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
}