package com.fred.inventory.domain.translators;

import com.fred.inventory.domain.models.Info;
import javax.inject.Inject;

/**
 * This defines the translator between a quantity domain model into a db quantity model
 */
public class DBInfoToInfoTranslator
    implements Translator<com.fred.inventory.data.db.models.Info, Info> {
  @Inject public DBInfoToInfoTranslator() {
  }

  @Override public Info translate(com.fred.inventory.data.db.models.Info model) {
    Info info = new Info();
    info.setQuantity(model.getQuantity());
    info.setQuantityUnit(model.getQuantityUnit());
    info.setExpirationDate(model.getExpirationDate());
    return info;
  }
}
