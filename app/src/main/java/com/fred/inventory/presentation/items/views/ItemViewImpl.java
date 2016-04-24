package com.fred.inventory.presentation.items.views;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import com.fred.inventory.MainActivity;
import com.fred.inventory.presentation.items.modules.ItemViewModule;
import com.fred.inventory.presentation.items.presenters.ItemPresenter;
import javax.inject.Inject;

public class ItemViewImpl extends CoordinatorLayout implements ItemView {
  @Inject ItemPresenter presenter;

  public ItemViewImpl(Context context) {
    super(context);
  }

  public ItemViewImpl(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ItemViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    if (isInEditMode()) return;
    MainActivity.scoped(new ItemViewModule(this)).inject(this);
  }

  @Override public void displayProductName(String name) {

  }
}
