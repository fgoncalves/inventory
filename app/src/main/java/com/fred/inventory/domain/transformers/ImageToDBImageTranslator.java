package com.fred.inventory.domain.transformers;

import com.fred.inventory.domain.models.Image;
import javax.inject.Inject;

/**
 * Translate an image domain model to an image db model
 */
public class ImageToDBImageTranslator
    implements Translator<Image, com.fred.inventory.data.db.models.Image> {

  @Inject public ImageToDBImageTranslator() {
  }

  @Override public com.fred.inventory.data.db.models.Image translate(Image model) {
    com.fred.inventory.data.db.models.Image image = new com.fred.inventory.data.db.models.Image();
    image.setName(model.getName());
    return image;
  }
}
