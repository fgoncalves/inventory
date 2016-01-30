package com.fred.inventory.data.datakick;

import com.fred.inventory.BuildConfig;
import com.fred.inventory.data.datakick.calls.Calls;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;

@Module(complete = false, library = true)
public class ApiModule {
  @Provides
  @Singleton
  public RestAdapter providesRestAdapter() {
    return new RestAdapter.Builder()
        .setClient(new OkClient())
        .setEndpoint("https://www.datakick.org/")
        .setLogLevel(BuildConfig.DEBUG? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
        .setLog(new AndroidLog("Datakick Restadapter"))
        .build();
  }

  @Provides
  @Singleton
  public Calls providesCalls(RestAdapter restAdapter) {
    return restAdapter.create(Calls.class);
  }
}
