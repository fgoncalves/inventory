package com.fred.inventory;

import android.app.Application;
import com.fred.inventory.utils.timber.CrashReportingTree;
import com.google.gson.Gson;
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
    setupTimber();
    CupboardFactory.setCupboard(new CupboardBuilder().
        registerFieldConverterFactory(new GsonListFieldConverterFactory(new Gson())).build());
  }

  /**
   * Plant timber in order to have a debug and production tree
   */
  private void setupTimber() {
    Timber.plant(BuildConfig.DEBUG ? new Timber.DebugTree() : new CrashReportingTree());
  }
}
