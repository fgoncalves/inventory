package com.fred.inventory;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import com.fred.inventory.data.ApiModule;
import com.fred.inventory.data.DBModule;
import com.fred.inventory.domain.modules.TranslatorModule;
import com.fred.inventory.domain.modules.UseCaseModule;
import com.fred.inventory.utils.path.PathManagerModule;
import com.fred.inventory.utils.rx.RxUtilsModule;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(library = true, includes = {
    ApiModule.class, DBModule.class, TranslatorModule.class, UseCaseModule.class,
    RxUtilsModule.class, PathManagerModule.class
}) public class RootModule {
  private final AppCompatActivity appCompatActivity;

  public RootModule(AppCompatActivity appCompatActivity) {
    this.appCompatActivity = appCompatActivity;
  }

  @Provides @Singleton public Context providesContext() {
    return appCompatActivity;
  }

  @Provides @Singleton public FragmentManager providesFragmentManager() {
    return appCompatActivity.getFragmentManager();
  }
}
