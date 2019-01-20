package com.fred.inventory.presentation.supplies.adapters.comparators;

import com.fred.inventory.data.firebase.models.SuppliesList;
import java.util.Comparator;
import javax.inject.Inject;

public class SuppliesListComparator implements Comparator<SuppliesList> {
  @Inject public SuppliesListComparator() {
  }

  @Override public int compare(SuppliesList one, SuppliesList other) {
    return one.name().compareToIgnoreCase(other.name());
  }
}
