package com.fred.inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import com.fred.inventory.data.db.OrmWrapper;
import com.fred.inventory.data.db.OrmWrapperImpl;
import com.fred.inventory.data.db.ProductService;
import com.fred.inventory.data.db.ProductServiceImpl;
import com.fred.inventory.data.db.SuppliewsSqliteOpenHelper;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(complete = false, library = true) public class DBModule {
  @Provides @Singleton public SQLiteOpenHelper providesSuppliesSqliteOpenHelper(Context context) {
    return new SuppliewsSqliteOpenHelper(context);
  }

  @Provides @Singleton public OrmWrapper providesRealmWrapper(OrmWrapperImpl realmWrapper) {
    return realmWrapper;
  }

  @Provides @Singleton
  public ProductService providesProductService(ProductServiceImpl productService) {
    return productService;
  }
}
