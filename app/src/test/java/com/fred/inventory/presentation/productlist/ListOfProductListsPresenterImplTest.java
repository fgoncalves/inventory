package com.fred.inventory.presentation.productlist;

import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.ListAllProductListsUseCase;
import com.fred.inventory.presentation.productlist.adapters.ListOfProductListsAdapter;
import com.fred.inventory.testhelpers.ImmediateToImmediateTransformer;
import com.fred.inventory.utils.rx.RxSubscriptionPool;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.Subscription;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListOfProductListsPresenterImplTest {
  @Mock ListOfProductListsView view;
  @Mock ListAllProductListsUseCase listAllProductListsUseCase;
  @Mock RxSubscriptionPool rxSubscriptionPool;
  @Mock ListOfProductListsAdapter adapter;

  private ListOfProductListsPresenterImpl presenter;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    when(listAllProductListsUseCase.list()).thenReturn(
        Observable.<List<ProductList>>just(new ArrayList<ProductList>()));

    presenter = new ListOfProductListsPresenterImpl(view, listAllProductListsUseCase,
        new ImmediateToImmediateTransformer(), rxSubscriptionPool, adapter);
  }

  @Test public void onAttachedToWindow_shouldQueryForAllProducts() {
    presenter.onAttachedToWindow();

    verify(listAllProductListsUseCase).list();
  }

  @Test public void onAttachedToWindow_shouldAddSubscriptionToRxPool() {
    presenter.onAttachedToWindow();

    verify(rxSubscriptionPool).addSubscription(anyString(), any(Subscription.class));
  }

  @Test public void onAttachedToWindow_shouldTellViewToDisplayAnEmptyViewIfNoListsArePresent() {
    presenter.onAttachedToWindow();

    verify(view).showEmptyView();
  }

  @Test public void onAttachedToWindow_shouldTellViewToHideTheEmptyViewIfThereAreLists() {
    List<ProductList> lists = new ArrayList<>();
    lists.add(new ProductList());
    when(listAllProductListsUseCase.list()).thenReturn(Observable.just(lists));

    presenter.onAttachedToWindow();

    verify(view).hideEmptyView();
  }

  @Test public void onAttachedToWindow_shouldTellViewToSetTheAdapter() {
    List<ProductList> lists = new ArrayList<>();
    lists.add(new ProductList());
    when(listAllProductListsUseCase.list()).thenReturn(Observable.just(lists));

    presenter.onAttachedToWindow();

    verify(adapter).attachModel(lists);
  }

  @Test public void onAttachedToWindow_shouldSetModelInAdapter() {
    presenter.onAttachedToWindow();

    verify(view).setAdapter(adapter);
  }


  @Test public void onAttachedToWindow_shouldTellViewDisplayAnErrorMessageWhenThereWasAnErrorRetrievingTheProductLists() {
    when(listAllProductListsUseCase.list()).thenReturn(
        Observable.<List<ProductList>>error(new Exception()));

    presenter.onAttachedToWindow();

    verify(view).displayListAllProductListsError();
  }

  @Test public void onDettachedFromWindow_shouldCancelAllSubscriptions() {
    presenter.onDettachedFromWindow();

    verify(rxSubscriptionPool).unsubscribeFrom(anyString());
  }
}
