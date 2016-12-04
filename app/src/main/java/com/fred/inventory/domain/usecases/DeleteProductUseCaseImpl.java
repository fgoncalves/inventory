package com.fred.inventory.domain.usecases;

import com.fred.inventory.data.db.ProductService;
import com.fred.inventory.domain.models.Product;
import com.fred.inventory.domain.modules.qualifiers.ProductToDBProductTranslator;
import com.fred.inventory.domain.translators.Translator;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;

public class DeleteProductUseCaseImpl implements DeleteProductUseCase {
  private final ProductService productService;
  private final Translator<Product, com.fred.inventory.data.db.models.Product> translator;

  @Inject public DeleteProductUseCaseImpl(ProductService productService,
      @ProductToDBProductTranslator
          Translator<Product, com.fred.inventory.data.db.models.Product> translator) {
    this.productService = productService;
    this.translator = translator;
  }

  @Override public Observable<Void> delete(final Product product) {
    return rx.Observable.defer(
        new Func0<rx.Observable<com.fred.inventory.data.db.models.Product>>() {
          @Override public Observable<com.fred.inventory.data.db.models.Product> call() {
            return Observable.just(translator.translate(product));
          }
        }).flatMap(new Func1<com.fred.inventory.data.db.models.Product, Observable<Void>>() {
      @Override public Observable<Void> call(com.fred.inventory.data.db.models.Product product) {
        return productService.delete(product);
      }
    });
  }
}