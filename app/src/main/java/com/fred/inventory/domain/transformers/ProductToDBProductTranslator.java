package com.fred.inventory.domain.transformers;

import com.fred.inventory.domain.models.Image;
import com.fred.inventory.domain.models.Product;
import com.fred.inventory.domain.models.Quantity;
import javax.inject.Inject;

/**
 * This class describes how to translate product domain models into db product models.
 */
public class ProductToDBProductTranslator
    implements Translator<Product, com.fred.inventory.data.db.models.Product> {
  private final ImageToDBImageTranslator imageToDBImageTranslator;
  private final QuantityToDBQuantityTranslator quantityToDBQuantityTranslator;

  @Inject public ProductToDBProductTranslator(ImageToDBImageTranslator imageToDBImageTranslator,
      QuantityToDBQuantityTranslator quantityToDBQuantityTranslator) {
    this.imageToDBImageTranslator = imageToDBImageTranslator;
    this.quantityToDBQuantityTranslator = quantityToDBQuantityTranslator;
  }

  @Override public com.fred.inventory.data.db.models.Product translate(Product model) {
    com.fred.inventory.data.db.models.Product product =
        new com.fred.inventory.data.db.models.Product();
    product.setId(model.getId());
    for (Image image : model.getImages())
      product.getImages().add(imageToDBImageTranslator.translate(image));
    for (Quantity quantity : model.getQuantities())
      product.getQuantities().add(quantityToDBQuantityTranslator.translate(quantity));
    return product;
  }
}
