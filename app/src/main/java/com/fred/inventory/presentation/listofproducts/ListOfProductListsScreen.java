package com.fred.inventory.presentation.listofproducts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.R;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.presentation.listofproducts.views.ListOfProductListsView;
import com.fred.inventory.presentation.listofproducts.views.ListOfProductListsViewImpl;
import com.fred.inventory.presentation.navigation.NavigationListener;

public class ListOfProductListsScreen extends BaseScreen {
  public static ListOfProductListsScreen newInstance() {
    return new ListOfProductListsScreen();
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ListOfProductListsViewImpl listOfProductListsView =
        (ListOfProductListsViewImpl) inflater.inflate(R.layout.list_of_products, null);
    setClickListeners(listOfProductListsView);
    return listOfProductListsView;
  }

  private void setClickListeners(ListOfProductListsViewImpl listOfProductListsView) {
    listOfProductListsView.addListOfProductListsClickListener(
        new ListOfProductListsView.ListOfProductListsClickListener() {
          @Override public void onAddButtonClicked() {
            notifyNavigationListenersThatAddButtonWasClicked();
          }
        });
  }

  private void notifyNavigationListenersThatAddButtonWasClicked() {
    for (NavigationListener listener : navigationListeners)
      listener.onAddProductListButtonClicked();
  }

  @Override protected boolean handleBackPress() {
    return false;
  }
}
