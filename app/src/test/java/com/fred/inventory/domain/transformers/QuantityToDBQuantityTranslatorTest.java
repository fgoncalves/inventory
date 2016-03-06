package com.fred.inventory.domain.transformers;

import com.fred.inventory.domain.models.Quantity;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuantityToDBQuantityTranslatorTest {
  private QuantityToDBQuantityTranslator translator;

  @Before public void setUp() throws Exception {
    translator = new QuantityToDBQuantityTranslator();
  }

  @Test public void translate_shouldCreateAQuantityDbModelWithTheSpecifiedData() {
    String expectQuantityUnit = "Kg";
    int expectQuantity = 123;
    Quantity quantity = new Quantity();
    quantity.setQuantityUnit(expectQuantityUnit);
    quantity.setQuantity(expectQuantity);

    com.fred.inventory.data.db.models.Quantity result = translator.translate(quantity);

    assertThat(result.getQuantity()).isEqualTo(expectQuantity);
    assertThat(result.getQuantityUnit()).isEqualTo(expectQuantityUnit);
  }
}
