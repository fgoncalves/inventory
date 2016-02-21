package com.fred.inventory.data;

import com.fred.inventory.BuildConfig;
import com.fred.inventory.data.datakick.calls.ItemCalls;
import com.fred.inventory.data.datakick.services.ItemService;
import com.fred.inventory.data.datakick.services.ItemServiceImpl;
import com.fred.inventory.data.outpan.calls.ProductCalls;
import com.fred.inventory.data.outpan.services.ProductService;
import com.fred.inventory.data.outpan.services.ProductServiceImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;

@Module(complete = false, library = true) public class ApiModule {
  @Named("DATA_KICK_ADAPTER") @Provides @Singleton
  public RestAdapter providesDataKickRestAdapter() {
    return new RestAdapter.Builder().setClient(new OkClient())
        .setEndpoint("https://www.datakick.org/")
        .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
        .setLog(new AndroidLog("Datakick Restadapter"))
        .build();
  }

  @Named("OUTPAN_ADAPTER") @Provides @Singleton public RestAdapter providesOutpanRestAdapter() {
    return new RestAdapter.Builder().setClient(new OkClient())
        .setEndpoint("https://api.outpan.com/")
        .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
        .setLog(new AndroidLog("Outpan Restadapter"))
        .build();
  }

  @Provides @Singleton
  public ItemCalls providesItemCalls(@Named("DATA_KICK_ADAPTER") RestAdapter restAdapter) {
    return restAdapter.create(ItemCalls.class);
  }

  @Provides @Singleton public ItemService providesItemService(ItemServiceImpl service) {
    return service;
  }

  @Provides @Singleton
  public ProductCalls providesProductCalls(@Named("OUTPAN_ADAPTER") RestAdapter restAdapter) {
    return restAdapter.create(ProductCalls.class);
  }

  @Provides @Singleton
  public ProductService providesProductService(ProductServiceImpl productService) {
    return productService;
  }
}
