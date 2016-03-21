package com.fred.inventory.presentation.itemlist;

import com.fred.inventory.utils.StringUtils;
import javax.inject.Inject;

public class ItemListPresenterImpl implements ItemListPresenter {
  private final ItemListView view;

  private String productListId;

  @Inject public ItemListPresenterImpl(ItemListView view) {
    this.view = view;
  }

  @Override public void forId(String productListId) {
    this.productListId = productListId;
  }

  @Override public void onAttachedToWindow() {
    if (StringUtils.isBlank(productListId)) {
      view.requestFocusOnToolbarEditText();
      return;
    }
  }

  @Override public void onDoneButtonClicked() {
    view.dismiss();
  }
}
