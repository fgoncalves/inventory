package com.fred.inventory;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import com.fred.inventory.di.ApiModule;
import com.fred.inventory.di.FirebaseModule;
import com.fred.inventory.di.TranslatorsModule;
import com.fred.inventory.di.UseCaseModule;
import com.fred.inventory.presentation.navbar.NavBarHeaderViewModel;
import com.fred.inventory.presentation.navbar.NavBarHeaderViewModelImpl;
import com.fred.inventory.utils.path.PathManagerModule;
import com.fred.inventory.utils.rx.RxUtilsModule;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(library = true, includes = {
    ApiModule.class, UseCaseModule.class, RxUtilsModule.class, PathManagerModule.class,
    FirebaseModule.class, TranslatorsModule.class
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

  @Provides @Singleton
  public NavBarHeaderViewModel providesNavBarHeaderViewModel(NavBarHeaderViewModelImpl viewModel) {
    return viewModel;
  }
}
