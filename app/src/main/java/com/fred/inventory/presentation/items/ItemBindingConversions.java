package com.fred.inventory.presentation.items;

import android.databinding.BindingConversion;
import android.databinding.ObservableField;
import java.text.DateFormat;
import java.util.Date;

/**
 * Holds methods for conversions of bindings
 * <p/>
 * Created by fred on 02.10.16.
 */
public class ItemBindingConversions {
  private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance();

  @BindingConversion public static String convertDateToString(ObservableField<Date> date) {
    if (date.get() == null) return "";
    return DATE_FORMAT.format(date.get());
  }
}
