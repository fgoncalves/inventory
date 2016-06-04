package com.fred.inventory.presentation.listofproducts.modules;

import com.fred.inventory.RootModule;
import com.fred.inventory.presentation.listofproducts.viewmodels.ListOfProductListsItemViewModel;
import com.fred.inventory.presentation.listofproducts.viewmodels.ListOfProductListsItemViewModelImpl;
import com.fred.inventory.presentation.listofproducts.views.ListOfProductListsItemViewImpl;
import dagger.Module;
import dagger.Provides;

@Module(injects = ListOfProductListsItemViewImpl.class, addsTo = RootModule.class)
public class ListOfProductListsItemModule {
  @Provides public ListOfProductListsItemViewModel providesListOfProductListsItemViewModel(
      ListOfProductListsItemViewModelImpl viewModel) {
    return viewModel;
  }
}
