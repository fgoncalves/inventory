package com.fred.inventory.presentation.productlist;

import com.fred.inventory.presentation.productlist.adapters.ListOfProductListsAdapter;

/**
 * The list of product lists
 * <p/>
 * Created by fred on 13.03.16.
 */
public interface ListOfProductListsView {
  interface ListOfProductListsClickListener {
    void onAddButtonClicked();
  }

  void showEmptyView();

  void hideEmptyView();

  void setAdapter(ListOfProductListsAdapter adapter);

  void displayListAllProductListsError();

  void addListOfProductListsClickListener(ListOfProductListsClickListener listener);

  void removeListOfProductListsClickListener(ListOfProductListsClickListener listener);

  void notifyListenersOfAddButtonClick();
}
