package com.fred.inventory.presentation.globalsearch.adapters.comparators;

import com.fred.inventory.domain.models.GlobalSearchResult;
import java.util.Comparator;
import javax.inject.Inject;

public class GlobalSearchResultComparator implements Comparator<GlobalSearchResult> {
  @Inject public GlobalSearchResultComparator() {
  }

  @Override public int compare(GlobalSearchResult one, GlobalSearchResult other) {
    int productListNameComparison =
        one.getProductListName().compareToIgnoreCase(other.getProductListName());
    if (productListNameComparison != 0) return productListNameComparison;
    return one.getProduct().getName().compareToIgnoreCase(other.getProduct().getName());
  }
}
