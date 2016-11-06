package com.fred.inventory.presentation.productlist.views;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.presentation.base.ViewInteraction;
import com.fred.inventory.presentation.productlist.models.Error;
import com.fred.inventory.presentation.productlist.models.ProductListScreenState;
import com.fred.inventory.presentation.productlist.modules.ProductListModule;
import com.fred.inventory.presentation.productlist.viewmodels.ProductListViewModel;
import com.fred.inventory.presentation.widgets.clicktoedittext.ClickToEditTextViewImpl;
import com.fred.inventory.utils.binding.Observer;
import icepick.Icepick;
import icepick.Icicle;
import javax.inject.Inject;
import rx.Observable;
import rx.subjects.PublishSubject;

public class ProductListViewImpl extends CoordinatorLayout implements ProductListView {
  private final Observer<String> productListNameObserver = new Observer<String>() {
    @Override public void update(String value) {
      clickToEditTextView.setText(value);
    }
  };
  private final Observer<String> showAddProductScreenObserver = new Observer<String>() {
    @Override public void update(String value) {
      showItemScreenForProductList(value);
    }
  };
  private final Observer<ProductListScreenState> stateObserver =
      new Observer<ProductListScreenState>() {
        @Override public void update(ProductListScreenState value) {
          Error error = value.error();
          if (error != null) {
            switch (error) {
              case EMPTY_PRODUCT_LIST_NAME:
                showEmptyProductListNameErrorMessage();
                return;
            }
          }

          if (value.showKeyboard()) {
            showKeyboardOnProductListName();
          } else {
            hideKeyboardOnProductListName();
          }

          emptyView.setVisibility(value.emptyViewVisibility());

          if (value.dismissed()) doDismiss();
        }
      };
  @Bind(R.id.product_list_name) ClickToEditTextViewImpl clickToEditTextView;
  @Bind(R.id.product_list_recycler_view) RecyclerView recyclerView;
  @Bind(R.id.empty_product_list_recycler) View emptyView;
  @Bind(R.id.toolbar_done_button) View doneButton;
  @Bind(R.id.add_button) View addButton;

  @Inject ProductListViewModel viewModel;

  private PublishSubject<ViewInteraction> interactions = PublishSubject.create();
  @Icicle String productListId;

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
    viewModel.forProductList(productListId);
    viewModel.onAttachedToWindow();
  }

  @Override public Parcelable onSaveInstanceState() {
    return Icepick.saveInstanceState(this, super.onSaveInstanceState());
  }

  @Override public void onRestoreInstanceState(Parcelable state) {
    super.onRestoreInstanceState(Icepick.restoreInstanceState(this, state));
  }

  @Override public void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    unbindFromViewModel();
  }

  @Override public void showProductList(@NonNull String productListId) {
    this.productListId = productListId;
  }

  @Override public Observable<ViewInteraction> interactions() {
    return interactions;
  }

  private void showKeyboardOnProductListName() {
    clickToEditTextView.onShowKeyboardRequest();
  }

  private void hideKeyboardOnProductListName() {
    clickToEditTextView.onHideKeyboardRequest();
  }

  private void doDismiss() {
    interactions.onNext(new ViewInteraction(ViewInteraction.ViewInteractionType.DISMISS));
    interactions.onCompleted();
  }

  private void showEmptyProductListNameErrorMessage() {
    Snackbar.make(this, R.string.no_product_list_name_error_message, Snackbar.LENGTH_LONG).show();
  }

  private void showItemScreenForProductList(String id) {
    ViewInteraction viewInteraction =
        new ViewInteraction(ViewInteraction.ViewInteractionType.ADD_PRODUCT_BUTTON_CLICKED);
    viewInteraction.getMetadata().putString(ViewInteraction.PRODUCT_LIST_METADATA_KEY, id);
    interactions.onNext(viewInteraction);
  }

  private void unbindFromViewModel() {
    viewModel.unbindProductListScreenStateObserver(stateObserver);
    viewModel.unbindProductNameObserver(productListNameObserver);
    viewModel.unbindShowAddProductScreenObservable(showAddProductScreenObserver);
  }

  private void bindToViewModel() {
    clickToEditTextView.addTextChangedListener(viewModel.productNameTextWatcher());
    viewModel.bindProductListScreenStateObserver(stateObserver);
    viewModel.bindProductNameObserver(productListNameObserver);
    viewModel.bindShowAddProductScreenObservable(showAddProductScreenObserver);
  }
}
