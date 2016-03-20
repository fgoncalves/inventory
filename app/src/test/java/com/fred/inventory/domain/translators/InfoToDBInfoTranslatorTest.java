package com.fred.inventory.domain.translators;

import com.fred.inventory.domain.models.Info;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InfoToDBInfoTranslatorTest {
  private InfoToDBInfoTranslator translator;

  @Before public void setUp() throws Exception {
    translator = new InfoToDBInfoTranslator();
  }

  @Test public void translate_shouldCreateAQuantityDbModelWithTheSpecifiedData() {
    String expectQuantityUnit = "Kg";
    String expectExpirationDate = "any expiration date";
    int expectQuantity = 123;
    Info info = new Info();
    info.setQuantityUnit(expectQuantityUnit);
    info.setQuantity(expectQuantity);
    info.setExpirationDate(expectExpirationDate);

    com.fred.inventory.data.db.models.Info result = translator.translate(info);

    assertThat(result.getQuantity()).isEqualTo(expectQuantity);
    assertThat(result.getQuantityUnit()).isEqualTo(expectQuantityUnit);
    assertThat(result.getExpirationDate()).isEqualTo(expectExpirationDate);
  }
}
