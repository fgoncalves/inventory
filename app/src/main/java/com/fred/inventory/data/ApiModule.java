package com.fred.inventory.data;

import com.fred.inventory.BuildConfig;
import com.fred.inventory.data.outpan.calls.ProductCalls;
import com.fred.inventory.data.outpan.services.ProductWebService;
import com.fred.inventory.data.outpan.services.ProductWebServiceImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;

@Module(complete = false, library = true) public class ApiModule {
  @Provides @Singleton public RestAdapter providesOutpanRestAdapter() {
    return new RestAdapter.Builder().setClient(new OkClient())
        .setEndpoint("https://api.outpan.com/")
        .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
        .setLog(new AndroidLog("Outpan Restadapter"))
        .build();
  }

  @Provides @Singleton public ProductCalls providesProductCalls(RestAdapter restAdapter) {
    return restAdapter.create(ProductCalls.class);
  }

  @Provides @Singleton
  public ProductWebService providesProductWebService(ProductWebServiceImpl productService) {
    return productService;
  }
}
