package com.fred.inventory.presentation.items.models;

import android.support.annotation.Nullable;
import com.fred.inventory.domain.models.Product;
import org.immutables.value.Value;

/**
 * Model for the item screen
 */
@Value.Immutable public abstract class ItemScreenModel {
  @Nullable public abstract Product product();

  @Value.Default @Nullable public Error error() {
    return null;
  }
}
