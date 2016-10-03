package com.fred.inventory;

import android.app.Application;
import com.fred.inventory.utils.timber.CrashReportingTree;
import io.realm.Realm;
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
    Realm.init(this);
  }

  /**
   * Plant timber in order to have a debug and production tree
   */
  private void setupTimber() {
    Timber.plant(BuildConfig.DEBUG ? new Timber.DebugTree() : new CrashReportingTree());
  }
}
