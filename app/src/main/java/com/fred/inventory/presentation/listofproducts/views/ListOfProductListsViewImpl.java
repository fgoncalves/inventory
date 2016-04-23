package com.fred.inventory.presentation.listofproducts.views;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.presentation.listofproducts.adapters.ListOfProductListsAdapter;
import com.fred.inventory.presentation.listofproducts.modules.ListOfProductListsModule;
import com.fred.inventory.presentation.listofproducts.presenters.ListOfProductListsPresenter;
import javax.inject.Inject;
import rx.Observable;

public class ListOfProductListsViewImpl extends CoordinatorLayout
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
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    if (isInEditMode()) return;
    MainActivity.scoped(new ListOfProductListsModule(this)).inject(this);
  }

  @Override public void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (isInEditMode()) return;
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

  @Override public Observable<ListOfProductListsEvent> interactions() {
    return presenter.interactions();
  }

  @OnClick(R.id.add_button) public void onAddButtonClicked() {
    presenter.onAddButtonClicked();
  }
}
