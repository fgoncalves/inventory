package com.fred.inventory.presentation.items.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.presentation.items.modules.ItemViewModule;
import com.fred.inventory.presentation.items.viewmodels.ItemViewModel;
import com.fred.inventory.presentation.widgets.clicktoedittext.ClickToEditTextViewImpl;
import javax.inject.Inject;

public class ItemViewImpl extends CoordinatorLayout implements ItemView {
  @Bind(R.id.product_name) ClickToEditTextViewImpl productName;
  @Inject ItemViewModel viewModel;

  public ItemViewImpl(Context context) {
    super(context);
  }

  public ItemViewImpl(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ItemViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    if (isInEditMode()) return;
    ButterKnife.bind(this);
    MainActivity.scoped(new ItemViewModule()).inject(this);
  }

  @Override public void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (isInEditMode()) return;
    viewModel.onAttachedToWindow();
  }

  @Override public void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    viewModel.onDetachedFromWindow();
  }

  @Override public void displayForProductList(@NonNull String productListId) {
    viewModel.forProductList(productListId);
  }

  @Override public void displayProductName(String name) {
    productName.setText(name);
  }

  @Override public void displayFailedToFetchProductListError() {
    Snackbar.make(this, R.string.no_product_list_found_error_message, Snackbar.LENGTH_LONG).show();
  }

  @Override public void showKeyboardOnItemTitle() {
    productName.onShowKeyboardRequest();
  }
}
