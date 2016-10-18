package com.fred.inventory.presentation.items.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextWatcher;
import android.view.View;
import com.fred.inventory.presentation.widgets.clicktoedittext.ClickToEditTextView;
import java.util.Date;

/**
 * View model for an item, a.k.a. product.
 */
public interface ItemViewModel {
  void onResume();

  void onPause();

  void forProductList(String productListId);

  void forProduct(String productId);

  void onEditExpireDateButtonClick(View view);

  void onDoneButtonClick(View view);

  ObservableField<Date> expirationDateObservable();

  ObservableField<String> itemNameObservable();

  ObservableInt quantityObservable();

  ObservableInt knownQuantityObservable();

  ObservableInt uncertainQuantityMaximumObservable();

  ObservableInt viewSwitcherDisplayedChildObservable();

  ObservableField<ClickToEditTextView.ClickToEditTextViewState> uncertainQuantityObservable();

  ObservableField<String> uncertainQuantityUnitObservable();

  View.OnTouchListener viewSwitcherOnTouchListener();

  TextWatcher uncertainQuantityMaximumTextWatcher();
}
