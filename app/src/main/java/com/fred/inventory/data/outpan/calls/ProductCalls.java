package com.fred.inventory.data.outpan.calls;

import com.fred.inventory.data.outpan.models.Product;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface ProductCalls {
  // TODO: Refactor after we decide to go with this api or not
  @GET("/v2/products/{productId}") Observable<Product> get(@Path("productId") String productId,
      @Query("apikey") String apiKey);
}
