package com.fred.inventory.presentation.items.viewmodels;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import com.fred.inventory.domain.models.Product;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.GetProductListUseCase;
import com.fred.inventory.domain.usecases.SaveProductListInLocalStorageUseCase;
import com.fred.inventory.utils.StringUtils;
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
  private class ViewSwitcherGestureDetector extends GestureDetector.SimpleOnGestureListener {
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

      // Swipe left (next)
      if (e1.getX() > e2.getX()) {
        //mViewSwitcher.setInAnimation(GestureActivity.this, R.anim.left_in);
        //mViewSwitcher.setOutAnimation(GestureActivity.this, R.anim.left_out);

        viewSwitcherDisplayedChild.set(1);
      }

      // Swipe right (previous)
      if (e1.getX() < e2.getX()) {
        //mViewSwitcher.setInAnimation(GestureActivity.this, R.anim.right_in);
        //mViewSwitcher.setOutAnimation(GestureActivity.this, R.anim.right_out);

        viewSwitcherDisplayedChild.set(0);
      }

      return super.onFling(e1, e2, velocityX, velocityY);
    }
  }

  private final ObservableField<Date> expirationDate = new ObservableField<>();
  private final ObservableField<String> itemName = new ObservableField<>();
  private final ObservableInt quantity = new ObservableInt(0);
  private final ObservableInt uncertainQuantityMaximum = new ObservableInt(1);
  private final ObservableField<String> uncertainQuantityUnit = new ObservableField<>();
  private final ObservableInt viewSwitcherDisplayedChild = new ObservableInt(0);

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
  private final GestureDetectorCompat gestureDetectorCompat;
  private String productListId;
  private String productId;
  private ProductList productList;
  private Product product;

  @Inject public ItemViewModelImpl(Context context, GetProductListUseCase getProductListUseCase,
      SaveProductListInLocalStorageUseCase saveProductListInLocalStorageUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer transformer,
      RxSubscriptionPool rxSubscriptionPool) {
    this.context = context;
    this.getProductListUseCase = getProductListUseCase;
    this.saveProductListInLocalStorageUseCase = saveProductListInLocalStorageUseCase;
    this.transformer = transformer;
    this.rxSubscriptionPool = rxSubscriptionPool;
    this.gestureDetectorCompat =
        new GestureDetectorCompat(context, new ViewSwitcherGestureDetector());
  }

  @Override public void onAttachedToWindow() {
    Subscription subscription = getProductListUseCase.get(productListId)
        .compose(transformer.<ProductList>applySchedulers())
        .subscribe(new ProductListSubscriber());

    rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
  }

  @Override public void onDetachedFromWindow() {
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

  private class ProductListSubscriber extends Subscriber<ProductList> {
    @Override public void onCompleted() {
      String name = (product == null) ? "" : StringUtils.valueOrDefault(product.getName(), "");

      if (StringUtils.isBlank(name)) {
        setupLayoutForEmptyItem();
        return;
      }

      itemName.set(name);
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

  @Override public ObservableInt viewSwitcherDisplayedChildObservable() {
    return viewSwitcherDisplayedChild;
  }

  @Override public void onDoneButtonClick(View view) {
    //if (product == null) product = new Product();

    // TODO: Save the item with the name + quantity and expiration date
    // TODO: Dismiss view
  }

  @Override public View.OnTouchListener viewSwitcherOnTouchListener() {
    return new View.OnTouchListener() {
      @Override public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetectorCompat.onTouchEvent(motionEvent);
      }
    };
  }

  private void setupLayoutForEmptyItem() {
    // TODO: Set first view of view switcher
  }
}
