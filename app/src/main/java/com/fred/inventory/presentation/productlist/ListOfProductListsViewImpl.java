package com.fred.inventory.presentation.productlist;

import android.content.Context;
import android.util.AttributeSet;
import com.fred.inventory.SuppliesApplication;
import com.fred.inventory.presentation.productlist.adapters.ListOfProductListsAdapter;
import javax.inject.Inject;

public class ListOfProductListsViewImpl extends android.support.v4.widget.NestedScrollView
    implements ListOfProductListsView {

  @Inject ListOfProductListsPresenter presenter;

  public ListOfProductListsViewImpl(Context context) {
    super(context);
  }

  public ListOfProductListsViewImpl(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ListOfProductListsViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    SuppliesApplication.scoped(new ListOfProductListsModule(this)).inject(this);
  }

  @Override public void showEmptyView() {

  }

  @Override public void hideEmptyView() {

  }

  @Override public void setAdapter(ListOfProductListsAdapter adapter) {

  }

  @Override public void displayListAllProductListsError() {

  }
}
