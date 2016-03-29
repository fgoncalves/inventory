package com.fred.inventory.presentation.listofproducts.modules;

import com.fred.inventory.RootModule;
import com.fred.inventory.presentation.listofproducts.adapters.ListOfProductListsAdapter;
import com.fred.inventory.presentation.listofproducts.adapters.ListOfProductListsAdapterImpl;
import com.fred.inventory.presentation.listofproducts.presenters.ListOfProductListsPresenter;
import com.fred.inventory.presentation.listofproducts.presenters.ListOfProductListsPresenterImpl;
import com.fred.inventory.presentation.listofproducts.views.ListOfProductListsView;
import com.fred.inventory.presentation.listofproducts.views.ListOfProductListsViewImpl;
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

  @Provides @Singleton public ListOfProductListsAdapter providesListOfProductListsAdapter(
      ListOfProductListsAdapterImpl adapter) {
    return adapter;
  }
}
