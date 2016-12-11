package com.fred.inventory.presentation.productlist;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.databinding.ProductListBinding;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.presentation.productlist.modules.ProductListModule;
import com.fred.inventory.presentation.productlist.viewmodels.ProductListViewModel;
import com.fred.inventory.utils.binding.Observer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import icepick.Icepick;
import icepick.Icicle;
import javax.inject.Inject;

public class ProductListScreen extends BaseScreen {
  private static final String PRODUCT_LIST_ID_BUNDLE_KEY = "product.lis.id";
  private final Observer<String> productListIdObserver = new Observer<String>() {
    @Override public void update(String value) {
      productListId = value;
    }
  };

  @Inject ProductListViewModel viewModel;

  @Icicle String productListId;

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
  public static ProductListScreen newInstance(String productListId) {
    ProductListScreen fragment = new ProductListScreen();
    Bundle args = new Bundle();
    args.putString(PRODUCT_LIST_ID_BUNDLE_KEY, productListId);
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ProductListBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.product_list, container, false);

    MainActivity.scoped(new ProductListModule()).inject(this);
    binding.setViewModel(viewModel);

    viewModel.setOnScanBarCodeButtonClickListener(
        new ProductListViewModel.OnScanBarCodeButtonClickListener() {
          @Override public void onScanBarCodeButtonClicked() {
            startBarcodeScanner();
          }
        });

    return binding.getRoot();
  }

  @Override protected boolean handleBackPress() {
    return false;
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Icepick.saveInstanceState(this, outState);
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (getArguments() != null) {
      productListId = getArguments().getString(PRODUCT_LIST_ID_BUNDLE_KEY);
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
      if (result.getContents() == null) {
        Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
      } else {
        Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
      }
    } else {
      super.onActivityResult(requestCode, resultCode, data);
    }
  }

  private void startBarcodeScanner() {
    IntentIntegrator.forFragment(this).initiateScan();
  }
}
