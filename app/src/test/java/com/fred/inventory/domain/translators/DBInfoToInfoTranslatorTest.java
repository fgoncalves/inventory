package com.fred.inventory.domain.translators;

import com.fred.inventory.domain.models.Info;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DBInfoToInfoTranslatorTest {
  private DBInfoToInfoTranslator dbInfoToInfoTranslator;

  @Before public void setUp() throws Exception {
    dbInfoToInfoTranslator = new DBInfoToInfoTranslator();
  }

  @Test public void translate_shouldCreateAQuantityDbModelWithTheSpecifiedData() {
    String expectQuantityUnit = "Kg";
    String expectExpirationDate = "some expiration date";
    int expectQuantity = 123;
    com.fred.inventory.data.db.models.Info info = new com.fred.inventory.data.db.models.Info();
    info.setQuantityUnit(expectQuantityUnit);
    info.setQuantity(expectQuantity);
    info.setExpirationDate(expectExpirationDate);

    Info result = dbInfoToInfoTranslator.translate(info);

    assertThat(result.getQuantity()).isEqualTo(expectQuantity);
    assertThat(result.getQuantityUnit()).isEqualTo(expectQuantityUnit);
    assertThat(result.getExpirationDate()).isEqualTo(expectExpirationDate);
  }
}
