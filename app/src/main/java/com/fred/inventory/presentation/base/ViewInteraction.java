package com.fred.inventory.presentation.base;

import android.os.Bundle;

/**
 * View interaction class which encapsulates all the needed fields to pass interaction data around
 * the app
 * <p/>
 * Created by fred on 16.05.16.
 */
public class ViewInteraction {
  public static final String PRODUCT_LIST_METADATA_KEY = "product.list.id";

  public enum ViewInteractionType {
    ADD_PRODUCT_BUTTON_CLICKED, DISMISS
  }

  private final ViewInteractionType type;
  private final Bundle metadata = new Bundle();

  public ViewInteraction(ViewInteractionType type) {
    this.type = type;
  }

  public ViewInteractionType getType() {
    return type;
  }

  public Bundle getMetadata() {
    return metadata;
  }
}
