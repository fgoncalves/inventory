package com.fred.inventory.data.outpan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Product {

  @SerializedName("gtin") @Expose private String gtin;
  @SerializedName("outpan_url") @Expose private String outpanUrl;
  @SerializedName("name") @Expose private String name;
  @SerializedName("attributes") @Expose private Map<String, String> attributes;
  @SerializedName("images") @Expose private List<String> images = new ArrayList<String>();
  @SerializedName("videos") @Expose private List<Object> videos = new ArrayList<Object>();
  @SerializedName("categories") @Expose private List<String> categories = new ArrayList<String>();

  /**
   * @return The gtin
   */
  public String getGtin() {
    return gtin;
  }

  /**
   * @param gtin The gtin
   */
  public void setGtin(String gtin) {
    this.gtin = gtin;
  }

  /**
   * @return The outpanUrl
   */
  public String getOutpanUrl() {
    return outpanUrl;
  }

  /**
   * @param outpanUrl The outpan_url
   */
  public void setOutpanUrl(String outpanUrl) {
    this.outpanUrl = outpanUrl;
  }

  /**
   * @return The name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name The name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return The images
   */
  public List<String> getImages() {
    return images;
  }

  /**
   * @param images The images
   */
  public void setImages(List<String> images) {
    this.images = images;
  }

  /**
   * @return The videos
   */
  public List<Object> getVideos() {
    return videos;
  }

  /**
   * @param videos The videos
   */
  public void setVideos(List<Object> videos) {
    this.videos = videos;
  }

  /**
   * @return The categories
   */
  public List<String> getCategories() {
    return categories;
  }

  /**
   * @param categories The categories
   */
  public void setCategories(List<String> categories) {
    this.categories = categories;
  }
}
