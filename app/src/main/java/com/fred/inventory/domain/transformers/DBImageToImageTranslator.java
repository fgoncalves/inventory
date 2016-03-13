package com.fred.inventory.domain.transformers;

import com.fred.inventory.domain.models.Image;
import javax.inject.Inject;

/**
 * Translate an image domain model to an image db model
 */
public class DBImageToImageTranslator
    implements Translator<com.fred.inventory.data.db.models.Image, Image> {

  @Inject public DBImageToImageTranslator() {
  }

  @Override public Image translate(com.fred.inventory.data.db.models.Image model) {
    Image image = new Image();
    image.setName(model.getName());
    return image;
  }
}
