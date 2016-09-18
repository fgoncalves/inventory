package com.fred.inventory.utils.binding.widgets;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import com.fred.inventory.utils.binding.Observable;
import java.util.Calendar;
import java.util.Date;

/**
 * A date set listener that can be observed
 * </p>
 * Created by fred on 18.09.16.
 */
public class ObservableOnSetDateSetListener extends Observable<Date>
    implements DatePickerDialog.OnDateSetListener {

  @Override public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    Calendar calendar = Calendar.getInstance();
    calendar.clear();
    if (value == null) {
      calendar.set(year, monthOfYear, dayOfMonth);
      set(calendar.getTime());
      return;
    }
    calendar.setTime(value);
    if (calendar.get(Calendar.YEAR) == year
        && calendar.get(Calendar.MONTH) == monthOfYear
        && calendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth) {
      return;
    }
    set(calendar.getTime());
  }

  public static ObservableOnSetDateSetListener create() {
    return new ObservableOnSetDateSetListener();
  }
}
