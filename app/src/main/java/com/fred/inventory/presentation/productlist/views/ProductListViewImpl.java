package com.fred.inventory.presentation.productlist.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.presentation.productlist.modules.ProductListModule;
import com.fred.inventory.presentation.productlist.presenters.ProductListPresenter;
import com.fred.inventory.presentation.widgets.clicktoedittext.ClickToEditTextViewImpl;
import javax.inject.Inject;

public class ProductListViewImpl extends RelativeLayout implements ProductListView {
  @Bind(R.id.product_list_name) ClickToEditTextViewImpl clickToEditTextView;
  @Bind(R.id.toolbar_done_button) Button button;
  @Bind(R.id.product_list_recycler_view) RecyclerView recyclerView;
  @Bind(R.id.empty_product_list_recycler) View emptyView;

  @Inject ProductListPresenter presenter;

  public ProductListViewImpl(Context context) {
    super(context);
  }

  public ProductListViewImpl(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ProductListViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
    if (isInEditMode()) return;
    MainActivity.scoped(new ProductListModule(this)).inject(this);
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
  }

  @Override public void displayProductListName(@NonNull String name) {

  }

  @Override public void showEmptyProductList() {
    emptyView.setVisibility(VISIBLE);
  }

  @Override public void hideEmptyProductList() {
    emptyView.setVisibility(GONE);
  }
}
