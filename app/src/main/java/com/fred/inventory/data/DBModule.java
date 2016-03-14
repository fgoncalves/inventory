package com.fred.inventory.data;

import com.fred.inventory.data.db.ProductService;
import com.fred.inventory.data.db.ProductServiceImpl;
import com.fred.inventory.data.db.RealmWrapper;
import com.fred.inventory.data.db.RealmWrapperImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(complete = false, library = true) public class DBModule {
  @Provides @Singleton public RealmWrapper providesRealmWrapper(RealmWrapperImpl realmWrapper) {
    return realmWrapper;
  }

  @Provides @Singleton
  public ProductService providesProductService(ProductServiceImpl productService) {
    return productService;
  }
}
