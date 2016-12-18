package com.fred.inventory.domain.usecases;

import com.fred.inventory.data.db.ProductService;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.translators.Translator;
import javax.inject.Inject;
import rx.Observable;
import timber.log.Timber;

public class GetProductListUseCaseImpl implements GetProductListUseCase {
  private final ProductService service;
  private final Translator<com.fred.inventory.data.db.models.ProductList, ProductList> translator;

  @Inject public GetProductListUseCaseImpl(ProductService service,
      @com.fred.inventory.domain.modules.qualifiers.DBProductListToProductListTranslator
          Translator<com.fred.inventory.data.db.models.ProductList, ProductList> translator) {
    this.service = service;
    this.translator = translator;
  }

  @Override public Observable<ProductList> get(Long id) {
    Timber.d("Retrieving product list with id %s", id);
    return service.productList(id).map(translator::translate);
  }
}
