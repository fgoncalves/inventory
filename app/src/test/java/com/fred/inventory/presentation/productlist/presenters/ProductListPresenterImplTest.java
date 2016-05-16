package com.fred.inventory.presentation.productlist.presenters;

import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.GetProductListUseCase;
import com.fred.inventory.domain.usecases.SaveProductListInLocalStorageUseCase;
import com.fred.inventory.presentation.productlist.views.ProductListView;
import com.fred.inventory.testhelpers.ImmediateToImmediateTransformer;
import com.fred.inventory.utils.rx.RxSubscriptionPool;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static com.fred.inventory.testhelpers.matchers.SubscriptionMatchers.anySubscription;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductListPresenterImplTest {
  @Mock ProductListView view;
  @Mock GetProductListUseCase getProductListUseCase;
  @Mock SaveProductListInLocalStorageUseCase saveProductListInLocalStorageUseCase;
  @Mock RxSubscriptionPool rxSubscriptionPool;

  private ProductListPresenterImpl presenter;
  private ProductList productList;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    presenter = new ProductListPresenterImpl(view, getProductListUseCase,
        saveProductListInLocalStorageUseCase, new ImmediateToImmediateTransformer(),
        rxSubscriptionPool);

    productList = new ProductList();
    productList.setName("Some name would go here");
    productList.setId("Some id");

    presenter.forProductList(productList.getId());

    when(getProductListUseCase.get(anyString())).thenReturn(Observable.just(productList));
    when(saveProductListInLocalStorageUseCase.save(any(ProductList.class))).thenReturn(
        Observable.just(productList));
    when(view.getProductListName()).thenReturn(productList.getName());
  }

  @Test public void onAttachedToWindow_shouldAddSubscriptionToPool() {
    presenter.onAttachedToWindow();

    verify(rxSubscriptionPool).addSubscription(anyString(), anySubscription());
  }

  @Test public void onDetachedFromWindow_shouldUnsubscribeFromTheSubscriptionsPool() {
    presenter.onDetachedFromWindow();

    verify(rxSubscriptionPool).unsubscribeFrom(anyString());
  }

  @Test public void onAttachedToWindow_shouldQueryForThePassedProductList() {
    presenter.onAttachedToWindow();

    verify(getProductListUseCase).get(productList.getId());
  }

  @Test public void onAttachedToWindow_shouldNotQueryForProductListsIfThePassedIdIsBlank() {
    presenter.forProductList("");

    presenter.onAttachedToWindow();

    verify(getProductListUseCase, never()).get(anyString());
  }

  @Test public void onAttachedToWindow_shouldSetTheProductListNameWhenProductListIsReceived() {
    presenter.onAttachedToWindow();

    verify(view).displayProductListName(productList.getName());
  }

  @Test public void onAttachedToWindow_shouldTellViewToHideEmptyViewWhenThereIsDataToBeShown() {
    presenter.onAttachedToWindow();

    verify(view).hideEmptyProductList();
  }

  @Test public void onAttachedToWindow_shouldTellViewToShowEmptyProductListWhenThereIsNoneFound() {
    when(getProductListUseCase.get(anyString())).thenReturn(Observable.<ProductList>empty());

    presenter.onAttachedToWindow();

    verify(view).showEmptyProductList();
  }

  @Test
  public void onAttachedToWindow_shouldTellViewToShowEmptyListWhenThereIsNoProductToGetFromDB() {
    presenter.forProductList(null);

    presenter.onAttachedToWindow();

    verify(view).showEmptyProductList();
  }

  @Test
  public void onAttachedToWindow_shouldTellViewToRequestKeyboardFocusOnClickToEditWidgetIfNoProductIsToBeGottenFromDB() {
    presenter.forProductList(null);

    presenter.onAttachedToWindow();

    verify(view).showKeyboardOnProductListName();
  }

  @Test public void onDoneButtonClicked_shouldSaveProductListToLocalStorage() {
    presenter.onAttachedToWindow();

    presenter.onDoneButtonClicked();

    verify(saveProductListInLocalStorageUseCase).save(productList);
  }

  @Test public void onDoneButtonClicked_shouldAddSubscriptionToRxPool() {
    presenter.onAttachedToWindow();

    presenter.onDoneButtonClicked();

    verify(rxSubscriptionPool, times(2)).addSubscription(anyString(), anySubscription());
  }

  @Test public void onDoneButtonClicked_shouldCallViewDismissMethod() {
    presenter.onAttachedToWindow();

    presenter.onDoneButtonClicked();

    verify(view).doDismiss();
  }

  @Test public void onDoneButtonClicked_shouldAskViewForTheNameOfTheProduct() {
    presenter.onAttachedToWindow();

    presenter.onDoneButtonClicked();

    verify(view).getProductListName();
  }

  @Test public void onDoneButtonClicked_shouldTellViewToDismissKeyboard() {
    presenter.onAttachedToWindow();

    presenter.onDoneButtonClicked();

    verify(view).hideKeyboard();
  }

  @Test public void onDoneButtonClicked_shouldNotTryToSaveProductListIfThereIsNoName() {
    when(view.getProductListName()).thenReturn("");
    presenter.onAttachedToWindow();

    presenter.onDoneButtonClicked();

    verify(view, never()).hideKeyboard();
    verify(saveProductListInLocalStorageUseCase, never()).save(any(ProductList.class));
  }

  @Test public void onDoneButtonClicked_shouldShowErrorMessageIfProductListNameIsEmpty() {
    when(view.getProductListName()).thenReturn("");
    presenter.onAttachedToWindow();

    presenter.onDoneButtonClicked();

    verify(view).showEmptyProductListNameErrorMessage();
  }

  @Test public void onAddProductButtonClicked_shouldShowErrorMessageIfProductListNameIsEmpty() {
    when(view.getProductListName()).thenReturn("");
    presenter.onAttachedToWindow();

    presenter.onAddProductButtonClicked();

    verify(view).showEmptyProductListNameErrorMessage();
  }

  @Test public void onAddProductButtonClicked_shouldShowItemScreenForTheProductListId() {
    presenter.onAttachedToWindow();

    presenter.onAddProductButtonClicked();

    verify(view).showItemScreenForProductList(productList.getId());
  }
}
