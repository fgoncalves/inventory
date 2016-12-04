package com.fred.inventory.presentation.listofproducts.viewmodels;

import android.view.View;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.ListAllProductListsUseCase;
import com.fred.inventory.presentation.listofproducts.adapters.ListOfProductListsAdapter;
import com.fred.inventory.testhelpers.ImmediateToImmediateTransformer;
import com.fred.inventory.utils.binding.Observer;
import com.fred.inventory.utils.rx.RxSubscriptionPool;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static com.fred.inventory.testhelpers.matchers.SubscriptionMatchers.anySubscription;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListOfProductListsViewModelImplTest {
  @Mock ListAllProductListsUseCase listAllProductListsUseCase;
  @Mock Observer<Integer> emptyViewVisibilityObserver;
  @Mock Observer<Void> showErrorMessageObserver;
  @Mock RxSubscriptionPool rxSubscriptionPool;
  @Mock ListOfProductListsAdapter adapter;

  private ListOfProductListsViewModelImpl viewModel;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    viewModel = new ListOfProductListsViewModelImpl(listAllProductListsUseCase,
        new ImmediateToImmediateTransformer(), rxSubscriptionPool, adapter, pathManager);
    viewModel.bindEmptyViewVisibilityObserver(emptyViewVisibilityObserver);
    viewModel.bindShowErrorMessageObserver(showErrorMessageObserver);

    List<ProductList> productLists = new ArrayList<>();
    productLists.add(new ProductList());
    when(listAllProductListsUseCase.list()).thenReturn(Observable.just(productLists));
  }

  @Test public void onAttachedToWindow_shouldQueryForData() {
    viewModel.onResume();

    verify(listAllProductListsUseCase).list();
  }

  @Test public void onAttachedToWindow_shouldAddSubscriptionsToThePool() {
    viewModel.onResume();

    verify(rxSubscriptionPool).addSubscription(anyString(), anySubscription());
  }

  @Test public void onDetachedFromWindow_shouldUnsubscribeFromAllSubscriptionsInThePool() {
    viewModel.onDetachedFromWindow();

    verify(rxSubscriptionPool).unsubscribeFrom(anyString());
  }

  @Test
  public void onAttachedToWindow_shouldSetTheEmptyViewToGoneIfThereIsSomeDataInTheProductList() {
    viewModel.onResume();

    verify(emptyViewVisibilityObserver).update(View.GONE);
  }

  @Test
  public void onAttachedToWindow_shouldSetTheEmptyViewToIfThereIsNoDataReturnedFromTheUseCase() {
    when(listAllProductListsUseCase.list()).thenReturn(Observable.<List<ProductList>>empty());

    viewModel.onResume();

    verify(emptyViewVisibilityObserver).update(View.VISIBLE);
  }

  @Test public void onAttachedToWindow_shouldSetTheEmptyViewToIfTheReturnedListIsEmpty() {
    when(listAllProductListsUseCase.list()).thenReturn(
        Observable.<List<ProductList>>just(new ArrayList<ProductList>()));

    viewModel.onResume();

    verify(emptyViewVisibilityObserver).update(View.VISIBLE);
  }

  @Test public void onAttachedToWindow_shouldSetTheAdaptersData() {
    viewModel.onResume();

    verify(adapter).setData(anyList());
    verify(adapter).onNewData();
  }

  @Test public void unbindEmptyViewVisibilityObserver_shouldPreventAnyFurtherInteractions() {
    viewModel.unbindEmptyViewVisibilityObserver(emptyViewVisibilityObserver);

    viewModel.onResume();

    verify(emptyViewVisibilityObserver, never()).update(anyInt());
  }

  @Test public void onAttachedToWindow_shouldUpdateShowErrorMessageObserverWhenThereIsAnError() {
    when(listAllProductListsUseCase.list()).thenReturn(
        Observable.<List<ProductList>>error(new Exception()));

    viewModel.onResume();

    verify(showErrorMessageObserver).update(null);
  }

  @Test public void unbindShowErrorMessageObserver_shouldPreventAnyFurtherInteractions() {
    when(listAllProductListsUseCase.list()).thenReturn(
        Observable.<List<ProductList>>error(new Exception()));
    viewModel.unbindShowErrorMessageObserver(showErrorMessageObserver);

    viewModel.onResume();

    verify(showErrorMessageObserver, never()).update(any(Void.class));
  }
}
