package com.fred.inventory.presentation.productlist.adapters.comparators;

import com.fred.inventory.domain.models.Product;
import java.util.Comparator;
import javax.inject.Inject;

public class ProductNameComparator implements Comparator<Product> {
  @Inject
  public ProductNameComparator() {
  }

  @Override public int compare(Product one, Product other) {
    return one.getName().compareToIgnoreCase(other.getName());
  }
}
