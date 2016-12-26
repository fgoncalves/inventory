package com.fred.inventory.presentation.widgets.clicktoedittext;

import com.fred.inventory.RootModule;
import dagger.Module;
import dagger.Provides;

@Module(injects = OldClickToEditTextViewImpl.class, addsTo = RootModule.class)
public class ClickToEditTextModule {
  @Provides public OldClickToEditViewModel providesViewModel(OldClickToEditViewModelImpl viewModel) {
    return viewModel;
  }
}
