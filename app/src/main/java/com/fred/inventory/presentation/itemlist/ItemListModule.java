package com.fred.inventory.presentation.itemlist;

import com.fred.inventory.RootModule;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(injects = ItemListViewImpl.class, addsTo = RootModule.class) public class ItemListModule {
  private final ItemListView view;

  public ItemListModule(ItemListView view) {
    this.view = view;
  }

  @Provides @Singleton public ItemListView providesItemListView() {
    return view;
  }

  @Provides @Singleton
  public ItemListPresenter providesItemListPresenter(ItemListPresenterImpl presenter) {
    return presenter;
  }
}
