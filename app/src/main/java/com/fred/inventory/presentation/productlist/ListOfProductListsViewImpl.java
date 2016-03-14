package com.fred.inventory.presentation.productlist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fred.inventory.R;
import com.fred.inventory.SuppliesApplication;
import com.fred.inventory.presentation.productlist.adapters.ListOfProductListsAdapter;
import javax.inject.Inject;

public class ListOfProductListsViewImpl extends android.support.v4.widget.NestedScrollView
    implements ListOfProductListsView {
  @Bind(R.id.empty_list_of_lists_recycler) View emptyView;
  @Bind(R.id.list_of_lists_recycler_view) RecyclerView recyclerView;

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
    ButterKnife.bind(this);
    if (isInEditMode()) return;
    SuppliesApplication.scoped(new ListOfProductListsModule(this)).inject(this);
  }

  @Override public void onAttachedToWindow() {
    super.onAttachedToWindow();
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    presenter.onAttachedToWindow();
  }

  @Override public void showEmptyView() {
    emptyView.setVisibility(VISIBLE);
  }

  @Override public void hideEmptyView() {
    emptyView.setVisibility(GONE);
  }

  @Override public void setAdapter(ListOfProductListsAdapter adapter) {
    recyclerView.setAdapter((RecyclerView.Adapter) adapter);
  }

  @Override public void displayListAllProductListsError() {
    Toast.makeText(getContext(), R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
  }
}
