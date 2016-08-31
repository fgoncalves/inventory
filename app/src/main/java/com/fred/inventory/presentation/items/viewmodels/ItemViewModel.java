package com.fred.inventory.presentation.items.viewmodels;

import com.fred.inventory.presentation.items.models.ItemScreenModel;
import com.fred.inventory.utils.binding.Observer;

/**
 * View model for an item, a.k.a. product.
 */
public interface ItemViewModel {
  void onAttachedToWindow();

  void onDetachedFromWindow();

  void forProductList(String productListId);

  void forProduct(String productId);

  void bindProductNameObserver(Observer<String> observer);

  void bindItemScreenModelObserver(Observer<ItemScreenModel> observer);

  void unbindProductNameObserver(Observer<String> observer);

  void unbindItemScreenModelObserver(Observer<ItemScreenModel> observer);
}
