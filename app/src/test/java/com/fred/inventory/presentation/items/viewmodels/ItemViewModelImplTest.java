package com.fred.inventory.presentation.items.viewmodels;

import com.fred.inventory.domain.models.Product;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.GetProductListUseCase;
import com.fred.inventory.domain.usecases.SaveProductListInLocalStorageUseCase;
import com.fred.inventory.presentation.items.models.Error;
import com.fred.inventory.presentation.items.models.ItemScreenModel;
import com.fred.inventory.testhelpers.ImmediateToImmediateTransformer;
import com.fred.inventory.utils.binding.Observer;
import com.fred.inventory.utils.rx.RxSubscriptionPool;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemViewModelImplTest {
  @Mock Observer<String> productNameObserver;
  @Mock Observer<ItemScreenModel> itemScreenModelObserver;
  @Mock GetProductListUseCase getProductListUseCase;
  @Mock SaveProductListInLocalStorageUseCase saveProductListInLocalStorageUseCase;
  @Mock RxSubscriptionPool rxSubscriptionPool;

  private ProductList productList;
  private ItemViewModelImpl viewModel;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    viewModel = new ItemViewModelImpl(context, getProductListUseCase, saveProductListInLocalStorageUseCase,
        new ImmediateToImmediateTransformer(), rxSubscriptionPool);
    viewModel.bindProductNameObserver(productNameObserver);
    viewModel.bindItemScreenModelObserver(itemScreenModelObserver);

    productList = new ProductList();
    productList.setId("some id");

    viewModel.forProductList(productList.getId());
    when(getProductListUseCase.get(productList.getId())).thenReturn(Observable.just(productList));
  }

  @Test public void onAttachedToWindow_shouldGetTheProductListFromLocalStorage() {
    viewModel.onResume();

    verify(getProductListUseCase).get(productList.getId());
  }

  @Test public void onDetachedFromWindow_shouldUnsubscribeAll() {
    viewModel.onPause();

    verify(rxSubscriptionPool).unsubscribeFrom(anyString());
  }

  @Test public void onAttachedToWindow_shouldTellViewToShowProductsName() {
    viewModel.onResume();

    verify(productNameObserver).update("");
  }

  @Test public void onAttachedToWindow_shouldTellViewToShowProductsNameForGivenProduct() {
    productList.setProducts(new ArrayList<Product>());
    Product product = new Product();
    product.setId("asdasdasdasdasda");
    product.setName("This is the name");
    productList.getProducts().add(product);
    viewModel.forProduct(product.getId());

    viewModel.onResume();

    verify(productNameObserver).update(product.getName());
  }

  @Test public void onAttachedToWindow_shouldTellViewToShowErrorWhenFetchingTheProductListFailed() {
    when(getProductListUseCase.get(productList.getId())).thenReturn(
        Observable.<ProductList>error(new Exception()));

    viewModel.onResume();

    ArgumentCaptor<ItemScreenModel> argumentCaptor = ArgumentCaptor.forClass(ItemScreenModel.class);

    verify(itemScreenModelObserver).update(argumentCaptor.capture());

    ItemScreenModel screenModel = argumentCaptor.getValue();

    assertThat(screenModel.error()).isEqualTo(Error.FAILED_TO_FIND_ITEM);
  }

  @Test public void onAttachedToWindow_shouldTellViewToRequestFocusOnItemTitleIfItemTitleIsEmpty() {
    viewModel.onResume();

    //verify(view).showKeyboardOnItemTitle();
  }

  @Test public void onAttachedToWindow_shouldNotRequestFocusOnItemNameIfTheTitleIsNotEmpty() {
    productList.setProducts(mock(ArrayList.class));
    Product product = new Product();
    product.setId("some id");
    product.setName("This is the name");
    when(productList.getProducts().get(anyInt())).thenReturn(product);
    viewModel.forProduct(product.getId());

    viewModel.onResume();

    //verify(view, never()).showKeyboardOnItemTitle();
  }
}
