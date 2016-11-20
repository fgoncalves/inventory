package com.fred.inventory.domain.translators;

import com.fred.inventory.domain.models.Image;
import com.fred.inventory.domain.models.Product;
import javax.inject.Inject;

/**
 * This class describes how to translate product domain models into db product models.
 */
public class DBProductToProductTranslator
    implements Translator<com.fred.inventory.data.db.models.Product, Product> {
  private final Translator<com.fred.inventory.data.db.models.Image, Image> imageToImageTranslator;

  @Inject public DBProductToProductTranslator(
      @com.fred.inventory.domain.modules.qualifiers.DBImageToImageTranslator
          Translator<com.fred.inventory.data.db.models.Image, Image> imageToImageTranslator) {
    this.imageToImageTranslator = imageToImageTranslator;
  }

  @Override public Product translate(com.fred.inventory.data.db.models.Product model) {
    Product product = new Product();
    product.setName(model.getName());
    product.setId(model.getId());
    product.setExpirationDate(model.getExpirationDate());
    product.setQuantityLabel(model.getQuantityLabel());
    product.setQuantity(model.getQuantity());
    product.setUnit(model.isUnit());
    product.setBarcode(model.getBarcode());
    for (com.fred.inventory.data.db.models.Image image : model.getImages())
      product.getImages().add(imageToImageTranslator.translate(image));
    return product;
  }
}
