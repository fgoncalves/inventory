package com.fred.inventory.presentation.productlist.views;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.fred.inventory.presentation.base.ViewInteraction;
import com.fred.inventory.presentation.productlist.modules.ProductListModule;
import com.fred.inventory.presentation.productlist.presenters.ProductListPresenter;
import com.fred.inventory.presentation.widgets.clicktoedittext.ClickToEditTextViewImpl;
import javax.inject.Inject;
import rx.Observable;
import rx.subjects.PublishSubject;

public class ProductListViewImpl extends CoordinatorLayout implements ProductListView {
  @Bind(R.id.product_list_name) ClickToEditTextViewImpl clickToEditTextView;
  @Bind(R.id.product_list_recycler_view) RecyclerView recyclerView;
  @Bind(R.id.empty_product_list_recycler) View emptyView;

  @Inject ProductListPresenter presenter;

  private PublishSubject<ViewInteraction> interactions = PublishSubject.create();

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

  @Override public void onAttachedToWindow() {
    super.onAttachedToWindow();
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    if (isInEditMode()) return;

    presenter.onAttachedToWindow();
  }

  @Override public void showProductList(@NonNull String productListId) {
    presenter.forProductList(productListId);
  }

  @Override public void displayProductListName(@NonNull String name) {
    clickToEditTextView.setText(name);
  }

  @Override public void showEmptyProductList() {
    emptyView.setVisibility(VISIBLE);
  }

  @Override public void hideEmptyProductList() {
    emptyView.setVisibility(GONE);
  }

  @Override public void showKeyboardOnProductListName() {
    clickToEditTextView.onShowKeyboardRequest();
  }

  @Override public void hideKeyboard() {
    clickToEditTextView.onHideKeyboardRequest();
  }

  @OnClick(R.id.toolbar_done_button) public void onDoneButtonClicked() {
    presenter.onDoneButtonClicked();
  }

  @Override public Observable<ViewInteraction> interactions() {
    return interactions;
  }

  @Override public void doDismiss() {
    interactions.onNext(new ViewInteraction(ViewInteraction.ViewInteractionType.DISMISS));
    interactions.onCompleted();
  }

  @Override public String getProductListName() {
    return clickToEditTextView.getText();
  }

  @Override public void showEmptyProductListNameErrorMessage() {
    Snackbar.make(this, R.string.no_product_list_name_error_message, Snackbar.LENGTH_LONG).show();
  }

  @OnClick(R.id.add_button) public void onAddProductClicked() {
    presenter.onAddProductButtonClicked();
  }

  @Override public void showItemScreenForProductList(String id) {
    ViewInteraction viewInteraction =
        new ViewInteraction(ViewInteraction.ViewInteractionType.ADD_PRODUCT_BUTTON_CLICKED);
    viewInteraction.getMetadata().putString(ViewInteraction.PRODUCT_LIST_METADATA_KEY, id);
    interactions.onNext(viewInteraction);
  }
}
