package com.fred.inventory.presentation.productlist.viewmodels;

import android.view.View;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.GetProductListUseCase;
import com.fred.inventory.domain.usecases.SaveProductListInLocalStorageUseCase;
import com.fred.inventory.presentation.productlist.models.Error;
import com.fred.inventory.testhelpers.ImmediateToImmediateTransformer;
import com.fred.inventory.utils.binding.Observer;
import com.fred.inventory.utils.rx.RxSubscriptionPool;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static com.fred.inventory.testhelpers.matchers.SubscriptionMatchers.anySubscription;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * <p/>
 * Created by fred on 09.07.16.
 */
public class ProductListViewModelImplTest {
  @Mock Observer<Integer> emptyViewVisibilityObserver;
  @Mock Observer<Boolean> showKeyboardObserver;
  @Mock Observer<String> productNameObserver;
  @Mock Observer<Error> errorObserver;
  @Mock GetProductListUseCase getProductListUseCase;
  @Mock SaveProductListInLocalStorageUseCase saveProductListInLocalStorageUseCase;
  @Mock RxSubscriptionPool rxSubscriptionPool;

  private ProductListViewModelImpl viewModel;
  private ProductList productList;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    viewModel =
        new ProductListViewModelImpl(getProductListUseCase, saveProductListInLocalStorageUseCase,
            new ImmediateToImmediateTransformer(), rxSubscriptionPool);

    viewModel.bindEmptyViewVisibilityObserver(emptyViewVisibilityObserver);
    viewModel.bindShowKeyboardObserver(showKeyboardObserver);
    viewModel.bindProductNameObserver(productNameObserver);
    viewModel.bindErrorObserver(errorObserver);

    productList = new ProductList();
    productList.setName("Some name would go here");
    productList.setId("Some id");

    viewModel.forProductList(productList.getId());

    when(getProductListUseCase.get(anyString())).thenReturn(Observable.just(productList));
    when(saveProductListInLocalStorageUseCase.save(any(ProductList.class))).thenReturn(
        Observable.just(productList));
  }

  @Test
  public void onAttachedToWindow_shouldUpdateEmptyViewVisibilityToVisibleWhenProductListIdIsNotGiven() {
    viewModel.forProductList(null);

    viewModel.onAttachedToWindow();

    verify(emptyViewVisibilityObserver).update(View.VISIBLE);
  }

  @Test
  public void onAttachedToWindow_shouldUpdateShowKeyboardObserverWithTrueWhenProductListIdIsNotGiven() {
    viewModel.forProductList(null);

    viewModel.onAttachedToWindow();

    verify(showKeyboardObserver).update(true);
  }

  @Test public void onAttachedToWindow_shouldNotTryToGetAnyProductWhenProductListIdIsNotGiven() {
    viewModel.forProductList(null);

    viewModel.onAttachedToWindow();

    verify(getProductListUseCase, never()).get(anyString());
  }

  @Test public void onAttachedToWind_shouldQueryForTheGivenProductList() {
    viewModel.onAttachedToWindow();

    verify(getProductListUseCase).get(productList.getId());
  }

  @Test public void onAttachedToWind_shouldAddTheSubscriptionToTheRxPoolProductList() {
    viewModel.onAttachedToWindow();

    verify(rxSubscriptionPool).addSubscription(anyString(), anySubscription());
  }

  @Test
  public void unbindEmptyViewVisibilityObserver_shouldUnbindTheObserverInAWayThatItWontUpdateTheObserver() {
    viewModel.unbindEmptyViewVisibilityObserver(emptyViewVisibilityObserver);
    viewModel.forProductList(null);

    viewModel.onAttachedToWindow();

    verify(emptyViewVisibilityObserver, never()).update(anyInt());
  }

  @Test
  public void unbindShowKeyboardObserver_shouldUnbindTheObserverInAWayThatItWontUpdateTheObserver() {
    viewModel.unbindShowKeyboardObserver(showKeyboardObserver);
    viewModel.forProductList(null);

    viewModel.onAttachedToWindow();

    verify(showKeyboardObserver, never()).update(anyBoolean());
  }

  @Test
  public void unbindProductNameObserver_shouldUnbindTheObserverInAWayThatItWontUpdateTheObserver() {
    viewModel.unbindProductNameObserver(productNameObserver);

    viewModel.onAttachedToWindow();

    verify(productNameObserver, never()).update(anyString());
  }

  @Test public void onDetachedFromWindow_shouldUnsubscribeAllSubscriptions() {
    viewModel.onDetachedFromWindow();

    verify(rxSubscriptionPool).unsubscribeFrom(anyString());
  }

  @Test public void onAttachedToWindow_shouldUpdateProductListName() {
    viewModel.onAttachedToWindow();

    verify(productNameObserver).update(productList.getName());
  }

  @Test public void doneButtonClickListener_shouldTellViewToShowErrorIfProductListNameIsEmpty() {
    viewModel.doneButtonClickListener().onClick(mock(View.class));

    verify(saveProductListInLocalStorageUseCase, never()).save(any(ProductList.class));
    verify(errorObserver).update(Error.EMPTY_PRODUCT_LIST_NAME);
  }

  @Test public void addButtonClickListener_shouldTellViewToShowErrorIfProductListNameIsEmpty() {
    viewModel.addButtonClickListener().onClick(mock(View.class));

    verify(saveProductListInLocalStorageUseCase, never()).save(any(ProductList.class));
    verify(errorObserver).update(Error.EMPTY_PRODUCT_LIST_NAME);
  }
}