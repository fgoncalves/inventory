package com.fred.inventory.presentation.items;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.R;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.presentation.items.views.ItemViewImpl;
import rx.Observable;

public class ItemScreen extends BaseScreen {
  public static ItemScreen newInstance() {
    return new ItemScreen();
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ItemViewImpl itemView = (ItemViewImpl) inflater.inflate(R.layout.item, null);

    return itemView;
  }

  @Override protected boolean handleBackPress() {
    return false;
  }

  @Override public Observable<ScreenEvent> screenEvents() {
    return Observable.empty();
  }
}
