package com.fred.inventory.domain.models;

/**
 * This class represents the quantity for an item. An item can have several
 * quantities because it can come in different packages.
 */
public class Quantity {
  public static final String NO_UNIT = null;

  //for now an int should be enough
  private int quantity;
  /**
   * This represents the unit of the quantity.
   */
  private String quantityUnit;

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getQuantityUnit() {
    return quantityUnit;
  }

  public void setQuantityUnit(String quantityUnit) {
    this.quantityUnit = quantityUnit;
  }
}
