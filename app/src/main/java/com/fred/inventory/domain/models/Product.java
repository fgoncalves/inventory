package com.fred.inventory.domain.models;

import io.realm.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product {
  /**
   * Same as the bar code
   */
  @PrimaryKey private String id;
  private String name;
  private List<Image> images = new ArrayList<>();
  private String quantity;
  private Date expirationDate;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Image> getImages() {
    return images;
  }

  public void setImages(List<Image> images) {
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

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Product)) return false;

    Product product = (Product) o;

    return !(id != null ? !id.equals(product.id) : product.id != null);
  }

  @Override public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
