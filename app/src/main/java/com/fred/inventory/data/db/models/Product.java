package com.fred.inventory.data.db.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.Date;

public class Product extends RealmObject {
  /**
   * Same as the bar code
   */
  @PrimaryKey private String id;

  private String quantity;
  private Date expirationDate;
  private RealmList<Image> images = new RealmList<>();

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

  public String getQuantity() {
    return quantity;
  }

  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = expirationDate;
  }
}
