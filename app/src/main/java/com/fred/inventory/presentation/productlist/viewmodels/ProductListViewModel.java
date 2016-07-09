package com.fred.inventory.presentation.productlist.viewmodels;

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

  void bindEmptyViewVisibilityObserver(Observer<Integer> observer);

  void bindShowKeyboardObserver(Observer<Boolean> observer);

  void unbindEmptyViewVisibilityObserver(Observer<Integer> observer);

  void unbindShowKeyboardObserver(Observer<Boolean> observer);

  void onDetachedFromWindow();
}
