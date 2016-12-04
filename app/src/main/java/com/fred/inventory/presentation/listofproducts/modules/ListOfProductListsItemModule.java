package com.fred.inventory.presentation.listofproducts.modules;

import com.fred.inventory.RootModule;
import com.fred.inventory.presentation.listofproducts.adapters.ListOfProductListsAdapterImpl;
import com.fred.inventory.presentation.listofproducts.viewmodels.ListOfProductListsItemViewModel;
import com.fred.inventory.presentation.listofproducts.viewmodels.ListOfProductListsItemViewModelImpl;
import dagger.Module;
import dagger.Provides;

@Module(injects = ListOfProductListsAdapterImpl.ViewHolder.class, addsTo = RootModule.class)
public class ListOfProductListsItemModule {
  @Provides public ListOfProductListsItemViewModel providesListOfProductListsItemViewModel(
      ListOfProductListsItemViewModelImpl viewModel) {
    return viewModel;
  }
}
