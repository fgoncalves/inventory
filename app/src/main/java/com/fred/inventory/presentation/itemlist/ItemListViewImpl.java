package com.fred.inventory.presentation.itemlist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.fred.inventory.MainActivity;
import javax.inject.Inject;

public class ItemListViewImpl extends RelativeLayout implements ItemListView {
  @Inject ItemListPresenter presenter;

  public ItemListViewImpl(Context context) {
    super(context);
  }

  public ItemListViewImpl(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ItemListViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (isInEditMode()) return;
    MainActivity.scoped(new ItemListModule(this)).inject(this);
  }

  @Override public void requestFocusOnToolbarEditText() {

  }

  @Override public void dismiss() {

  }
}
