package com.fred.inventory.presentation.listofproducts;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.databinding.ListOfProductsBinding;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.presentation.listofproducts.modules.ListOfProductListsModule;
import com.fred.inventory.presentation.listofproducts.viewmodels.ListOfProductListsViewModel;
import javax.inject.Inject;

public class ListOfProductListsScreen extends BaseScreen {
  @Inject ListOfProductListsViewModel viewModel;

  public static ListOfProductListsScreen newInstance() {
    return new ListOfProductListsScreen();
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ListOfProductsBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.list_of_products, container, false);

    MainActivity.scoped(new ListOfProductListsModule()).inject(this);
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
}
