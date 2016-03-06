package com.fred.inventory.data.db.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * The image class holds the information about the image for the product locally
 */
public class Image extends RealmObject {
  @PrimaryKey
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
