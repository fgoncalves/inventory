package com.fred.inventory.domain.models;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a product list. Basically it's possible to have more than one list of
 * supplies.
 */
public class ProductList {
  private Long id;
  private String name;
  private List<Product> products = new ArrayList<>();

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
