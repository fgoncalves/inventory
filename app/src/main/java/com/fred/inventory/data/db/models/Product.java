package com.fred.inventory.data.db.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Product extends RealmObject {
  /**
   * Same as the bar code
   */
  @PrimaryKey private String id;
  private RealmList<Image> images = new RealmList<>();
  private RealmList<Info> quantities = new RealmList<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public RealmList<Image> getImages() {
    return images;
  }

  public void setImages(RealmList<Image> images) {
    this.images = images;
  }

  public RealmList<Info> getQuantities() {
    return quantities;
  }

  public void setQuantities(RealmList<Info> quantities) {
    this.quantities = quantities;
  }
}
