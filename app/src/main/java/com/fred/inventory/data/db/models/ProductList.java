package com.fred.inventory.data.db.models;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a product list. Basically it's possible to have more than one list of
 * supplies.
 */
public class ProductList implements Entity {
  private Long _id;
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

  @Override public Long getId() {
    return _id;
  }

  @Override public void setId(Long id) {
    this._id = id;
  }
}
