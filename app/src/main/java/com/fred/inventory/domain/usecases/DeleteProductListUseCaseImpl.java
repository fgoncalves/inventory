package com.fred.inventory.domain.usecases;

import com.fred.inventory.data.db.ProductService;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.modules.qualifiers.ProductListToDBProductListTranslator;
import com.fred.inventory.domain.translators.Translator;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * <p/>
 * Created by fred on 04.12.16.
 */

public class DeleteProductListUseCaseImpl implements DeleteProductListUseCase {
  private final ProductService productService;
  private final Translator<ProductList, com.fred.inventory.data.db.models.ProductList> translator;

  @Inject public DeleteProductListUseCaseImpl(ProductService productService,
      @ProductListToDBProductListTranslator
          Translator<ProductList, com.fred.inventory.data.db.models.ProductList> translator) {
    this.productService = productService;
    this.translator = translator;
  }

  @Override public Observable<Void> delete(final ProductList productList) {
    return Observable.fromCallable(new Func0<com.fred.inventory.data.db.models.ProductList>() {
      @Override public com.fred.inventory.data.db.models.ProductList call() {
        return translator.translate(productList);
      }
    }).flatMap(new Func1<com.fred.inventory.data.db.models.ProductList, Observable<Void>>() {
      @Override
      public Observable<Void> call(com.fred.inventory.data.db.models.ProductList productList) {
        return productService.delete(productList);
      }
    });
  }
}
