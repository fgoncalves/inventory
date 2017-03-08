package com.fred.inventory.domain.translators;

import com.fred.inventory.data.firebase.models.SupplyItem;
import com.fred.inventory.data.outpan.models.Product;
import javax.inject.Inject;

public class OutpanProductToProductTranslator implements Translator<Product, SupplyItem> {

  @Inject public OutpanProductToProductTranslator() {
  }

  @Override public SupplyItem translate(Product model) {
    String name = model.getName();
    return SupplyItem.create("", name == null ? "" : name, 0, true, model.getGtin(), "1", null);
  }
}
