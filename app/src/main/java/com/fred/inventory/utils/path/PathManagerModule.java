package com.fred.inventory.utils.path;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(complete = false, library = true) public class PathManagerModule {
  @Provides @Singleton public PathManager providesPathManager(PathManagerImpl pathManager) {
    return pathManager;
  }
}
