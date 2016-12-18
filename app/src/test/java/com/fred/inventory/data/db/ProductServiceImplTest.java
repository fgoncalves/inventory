package com.fred.inventory.data.db;

import com.fred.inventory.data.db.models.Product;
import com.fred.inventory.data.db.models.ProductList;
import java.util.ArrayList;
import java.util.Collections;
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
  @Mock OrmWrapper ormWrapper;

  private ProductServiceImpl service;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    service = new ProductServiceImpl(ormWrapper);
  }

  @Test public void all_shouldReturnEmptyIfThereAreNoObjectsInTheDatabaseOfTheRequestedType() {
    TestSubscriber<List<ProductList>> testSubscriber = new TestSubscriber<>();
    when(ormWrapper.all(any(Class.class))).thenReturn(new ArrayList());

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
    when(ormWrapper.all(any(Class.class))).thenReturn(lists);

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
    when(ormWrapper.get(any(Class.class), anyString())).thenReturn(null);

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
    when(ormWrapper.get(any(Class.class), anyString())).thenReturn(productList);

    service.productList("foo")
        .subscribeOn(Schedulers.immediate())
        .observeOn(Schedulers.immediate())
        .subscribe(testSubscriber);

    testSubscriber.assertCompleted();
    testSubscriber.assertNoErrors();
    testSubscriber.assertValues(productList);
  }

  @Test public void createOrUpdate_shouldSetARandomIdIfNoneIsGiven() {
    ProductList productList = mock(ProductList.class);

    service.createOrUpdate(productList);

    verify(productList).setId(anyString());
  }

  @Test public void createOrUpdate_shouldStoreProductListToRealm() {
    TestSubscriber<ProductList> testSubscriber = new TestSubscriber<>();
    ProductList productList = new ProductList();
    when(ormWrapper.store(any(ProductList.class))).thenReturn(productList);

    service.createOrUpdate(productList)
        .subscribeOn(Schedulers.immediate())
        .observeOn(Schedulers.immediate())
        .subscribe(testSubscriber);

    testSubscriber.assertCompleted();
    testSubscriber.assertNoErrors();
    testSubscriber.assertReceivedOnNext(Collections.singletonList(productList));
  }

  @Test public void createOrUpdate_shouldAssignAnIdIfNoneIsGiven() {
    Product product = mock(Product.class);

    service.createOrUpdate(product);

    verify(product).setId(anyString());
  }

  @Test public void createOrUpdate_shouldUpdateProductAndReturnIt() {
    TestSubscriber<Product> testSubscriber = new TestSubscriber<>();
    Product product = new Product();
    product.setId("some id");
    when(ormWrapper.store(any(Product.class))).thenReturn(product);

    service.createOrUpdate(product)
        .subscribeOn(Schedulers.immediate())
        .observeOn(Schedulers.immediate())
        .subscribe(testSubscriber);

    testSubscriber.assertCompleted();
    testSubscriber.assertNoErrors();
    testSubscriber.assertReceivedOnNext(Collections.singletonList(product));
  }
}
