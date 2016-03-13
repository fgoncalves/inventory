package com.fred.inventory.data;

import com.fred.inventory.data.db.RealmWrapper;
import com.fred.inventory.data.db.RealmWrapperImpl;
import com.fred.inventory.data.outpan.services.ProductService;
import com.fred.inventory.data.outpan.services.ProductServiceImpl;
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
