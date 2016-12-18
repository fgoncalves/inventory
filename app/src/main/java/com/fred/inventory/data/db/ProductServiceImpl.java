package com.fred.inventory.data.db;

import com.fred.inventory.data.db.models.Image;
import com.fred.inventory.data.db.models.Product;
import com.fred.inventory.data.db.models.ProductList;
import com.fred.inventory.utils.UniqueIdGenerator;
import java.util.List;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Func1;

public class ProductServiceImpl implements ProductService {
  private final static Func1<ProductList, Boolean> FILTER_OUT_NULLS =
      productList -> productList != null;
  private final OrmWrapper ormWrapper;

  @Inject public ProductServiceImpl(OrmWrapper ormWrapper) {
    this.ormWrapper = ormWrapper;
  }

  @Override public Observable<List<ProductList>> all() {
    return Observable.fromCallable(() -> ormWrapper.all(ProductList.class))
        .filter(productLists -> !productLists.isEmpty());
  }

  @Override public Observable<ProductList> productList(final Long id) {
    return Observable.fromCallable(() -> ormWrapper.get(ProductList.class, id))
        .filter(FILTER_OUT_NULLS);
  }

  @Override public Observable<ProductList> createOrUpdate(final ProductList productList) {
    return Observable.fromCallable(() -> {
      // Ensure all products and images have an id
      for (Product product : productList.getProducts()) {
        if (product.getId() == null) product.setId(UniqueIdGenerator.id());
        for (Image image : product.getImages())
          if (image.getId() == null) image.setId(UniqueIdGenerator.id());
      }
      return ormWrapper.store(productList);
    });
  }

  @Override public Observable<Product> createOrUpdate(final Product product) {
    return Observable.fromCallable(() -> {
      if (product.getId() == null) product.setId(UniqueIdGenerator.id());
      for (Image image : product.getImages())
        if (image.getId() == null) image.setId(UniqueIdGenerator.id());
      return ormWrapper.store(product);
    });
  }

  @Override public Observable<Void> delete(final Product product) {
    return Observable.fromCallable(new Callable<Void>() {
      @Override public Void call() throws Exception {
        ormWrapper.delete(Product.class, product.getId());
        return null;
      }
    });
  }

  @Override public Observable<Void> delete(final ProductList productList) {
    return Observable.fromCallable(new Callable<Void>() {
      @Override public Void call() throws Exception {
        ormWrapper.delete(ProductList.class, productList.getId());
        return null;
      }
    });
  }
}
