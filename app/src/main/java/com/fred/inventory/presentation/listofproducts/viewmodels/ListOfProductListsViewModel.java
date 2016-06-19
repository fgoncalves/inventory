package com.fred.inventory.presentation.listofproducts.viewmodels;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.fred.inventory.presentation.listofproducts.views.ListOfProductListsView;
import com.fred.inventory.utils.binding.Observer;
import rx.Observable;

/**
 * View model for the list of product lists
 */
public interface ListOfProductListsViewModel {
  void bindEmptyViewVisibilityObserver(Observer<Integer> observer);

  void onAttachedToWindow();

  void onDetachedFromWindow();

  void unbindEmptyViewVisibilityObserver(Observer<Integer> observer);

  void bindShowErrorMessageObserver(Observer<Void> observer);

  void unbindShowErrorMessageObserver(Observer<Void> observer);

  RecyclerView.Adapter adapter();

  Observable<ListOfProductListsView.ListOfProductListsEvent> interactions();

  View.OnClickListener addButtonClickListener();
}
