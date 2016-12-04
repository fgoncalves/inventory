package com.fred.inventory.presentation.items.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.SpinnerAdapter;
import java.util.Date;

/**
 * View model for an item, a.k.a. product.
 */
public interface ItemViewModel {
  void onResume();

  void onPause();

  void forProductList(String productListId);

  void forProduct(String productId);

  ObservableInt spinnerSelection();

  void onEditExpireDateButtonClick(View view);

  void onDoneButtonClick(View view);

  ObservableField<Date> expirationDateObservable();

  ObservableField<String> itemNameObservable();

  ObservableField<String> itemNameError();

  TextWatcher itemNameTextWatcher();

  TextWatcher itemQuantityLabelTextWatcher();

  ObservableInt seekBarVisibility();

  SpinnerAdapter itemSpinnerAdapter();

  AdapterView.OnItemSelectedListener spinnerOnItemSelectedListener();

  ObservableField<String> itemQuantityLabel();

  ObservableInt seekBarProgress();

  SeekBar.OnSeekBarChangeListener seekBarChangeListener();
}
