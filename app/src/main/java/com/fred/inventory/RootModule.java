package com.fred.inventory;

import android.content.Context;
import com.fred.inventory.data.ApiModule;
import com.fred.inventory.data.DBModule;
import com.fred.inventory.domain.modules.TranslatorModule;
import com.fred.inventory.domain.modules.UseCaseModule;
import com.fred.inventory.utils.rx.RxUtilsModule;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(library = true, includes = {
    ApiModule.class, DBModule.class, TranslatorModule.class, UseCaseModule.class,
    RxUtilsModule.class
}) public class RootModule {
  private final Context context;

  public RootModule(Context context) {
    this.context = context;
  }

  @Provides @Singleton public Context providesContext() {
    return context;
  }
}
