package com.fred.inventory.presentation.productlist.adapters.comparators;

import com.fred.inventory.data.firebase.models.SupplyItem;
import java.util.Comparator;
import javax.inject.Inject;

public class ProductNameComparator implements Comparator<SupplyItem> {
  @Inject public ProductNameComparator() {
  }

  @Override public int compare(SupplyItem one, SupplyItem other) {
    return one.name().compareToIgnoreCase(other.name());
  }
}
