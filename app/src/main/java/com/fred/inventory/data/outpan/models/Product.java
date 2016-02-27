package com.fred.inventory.data.outpan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product {

  @SerializedName("gtin") @Expose private String gtin;
  @SerializedName("outpan_url") @Expose private String outpanUrl;
  @SerializedName("name") @Expose private String name;
  @SerializedName("attributes") @Expose private Map<String, String> attributes = new HashMap<>();
  @SerializedName("images") @Expose private List<String> images = new ArrayList<>();
  @SerializedName("videos") @Expose private List<String> videos = new ArrayList<>();
  @SerializedName("categories") @Expose private List<String> categories = new ArrayList<>();

  public String getGtin() {
    return gtin;
  }

  public void setGtin(String gtin) {
    this.gtin = gtin;
  }

  public String getOutpanUrl() {
    return outpanUrl;
  }

  public void setOutpanUrl(String outpanUrl) {
    this.outpanUrl = outpanUrl;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<String, String> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, String> attributes) {
    this.attributes = attributes;
  }

  public List<String> getImages() {
    return images;
  }

  public void setImages(List<String> images) {
    this.images = images;
  }

  public List<String> getVideos() {
    return videos;
  }

  public void setVideos(List<String> videos) {
    this.videos = videos;
  }

  public List<String> getCategories() {
    return categories;
  }

  public void setCategories(List<String> categories) {
    this.categories = categories;
  }
}
