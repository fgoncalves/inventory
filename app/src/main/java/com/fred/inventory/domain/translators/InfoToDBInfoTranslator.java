package com.fred.inventory.domain.translators;

import com.fred.inventory.domain.models.Info;
import javax.inject.Inject;

/**
 * This defines the translator between a quantity domain model into a db quantity model
 */
public class InfoToDBInfoTranslator
    implements Translator<Info, com.fred.inventory.data.db.models.Info> {
  @Inject public InfoToDBInfoTranslator() {
  }

  @Override public com.fred.inventory.data.db.models.Info translate(Info model) {
    com.fred.inventory.data.db.models.Info info = new com.fred.inventory.data.db.models.Info();
    info.setQuantity(model.getQuantity());
    info.setQuantityUnit(model.getQuantityUnit());
    info.setExpirationDate(model.getExpirationDate());
    return info;
  }
}
