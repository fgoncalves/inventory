package com.fred.inventory.data.datakick.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Error {
  @SerializedName("message") @Expose private final String message;

  protected Error(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public static class Builder {
    private String message;

    public Builder withMessage(String message) {
      this.message = message;
      return this;
    }

    public Error build() {
      return new Error(message);
    }
  }
}
