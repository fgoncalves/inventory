package com.fred.inventory.domain.translators;

import com.fred.inventory.domain.models.Product;
import com.fred.inventory.domain.models.ProductList;
import javax.inject.Inject;

/**
 * Translator for the product lists. It transforms a domain product list into a db
 * <p/>
 * Created by fred on 13.03.16.
 */
public class DBProductListToProductListTranslator
    implements Translator<com.fred.inventory.data.db.models.ProductList, ProductList> {
  private final Translator<com.fred.inventory.data.db.models.Product, Product>
      dbProductToProductTranslator;

  @Inject public DBProductListToProductListTranslator(
      @com.fred.inventory.domain.modules.qualifiers.DBProductToProductTranslator
      Translator<com.fred.inventory.data.db.models.Product, Product> dbProductToProductTranslator) {
    this.dbProductToProductTranslator = dbProductToProductTranslator;
  }

  @Override public ProductList translate(com.fred.inventory.data.db.models.ProductList model) {
    ProductList productList = new ProductList();
    productList.setName(model.getName());
    productList.setId(model.getId());
    for (com.fred.inventory.data.db.models.Product product : model.getProducts())
      productList.getProducts().add(dbProductToProductTranslator.translate(product));
    return productList;
  }
}
