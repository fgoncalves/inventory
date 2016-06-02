package com.fred.inventory.presentation.widgets.clicktoedittext;

import com.fred.inventory.RootModule;
import dagger.Module;
import dagger.Provides;

@Module(injects = ClickToEditTextViewImpl.class, addsTo = RootModule.class)
public class ClickToEditTextModule {
  @Provides public ClickToEditViewModel providesViewModel(ClickToEditViewModelImpl viewModel) {
    return viewModel;
  }
}
