package com.fred.inventory.presentation.items;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.databinding.ItemBinding;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.presentation.items.modules.ItemViewModule;
import com.fred.inventory.presentation.items.viewmodels.ItemViewModel;
import javax.inject.Inject;
import rx.Observable;

public class ItemScreen extends BaseScreen {
  private static final String PRODUCT_LIST_ID = "product.list.id";

  @Inject ItemViewModel viewModel;

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
    ItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.item, container, false);

    MainActivity.scoped(new ItemViewModule()).inject(this);
    binding.setViewModel(viewModel);

    return binding.getRoot();
  }

  @Override protected boolean handleBackPress() {
    return false;
  }

  @Override public Observable<ScreenEvent> screenEvents() {
    return Observable.empty();
  }
}
