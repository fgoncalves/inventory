package com.fred.inventory.presentation.productlist.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextWatcher;
import android.view.View;
import com.fred.inventory.utils.binding.Observer;

/**
 * View model for the product list
 * <p/>
 * Created by fred on 09.07.16.
 */
public interface ProductListViewModel {
  /***
   * Prepare this view model to show details for this product list
   *
   * @param id The id of the product list
   */
  void forProductList(String id);

  void onActivityCreated();

  void onDestroyView();

  ObservableField<String> productListName();

  TextWatcher productNameTextWatcher();

  void onDoneButtonClick(View view);

  void onAddButtonClick(View view);

  ObservableInt emptyListVisibility();

  ObservableInt itemListVisibility();

  void unbindProductListIdObserver(Observer<String> observer);

  void bindProductListIdObserver(Observer<String> observer);
}
