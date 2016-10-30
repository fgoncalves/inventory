package com.fred.inventory.presentation.items.viewmodels;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import com.fred.inventory.R;
import com.fred.inventory.domain.models.Product;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.GetProductListUseCase;
import com.fred.inventory.domain.usecases.SaveProductListInLocalStorageUseCase;
import com.fred.inventory.utils.StringUtils;
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
  private final ObservableInt quantity = new ObservableInt(0);
  private final ObservableInt uncertainQuantityMaximum = new ObservableInt(0);
  private final ObservableField<String> uncertainQuantityUnit = new ObservableField<>();
  private final ObservableField<String> maxQuantityError = new ObservableField<>();
  private final TextWatcher watcher = new TextWatcher() {
    @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override public void afterTextChanged(Editable s) {
      String text = s.toString();
      if (text.isEmpty()) {
        uncertainQuantityMaximum.set(1);
        return;
      }

      uncertainQuantityMaximum.set(Integer.parseInt(s.toString()));
    }
  };

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
  private String productListId;
  private String productId;
  private ProductList productList;
  private Product product;

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

  @Override public ObservableInt knownQuantityObservable() {
    return quantity;
  }

  @Override public ObservableInt uncertainQuantityMaximumObservable() {
    return uncertainQuantityMaximum;
  }

  @Override public ObservableField<String> uncertainQuantityUnitObservable() {
    return uncertainQuantityUnit;
  }

  @Override public ObservableInt quantityObservable() {
    return quantity;
  }

  @Override public TextWatcher uncertainQuantityMaximumTextWatcher() {
    return watcher;
  }

  @Override public void onDoneButtonClick(View view) {
    if (!checkInput()) return;

    if (product == null) product = new Product();

    fillProductFromInput(product);

    productList.getProducts().add(product);
    saveProductListInLocalStorageUseCase.save(productList)
        .compose(transformer.<ProductList>applySchedulers())
        .subscribe(new Subscriber<ProductList>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {
            //TODO: show some error
          }

          @Override public void onNext(ProductList productList) {
            pathManager.back();
          }
        });
  }

  @Override public ObservableField<String> maxQuantityErrorObservable() {
    return maxQuantityError;
  }

  @Override public ObservableField<String> itemNameError() {
    return itemNameError;
  }

  private boolean checkInput() {
    boolean valid = true;
    if (StringUtils.isBlank(itemName.get())) {
      itemNameError.set(context.getString(R.string.mandatory_field));
      valid = false;
    }
    if (uncertainQuantityMaximum.get() == 0) {
      maxQuantityError.set(context.getString(R.string.mandatory_field));
      valid = false;
    }
    return valid;
  }

  private void fillProductFromInput(Product product) {
    product.setName(itemName.get());
    product.setExpirationDate(expirationDate.get());
    product.setQuantity(quantity.get());
    // TODO: This shit!!
    //product.setQuantityUnit(uncertainQuantityMaximum);
  }

  private class ProductListSubscriber extends Subscriber<ProductList> {
    @Override public void onCompleted() {
      if (product == null) return;

      itemName.set(product.getName());
      quantity.set(product.getQuantity());
    }

    @Override public void onError(Throwable e) {
      Timber.e(e, "Failed to retrieve product list from local storage");
      //ItemScreenModel model =
      //    ImmutableItemScreenModel.builder().error(Error.FAILED_TO_FIND_ITEM).build();
      //itemScreenModelObservable.set(model);
    }

    @Override public void onNext(ProductList productList) {
      ItemViewModelImpl.this.productList = productList;
      ItemViewModelImpl.this.product = findProduct(productList);
    }
  }
}
