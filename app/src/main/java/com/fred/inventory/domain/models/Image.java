package com.fred.inventory.domain.models;

/**
 * The image class holds the information about the image for the product locally
 */
public class Image {
  private Long id;
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
