package com.fred.inventory.presentation.productlist;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.databinding.ProductListBinding;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.presentation.productlist.modules.ProductListModule;
import com.fred.inventory.presentation.productlist.viewmodels.ProductListViewModel;
import javax.inject.Inject;
import rx.Observable;

public class ProductListScreen extends BaseScreen {
  @Inject ProductListViewModel viewModel;

  public static ProductListScreen newInstance() {
    return new ProductListScreen();
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ProductListBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.product_list, container, false);

    MainActivity.scoped(new ProductListModule()).inject(this);
    binding.setViewModel(viewModel);

    return binding.getRoot();
  }

  @Override protected boolean handleBackPress() {
    return false;
  }

  @Override public Observable<ScreenEvent> screenEvents() {
    return Observable.empty();
  }
}
