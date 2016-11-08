package com.fred.inventory.domain.translators;

import com.fred.inventory.domain.models.Image;
import com.fred.inventory.domain.models.Product;
import javax.inject.Inject;

/**
 * This class describes how to translate product domain models into db product models.
 */
public class ProductToDBProductTranslator
    implements Translator<Product, com.fred.inventory.data.db.models.Product> {
  private final Translator<Image, com.fred.inventory.data.db.models.Image> imageToDBImageTranslator;

  @Inject public ProductToDBProductTranslator(
      @com.fred.inventory.domain.modules.qualifiers.ImageToDBImageTranslator
          Translator<Image, com.fred.inventory.data.db.models.Image> imageToDBImageTranslator) {
    this.imageToDBImageTranslator = imageToDBImageTranslator;
  }

  @Override public com.fred.inventory.data.db.models.Product translate(Product model) {
    com.fred.inventory.data.db.models.Product product =
        new com.fred.inventory.data.db.models.Product();
    product.setId(model.getId());
    product.setExpirationDate(model.getExpirationDate());
    product.setQuantityLabel(model.getQuantityLabel());
    product.setQuantity(model.getQuantity());
    product.setBarcode(model.getBarcode());
    for (Image image : model.getImages())
      product.getImages().add(imageToDBImageTranslator.translate(image));
    return product;
  }
}
