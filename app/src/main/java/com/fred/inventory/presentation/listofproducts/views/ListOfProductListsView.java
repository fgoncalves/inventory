package com.fred.inventory.presentation.listofproducts.views;

import com.fred.inventory.presentation.listofproducts.adapters.ListOfProductListsAdapter;
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

  void showEmptyView();

  void hideEmptyView();

  void setAdapter(ListOfProductListsAdapter adapter);

  void displayListAllProductListsError();

  Observable<ListOfProductListsEvent> interactions();
}
