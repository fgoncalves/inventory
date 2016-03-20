package com.fred.inventory.presentation.widgets.clicktoedittext;

import com.fred.inventory.RootModule;
import dagger.Module;
import dagger.Provides;

@Module(injects = ClickToEditTextViewImpl.class, addsTo = RootModule.class)
public class ClickToEditTextModule {
  private final ClickToEditTextView view;

  public ClickToEditTextModule(ClickToEditTextView view) {
    this.view = view;
  }

  @Provides public ClickToEditTextView providesClickToEditTextView() {
    return view;
  }

  @Provides public ClickToEditTextPresenter providesClickToEditTextPresenter(
      ClickToEditTextPresenterImpl presenter) {
    return presenter;
  }
}
