package com.fred.inventory.data.datakick.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {
  @SerializedName("url") @Expose private final String url;

  private Image(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public static class Builder {
    private String url;

    public Builder withUrl(String url) {
      this.url = url;
      return this;
    }

    public Image build() {
      return new Image(url);
    }
  }
}
