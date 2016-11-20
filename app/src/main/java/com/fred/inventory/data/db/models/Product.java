package com.fred.inventory.data.db.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.Date;

public class Product extends RealmObject {
  @PrimaryKey private String id;
  private String name;
  /**
   * Internal quantity intended only for display purposes
   */
  private int quantity;
  private boolean unit;
  private String barcode;
  private String quantityLabel;
  private Date expirationDate;
  private RealmList<Image> images = new RealmList<>();

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

  public RealmList<Image> getImages() {
    return images;
  }

  public void setImages(RealmList<Image> images) {
    this.images = images;
  }

  public String getQuantityLabel() {
    return quantityLabel;
  }

  public void setQuantityLabel(String quantityLabel) {
    this.quantityLabel = quantityLabel;
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = expirationDate;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getBarcode() {
    return barcode;
  }

  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }

  public boolean isUnit() {
    return unit;
  }

  public void setUnit(boolean unit) {
    this.unit = unit;
  }
}
