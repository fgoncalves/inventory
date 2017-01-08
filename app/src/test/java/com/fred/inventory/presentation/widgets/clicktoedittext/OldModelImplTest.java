package com.fred.inventory.presentation.widgets.clicktoedittext;

import android.databinding.Observable;
import android.databinding.ObservableField;
import android.text.Editable;
import android.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OldModelImplTest {
  @Mock Observable.OnPropertyChangedCallback modePropertyChangedCallback;
  @Mock Observable.OnPropertyChangedCallback textPropertyChangedCallback;
  private ClickToEditTextViewModelImpl viewModel;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    viewModel = new ClickToEditTextViewModelImpl();

    viewModel.mode().addOnPropertyChangedCallback(modePropertyChangedCallback);
    viewModel.text().addOnPropertyChangedCallback(textPropertyChangedCallback);
  }

  @Test public void onClick_shouldSetTheModeToEdit() {
    ClickToEditTextViewMode expected = ClickToEditTextViewMode.EDIT;
    viewModel.clickListener().onClick(mock(View.class));

    ArgumentCaptor<ObservableField<ClickToEditTextViewMode>> captor =
        ArgumentCaptor.forClass(ObservableField.class);
    verify(modePropertyChangedCallback).onPropertyChanged(captor.capture(), anyInt());
    ClickToEditTextViewMode result = captor.getValue().get();
    assertThat(result).isEqualTo(expected);
  }

  @Test public void onTextChanged_shouldSetTheTextInTheObserver() {
    String expected = "This would be the text";
    Editable editable = mock(Editable.class);
    when(editable.toString()).thenReturn(expected);

    viewModel.textWatcher().afterTextChanged(editable);

    ArgumentCaptor<ObservableField<String>> captor = ArgumentCaptor.forClass(ObservableField.class);
    verify(textPropertyChangedCallback).onPropertyChanged(captor.capture(), anyInt());
    assertThat(captor.getValue().get()).isEqualTo(expected);
  }

  @Test public void onFocusChanged_shouldSetTheEditTextToEditable() {
    ClickToEditTextViewMode expected = ClickToEditTextViewMode.EDIT;

    viewModel.editTextFocusChangeListener().onFocusChange(mock(View.class), true);

    ArgumentCaptor<ObservableField<ClickToEditTextViewMode>> captor =
        ArgumentCaptor.forClass(ObservableField.class);
    verify(modePropertyChangedCallback).onPropertyChanged(captor.capture(), anyInt());
    ClickToEditTextViewMode result = captor.getValue().get();
    assertThat(result).isEqualTo(expected);
  }

  @Test public void onModeChanged_shouldNotSetTheModeIfItIsTheSame() {
    viewModel.modeChangedListener().onModeChanged(ClickToEditTextViewMode.TEXT);

    verify(modePropertyChangedCallback, never()).onPropertyChanged(any(Observable.class), anyInt());
  }

  @Test public void onModeChanged_shouldSetTheEditTextToEditable() {
    ClickToEditTextViewMode expected = ClickToEditTextViewMode.EDIT;

    viewModel.modeChangedListener().onModeChanged(expected);

    ArgumentCaptor<ObservableField<ClickToEditTextViewMode>> captor =
        ArgumentCaptor.forClass(ObservableField.class);
    verify(modePropertyChangedCallback).onPropertyChanged(captor.capture(), anyInt());
    ClickToEditTextViewMode result = captor.getValue().get();
    assertThat(result).isEqualTo(expected);
  }
}