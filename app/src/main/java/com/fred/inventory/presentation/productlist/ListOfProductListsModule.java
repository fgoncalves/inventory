package com.fred.inventory.presentation.productlist;

import com.fred.inventory.RootModule;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(injects = ListOfProductListsViewImpl.class, addsTo = RootModule.class)
public class ListOfProductListsModule {
  private final ListOfProductListsView view;

  public ListOfProductListsModule(ListOfProductListsView view) {
    this.view = view;
  }

  @Provides @Singleton public ListOfProductListsView providesListOfProductListsView() {
    return view;
  }

  @Provides @Singleton public ListOfProductListsPresenter providesListOfProductListsPresenter(
      ListOfProductListsPresenterImpl presenter) {
    return presenter;
  }
}
