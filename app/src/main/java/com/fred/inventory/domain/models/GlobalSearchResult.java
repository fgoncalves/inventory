package com.fred.inventory.domain.models;

import android.support.annotation.NonNull;
import com.fred.inventory.data.firebase.models.SuppliesList;
import com.fred.inventory.data.firebase.models.SupplyItem;
import com.google.auto.value.AutoValue;

/**
 * Represents an domain model from the global search
 */
@AutoValue public abstract class GlobalSearchResult {
  public abstract SuppliesList suppliesList();

  public abstract SupplyItem supplyItem();

  public static GlobalSearchResult create(@NonNull final SuppliesList suppliesList,
      @NonNull final SupplyItem supplyItem) {
    return new AutoValue_GlobalSearchResult(suppliesList, supplyItem);
  }
}
