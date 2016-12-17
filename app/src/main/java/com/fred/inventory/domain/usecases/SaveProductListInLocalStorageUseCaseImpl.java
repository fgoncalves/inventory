package com.fred.inventory.domain.usecases;

import com.fred.inventory.data.db.ProductService;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.modules.qualifiers.DBProductListToProductListTranslator;
import com.fred.inventory.domain.modules.qualifiers.ProductListToDBProductListTranslator;
import com.fred.inventory.domain.translators.Translator;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Func0;

public class SaveProductListInLocalStorageUseCaseImpl
    implements SaveProductListInLocalStorageUseCase {
  private final ProductService service;
  private final Translator<com.fred.inventory.data.db.models.ProductList, ProductList>
      dbToDomainTranslator;
  private final Translator<ProductList, com.fred.inventory.data.db.models.ProductList>
      domainToDBTranslator;

  @Inject public SaveProductListInLocalStorageUseCaseImpl(ProductService service,
      @DBProductListToProductListTranslator
          Translator<com.fred.inventory.data.db.models.ProductList, ProductList> dbToDomainTranslator,
      @ProductListToDBProductListTranslator
          Translator<ProductList, com.fred.inventory.data.db.models.ProductList> domainToDBTranslator) {
    this.service = service;
    this.dbToDomainTranslator = dbToDomainTranslator;
    this.domainToDBTranslator = domainToDBTranslator;
  }

  @Override public Observable<ProductList> save(final ProductList productList) {
    return translateToDB(productList).flatMap(service::createOrUpdate)
        .map(dbToDomainTranslator::translate);
  }

  /**
   * Return an observable that will emit the translated product list at subscription time
   *
   * @param productList The product list to translate
   * @return An observable that emits the translated list
   */
  private Observable<com.fred.inventory.data.db.models.ProductList> translateToDB(
      final ProductList productList) {
    return Observable.fromCallable(
        (Func0<com.fred.inventory.data.db.models.ProductList>) () -> domainToDBTranslator.translate(
            productList));
  }
}
