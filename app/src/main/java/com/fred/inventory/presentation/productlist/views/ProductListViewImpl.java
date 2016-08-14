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
import com.fred.inventory.presentation.productlist.viewmodels.ProductListViewModel;
import com.fred.inventory.presentation.widgets.clicktoedittext.ClickToEditTextViewImpl;
import com.fred.inventory.utils.binding.Observer;
import javax.inject.Inject;
import rx.Observable;
import rx.subjects.PublishSubject;

public class ProductListViewImpl extends CoordinatorLayout implements ProductListView {
  private final Observer<Integer> emptyViewVisibilityObserver = new Observer<Integer>() {
    @Override public void update(Integer value) {
      emptyView.setVisibility(value);
    }
  };
  private final Observer<Boolean> showKeyboardObserver = new Observer<Boolean>() {
    @Override public void update(Boolean value) {
      showKeyboardOnProductListName();
    }
  };
  @Bind(R.id.product_list_name) ClickToEditTextViewImpl clickToEditTextView;
  @Bind(R.id.product_list_recycler_view) RecyclerView recyclerView;
  @Bind(R.id.empty_product_list_recycler) View emptyView;

  @Inject ProductListViewModel viewModel;

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
    MainActivity.scoped(new ProductListModule()).inject(this);
  }

  @Override public void onAttachedToWindow() {
    super.onAttachedToWindow();
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    if (isInEditMode()) return;

    bindToViewModel();
    viewModel.onAttachedToWindow();
  }

  @Override public void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    unbindFromViewModel();
  }

  @Override public void showProductList(@NonNull String productListId) {
    viewModel.forProductList(productListId);
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
    viewModel.onDoneButtonClicked();
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
    viewModel.onAddProductButtonClicked();
  }

  @Override public void showItemScreenForProductList(String id) {
    ViewInteraction viewInteraction =
        new ViewInteraction(ViewInteraction.ViewInteractionType.ADD_PRODUCT_BUTTON_CLICKED);
    viewInteraction.getMetadata().putString(ViewInteraction.PRODUCT_LIST_METADATA_KEY, id);
    interactions.onNext(viewInteraction);
  }

  private void unbindFromViewModel() {
    viewModel.unbindEmptyViewVisibilityObserver(emptyViewVisibilityObserver);
    viewModel.unbindShowKeyboardObserver(showKeyboardObserver);
  }

  private void bindToViewModel() {
    viewModel.bindEmptyViewVisibilityObserver(emptyViewVisibilityObserver);
    viewModel.bindShowKeyboardObserver(showKeyboardObserver);
  }
}
