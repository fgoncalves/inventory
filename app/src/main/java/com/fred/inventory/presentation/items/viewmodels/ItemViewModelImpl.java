package com.fred.inventory.presentation.items.viewmodels;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.SpinnerAdapter;
import com.fred.inventory.R;
import com.fred.inventory.domain.models.Product;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.GetProductListUseCase;
import com.fred.inventory.domain.usecases.SaveProductListInLocalStorageUseCase;
import com.fred.inventory.utils.StringUtils;
import com.fred.inventory.utils.binding.widgets.OneTimeTextWatcher;
import com.fred.inventory.utils.path.PathManager;
import com.fred.inventory.utils.rx.RxSubscriptionPool;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

public class ItemViewModelImpl implements ItemViewModel {
  private final ObservableField<Date> expirationDate = new ObservableField<>();
  private final ObservableField<String> itemName = new ObservableField<>();
  private final ObservableField<String> itemNameError = new ObservableField<>();
  private final ObservableInt seekBarVisibility = new ObservableInt(View.GONE);
  private final TextWatcher itemNameTextWatcher = new OneTimeTextWatcher(itemNameObservable());

  private final Context context;
  private final GetProductListUseCase getProductListUseCase;
  private final SaveProductListInLocalStorageUseCase saveProductListInLocalStorageUseCase;
  private final SchedulerTransformer transformer;
  private final RxSubscriptionPool rxSubscriptionPool;
  private final DatePickerDialog.OnDateSetListener dateSetListener =
      new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
          Calendar calendar = Calendar.getInstance();
          calendar.clear();
          calendar.set(year, monthOfYear, dayOfMonth);
          expirationDate.set(calendar.getTime());
        }
      };
  private final PathManager pathManager;
  private final ArrayAdapter<CharSequence> itemSpinnerAdapter;
  private final AdapterView.OnItemSelectedListener spinnerOnItemSelectedListener =
      new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
          // Selected item(s)
          if (position == 0) {
            seekBarVisibility.set(View.GONE);
            isUnit = true;
          } else {
            seekBarVisibility.set(View.VISIBLE);
            isUnit = false;
          }
        }

        @Override public void onNothingSelected(AdapterView<?> adapterView) {
          seekBarVisibility.set(View.GONE);
        }
      };
  private final SeekBar.OnSeekBarChangeListener seekBarChangeListener =
      new SeekBar.OnSeekBarChangeListener() {
        @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
          if (progress == seekBarProgress.get()) return;
          seekBarProgress.set(progress);
        }

        @Override public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override public void onStopTrackingTouch(SeekBar seekBar) {

        }
      };
  private final ObservableField<String> itemQuantityLabel = new ObservableField<>();
  private final ObservableInt seekBarProgress = new ObservableInt(0);
  private final ObservableInt spinnerSelection = new ObservableInt(0);
  private final TextWatcher itemQuantityLabelTextWatcher =
      new OneTimeTextWatcher(itemQuantityLabel);

  private String productListId;
  private String productId;
  private ProductList productList;
  private Product product;
  private boolean isUnit;

  @Inject public ItemViewModelImpl(Context context, GetProductListUseCase getProductListUseCase,
      SaveProductListInLocalStorageUseCase saveProductListInLocalStorageUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer transformer,
      RxSubscriptionPool rxSubscriptionPool, PathManager pathManager) {
    this.context = context;
    this.getProductListUseCase = getProductListUseCase;
    this.saveProductListInLocalStorageUseCase = saveProductListInLocalStorageUseCase;
    this.transformer = transformer;
    this.rxSubscriptionPool = rxSubscriptionPool;
    this.pathManager = pathManager;
    this.itemSpinnerAdapter = ArrayAdapter.createFromResource(context, R.array.item_types,
        android.R.layout.simple_spinner_item);
    itemSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
  }

  @Override public void onResume() {
    Subscription subscription = getProductListUseCase.get(productListId)
        .compose(transformer.<ProductList>applySchedulers())
        .subscribe(new ProductListSubscriber());

    rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
  }

  @Override public void onPause() {
    rxSubscriptionPool.unsubscribeFrom(getClass().getCanonicalName());
  }

  @Override public void forProductList(String productListId) {
    this.productListId = productListId;
  }

  @Override public void forProduct(String productId) {
    this.productId = productId;
  }

  @Override public ObservableInt spinnerSelection() {
    return spinnerSelection;
  }

  /**
   * Loop though the product list and find the product with the id this presenter was prepared for.
   * If the id is null, then the return value is null.
   *
   * @param productList The product list to look for the product.
   * @return The product to display. Null otherwise.
   */
  private Product findProduct(ProductList productList) {
    if (productId == null) return null;

    for (Product product : productList.getProducts())
      if (product.getId().equals(productId)) return product;

    return null;
  }

  @Override public void onEditExpireDateButtonClick(View view) {
    Calendar calendar = Calendar.getInstance();
    if (expirationDate.get() != null) {
      calendar.clear();
      calendar.setTime(expirationDate.get());
    }
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    new DatePickerDialog(context, dateSetListener, year, month, dayOfMonth).show();
  }

  @Override public ObservableField<Date> expirationDateObservable() {
    return expirationDate;
  }

  @Override public ObservableField<String> itemNameObservable() {
    return itemName;
  }

  @Override public void onDoneButtonClick(View view) {
    if (!checkInput()) return;

    if (product == null) product = new Product();

    fillProductFromInput(product);
    addOrUpdateProductInList(productList, product);

    saveProductListInLocalStorageUseCase.save(productList)
        .compose(transformer.<ProductList>applySchedulers())
        .subscribe(new Subscriber<ProductList>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {
            Timber.e(e, "Failed to save product in db");
            //TODO: show some error
          }

          @Override public void onNext(ProductList productList) {
            pathManager.back();
          }
        });
  }

  @Override public ObservableField<String> itemNameError() {
    return itemNameError;
  }

  @Override public TextWatcher itemNameTextWatcher() {
    return itemNameTextWatcher;
  }

  @Override public ObservableInt seekBarVisibility() {
    return seekBarVisibility;
  }

  @Override public SpinnerAdapter itemSpinnerAdapter() {
    return itemSpinnerAdapter;
  }

  @Override public AdapterView.OnItemSelectedListener spinnerOnItemSelectedListener() {
    return spinnerOnItemSelectedListener;
  }

  @Override public TextWatcher itemQuantityLabelTextWatcher() {
    return itemQuantityLabelTextWatcher;
  }

  @Override public ObservableField<String> itemQuantityLabel() {
    return itemQuantityLabel;
  }

  @Override public ObservableInt seekBarProgress() {
    return seekBarProgress;
  }

  @Override public SeekBar.OnSeekBarChangeListener seekBarChangeListener() {
    return seekBarChangeListener;
  }

  private void addOrUpdateProductInList(ProductList productList, Product product) {
    int index = productList.getProducts().indexOf(product);
    if (index == -1) {
      productList.getProducts().add(product);
    } else {
      productList.getProducts().set(index, product);
    }
  }

  private boolean checkInput() {
    boolean valid = true;
    if (StringUtils.isBlank(itemName.get())) {
      itemNameError.set(context.getString(R.string.mandatory_field));
      valid = false;
    }
    return valid;
  }

  private void fillProductFromInput(Product product) {
    product.setName(itemName.get());
    product.setExpirationDate(expirationDate.get());
    product.setQuantityLabel(itemQuantityLabel.get());
    product.setQuantity(seekBarProgress.get());
    product.setUnit(isUnit);
  }

  private class ProductListSubscriber extends Subscriber<ProductList> {
    @Override public void onCompleted() {
      if (product == null) return;

      itemName.set(product.getName());
      expirationDate.set(product.getExpirationDate());
      itemQuantityLabel.set(product.getQuantityLabel());
      seekBarProgress.set(product.getQuantity());
      isUnit = product.isUnit();
      if (isUnit) {
        seekBarVisibility.set(View.GONE);
        spinnerSelection.set(0);
      } else {
        seekBarVisibility.set(View.VISIBLE);
        spinnerSelection.set(1);
      }
    }

    @Override public void onError(Throwable e) {
      Timber.e(e, "Failed to retrieve product list from local storage");
      // TODO: make view show something
    }

    @Override public void onNext(ProductList productList) {
      ItemViewModelImpl.this.productList = productList;
      ItemViewModelImpl.this.product = findProduct(productList);
    }
  }
}
