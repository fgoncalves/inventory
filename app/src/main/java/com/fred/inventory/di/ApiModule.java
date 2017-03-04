package com.fred.inventory.di;

import com.fred.inventory.BuildConfig;
import com.fred.inventory.data.firebase.services.SuppliesItemService;
import com.fred.inventory.data.firebase.services.SuppliesItemServiceImpl;
import com.fred.inventory.data.firebase.services.SupplyListsService;
import com.fred.inventory.data.firebase.services.SupplyListsServiceImpl;
import com.fred.inventory.data.firebase.services.UserService;
import com.fred.inventory.data.firebase.services.UserServiceImpl;
import com.fred.inventory.data.outpan.calls.ProductCalls;
import com.fred.inventory.data.outpan.services.SupplyItemWebService;
import com.fred.inventory.data.outpan.services.SupplyItemWebServiceImpl;
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
  public SupplyItemWebService providesProductWebService(SupplyItemWebServiceImpl productService) {
    return productService;
  }

  @Provides @Singleton public UserService providesUserService(UserServiceImpl userService) {
    return userService;
  }

  @Provides @Singleton
  public SupplyListsService providesSupplyListsService(SupplyListsServiceImpl supplyListsService) {
    return supplyListsService;
  }

  @Provides @Singleton
  public SuppliesItemService providesSuppliesItemService(SuppliesItemServiceImpl service) {
    return service;
  }
}
