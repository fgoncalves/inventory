package com.fred.inventory.presentation.itemlist;

/**
 * Presenter for the item list
 * <p/>
 * Created by fred on 21.03.16.
 */
public interface ItemListPresenter {
  void forId(String productListId);

  void onAttachedToWindow();

  void onDoneButtonClicked();
}
