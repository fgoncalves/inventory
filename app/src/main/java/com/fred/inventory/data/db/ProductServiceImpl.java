package com.fred.inventory.data.db;

import com.fred.inventory.data.db.models.Product;
import com.fred.inventory.data.db.models.ProductList;
import com.fred.inventory.utils.StringUtils;
import com.fred.inventory.utils.UniqueIdGenerator;
import java.util.List;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Func1;

public class ProductServiceImpl implements ProductService {
  private final static Func1<ProductList, Boolean> FILTER_OUT_NULLS =
      productList -> productList != null;
  private final RealmWrapper realmWrapper;

  @Inject public ProductServiceImpl(RealmWrapper realmWrapper) {
    this.realmWrapper = realmWrapper;
  }

  @Override public Observable<List<ProductList>> all() {
    return Observable.fromCallable(() -> realmWrapper.all(ProductList.class))
        .filter(productLists -> !productLists.isEmpty());
  }

  @Override public Observable<ProductList> productList(final String id) {
    return Observable.fromCallable(() -> realmWrapper.get(ProductList.class, id))
        .filter(FILTER_OUT_NULLS);
  }

  @Override public Observable<ProductList> createOrUpdate(final ProductList productList) {
    if (StringUtils.isBlank(productList.getId())) productList.setId(UniqueIdGenerator.id());

    // Ensure product ids are set
    for (Product product : productList.getProducts()) {
      if (StringUtils.isBlank(product.getId())) product.setId(UniqueIdGenerator.id());
    }

    return Observable.fromCallable(() -> realmWrapper.store(productList));
  }

  @Override public Observable<Product> createOrUpdate(final Product product) {
    if (StringUtils.isBlank(product.getId())) product.setId(UniqueIdGenerator.id());

    return Observable.fromCallable(() -> realmWrapper.store(product));
  }

  @Override public Observable<Void> delete(final Product product) {
    return Observable.fromCallable(new Callable<Void>() {
      @Override public Void call() throws Exception {
        realmWrapper.delete(Product.class, product.getId());
        return null;
      }
    });
  }

  @Override public Observable<Void> delete(final ProductList productList) {
    return Observable.fromCallable(new Callable<Void>() {
      @Override public Void call() throws Exception {
        realmWrapper.delete(ProductList.class, productList.getId());
        return null;
      }
    });
  }
}
