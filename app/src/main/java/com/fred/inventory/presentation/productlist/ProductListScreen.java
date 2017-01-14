package com.fred.inventory.presentation.productlist;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.databinding.ProductListBinding;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.presentation.productlist.modules.ProductListModule;
import com.fred.inventory.presentation.productlist.viewmodels.ProductListViewModel;
import com.fred.inventory.utils.binding.Observer;
import com.fred.inventory.utils.path.PathManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import icepick.Icepick;
import icepick.Icicle;
import javax.inject.Inject;

public class ProductListScreen extends BaseScreen {
  private static final String PRODUCT_LIST_ID_BUNDLE_KEY = "product.lis.id";
  private final Observer<Long> productListIdObserver = new Observer<Long>() {
    @Override public void update(Long value) {
      productListId = value;
    }
  };

  @Inject ProductListViewModel viewModel;
  @Inject PathManager pathManager;

  @Icicle Long productListId;
  private ValueAnimator toolbarValueAnimator;

  /**
   * Create a product list screen for no product list in specific. Use this when creating a new
   * list.
   *
   * @return The product list screen
   */
  public static ProductListScreen newInstance() {
    return new ProductListScreen();
  }

  /**
   * Create a product list to display the given product list.
   *
   * @param productListId Product list id of the list to show
   * @return The product list screen
   */
  public static ProductListScreen newInstance(Long productListId) {
    ProductListScreen fragment = new ProductListScreen();
    Bundle args = new Bundle();
    args.putLong(PRODUCT_LIST_ID_BUNDLE_KEY, productListId);
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ProductListBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.product_list, container, false);

    MainActivity.scoped(new ProductListModule()).inject(this);
    binding.setViewModel(viewModel);

    viewModel.setOnScanBarCodeButtonClickListener(this::startBarcodeScanner);

    toolbarValueAnimator = createToolbarValueAnimator();

    return binding.getRoot();
  }

  @Override protected boolean handleBackPress() {
    return false;
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (productListId == null) return;
    Icepick.saveInstanceState(this, outState);
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (getArguments() != null) {
      if (getArguments().containsKey(PRODUCT_LIST_ID_BUNDLE_KEY)) {
        productListId = getArguments().getLong(PRODUCT_LIST_ID_BUNDLE_KEY);
      }
    }
    Icepick.restoreInstanceState(this, savedInstanceState);
    viewModel.forProductList(productListId);
    viewModel.onActivityCreated();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    viewModel.onDestroyView();
  }

  @Override public void onStart() {
    super.onStart();
    viewModel.bindProductListIdObserver(productListIdObserver);
  }

  @Override public void onStop() {
    super.onStop();
    viewModel.unbindProductListIdObserver(productListIdObserver);
  }

  @Override public Toolbar getToolbar() {
    return (Toolbar) getView().findViewById(R.id.product_list_toolbar);
  }

  @Override protected String getToolbarTitle() {
    return "";
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
    if (result != null) {
      if (result.getContents() != null) {
        viewModel.onCodeScanned(result.getContents());
      }
    } else {
      super.onActivityResult(requestCode, resultCode, data);
    }
  }

  @Override protected int getMenuResource() {
    return R.menu.product_list_menu;
  }

  @Override protected boolean isHomeButtonSupported() {
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.toolbar_done_button) {
      viewModel.onDoneButtonClick(item.getActionView());
      return true;
    }
    if (item.getItemId() == R.id.action_search) {
      viewModel.onSearchButtonClicked(item.getActionView());
      animateToolbarTransition();
      return true;
    }
    if (item.getItemId() == android.R.id.home) {
      if (viewModel.onHomeButtonPressed()) {
        reverseToolbarTransition();
        return true;
      }
      pathManager.back();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void reverseToolbarTransition() {
    toolbarValueAnimator.reverse();
  }

  private void animateToolbarTransition() {
    toolbarValueAnimator.start();
  }

  @NonNull private ValueAnimator createToolbarValueAnimator() {
    int from = ContextCompat.getColor(getActivity(), R.color.primary);
    int to = ContextCompat.getColor(getActivity(), R.color.primary_opposite);
    ValueAnimator valueAnimator = ValueAnimator.ofArgb(from, to);
    valueAnimator.setDuration(500);
    valueAnimator.addUpdateListener(animator -> {
      getToolbar().setBackgroundColor((Integer) animator.getAnimatedValue());
    });
    return valueAnimator;
  }

  private void startBarcodeScanner() {
    IntentIntegrator.forFragment(this).initiateScan();
  }
}
