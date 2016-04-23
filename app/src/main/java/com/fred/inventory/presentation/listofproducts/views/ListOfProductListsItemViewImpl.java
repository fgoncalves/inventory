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
import com.fred.inventory.presentation.listofproducts.presenters.ListOfProductListsItemPresenter;
import javax.inject.Inject;

public class ListOfProductListsItemViewImpl extends CardView implements ListOfProductListsItemView {
  @Bind(R.id.list_of_product_lists_title) TextView productListName;
  @Bind(R.id.list_of_product_lists_info_text) TextView infoText;

  @Inject ListOfProductListsItemPresenter presenter;

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

    MainActivity.scoped(new ListOfProductListsItemModule(this)).inject(this);
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (isInEditMode()) return;
    presenter.onAttachedToWindow();
  }

  @Override public void displayProductListName(@NonNull String name) {
    productListName.setText(name);
  }

  @Override public void displayNumberOfProducts(int items) {
    infoText.setText(getContext().getString(R.string.number_of_items, items));
  }

  @Override public void displayProductList(@NonNull ProductList productList) {
    presenter.attachModel(productList);
  }
}
