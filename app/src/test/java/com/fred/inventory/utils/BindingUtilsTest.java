package com.fred.inventory.utils;

import android.databinding.ObservableField;
import java.util.Date;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BindingUtilsTest {
  @Test public void convertDateToString_shouldReturnEmptyStringIfObservableValueIsEmpty() {
    ObservableField<Date> observable = new ObservableField<>();

    String result = BindingUtils.convertDateToString(observable);

    assertThat(result).isEqualTo("");
  }

  @Test public void convertDateToString_shouldReturnFormattedWhenObservableCarriesAValue() {
    Date date = new Date();
    ObservableField<Date> observable = new ObservableField<>(date);

    String result = BindingUtils.convertDateToString(observable);

    assertThat(result).isEqualTo(BindingUtils.DATE_FORMAT.format(date));
  }
}
