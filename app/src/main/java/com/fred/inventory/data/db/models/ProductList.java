package com.fred.inventory.data.db.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * This class represents a product list. Basically it's possible to have more than one list of
 * supplies.
 */
public class ProductList extends RealmObject {
  private RealmList<Product> products = new RealmList<>();

  public RealmList<Product> getProducts() {
    return products;
  }

  public void setProducts(RealmList<Product> products) {
    this.products = products;
  }
}
