package com.fred.inventory.domain.usecases;

import com.fred.inventory.data.db.ProductService;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.modules.qualifiers.DBProductListToProductListTranslator;
import com.fred.inventory.domain.modules.qualifiers.ProductListToDBProductListTranslator;
import com.fred.inventory.domain.translators.Translator;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;
import timber.log.Timber;

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
    return translateToDB(productList).flatMap(new CreateOrUpdateFunc())
        .map(new TranslateToDomainFunc())
        .map(new Func1<ProductList, ProductList>() {
          @Override public ProductList call(ProductList productList) {
            Timber.d("Saving product list with id %s", productList.getId());
            return productList;
          }
        });
  }

  /**
   * Return an observable that will emit the translated product list at subscription time
   *
   * @param productList The product list to translate
   * @return An observable that emits the translated list
   */
  private Observable<com.fred.inventory.data.db.models.ProductList> translateToDB(
      final ProductList productList) {
    return Observable.fromCallable(new Func0<com.fred.inventory.data.db.models.ProductList>() {
      @Override public com.fred.inventory.data.db.models.ProductList call() {
        return domainToDBTranslator.translate(productList);
      }
    });
  }

  /**
   * Func1 that will create or update the product list in the db
   */
  private class CreateOrUpdateFunc implements
      Func1<com.fred.inventory.data.db.models.ProductList, Observable<com.fred.inventory.data.db.models.ProductList>> {
    @Override public Observable<com.fred.inventory.data.db.models.ProductList> call(
        com.fred.inventory.data.db.models.ProductList productList) {
      return service.createOrUpdate(productList);
    }
  }

  /**
   * Func1 that will translate a db product list into a domain product list
   */
  private class TranslateToDomainFunc
      implements Func1<com.fred.inventory.data.db.models.ProductList, ProductList> {
    @Override public ProductList call(com.fred.inventory.data.db.models.ProductList productList) {
      return dbToDomainTranslator.translate(productList);
    }
  }
}
