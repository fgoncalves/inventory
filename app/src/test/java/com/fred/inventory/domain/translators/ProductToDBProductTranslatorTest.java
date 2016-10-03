package com.fred.inventory.domain.translators;

import com.fred.inventory.domain.models.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductToDBProductTranslatorTest {
  @Mock ImageToDBImageTranslator imageToDBImageTranslator;
  @Mock InfoToDBInfoTranslator infoToDBInfoTranslator;

  private ProductToDBProductTranslator translator;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    translator =
        new ProductToDBProductTranslator(imageToDBImageTranslator);
  }

  @Test public void translate_shouldCreateADBProductModelWithTheCorrectData() {
    String expectId = "some id";
    Product product = new Product();
    product.setId(expectId);

    com.fred.inventory.data.db.models.Product result = translator.translate(product);

    assertThat(product.getId()).isEqualTo(expectId);
    assertThat(product.getImages()).isEmpty();
    assertThat(product.getQuantities()).isEmpty();
  }
}
