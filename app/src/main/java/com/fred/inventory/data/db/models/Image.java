package com.fred.inventory.data.db.models;

/**
 * The image class holds the information about the image for the product locally
 */
public class Image implements Entity {
  private Long _id;
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override public Long getId() {
    return _id;
  }

  @Override public void setId(Long _id) {
    this._id = _id;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Image)) return false;

    Image image = (Image) o;

    return _id != null ? _id.equals(image._id) : image._id == null;
  }

  @Override public int hashCode() {
    return _id != null ? _id.hashCode() : 0;
  }
}
