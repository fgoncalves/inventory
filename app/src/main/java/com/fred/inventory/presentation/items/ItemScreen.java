package com.fred.inventory.presentation.items;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.databinding.ItemBinding;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.presentation.items.modules.ItemViewModule;
import com.fred.inventory.presentation.items.viewmodels.ItemViewModel;
import javax.inject.Inject;

public class ItemScreen extends BaseScreen {
  private static final String PRODUCT_LIST_ID = "product.list.id";
  private static final String PRODUCT_ID = "product.id";

  @Inject ItemViewModel viewModel;
  private Toolbar toolbar;

  /**
   * Create an instance of an item screen which will display a new item for the given product
   * list.
   *
   * @param productListId The product list id to display the item for
   * @return The instance of the screen to use
   */
  public static ItemScreen newInstance(@NonNull Long productListId) {
    ItemScreen itemScreen = new ItemScreen();

    Bundle args = new Bundle();
    args.putLong(PRODUCT_LIST_ID, productListId);
    itemScreen.setArguments(args);

    return itemScreen;
  }

  /**
   * Create an instance of an item screen which will display the given item for the given product
   * list.
   *
   * @param productListId The product list id to display the item for
   * @param productId The product to display
   * @return The instance of the screen to use
   */
  public static ItemScreen newInstance(@NonNull Long productListId, @NonNull Long productId) {
    ItemScreen itemScreen = new ItemScreen();

    Bundle args = new Bundle();
    args.putLong(PRODUCT_LIST_ID, productListId);
    args.putLong(PRODUCT_ID, productId);
    itemScreen.setArguments(args);

    return itemScreen;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.item, container, false);

    MainActivity.scoped(new ItemViewModule()).inject(this);
    binding.setViewModel(viewModel);

    return binding.getRoot();
  }

  @Override public void onResume() {
    super.onResume();
    if (getArguments().containsKey(PRODUCT_LIST_ID)) {
      viewModel.forProductList(getArguments().getLong(PRODUCT_LIST_ID));
    }
    if (getArguments().containsKey(PRODUCT_ID)) {
      viewModel.forProduct(getArguments().getLong(PRODUCT_ID));
    }
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
    if (toolbar == null) toolbar = (Toolbar) getView().findViewById(R.id.item_toolbar);
    return toolbar;
  }

  @Override protected String getToolbarTitle() {
    return toolbar.getTitle().toString();
  }

  @Override protected int getMenuResource() {
    return NO_RESOURCE_ID;
  }

  @Override protected boolean isHomeButtonSupported() {
    return true;
  }
}
