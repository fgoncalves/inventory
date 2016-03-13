package com.fred.inventory;

import android.app.Application;
import dagger.ObjectGraph;

/**
 * Application class holding the dependency graph
 * <p/>
 * Created by fred on 13.03.16.
 */
public class SuppliesApplication extends Application {
  private static ObjectGraph objectGraph;

  @Override public void onCreate() {
    super.onCreate();
    objectGraph = ObjectGraph.create(new RootModule(this));
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
