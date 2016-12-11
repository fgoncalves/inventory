package com.fred.inventory.domain.translators;

import com.fred.inventory.data.outpan.models.Product;
import javax.inject.Inject;

/**
 * Created by fred on 11.12.16.
 */

public class OutpanProductToProductTranslator
    implements Translator<Product, com.fred.inventory.domain.models.Product> {

  @Inject public OutpanProductToProductTranslator() {
  }

  @Override public com.fred.inventory.domain.models.Product translate(Product model) {
    com.fred.inventory.domain.models.Product domainProduct =
        new com.fred.inventory.domain.models.Product();
    domainProduct.setName(model.getName());
    domainProduct.setBarcode(model.getGtin());
    // TODO: Add support for images here
    return domainProduct;
  }
}
