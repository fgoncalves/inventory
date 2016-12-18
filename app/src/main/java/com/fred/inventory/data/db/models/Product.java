package com.fred.inventory.data.db.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product implements Entity {
  private Long _id;
  private String name;
  /**
   * Internal quantity intended only for display purposes
   */
  private int quantity;
  private boolean unit;
  private String barcode;
  private String quantityLabel;
  private Date expirationDate;
  private List<Image> images = new ArrayList<>();

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

  public List<Image> getImages() {
    return images;
  }

  public void setImages(List<Image> images) {
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
