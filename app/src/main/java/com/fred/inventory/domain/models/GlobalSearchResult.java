package com.fred.inventory.domain.models;

/**
 * Represents an domain model from the global search
 */

public class GlobalSearchResult {
  private Long productListId;
  private String productListName;
  private Product product;

  public String getProductListName() {
    return productListName;
  }

  public void setProductListName(String productListName) {
    this.productListName = productListName;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Long getProductListId() {
    return productListId;
  }

  public void setProductListId(Long productListId) {
    this.productListId = productListId;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GlobalSearchResult)) return false;

    GlobalSearchResult that = (GlobalSearchResult) o;

    if (productListName != null ? !productListName.equals(that.productListName)
        : that.productListName != null) {
      return false;
    }
    return product != null ? product.equals(that.product) : that.product == null;
  }

  @Override public int hashCode() {
    int result = productListName != null ? productListName.hashCode() : 0;
    result = 31 * result + (product != null ? product.hashCode() : 0);
    return result;
  }
}
