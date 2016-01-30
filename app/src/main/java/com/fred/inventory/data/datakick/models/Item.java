package com.fred.inventory.data.datakick.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.List;

public class Item extends Error {

  @SerializedName("gtin14") @Expose private final String gtin14;
  @SerializedName("brand_name") @Expose private final String brandName;
  @SerializedName("name") @Expose private final String name;
  @SerializedName("size") @Expose private final String size;
  @SerializedName("ingredients") @Expose private final String ingredients;
  @SerializedName("serving_size") @Expose private final String servingSize;
  @SerializedName("servings_per_container") @Expose private final String servingsPerContainer;
  @SerializedName("calories") @Expose private final Integer calories;
  @SerializedName("fat_calories") @Expose private final Integer fatCalories;
  @SerializedName("fat") @Expose private final Double fat;
  @SerializedName("potassium") @Expose private final Integer potassium;
  @SerializedName("carbohydrate") @Expose private final Integer carbohydrate;
  @SerializedName("sugars") @Expose private final Integer sugars;
  @SerializedName("protein") @Expose private final Integer protein;
  @SerializedName("publisher") @Expose private final String publisher;
  @SerializedName("images") @Expose private final List<Image> images;

  private Item(String errorMessage, String gtin14, String brandName, String name, String size,
      String ingredients, String servingSize, String servingsPerContainer, Integer calories,
      Integer fatCalories, Double fat, Integer potassium, Integer carbohydrate, Integer sugars,
      Integer protein, String publisher, List<Image> images) {
    super(errorMessage);
    this.gtin14 = gtin14;
    this.brandName = brandName;
    this.name = name;
    this.size = size;
    this.ingredients = ingredients;
    this.servingSize = servingSize;
    this.servingsPerContainer = servingsPerContainer;
    this.calories = calories;
    this.fatCalories = fatCalories;
    this.fat = fat;
    this.potassium = potassium;
    this.carbohydrate = carbohydrate;
    this.sugars = sugars;
    this.protein = protein;
    this.publisher = publisher;
    this.images = images;
  }

  public String getGtin14() {
    return gtin14;
  }

  public String getBrandName() {
    return brandName;
  }

  public String getName() {
    return name;
  }

  public String getSize() {
    return size;
  }

  public String getIngredients() {
    return ingredients;
  }

  public String getServingSize() {
    return servingSize;
  }

  public String getServingsPerContainer() {
    return servingsPerContainer;
  }

  public Integer getCalories() {
    return calories;
  }

  public Integer getFatCalories() {
    return fatCalories;
  }

  public Double getFat() {
    return fat;
  }

  public Integer getPotassium() {
    return potassium;
  }

  public Integer getCarbohydrate() {
    return carbohydrate;
  }

  public Integer getSugars() {
    return sugars;
  }

  public Integer getProtein() {
    return protein;
  }

  public String getPublisher() {
    return publisher;
  }

  public List<Image> getImages() {
    return Collections.unmodifiableList(images);
  }

  public static class Builder {
    private String errorMessage;
    private String gtin14;
    private String brandName;
    private String name;
    private String size;
    private String ingredients;
    private String servingSize;
    private String servingsPerContainer;
    private Integer calories;
    private Integer fatCalories;
    private Double fat;
    private Integer potassium;
    private Integer carbohydrate;
    private Integer sugars;
    private Integer protein;
    private String publisher;
    private List<Image> images;

    public Builder withErrorMessage(String errorMessage) {
      this.errorMessage = errorMessage;
      return this;
    }

    public Builder withGtin14(String gtin14) {
      this.gtin14 = gtin14;
      return this;
    }

    public Builder withBrandName(String brandName) {
      this.brandName = brandName;
      return this;
    }

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Builder withSize(String size) {
      this.size = size;
      return this;
    }

    public Builder withIngredients(String ingredients) {
      this.ingredients = ingredients;
      return this;
    }

    public Builder withServingSize(String servingSize) {
      this.servingSize = servingSize;
      return this;
    }

    public Builder withServingsPerContainer(String servingsPerContainer) {
      this.servingsPerContainer = servingsPerContainer;
      return this;
    }

    public Builder withCalories(Integer calories) {
      this.calories = calories;
      return this;
    }

    public Builder withFatCalories(Integer fatCalories) {
      this.fatCalories = fatCalories;
      return this;
    }

    public Builder withFat(Double fat) {
      this.fat = fat;
      return this;
    }

    public Builder withPotassium(Integer potassium) {
      this.potassium = potassium;
      return this;
    }

    public Builder withCarbohydrate(Integer carbohydrate) {
      this.carbohydrate = carbohydrate;
      return this;
    }

    public Builder withSugars(Integer sugars) {
      this.sugars = sugars;
      return this;
    }

    public Builder withProtein(Integer protein) {
      this.protein = protein;
      return this;
    }

    public Builder withPublisher(String publisher) {
      this.publisher = publisher;
      return this;
    }

    public Builder withImages(List<Image> images) {
      this.images = images;
      return this;
    }

    public Item build() {
      return new Item(errorMessage, gtin14, brandName, name, size, ingredients, servingSize,
          servingsPerContainer, calories, fatCalories, fat, potassium, carbohydrate, sugars,
          protein, publisher, images);
    }
  }
}
