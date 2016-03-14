package com.fred.inventory.domain.translators;

import com.fred.inventory.domain.models.Image;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DBImageToImageTranslatorTest {
  private DBImageToImageTranslator dbImageToImageTranslator;

  @Before public void setUp() throws Exception {
    dbImageToImageTranslator = new DBImageToImageTranslator();
  }

  @Test public void translate_shouldProvideADbImageWithTheCorrectDataInIt() {
    String expectedName = "some name";
    com.fred.inventory.data.db.models.Image image = new com.fred.inventory.data.db.models.Image();
    image.setName(expectedName);

    Image result = dbImageToImageTranslator.translate(image);

    assertThat(result.getName()).isEqualTo(expectedName);
  }
}
