package com.fred.inventory.presentation.productlist.presenters;

import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.GetProductListUseCase;
import com.fred.inventory.presentation.productlist.views.ProductListView;
import com.fred.inventory.testhelpers.ImmediateToImmediateTransformer;
import com.fred.inventory.utils.rx.RxSubscriptionPool;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static com.fred.inventory.testhelpers.matchers.SubscriptionMatchers.anySubscription;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductListPresenterImplTest {
  @Mock ProductListView view;
  @Mock GetProductListUseCase getProductListUseCase;
  @Mock RxSubscriptionPool rxSubscriptionPool;

  private ProductListPresenterImpl presenter;
  private ProductList productList;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    presenter = new ProductListPresenterImpl(view, getProductListUseCase,
        new ImmediateToImmediateTransformer(), rxSubscriptionPool);

    productList = new ProductList();
    productList.setName("Some name would go here");
    when(getProductListUseCase.get(anyString())).thenReturn(Observable.just(productList));
  }

  @Test public void onAttachedToWindow_shouldAddSubscriptionToPool() {
    presenter.onAttachedToWindow("foo");

    verify(rxSubscriptionPool).addSubscription(anyString(), anySubscription());
  }

  @Test public void onDetachedFromWindow_shouldUnsubscribeFromTheSubscriptionsPool() {
    presenter.onDetachedFromWindow();

    verify(rxSubscriptionPool).unsubscribeFrom(anyString());
  }

  @Test public void onAttachedToWindow_shouldQueryForThePassedProductList() {
    String expectedId = "foobar";

    presenter.onAttachedToWindow(expectedId);

    verify(getProductListUseCase).get(expectedId);
  }

  @Test public void onAttachedToWindow_shouldNotQueryForProductListsIfThePassedIdIsBlank() {
    presenter.onAttachedToWindow("");

    verify(getProductListUseCase, never()).get(anyString());
  }

  @Test public void onAttachedToWindow_shouldSetTheProductListNameWhenProducListIsReceived() {
    presenter.onAttachedToWindow("foo");

    verify(view).displayProductListName(productList.getName());
  }
}