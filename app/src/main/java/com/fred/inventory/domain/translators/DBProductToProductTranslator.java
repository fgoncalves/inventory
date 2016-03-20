package com.fred.inventory.domain.translators;

import com.fred.inventory.domain.models.Image;
import com.fred.inventory.domain.models.Product;
import com.fred.inventory.domain.models.Info;
import javax.inject.Inject;

/**
 * This class describes how to translate product domain models into db product models.
 */
public class DBProductToProductTranslator
    implements Translator<com.fred.inventory.data.db.models.Product, Product> {
  private final Translator<com.fred.inventory.data.db.models.Image, Image> imageToImageTranslator;
  private final Translator<com.fred.inventory.data.db.models.Info, Info>
      quantityToQuantityTranslator;

  @Inject public DBProductToProductTranslator(
      @com.fred.inventory.domain.modules.qualifiers.DBImageToImageTranslator
      Translator<com.fred.inventory.data.db.models.Image, Image> imageToImageTranslator,
      @com.fred.inventory.domain.modules.qualifiers.DBQuantityToQuantityTranslator
      Translator<com.fred.inventory.data.db.models.Info, Info> quantityToQuantityTranslator) {
    this.imageToImageTranslator = imageToImageTranslator;
    this.quantityToQuantityTranslator = quantityToQuantityTranslator;
  }

  @Override public Product translate(com.fred.inventory.data.db.models.Product model) {
    Product product = new Product();
    product.setId(model.getId());
    for (com.fred.inventory.data.db.models.Image image : model.getImages())
      product.getImages().add(imageToImageTranslator.translate(image));
    for (com.fred.inventory.data.db.models.Info info : model.getQuantities())
      product.getQuantities().add(quantityToQuantityTranslator.translate(info));
    return product;
  }
}
