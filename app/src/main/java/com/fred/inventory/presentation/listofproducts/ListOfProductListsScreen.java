package com.fred.inventory.presentation.listofproducts;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fred.inventory.R;
import com.fred.inventory.presentation.listofproducts.views.ListOfProductListsView;
import com.fred.inventory.presentation.listofproducts.views.ListOfProductListsViewImpl;

/**
 * This class is the class
 * <p/>
 * Created by fred on 20.03.16.
 */
public class ListOfProductListsScreen extends Fragment {
  @Bind(R.id.toolbar) Toolbar toolbar;

  public static ListOfProductListsScreen newInstance() {
    return new ListOfProductListsScreen();
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ListOfProductListsViewImpl listOfProductListsView =
        (ListOfProductListsViewImpl) inflater.inflate(R.layout.list_of_products, null);
    setClickListeners(listOfProductListsView);
    ButterKnife.bind(this, listOfProductListsView);
    return listOfProductListsView;
  }

  @Override public void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }

  private void setClickListeners(ListOfProductListsViewImpl listOfProductListsView) {
    listOfProductListsView.addListOfProductListsClickListener(
        new ListOfProductListsView.ListOfProductListsClickListener() {
          @Override public void onAddButtonClicked() {
            Toast.makeText(getActivity(), "This theory works fine", Toast.LENGTH_SHORT).show();
          }
        });
  }
}
