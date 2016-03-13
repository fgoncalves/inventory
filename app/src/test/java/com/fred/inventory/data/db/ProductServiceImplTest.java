package com.fred.inventory.data.db;

import com.fred.inventory.data.db.models.ProductList;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {
  @Mock RealmWrapper realmWrapper;

  ProductServiceImpl productService;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    productService = new ProductServiceImpl(realmWrapper);
  }

  @Test public void all_shouldCallOnCompleteWithoutAnyOnNextCallsBecauseListIsEmpty() {
    when(realmWrapper.all(ProductList.class)).thenReturn(new ArrayList<ProductList>());

    TestSubscriber<List<ProductList>> testSubscriber = new TestSubscriber<>();
    productService.all()
        .subscribeOn(Schedulers.immediate())
        .observeOn(Schedulers.immediate())
        .subscribe(testSubscriber);

    testSubscriber.assertNoErrors();
    assertThat(testSubscriber.getOnNextEvents()).isEmpty();
    assertThat(testSubscriber.getOnCompletedEvents()).isNotEmpty();
  }

  @Test public void all_shouldCallOnNextWhenThereAreProductLists() {
    ProductList productList = new ProductList();
    List<ProductList> listOflists = new ArrayList<>();
    listOflists.add(productList);
    when(realmWrapper.all(ProductList.class)).thenReturn(listOflists);

    TestSubscriber<List<ProductList>> testSubscriber = new TestSubscriber<>();
    productService.all()
        .subscribeOn(Schedulers.immediate())
        .observeOn(Schedulers.immediate())
        .subscribe(testSubscriber);

    testSubscriber.assertNoErrors();
    assertThat(testSubscriber.getOnNextEvents().size()).isEqualTo(1);
  }
}