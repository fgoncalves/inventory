package com.fred.inventory.presentation.listofproducts.views;

import rx.Observable;

/**
 * The list of product lists
 * <p/>
 * Created by fred on 13.03.16.
 */
public interface ListOfProductListsView {
  enum ListOfProductListsEvent {
    ADD_BUTTON_CLICKED
  }

  Observable<ListOfProductListsEvent> interactions();
}
