package com.fred.inventory.presentation.items;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.R;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.presentation.items.views.ItemViewImpl;
import rx.Observable;

public class ItemScreen extends BaseScreen {
  private static final String PRODUCT_LIST_ID = "product.list.id";

  /**
   * Create an instance of an item screen which will display a new item for the given product
   * list.
   *
   * @param productListId The product list id to display the item for
   * @return The instance of the screen to use
   */
  public static ItemScreen newInstance(@NonNull String productListId) {
    ItemScreen itemScreen = new ItemScreen();

    Bundle args = new Bundle();
    args.putString(PRODUCT_LIST_ID, productListId);
    itemScreen.setArguments(args);

    return itemScreen;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ItemViewImpl itemView = (ItemViewImpl) inflater.inflate(R.layout.item, null);

    String productListId = getArguments().getString(PRODUCT_LIST_ID, "");
    itemView.displayForProductList(productListId);

    return itemView;
  }

  @Override protected boolean handleBackPress() {
    return false;
  }

  @Override public Observable<ScreenEvent> screenEvents() {
    return Observable.empty();
  }
}
