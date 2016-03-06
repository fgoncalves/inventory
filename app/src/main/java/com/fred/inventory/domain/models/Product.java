package com.fred.inventory.domain.models;

import io.realm.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.List;

public class Product {
  /**
   * Same as the bar code
   */
  @PrimaryKey private String id;
  private List<Image> images = new ArrayList<>();
  private List<Quantity> quantities = new ArrayList<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<Image> getImages() {
    return images;
  }

  public void setImages(List<Image> images) {
    this.images = images;
  }

  public List<Quantity> getQuantities() {
    return quantities;
  }

  public void setQuantities(List<Quantity> quantities) {
    this.quantities = quantities;
  }
}
