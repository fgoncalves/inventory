package com.fred.inventory.domain.transformers;

import com.fred.inventory.domain.models.Quantity;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DBQuantityToQuantityTranslatorTest {
  private DBQuantityToQuantityTranslator dbQuantityToQuantityTranslator;

  @Before public void setUp() throws Exception {
    dbQuantityToQuantityTranslator = new DBQuantityToQuantityTranslator();
  }

  @Test public void translate_shouldCreateAQuantityDbModelWithTheSpecifiedData() {
    String expectQuantityUnit = "Kg";
    int expectQuantity = 123;
    com.fred.inventory.data.db.models.Quantity quantity =
        new com.fred.inventory.data.db.models.Quantity();
    quantity.setQuantityUnit(expectQuantityUnit);
    quantity.setQuantity(expectQuantity);

    Quantity result = dbQuantityToQuantityTranslator.translate(quantity);

    assertThat(result.getQuantity()).isEqualTo(expectQuantity);
    assertThat(result.getQuantityUnit()).isEqualTo(expectQuantityUnit);
  }
}
