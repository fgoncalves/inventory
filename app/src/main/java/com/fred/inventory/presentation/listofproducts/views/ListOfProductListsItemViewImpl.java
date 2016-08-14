package com.fred.inventory.presentation.listofproducts.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.presentation.listofproducts.modules.ListOfProductListsItemModule;
import com.fred.inventory.presentation.listofproducts.viewmodels.ListOfProductListsItemViewModel;
import com.fred.inventory.utils.binding.Observer;
import javax.inject.Inject;

public class ListOfProductListsItemViewImpl extends CardView implements ListOfProductListsItemView {
  private final Observer<String> productListNameObserver = new Observer<String>() {
    @Override public void update(String value) {
      productListName.setText(value);
    }
  };
  private final Observer<String> infoTextObserver = new Observer<String>() {
    @Override public void update(String value) {
      infoText.setText(value);
    }
  };
  @Bind(R.id.list_of_product_lists_title) TextView productListName;
  @Bind(R.id.list_of_product_lists_info_text) TextView infoText;

  @Inject ListOfProductListsItemViewModel viewModel;

  public ListOfProductListsItemViewImpl(Context context) {
    super(context);
  }

  public ListOfProductListsItemViewImpl(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ListOfProductListsItemViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);

    if (isInEditMode()) return;

    MainActivity.scoped(new ListOfProductListsItemModule()).inject(this);
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (isInEditMode()) return;
    viewModel.bindProductListNameObserver(productListNameObserver);
    viewModel.bindInfoTextObserver(infoTextObserver);

    viewModel.onAttachedToWindow();
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    viewModel.unbindProductListNameObserver(productListNameObserver);
    viewModel.unbindInfoTextObserver(infoTextObserver);
  }

  @Override public void displayProductList(@NonNull ProductList productList) {
    viewModel.attachModel(productList);
  }
}
