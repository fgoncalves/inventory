package com.fred.inventory.presentation.globalsearch;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.databinding.GlobalSearchBinding;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.di.GlobalSearchModule;
import com.fred.inventory.presentation.globalsearch.viewmodels.GlobalSearchViewModel;
import javax.inject.Inject;

public class GlobalSearchScreen extends BaseScreen {
  @Inject GlobalSearchViewModel viewModel;

  public static GlobalSearchScreen newInstance() {
    return new GlobalSearchScreen();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    GlobalSearchBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.global_search, container, false);

    MainActivity.scoped(new GlobalSearchModule()).inject(this);
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

  @Override protected int getMenuResource() {
    return R.menu.global_search_tool_bar_menu;
  }

  @Override protected boolean isHomeButtonSupported() {
    return true;
  }

  @Override protected boolean handleBackPress() {
    return false;
  }

  @Override protected Toolbar getToolbar() {
    return (Toolbar) getView().findViewById(R.id.global_search_toolbar);
  }

  @Override protected String getToolbarTitle() {
    return "";
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_global_search) {
      viewModel.onSearchButtonClicked(item);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected boolean supportsDrawer() {
    return false;
  }
}
