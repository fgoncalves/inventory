package com.fred.inventory.presentation.items.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.presentation.items.modules.ItemViewModule;
import com.fred.inventory.presentation.items.viewmodels.ItemViewModel;
import com.fred.inventory.presentation.widgets.clicktoedittext.ClickToEditTextViewImpl;
import com.fred.inventory.utils.binding.Observer;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Inject;

public class ItemViewImpl extends CoordinatorLayout implements ItemView {
  private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance();
  private final Observer<Date> expirationDateObserver = new Observer<Date>() {
    @Override public void update(Date value) {
      expirationDate.setText(DATE_FORMAT.format(value));
    }
  };

  @Bind(R.id.product_name) ClickToEditTextViewImpl productName;
  @Bind(R.id.item_expiration_date) TextView expirationDate;
  @Bind(R.id.item_quantity_seekbar) SeekBar itemSeekbar;

  @Inject ItemViewModel viewModel;

  public ItemViewImpl(Context context) {
    super(context);
  }

  public ItemViewImpl(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ItemViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    if (isInEditMode()) return;
    ButterKnife.bind(this);
    MainActivity.scoped(new ItemViewModule()).inject(this);
  }

  @Override public void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (isInEditMode()) return;

    viewModel.bindExpirationDateObserver(expirationDateObserver);
    viewModel.onAttachedToWindow();
  }

  @Override public void onDetachedFromWindow() {
    super.onDetachedFromWindow();

    viewModel.unbindExpirationDateObserver(expirationDateObserver);
    viewModel.onDetachedFromWindow();
  }

  @Override public void displayForProductList(@NonNull String productListId) {
    viewModel.forProductList(productListId);
  }

  @Override public void displayProductName(String name) {
    productName.setText(name);
  }

  @Override public void displayFailedToFetchProductListError() {
    Snackbar.make(this, R.string.no_product_list_found_error_message, Snackbar.LENGTH_LONG).show();
  }

  @Override public void showKeyboardOnItemTitle() {
    productName.onShowKeyboardRequest();
  }

  @OnClick(R.id.item_edit_expiration_date) public void onEditExpirationDateClick() {
    int year = Calendar.getInstance().get(Calendar.YEAR);
    int month = Calendar.getInstance().get(Calendar.MONTH);
    int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    new DatePickerDialog(getContext(), viewModel.onDateSetListener(), year, month,
        dayOfMonth).show();
  }
}
