package com.fred.inventory.presentation.productlist.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.View;
import com.fred.inventory.utils.binding.Observer;

/**
 * View model for the product list
 * <p/>
 * Created by fred on 09.07.16.
 */
public interface ProductListViewModel {
  interface OnScanBarCodeButtonClickListener {
    void onScanBarCodeButtonClicked();
  }

  void setOnScanBarCodeButtonClickListener(OnScanBarCodeButtonClickListener listener);

  /***
   * Prepare this view model to show details for this product list
   *
   * @param id The id of the product list
   */
  void forProductList(Long id);

  void onActivityCreated();

  void onDestroyView();

  ObservableField<String> productListName();

  TextWatcher productListTextWatcher();

  void onDoneButtonClick(View view);

  void onAddButtonClick(View view);

  void onScanBarCodeButtonClick(View view);

  ObservableInt emptyListVisibility();

  ObservableInt itemListVisibility();

  ObservableInt progressBarVisibility();

  void unbindProductListIdObserver(Observer<Long> observer);

  void bindProductListIdObserver(Observer<Long> observer);

  RecyclerView.Adapter<?> productListRecyclerViewAdapter();

  void onCodeScanned(String barcode);

  void onSearchButtonClicked(View actionView);

  ObservableInt toolBarDisplayedChild();

  boolean onHomeButtonPressed();
}
