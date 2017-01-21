package com.fred.inventory.domain.usecases;

import android.support.annotation.NonNull;
import com.fred.inventory.data.db.ProductService;
import com.fred.inventory.data.db.models.ProductList;
import com.fred.inventory.domain.models.GlobalSearchResult;
import com.fred.inventory.domain.modules.qualifiers.DBProductListListToGlobalSearchResultListTranslator;
import com.fred.inventory.domain.translators.Translator;
import com.fred.inventory.utils.IterableUtils;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

public class SearchForProductUseCaseImpl implements SearchForProductUseCase {
  private final ProductService service;
  private final Translator<List<ProductList>, List<GlobalSearchResult>> translator;

  @Inject public SearchForProductUseCaseImpl(ProductService service,
      @DBProductListListToGlobalSearchResultListTranslator
          Translator<List<ProductList>, List<GlobalSearchResult>> translator) {
    this.service = service;
    this.translator = translator;
  }

  @Override public Observable<List<GlobalSearchResult>> search(@NonNull String query) {
    return service.all()
        .flatMap(Observable::from)
        .map(productList -> {
          ProductList mapped = new ProductList();
          mapped.setId(productList.getId());
          mapped.setName(productList.getName());
          mapped.setProducts(IterableUtils.filter(productList.getProducts(),
              element -> element.getName().toLowerCase().contains(query.toLowerCase())));
          return mapped;
        })
        .filter(productList -> !productList.getProducts().isEmpty())
        .toList()
        .map(translator::translate);
  }
}
