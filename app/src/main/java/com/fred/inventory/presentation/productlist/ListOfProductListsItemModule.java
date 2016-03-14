package com.fred.inventory.presentation.productlist;

import com.fred.inventory.RootModule;
import dagger.Module;
import dagger.Provides;

@Module(injects = ListOfProductListsItemViewImpl.class, addsTo = RootModule.class)
public class ListOfProductListsItemModule {
  private final ListOfProductListsItemView view;

  public ListOfProductListsItemModule(ListOfProductListsItemView view) {
    this.view = view;
  }

  @Provides public ListOfProductListsItemView providesListOfProductListsItemView() {
    return view;
  }

  @Provides public ListOfProductListsItemPresenter providesListOfProductListsItemPresenter(
      ListOfProductListsItemPresenterImpl presenter) {
    return presenter;
  }
}