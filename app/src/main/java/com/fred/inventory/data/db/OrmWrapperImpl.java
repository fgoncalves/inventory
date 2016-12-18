package com.fred.inventory.data.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.fred.inventory.data.db.models.Entity;
import java.util.List;
import javax.inject.Inject;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class OrmWrapperImpl implements OrmWrapper {
  private final SQLiteOpenHelper sqLiteOpenHelper;

  @Inject public OrmWrapperImpl(SQLiteOpenHelper sqLiteOpenHelper) {
    this.sqLiteOpenHelper = sqLiteOpenHelper;
  }

  @Override public <T extends Entity> List<T> all(Class<T> clazz) {
    try (SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase()) {
      return cupboard().withDatabase(db).query(clazz).list();
    }
  }

  @Override public <T extends Entity> T get(Class<T> clazz, Long id) {
    try (SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase()) {
      return cupboard().withDatabase(db).get(clazz, id);
    }
  }

  @Override public <T extends Entity> T store(T object) {
    try (SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase()) {
      long id = cupboard().withDatabase(db).put(object);
      object.setId(id);
      return cupboard().withDatabase(db).get(object);
    }
  }

  @Override public <T extends Entity> void delete(Class<T> clazz, Long id) {
    try (SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase()) {
      cupboard().withDatabase(db).delete(clazz, id);
    }
  }
}
