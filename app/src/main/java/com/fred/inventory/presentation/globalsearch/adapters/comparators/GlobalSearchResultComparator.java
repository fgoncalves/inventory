package com.fred.inventory.presentation.globalsearch.adapters.comparators;

import com.fred.inventory.domain.models.GlobalSearchResult;
import java.util.Comparator;
import javax.inject.Inject;

public class GlobalSearchResultComparator implements Comparator<GlobalSearchResult> {
  @Inject public GlobalSearchResultComparator() {
  }

  @Override public int compare(GlobalSearchResult one, GlobalSearchResult other) {
    int productListNameComparison =
        one.suppliesList().name().compareToIgnoreCase(other.suppliesList().name());
    if (productListNameComparison != 0) return productListNameComparison;
    return one.supplyItem().name().compareToIgnoreCase(other.supplyItem().name());
  }
}
