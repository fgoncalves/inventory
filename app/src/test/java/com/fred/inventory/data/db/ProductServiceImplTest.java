package com.fred.inventory.data.db;

import com.fred.inventory.data.db.models.ProductList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {
  @Mock RealmWrapper realmWrapper;

  private ProductServiceImpl service;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    service = new ProductServiceImpl(realmWrapper);
  }

  @Test public void all_shouldReturnEmptyIfThereAreNoObjectsInTheDatabaseOfTheRequestedType() {
    TestSubscriber<List<ProductList>> testSubscriber = new TestSubscriber<>();
    when(realmWrapper.all(any(Class.class))).thenReturn(new ArrayList());

    service.all()
        .subscribeOn(Schedulers.immediate())
        .observeOn(Schedulers.immediate())
        .subscribe(testSubscriber);

    testSubscriber.assertCompleted();
    testSubscriber.assertNoErrors();
    testSubscriber.assertNoValues();
  }

  @Test public void all_shouldReturnTheObjectIfThereIsOneFound() {
    TestSubscriber<List<ProductList>> testSubscriber = new TestSubscriber<>();
    ProductList productList = new ProductList();
    List<ProductList> lists = new ArrayList<>();
    lists.add(productList);
    when(realmWrapper.all(any(Class.class))).thenReturn(lists);

    service.all()
        .subscribeOn(Schedulers.immediate())
        .observeOn(Schedulers.immediate())
        .subscribe(testSubscriber);

    testSubscriber.assertCompleted();
    testSubscriber.assertNoErrors();
    testSubscriber.assertValues(lists);
  }

  @Test public void productList_shouldReturnEmptyIfTheSpecifiedObjectDoesntExist() {
    TestSubscriber<ProductList> testSubscriber = new TestSubscriber<>();
    when(realmWrapper.get(any(Class.class), anyString())).thenReturn(null);

    service.productList("foo")
        .subscribeOn(Schedulers.immediate())
        .observeOn(Schedulers.immediate())
        .subscribe(testSubscriber);

    testSubscriber.assertCompleted();
    testSubscriber.assertNoErrors();
    testSubscriber.assertNoValues();
  }

  @Test public void productList_shouldReturnTheFoundObjectWhenThereIsOneInTheLocalStorage() {
    TestSubscriber<ProductList> testSubscriber = new TestSubscriber<>();
    ProductList productList = new ProductList();
    when(realmWrapper.get(any(Class.class), anyString())).thenReturn(productList);

    service.productList("foo")
        .subscribeOn(Schedulers.immediate())
        .observeOn(Schedulers.immediate())
        .subscribe(testSubscriber);

    testSubscriber.assertCompleted();
    testSubscriber.assertNoErrors();
    testSubscriber.assertValues(productList);
  }

  @Test public void store_shouldSetARandomIdIfNoneIsGiven() {
    ProductList productList = mock(ProductList.class);

    service.createOrUpdate(productList);

    verify(productList).setId(anyString());
  }

  @Test public void store_shouldStoreProductListToRealm() {
    TestSubscriber<ProductList> testSubscriber = new TestSubscriber<>();
    ProductList productList = new ProductList();
    when(realmWrapper.store(any(ProductList.class))).thenReturn(productList);

    service.createOrUpdate(productList)
        .subscribeOn(Schedulers.immediate())
        .observeOn(Schedulers.immediate())
        .subscribe(testSubscriber);

    testSubscriber.assertCompleted();
    testSubscriber.assertNoErrors();
    testSubscriber.assertReceivedOnNext(Arrays.asList(productList));
  }
}