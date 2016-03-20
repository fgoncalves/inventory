package com.fred.inventory.presentation.productlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.domain.models.ProductList;
import javax.inject.Inject;

public class ListOfProductListsItemViewImpl extends RelativeLayout
    implements ListOfProductListsItemView {
  @Bind(R.id.product_list_name) TextView productListName;

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

  @Override public void displayProductListName(@NonNull String name) {
    productListName.setText(name);
  }

  @Override public void displayProductList(@NonNull ProductList productList) {
    presenter.attachModel(productList);
  }
}
