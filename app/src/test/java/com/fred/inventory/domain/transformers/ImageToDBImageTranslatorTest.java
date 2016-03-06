package com.fred.inventory.domain.transformers;

import com.fred.inventory.domain.models.Image;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageToDBImageTranslatorTest {
  private ImageToDBImageTranslator imageToDBImageTranslator;

  @Before public void setUp() throws Exception {
    imageToDBImageTranslator = new ImageToDBImageTranslator();
  }

  @Test public void translate_shouldProvideADbImageWithTheCorrectDataInIt() {
    String expectedName = "some name";
    Image image = new Image();
    image.setName(expectedName);

    com.fred.inventory.data.db.models.Image result = imageToDBImageTranslator.translate(image);

    assertThat(result.getName()).isEqualTo(expectedName);
  }
}
