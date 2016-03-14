package com.fred.inventory;

import android.app.Application;
import com.fred.inventory.utils.timber.CrashReportingTree;
import dagger.ObjectGraph;
import timber.log.Timber;

/**
 * Application class holding the dependency graph
 * <p/>
 * Created by fred on 13.03.16.
 */
public class SuppliesApplication extends Application {
  private static ObjectGraph objectGraph;

  @Override public void onCreate() {
    super.onCreate();
    setupTimber();

    objectGraph = ObjectGraph.create(new RootModule(this));
  }

  /**
   * Plant timber in order to have a debug and production tree
   */
  private void setupTimber() {
    Timber.plant(BuildConfig.DEBUG? new Timber.DebugTree() : new CrashReportingTree());
  }

  /**
   * Get the dependency graph scoped with the provided modules
   *
   * @param modules The modules to add to the graph already created
   * @return The scoped graph
   */
  public static ObjectGraph scoped(Object... modules) {
    return objectGraph.plus(modules);
  }
}
