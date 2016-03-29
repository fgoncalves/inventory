package com.fred.inventory.presentation.listofproducts.modules;

import com.fred.inventory.RootModule;
import com.fred.inventory.presentation.listofproducts.presenters.ListOfProductListsItemPresenter;
import com.fred.inventory.presentation.listofproducts.presenters.ListOfProductListsItemPresenterImpl;
import com.fred.inventory.presentation.listofproducts.views.ListOfProductListsItemView;
import com.fred.inventory.presentation.listofproducts.views.ListOfProductListsItemViewImpl;
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
