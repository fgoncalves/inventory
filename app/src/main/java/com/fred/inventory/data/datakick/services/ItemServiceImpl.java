package com.fred.inventory.data.datakick.services;

import com.fred.inventory.data.datakick.calls.ItemCalls;
import com.fred.inventory.data.datakick.models.Item;
import javax.inject.Inject;
import rx.Observable;

import static com.fred.inventory.utils.Preconditions.checkNotNull;

public class ItemServiceImpl implements ItemService {
  private final ItemCalls calls;

  @Inject public ItemServiceImpl(ItemCalls calls) {
    this.calls = calls;
  }

  @Override public Observable<Item> get(String productId) {
    checkNotNull(productId, "Cannot get item details for a null product id");
    return calls.get(productId);
  }
}
