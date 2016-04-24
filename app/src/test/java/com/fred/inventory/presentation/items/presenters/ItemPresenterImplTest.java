package com.fred.inventory.presentation.items.presenters;

import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.GetProductListUseCase;
import com.fred.inventory.domain.usecases.SaveProductListInLocalStorageUseCase;
import com.fred.inventory.presentation.items.views.ItemView;
import com.fred.inventory.testhelpers.ImmediateToImmediateTransformer;
import com.fred.inventory.utils.rx.RxSubscriptionPool;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemPresenterImplTest {
  @Mock GetProductListUseCase getProductListUseCase;
  @Mock SaveProductListInLocalStorageUseCase saveProductListInLocalStorageUseCase;
  @Mock ItemView view;
  @Mock RxSubscriptionPool rxSubscriptionPool;

  private ItemPresenterImpl presenter;
  private ProductList productList;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    presenter =
        new ItemPresenterImpl(getProductListUseCase, saveProductListInLocalStorageUseCase, view,
            new ImmediateToImmediateTransformer(), rxSubscriptionPool);

    productList = new ProductList();
    productList.setId("Some id");

    presenter.forProductList(productList.getId());
    when(getProductListUseCase.get(productList.getId())).thenReturn(Observable.just(productList));
  }

  @Test public void onAttachedToWindow_shouldGetTheProductListFromLocalStorage() {
    presenter.onAttachedToWindow();

    verify(getProductListUseCase).get(productList.getId());
  }

  @Test public void onDetachedFromWindow_shouldUnsubscribeAll() {
    presenter.onDetachedFromWindow();

    verify(rxSubscriptionPool).unsubscribeFrom(anyString());
  }

  @Test public void onAttachedToWindow_shouldTellViewToShowProductsName() {
    presenter.onAttachedToWindow();

    verify(view).displayProductName("");
  }
}
