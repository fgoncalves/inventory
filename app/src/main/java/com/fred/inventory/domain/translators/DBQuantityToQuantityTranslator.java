package com.fred.inventory.domain.translators;

import com.fred.inventory.domain.models.Quantity;
import javax.inject.Inject;

/**
 * This defines the translator between a quantity domain model into a db quantity model
 */
public class DBQuantityToQuantityTranslator
    implements Translator<com.fred.inventory.data.db.models.Quantity, Quantity> {
  @Inject public DBQuantityToQuantityTranslator() {
  }

  @Override public Quantity translate(com.fred.inventory.data.db.models.Quantity model) {
    Quantity quantity = new Quantity();
    quantity.setQuantity(model.getQuantity());
    quantity.setQuantityUnit(model.getQuantityUnit());
    return quantity;
  }
}
