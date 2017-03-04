package com.fred.inventory;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.fred.inventory.utils.timber.CrashReportingTree;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Application class holding the dependency graph
 * <p/>
 * Created by fred on 13.03.16.
 */
public class SuppliesApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();

    setupFabric();
    setupTimber();
  }

  private void setupFabric() {
    Crashlytics crashlyticsKit = new Crashlytics.Builder().core(
        new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()).build();
    Fabric.with(this, crashlyticsKit);
  }

  /**
   * Plant timber in order to have a debug and production tree
   */
  private void setupTimber() {
    Timber.plant(BuildConfig.DEBUG ? new Timber.DebugTree() : new CrashReportingTree());
  }
}
