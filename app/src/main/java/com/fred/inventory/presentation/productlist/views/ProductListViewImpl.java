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
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.presentation.base.ViewInteraction;
import com.fred.inventory.presentation.productlist.models.Error;
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
  private final Observer<Error> errorObserver = new Observer<Error>() {
    @Override public void update(Error value) {
      switch (value) {
        case EMPTY_PRODUCT_LIST_NAME:
          showEmptyProductListNameErrorMessage();
          break;
      }
    }
  };
  @Bind(R.id.product_list_name) ClickToEditTextViewImpl clickToEditTextView;
  @Bind(R.id.product_list_recycler_view) RecyclerView recyclerView;
  @Bind(R.id.empty_product_list_recycler) View emptyView;
  @Bind(R.id.toolbar_done_button) View doneButton;
  @Bind(R.id.add_button) View addButton;

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

  @Override public void showKeyboardOnProductListName() {
    clickToEditTextView.onShowKeyboardRequest();
  }

  @Override public Observable<ViewInteraction> interactions() {
    return interactions;
  }

  //@Override public void doDismiss() {
  //interactions.onNext(new ViewInteraction(ViewInteraction.ViewInteractionType.DISMISS));
  //interactions.onCompleted();
  //}

  private void showEmptyProductListNameErrorMessage() {
    Snackbar.make(this, R.string.no_product_list_name_error_message, Snackbar.LENGTH_LONG).show();
  }

  //@Override public void showItemScreenForProductList(String id) {
  //  ViewInteraction viewInteraction =
  //      new ViewInteraction(ViewInteraction.ViewInteractionType.ADD_PRODUCT_BUTTON_CLICKED);
  //  viewInteraction.getMetadata().putString(ViewInteraction.PRODUCT_LIST_METADATA_KEY, id);
  //  interactions.onNext(viewInteraction);
  //}

  private void unbindFromViewModel() {
    viewModel.unbindEmptyViewVisibilityObserver(emptyViewVisibilityObserver);
    viewModel.unbindShowKeyboardObserver(showKeyboardObserver);
    viewModel.unbindErrorObserver(errorObserver);
  }

  private void bindToViewModel() {
    viewModel.bindEmptyViewVisibilityObserver(emptyViewVisibilityObserver);
    viewModel.bindShowKeyboardObserver(showKeyboardObserver);
    viewModel.bindErrorObserver(errorObserver);
    doneButton.setOnClickListener(viewModel.doneButtonClickListener());
    addButton.setOnClickListener(viewModel.addButtonClickListener());
    clickToEditTextView.setTextWatcher(viewModel.productNameTextWatcher());
  }
}
