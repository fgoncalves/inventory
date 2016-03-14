package com.fred.inventory.utils.timber;

import timber.log.Timber;

/**
 * Timber tree that reports to the crash tools the highest prio crashes
 * <p/>
 * Created by fred on 14.03.16.
 */
public class CrashReportingTree extends Timber.Tree {
  @Override protected void log(int priority, String tag, String message, Throwable t) {
    // TODO: Report to crash tool
  }
}
