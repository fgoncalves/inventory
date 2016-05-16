package com.fred.inventory.presentation.productlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.R;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.presentation.base.ViewInteraction;
import com.fred.inventory.presentation.productlist.views.ProductListViewImpl;
import rx.Observable;
import rx.functions.Func1;

public class ProductListScreen extends BaseScreen {
  private Observable<ScreenEvent> events;

  public static ProductListScreen newInstance() {
    return new ProductListScreen();
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ProductListViewImpl listOfProductListsView =
        (ProductListViewImpl) inflater.inflate(R.layout.product_list, null);

    events = listOfProductListsView.interactions().map(new Func1<ViewInteraction, ScreenEvent>() {
      @Override public ScreenEvent call(ViewInteraction viewInteraction) {
        switch (viewInteraction.getType()) {
          case ADD_PRODUCT_BUTTON_CLICKED:
            ScreenEvent event = new ScreenEvent(ScreenEvent.Type.ADD_PRODUCT_SCREEN);
            event.getMetadata()
                .putString(ScreenEvent.PRODUCT_LIST_ID_METADATA_KEY, viewInteraction.getMetadata()
                    .getString(ViewInteraction.PRODUCT_LIST_METADATA_KEY, ""));
            return event;
          case DISMISS:
            return new ScreenEvent(ScreenEvent.Type.REMOVE_PRODUCT_LIST_SCREEN);
        }
        return new ScreenEvent(ScreenEvent.Type.NOOP);
      }
    });

    return listOfProductListsView;
  }

  @Override protected boolean handleBackPress() {
    return false;
  }

  @Override public Observable<ScreenEvent> screenEvents() {
    return events;
  }
}
