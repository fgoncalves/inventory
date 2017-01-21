package com.fred.inventory.domain.translators;

import android.support.annotation.NonNull;
import com.fred.inventory.data.db.models.ProductList;
import com.fred.inventory.domain.models.GlobalSearchResult;
import com.fred.inventory.domain.models.Product;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Translate a list of product lists into a list of global search results
 * <p/>
 * Created by fred on 13.03.16.
 */
public class DBProductListListToGlobalSearchResultListTranslator
    implements Translator<List<ProductList>, List<GlobalSearchResult>> {

  private final Translator<com.fred.inventory.data.db.models.Product, Product>
      dbProductToProductTranslator;

  @Inject public DBProductListListToGlobalSearchResultListTranslator(
      @com.fred.inventory.domain.modules.qualifiers.DBProductToProductTranslator
          Translator<com.fred.inventory.data.db.models.Product, Product> dbProductToProductTranslator) {
    this.dbProductToProductTranslator = dbProductToProductTranslator;
  }

  @Override public List<GlobalSearchResult> translate(List<ProductList> model) {
    List<GlobalSearchResult> results = new ArrayList<>();
    for (ProductList productList : model) {
      results.addAll(convert(productList));
    }
    return results;
  }

  @NonNull private List<GlobalSearchResult> convert(ProductList productList) {
    List<GlobalSearchResult> result = new ArrayList<>();
    for (com.fred.inventory.data.db.models.Product product : productList.getProducts()) {
      GlobalSearchResult globalSearchResult = new GlobalSearchResult();
      globalSearchResult.setProductListId(productList.getId());
      globalSearchResult.setProductListName(productList.getName());
      globalSearchResult.setProduct(dbProductToProductTranslator.translate(product));
      result.add(globalSearchResult);
    }

    return result;
  }
}
