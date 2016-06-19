package com.fred.inventory.presentation.listofproducts.views;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.presentation.listofproducts.modules.ListOfProductListsModule;
import com.fred.inventory.presentation.listofproducts.viewmodels.ListOfProductListsViewModel;
import com.fred.inventory.utils.binding.Observer;
import javax.inject.Inject;
import rx.Observable;

public class ListOfProductListsViewImpl extends CoordinatorLayout
    implements ListOfProductListsView {
  private final Observer<Integer> emptyViewVisibilityObserver = new Observer<Integer>() {
    @Override public void update(Integer value) {
      emptyView.setVisibility(value);
    }
  };
  private final Observer<Void> showErrorMessageObserver = new Observer<Void>() {
    @Override public void update(Void value) {
      Snackbar.make(ListOfProductListsViewImpl.this, R.string.something_went_wrong,
          Snackbar.LENGTH_SHORT).show();
    }
  };

  @Bind(R.id.empty_list_of_lists_recycler) View emptyView;
  @Bind(R.id.list_of_lists_recycler_view) RecyclerView recyclerView;

  @Inject ListOfProductListsViewModel viewModel;

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
    MainActivity.scoped(new ListOfProductListsModule()).inject(this);
  }

  @Override public void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (isInEditMode()) return;

    viewModel.bindEmptyViewVisibilityObserver(emptyViewVisibilityObserver);
    viewModel.bindShowErrorMessageObserver(showErrorMessageObserver);
    recyclerView.setAdapter(viewModel.adapter());

    viewModel.onAttachedToWindow();
  }

  @Override public void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    viewModel.onDetachedFromWindow();

    viewModel.unbindEmptyViewVisibilityObserver(emptyViewVisibilityObserver);
    viewModel.unbindShowErrorMessageObserver(showErrorMessageObserver);
  }

  @Override public Observable<ListOfProductListsEvent> interactions() {
    return viewModel.interactions();
  }

  @OnClick(R.id.add_button) public void onAddButtonClicked(View view) {
    viewModel.addButtonClickListener().onClick(view);
  }
}
