package com.fred.inventory.presentation.listofproducts.presenters;

import com.fred.inventory.presentation.listofproducts.views.ListOfProductListsView;
import rx.Observable;

/**
 * Presenter for the list of products
 * <p/>
 * Created by fred on 13.03.16.
 */
public interface ListOfProductListsPresenter {
  void onAttachedToWindow();

  void onDetachedFromWindow();

  void onAddButtonClicked();

  Observable<ListOfProductListsView.ListOfProductListsEvent> interactions();
}
