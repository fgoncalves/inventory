package com.fred.inventory.presentation.listofproducts.viewmodels;

import android.content.Context;
import com.fred.inventory.R;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.utils.binding.Observable;
import com.fred.inventory.utils.binding.Observer;
import javax.inject.Inject;

public class ListOfProductListsItemViewModelImpl implements ListOfProductListsItemViewModel {
  private final Observable<String> productListNameObservable = Observable.create();
  private final Observable<String> infoTextObservable = Observable.create();
  private final Context context;
  private ProductList productList;

  @Inject public ListOfProductListsItemViewModelImpl(Context context) {
    this.context = context;
  }

  @Override public void bindProductListNameObserver(Observer<String> observer) {
    productListNameObservable.bind(observer);
  }

  @Override public void bindInfoTextObserver(Observer<String> observer) {
    infoTextObservable.bind(observer);
  }

  @Override public void attachModel(ProductList productList) {
    this.productList = productList;
  }

  @Override public void unbindProductListNameObserver(Observer<String> observer) {
    productListNameObservable.unbind(observer);
  }

  @Override public void unbindInfoTextObserver(Observer<String> observer) {
    infoTextObservable.unbind(observer);
  }

  @Override public void onAttachedToWindow() {
    productListNameObservable.set(productList.getName());
    infoTextObservable.set(
        context.getString(R.string.number_of_items, productList.getProducts().size()));
  }
}
