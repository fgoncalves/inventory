package com.fred.inventory.presentation.items.viewmodels;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import com.fred.inventory.domain.models.Product;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.GetProductListUseCase;
import com.fred.inventory.domain.usecases.SaveProductListInLocalStorageUseCase;
import com.fred.inventory.presentation.items.models.Error;
import com.fred.inventory.presentation.items.models.ImmutableItemScreenModel;
import com.fred.inventory.presentation.items.models.ItemScreenModel;
import com.fred.inventory.utils.StringUtils;
import com.fred.inventory.utils.rx.RxSubscriptionPool;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import java.text.DateFormat;
import java.util.Calendar;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

public class ItemViewModelImpl implements ItemViewModel {
  private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance();
  private final Context context;
  private final GetProductListUseCase getProductListUseCase;
  private final SaveProductListInLocalStorageUseCase saveProductListInLocalStorageUseCase;
  private final SchedulerTransformer transformer;
  private final RxSubscriptionPool rxSubscriptionPool;

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
      //productNameObservableTextWatcher.set(name);
      //if (StringUtils.isBlank(product.getName())) view.showKeyboardOnItemTitle();
    }

    @Override public void onError(Throwable e) {
      Timber.e(e, "Failed to retrieve product list from local storage");
      ItemScreenModel model =
          ImmutableItemScreenModel.builder().error(Error.FAILED_TO_FIND_ITEM).build();
      //itemScreenModelObservable.set(model);
    }

    @Override public void onNext(ProductList productList) {
      ItemViewModelImpl.this.productList = productList;
      ItemViewModelImpl.this.product = findProduct(productList);
    }
  }

  @Override public void onEditExpireDateButtonClick(View view) {
    int year = Calendar.getInstance().get(Calendar.YEAR);
    int month = Calendar.getInstance().get(Calendar.MONTH);
    int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    new DatePickerDialog(context, null, year, month, dayOfMonth).show();
  }
}
