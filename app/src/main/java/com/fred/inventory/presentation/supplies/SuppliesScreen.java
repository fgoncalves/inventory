package com.fred.inventory.presentation.supplies;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.databinding.ListOfProductsBinding;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.di.SupplyListsModule;
import com.fred.inventory.presentation.supplies.viewmodels.SuppliesViewModel;
import javax.inject.Inject;

public class SuppliesScreen extends BaseScreen {
  @Inject SuppliesViewModel viewModel;

  public static SuppliesScreen newInstance() {
    return new SuppliesScreen();
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ListOfProductsBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.list_of_products, container, false);

    MainActivity.scoped(new SupplyListsModule()).inject(this);
    binding.setViewModel(viewModel);

    return binding.getRoot();
  }

  @Override public void onResume() {
    super.onResume();
    viewModel.onResume();
  }

  @Override public void onPause() {
    super.onPause();
    viewModel.onPause();
  }

  @Override protected boolean handleBackPress() {
    return false;
  }

  @Override public Toolbar getToolbar() {
    return (Toolbar) getView().findViewById(R.id.list_of_lists_toolbar);
  }

  @Override protected String getToolbarTitle() {
    return getString(R.string.lists);
  }

  @Override protected int getMenuResource() {
    return NO_RESOURCE_ID;
  }

  @Override protected boolean isHomeButtonSupported() {
    return false;
  }

  @Override protected boolean supportsDrawer() {
    return true;
  }
}
