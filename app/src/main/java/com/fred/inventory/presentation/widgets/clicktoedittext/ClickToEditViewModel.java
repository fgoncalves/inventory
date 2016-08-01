package com.fred.inventory.presentation.widgets.clicktoedittext;

import android.support.annotation.NonNull;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import com.fred.inventory.utils.binding.Observer;

/**
 * View model interface for the click to edit widgets
 * <p/>
 * Created by fred on 29.05.16.
 */
public interface ClickToEditViewModel {
  void bindEditableTextObserver(Observer<String> observer);

  void bindTextObserver(Observer<String> observer);

  TextWatcher textWatcher();

  void unbindEditableTextObserver(Observer<String> observer);

  void unbindTextObserver(Observer<String> observer);

  void bindShowKeyboardObserver(Observer<Boolean> observer);

  TextView.OnEditorActionListener editorActionListener();

  void bindSwitchToEditTextViewObserver(Observer<Void> observer);

  View.OnClickListener textViewClickListener();

  void bindSwitchToTextViewObserver(Observer<Void> observer);

  void attachModel(@NonNull String text);

  void onAttachToWindow();
}
