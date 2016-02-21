package com.fred.inventory.data.outpan.services;

import com.fred.inventory.data.outpan.calls.ProductCalls;
import com.fred.inventory.data.outpan.models.Product;
import javax.inject.Inject;
import rx.Observable;

import static com.fred.inventory.utils.Preconditions.checkNotNull;

public class ProductServiceImpl implements ProductService {
  private final ProductCalls calls;

  @Inject public ProductServiceImpl(ProductCalls calls) {
    this.calls = calls;
  }

  @Override public Observable<Product> get(String productId) {
    checkNotNull(productId, "Cannot get product details for a null product id");
    return calls.get(productId, "67431fadcce027f83ebd7ea280fab41b");
  }
}
