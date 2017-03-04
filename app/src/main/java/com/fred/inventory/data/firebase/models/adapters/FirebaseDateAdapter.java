package com.fred.inventory.data.firebase.models.adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import me.mattlogan.auto.value.firebase.adapter.TypeAdapter;

/**
 * <p/>
 * Created by fred on 19.02.17.
 */

public class FirebaseDateAdapter implements TypeAdapter<Date, String> {
  private final ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal =
      new ThreadLocal<SimpleDateFormat>() {
        @Override public SimpleDateFormat get() {
          return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
        }
      };

  @Override public Date fromFirebaseValue(String date) {
    try {
      return getSimpleDateFormat().parse(date);
    } catch (ParseException e) {
      // Can't throw the original exception because of the method inheritance
      throw new RuntimeException("Failed to parse date", e);
    }
  }

  @Override public String toFirebaseValue(Date date) {
    return getSimpleDateFormat().format(date);
  }

  private SimpleDateFormat getSimpleDateFormat() {
    return simpleDateFormatThreadLocal.get();
  }
}
