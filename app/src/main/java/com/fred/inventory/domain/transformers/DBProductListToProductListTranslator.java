package com.fred.inventory.domain.transformers;

import com.fred.inventory.domain.models.ProductList;
import javax.inject.Inject;

/**
 * Translator for the product lists. It transforms a domain product list into a db
 * <p/>
 * Created by fred on 13.03.16.
 */
public class DBProductListToProductListTranslator
    implements Translator<com.fred.inventory.data.db.models.ProductList, ProductList> {
  private final DBProductToProductTranslator dbProductToProductTranslator;

  @Inject public DBProductListToProductListTranslator(
      DBProductToProductTranslator dbProductToProductTranslator) {
    this.dbProductToProductTranslator = dbProductToProductTranslator;
  }

  @Override public ProductList translate(com.fred.inventory.data.db.models.ProductList model) {
    ProductList productList = new ProductList();
    productList.setName(model.getName());
    for (com.fred.inventory.data.db.models.Product product : model.getProducts())
      productList.getProducts().add(dbProductToProductTranslator.translate(product));
    return productList;
  }
}
