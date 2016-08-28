package com.fred.inventory.presentation.productlist.viewmodels;

import android.text.TextWatcher;
import android.view.View;
import com.fred.inventory.presentation.productlist.models.Error;
import com.fred.inventory.presentation.productlist.models.ProductListScreenState;
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

  void onAttachedToWindow();

  void bindProductListScreenStateObserver(Observer<ProductListScreenState> observer);

  void unbindProductListScreenStateObserver(Observer<ProductListScreenState> observer);

  void bindProductNameObserver(Observer<String> observer);

  void unbindProductNameObserver(Observer<String> observer);

  void onDetachedFromWindow();

  TextWatcher productNameTextWatcher();

  View.OnClickListener doneButtonClickListener();

  View.OnClickListener addButtonClickListener();
}
