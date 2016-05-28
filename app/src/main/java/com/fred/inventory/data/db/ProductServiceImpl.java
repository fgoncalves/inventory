package com.fred.inventory.data.db;

import com.fred.inventory.data.db.models.Product;
import com.fred.inventory.data.db.models.ProductList;
import com.fred.inventory.utils.StringUtils;
import com.fred.inventory.utils.UniqueIdGenerator;
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

  @Override public Observable<ProductList> createOrUpdate(final ProductList productList) {
    if (StringUtils.isBlank(productList.getId())) productList.setId(UniqueIdGenerator.id());

    return Observable.create(new Observable.OnSubscribe<ProductList>() {
      @Override public void call(Subscriber<? super ProductList> subscriber) {
        subscriber.onNext(realmWrapper.store(productList));
        subscriber.onCompleted();
      }
    });
  }

  @Override public Observable<Product> createOrUpdate(final Product product) {
    if (StringUtils.isBlank(product.getId())) product.setId(UniqueIdGenerator.id());

    return Observable.create(new Observable.OnSubscribe<Product>() {
      @Override public void call(Subscriber<? super Product> subscriber) {
        subscriber.onNext(realmWrapper.store(product));
        subscriber.onCompleted();
      }
    });
  }
}
