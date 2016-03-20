package com.fred.inventory.presentation.productlist;

import android.support.annotation.IdRes;

/**
 * Presenter for the list of products
 * <p/>
 * Created by fred on 13.03.16.
 */
public interface ListOfProductListsPresenter {
  void onAttachedToWindow();

  void onDetachedFromWindow();

  void onAddButtonClicked();
}
