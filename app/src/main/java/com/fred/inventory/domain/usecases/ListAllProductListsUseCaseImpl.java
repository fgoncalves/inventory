package com.fred.inventory.domain.usecases;

import com.fred.inventory.data.db.ProductService;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.translators.Translator;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Func1;

public class ListAllProductListsUseCaseImpl implements ListAllProductListsUseCase {
  private final ProductService service;
  private final Translator<com.fred.inventory.data.db.models.ProductList, ProductList>
      dbProductListToProductListTranslator;

  @Inject public ListAllProductListsUseCaseImpl(ProductService service,
      @com.fred.inventory.domain.modules.qualifiers.DBProductListToProductListTranslator
      Translator<com.fred.inventory.data.db.models.ProductList, ProductList> dbProductListToProductListTranslator) {
    this.service = service;
    this.dbProductListToProductListTranslator = dbProductListToProductListTranslator;
  }

  @Override public Observable<List<ProductList>> list() {
    return service.all()
        .map(new Func1<List<com.fred.inventory.data.db.models.ProductList>, List<ProductList>>() {
          @Override public List<ProductList> call(
              List<com.fred.inventory.data.db.models.ProductList> productLists) {
            List<ProductList> result = new ArrayList<>();
            for (com.fred.inventory.data.db.models.ProductList productList : productLists)
              result.add(dbProductListToProductListTranslator.translate(productList));

            return result;
          }
        });
  }
}
