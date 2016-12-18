package com.fred.inventory.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.fred.inventory.data.db.models.Image;
import com.fred.inventory.data.db.models.Product;
import com.fred.inventory.data.db.models.ProductList;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class SuppliewsSqliteOpenHelper extends SQLiteOpenHelper {
  private final static int DB_VERSION = 1;
  private final static String DB_NAME = "supplies.sqlite3";

  static {
    cupboard().register(Image.class);
    cupboard().register(Product.class);
    cupboard().register(ProductList.class);
  }

  public SuppliewsSqliteOpenHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    cupboard().withDatabase(db).createTables();
  }

  @Override public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    cupboard().withDatabase(db).upgradeTables();
  }
}
