package com.fred.inventory.utils.binding.widgets;

import android.text.Editable;
import com.fred.inventory.utils.binding.Observer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ObservableTextWatcherTest {
  @Mock Observer<String> editableObserver;

  private String currentText = "current text";
  private ObservableTextWatcher observableTextWatcher;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    observableTextWatcher = ObservableTextWatcher.create();
    observableTextWatcher.set(currentText);
    observableTextWatcher.bind(editableObserver);
  }

  @Test public void afterTextChanged_shouldFireIfTheValuesAreDifferent() {
    Editable anotherEditable = mock(Editable.class);
    String expected = "some text";
    when(anotherEditable.toString()).thenReturn(expected);

    observableTextWatcher.afterTextChanged(anotherEditable);

    verify(editableObserver).update(expected);
  }

  @Test public void afterTextChanged_shouldNotFireIfTheValuesAreDifferent() {
    Editable anotherEditable = mock(Editable.class);
    when(anotherEditable.toString()).thenReturn(currentText);

    observableTextWatcher.afterTextChanged(anotherEditable);

    verify(editableObserver, never()).update(any(String.class));
  }
}
