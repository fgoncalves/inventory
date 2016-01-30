package com.fred.inventory;

import com.fred.inventory.data.datakick.ApiModule;
import dagger.Module;

@Module(library = true, includes = { ApiModule.class }) public class RootModule {
}
