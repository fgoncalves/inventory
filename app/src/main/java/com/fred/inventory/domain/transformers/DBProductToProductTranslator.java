package com.fred.inventory.domain.transformers;

import com.fred.inventory.domain.models.Product;
import javax.inject.Inject;

/**
 * This class describes how to translate product domain models into db product models.
 */
public class DBProductToProductTranslator
    implements Translator<com.fred.inventory.data.db.models.Product, Product> {
  private final DBImageToImageTranslator imageToImageTranslator;
  private final DBQuantityToQuantityTranslator quantityToQuantityTranslator;

  @Inject public DBProductToProductTranslator(DBImageToImageTranslator imageToImageTranslator,
      DBQuantityToQuantityTranslator quantityToQuantityTranslator) {
    this.imageToImageTranslator = imageToImageTranslator;
    this.quantityToQuantityTranslator = quantityToQuantityTranslator;
  }

  @Override public Product translate(com.fred.inventory.data.db.models.Product model) {
    Product product = new Product();
    product.setId(model.getId());
    for (com.fred.inventory.data.db.models.Image image : model.getImages())
      product.getImages().add(imageToImageTranslator.translate(image));
    for (com.fred.inventory.data.db.models.Quantity quantity : model.getQuantities())
      product.getQuantities().add(quantityToQuantityTranslator.translate(quantity));
    return product;
  }
}
