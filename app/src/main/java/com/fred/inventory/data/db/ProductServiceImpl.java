package com.fred.inventory.data.db;

import com.fred.inventory.data.db.models.ProductList;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;

public class ProductServiceImpl implements ProductService {
  private final RealmWrapper realmWrapper;

  @Inject public ProductServiceImpl(RealmWrapper realmWrapper) {
    this.realmWrapper = realmWrapper;
  }

  @Override public Observable<List<ProductList>> all() {
    return Observable.create(new Observable.OnSubscribe<List<ProductList>>() {
      @Override public void call(Subscriber<? super List<ProductList>> subscriber) {
        List<ProductList> results = realmWrapper.all(ProductList.class);
        if (results.size() > 0) subscriber.onNext(results);
        subscriber.onCompleted();
      }
    });
  }

  @Override public Observable<ProductList> productList(final String id) {
    return Observable.create(new Observable.OnSubscribe<ProductList>() {
      @Override public void call(Subscriber<? super ProductList> subscriber) {
        ProductList productList = realmWrapper.get(ProductList.class, id);
        if (productList != null) subscriber.onNext(productList);
        subscriber.onCompleted();
      }
    });
  }
}
