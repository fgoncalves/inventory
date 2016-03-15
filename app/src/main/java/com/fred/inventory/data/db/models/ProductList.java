package com.fred.inventory.data.db.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * This class represents a product list. Basically it's possible to have more than one list of
 * supplies.
 */
public class ProductList extends RealmObject {
  @PrimaryKey private String id;
  private String name;
  private RealmList<Product> products = new RealmList<>();

  public RealmList<Product> getProducts() {
    return products;
  }

  public void setProducts(RealmList<Product> products) {
    this.products = products;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
