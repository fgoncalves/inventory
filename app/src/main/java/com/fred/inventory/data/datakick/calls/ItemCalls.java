package com.fred.inventory.data.datakick.calls;

import com.fred.inventory.data.datakick.models.Item;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface ItemCalls {
  @GET("/api/items/{itemID}") Observable<Item> get(@Path("itemID") String itemId);
}
