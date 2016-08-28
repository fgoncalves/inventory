package com.fred.inventory.presentation.productlist.models;

import android.support.annotation.Nullable;
import android.view.View;
import com.fred.inventory.domain.models.ProductList;
import org.immutables.value.Value;

/**
 * This is the model that contains the state of the product list
 * screen.
 * <p/>
 * By default the screen is not dismissed and has no errors. The empty view is not
 * shown and the keyboard is also not suppose to be shown.
 * <p/>
 * Created by fred on 28.08.16.
 */
@Value.Immutable public abstract class ProductListScreenState {
  @Value.Default public boolean dismissed() {
    return false;
  }

  @Value.Default public boolean showKeyboard() {
    return false;
  }

  @Value.Default public int emptyViewVisibility() {
    return View.GONE;
  }

  @Value.Default @Nullable public Error error() {
    return null;
  }

  public abstract ProductList productList();
}
