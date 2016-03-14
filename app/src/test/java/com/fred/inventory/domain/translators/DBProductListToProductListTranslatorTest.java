package com.fred.inventory.domain.translators;

import com.fred.inventory.domain.models.ProductList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

public class DBProductListToProductListTranslatorTest {
  @Mock DBProductToProductTranslator dbProductToProductTranslator;

  private DBProductListToProductListTranslator dbProductListToProductListTranslator;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    dbProductListToProductListTranslator =
        new DBProductListToProductListTranslator(dbProductToProductTranslator);
  }

  @Test public void translate_shouldCreateADBModelWithoutAnyProductsButWithName() {
    com.fred.inventory.data.db.models.ProductList productList =
        new com.fred.inventory.data.db.models.ProductList();
    String expectedName = "product list";
    productList.setName(expectedName);

    ProductList result = dbProductListToProductListTranslator.translate(productList);

    assertThat(result.getName()).isEqualTo(expectedName);
    assertThat(result.getProducts()).isEmpty();
  }
}
