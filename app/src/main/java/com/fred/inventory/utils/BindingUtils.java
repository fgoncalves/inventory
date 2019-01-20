package com.fred.inventory.utils;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.net.Uri;
import android.widget.ImageView;
import com.fred.inventory.R;
import com.squareup.picasso.Picasso;
import java.text.DateFormat;
import java.util.Date;

/**
 * Holds methods for conversions of bindings
 * <p/>
 * Created by fred on 02.10.16.
 */
public class BindingUtils {
  static final DateFormat DATE_FORMAT = DateFormat.getDateInstance();

  @BindingConversion public static String convertDateToString(Date date) {
    if (date == null) return "";
    return DATE_FORMAT.format(date);
  }

  @BindingAdapter({ "imageUrl" }) public static void loadImage(ImageView view, Uri uri) {
    Picasso.with(view.getContext()).load(uri).error(R.drawable.ic_account).into(view);
  }
}
