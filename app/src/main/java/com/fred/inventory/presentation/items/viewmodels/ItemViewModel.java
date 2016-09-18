package com.fred.inventory.presentation.items.viewmodels;

import android.app.DatePickerDialog;
import com.fred.inventory.presentation.items.models.ItemScreenModel;
import com.fred.inventory.utils.binding.Observer;
import java.util.Date;

/**
 * View model for an item, a.k.a. product.
 */
public interface ItemViewModel {
  void onAttachedToWindow();

  void onDetachedFromWindow();

  void bindExpirationDateObserver(Observer<Date> observer);

  void unbindExpirationDateObserver(Observer<Date> observer);

  void forProductList(String productListId);

  void forProduct(String productId);

  void bindProductNameObserver(Observer<String> observer);

  void bindItemScreenModelObserver(Observer<ItemScreenModel> observer);

  void unbindProductNameObserver(Observer<String> observer);

  void unbindItemScreenModelObserver(Observer<ItemScreenModel> observer);

  DatePickerDialog.OnDateSetListener onDateSetListener();
}
