package com.fred.inventory.presentation.productlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.R;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.presentation.productlist.views.ProductListView;
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

    events = listOfProductListsView.interactions()
        .map(new Func1<ProductListView.ViewInteractionType, ScreenEvent>() {
          @Override
          public ScreenEvent call(ProductListView.ViewInteractionType viewInteractionType) {
            switch (viewInteractionType) {
              case DISMISS:
                return ScreenEvent.REMOVE;
            }
            return ScreenEvent.NOOP;
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
