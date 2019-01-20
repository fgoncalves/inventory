package com.fred.inventory.presentation.supplieslist.adapters.comparators;

import com.fred.inventory.data.firebase.models.SupplyItem;
import java.util.Comparator;
import javax.inject.Inject;

public class SupplyItemNameComparator implements Comparator<SupplyItem> {
  @Inject public SupplyItemNameComparator() {
  }

  @Override public int compare(SupplyItem one, SupplyItem other) {
    return one.name().compareToIgnoreCase(other.name());
  }
}
