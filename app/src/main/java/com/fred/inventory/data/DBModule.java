package com.fred.inventory.data;

import com.fred.inventory.data.db.ProductService;
import com.fred.inventory.data.db.ProductServiceImpl;
import com.fred.inventory.data.db.RealmWrapper;
import com.fred.inventory.data.db.RealmWrapperImpl;
import dagger.Module;
import dagger.Provides;
import io.realm.RealmConfiguration;
import javax.inject.Singleton;

@Module(complete = false, library = true) public class DBModule {
  @Provides @Singleton public RealmConfiguration providesRealmConfiguration() {
    return new RealmConfiguration.Builder().name("supplies_products.realm")
        .schemaVersion(2)
        .build();
  }

  @Provides @Singleton public RealmWrapper providesRealmWrapper(RealmWrapperImpl realmWrapper) {
    return realmWrapper;
  }

  @Provides @Singleton
  public ProductService providesProductService(ProductServiceImpl productService) {
    return productService;
  }
}
