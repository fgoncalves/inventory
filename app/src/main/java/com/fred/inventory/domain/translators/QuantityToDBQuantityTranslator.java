package com.fred.inventory.domain.translators;

import com.fred.inventory.domain.models.Quantity;
import javax.inject.Inject;

/**
 * This defines the translator between a quantity domain model into a db quantity model
 */
public class QuantityToDBQuantityTranslator
    implements Translator<Quantity, com.fred.inventory.data.db.models.Quantity> {
  @Inject public QuantityToDBQuantityTranslator() {
  }

  @Override public com.fred.inventory.data.db.models.Quantity translate(Quantity model) {
    com.fred.inventory.data.db.models.Quantity quantity =
        new com.fred.inventory.data.db.models.Quantity();
    quantity.setQuantity(model.getQuantity());
    quantity.setQuantityUnit(model.getQuantityUnit());
    return quantity;
  }
}
