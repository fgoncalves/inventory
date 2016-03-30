package com.fred.inventory.presentation.productlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.R;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.presentation.productlist.views.ProductListViewImpl;

public class ProductListScreen extends BaseScreen {
  public static ProductListScreen newInstance() {
    return new ProductListScreen();
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ProductListViewImpl listOfProductListsView =
        (ProductListViewImpl) inflater.inflate(R.layout.product_list, null);
    return listOfProductListsView;
  }

  @Override protected boolean handleBackPress() {
    return false;
  }
}
