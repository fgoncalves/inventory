package com.fred.inventory.presentation.items.modules;

import com.fred.inventory.RootModule;
import com.fred.inventory.presentation.items.presenters.ItemPresenter;
import com.fred.inventory.presentation.items.presenters.ItemPresenterImpl;
import com.fred.inventory.presentation.items.views.ItemView;
import com.fred.inventory.presentation.items.views.ItemViewImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(injects = { ItemViewImpl.class }, addsTo = RootModule.class) public class ItemViewModule {
  private final ItemView view;

  public ItemViewModule(ItemView view) {
    this.view = view;
  }

  @Provides @Singleton public ItemView providesView() {
    return view;
  }

  @Provides @Singleton public ItemPresenter providesItemPresenter(ItemPresenterImpl presenter) {
    return presenter;
  }
}
