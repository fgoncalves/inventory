package com.fred.inventory.domain.transformers;

import com.fred.inventory.domain.models.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

public class DBProductToProductTranslatorTest {
  @Mock DBImageToImageTranslator imageToDBImageTranslator;
  @Mock DBQuantityToQuantityTranslator quantityToDBQuantityTranslator;

  private DBProductToProductTranslator translator;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    translator =
        new DBProductToProductTranslator(imageToDBImageTranslator, quantityToDBQuantityTranslator);
  }

  @Test public void translate_shouldCreateADBProductModelWithTheCorrectData() {
    String expectId = "some id";
    com.fred.inventory.data.db.models.Product product =
        new com.fred.inventory.data.db.models.Product();
    product.setId(expectId);

    Product result = translator.translate(product);

    assertThat(product.getId()).isEqualTo(expectId);
    assertThat(product.getImages()).isEmpty();
    assertThat(product.getQuantities()).isEmpty();
  }
}
