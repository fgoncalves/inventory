package com.fred.inventory.domain.translators;

import com.fred.inventory.domain.models.Product;
import com.fred.inventory.domain.models.ProductList;
import javax.inject.Inject;

/**
 * Translator for the product lists. It transforms a domain product list into a db
 * <p/>
 * Created by fred on 13.03.16.
 */
public class ProductListToDBProductListTranslator
    implements Translator<ProductList, com.fred.inventory.data.db.models.ProductList> {
  private final Translator<Product, com.fred.inventory.data.db.models.Product>
      productToDBProductTranslator;

  @Inject public ProductListToDBProductListTranslator(
      @com.fred.inventory.domain.modules.qualifiers.ProductToDBProductTranslator
      Translator<Product, com.fred.inventory.data.db.models.Product> productToDBProductTranslator) {
    this.productToDBProductTranslator = productToDBProductTranslator;
  }

  @Override public com.fred.inventory.data.db.models.ProductList translate(ProductList model) {
    com.fred.inventory.data.db.models.ProductList productList =
        new com.fred.inventory.data.db.models.ProductList();
    productList.setName(model.getName());
    for (Product product : model.getProducts())
      productList.getProducts().add(productToDBProductTranslator.translate(product));
    return productList;
  }
}
