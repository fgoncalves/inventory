package com.fred.inventory;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.fred.inventory.utils.timber.CrashReportingTree;
import com.google.gson.Gson;
import io.fabric.sdk.android.Fabric;
import nl.littlerobots.cupboard.tools.gson.GsonListFieldConverterFactory;
import nl.qbusict.cupboard.CupboardBuilder;
import nl.qbusict.cupboard.CupboardFactory;
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
    setupCupboard();
  }

  private void setupCupboard() {
    CupboardFactory.setCupboard(new CupboardBuilder().
        registerFieldConverterFactory(new GsonListFieldConverterFactory(new Gson())).build());
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
