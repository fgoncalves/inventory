package com.fred.inventory.data.db.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.Date;

public class Product extends RealmObject {
  public static final String NO_UNIT = null;
  /**
   * Same as the bar code
   */
  @PrimaryKey private String id;

  //for now an int should be enough
  private int quantity;
  /**
   * This represents the unit of the quantity.
   */
  private String quantityUnit;
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

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getQuantityUnit() {
    return quantityUnit;
  }

  public void setQuantityUnit(String quantityUnit) {
    this.quantityUnit = quantityUnit;
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = expirationDate;
  }
}
