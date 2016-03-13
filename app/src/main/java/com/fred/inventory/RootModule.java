package com.fred.inventory;

import android.content.Context;
import com.fred.inventory.data.ApiModule;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(library = true, includes = { ApiModule.class }) public class RootModule {
  private final Context context;

  public RootModule(Context context) {
    this.context = context;
  }

  @Provides @Singleton public Context providesContext() {
    return context;
  }
}
