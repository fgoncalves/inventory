package com.fred.inventory.presentation.itemlist;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class ItemListPresenterImplTest {
  @Mock ItemListView view;

  private ItemListPresenterImpl presenter;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    presenter = new ItemListPresenterImpl(view);
  }

  @Test public void onAttachedToWindow_shouldRequestFocusOnToolbarEditTextIfProductListIsBeingCreated() {
    presenter.forId(null);

    presenter.onAttachedToWindow();

    verify(view).requestFocusOnToolbarEditText();
  }

  @Test public void onAttachedToWindow_shouldNotRequestFocusOnToolbarIfTheProductAlreadyExists() {
    presenter.forId("123");

    presenter.onAttachedToWindow();

    verify(view, never()).requestFocusOnToolbarEditText();
  }

  @Test public void onDoneButtonClicked_shouldTellViewToDismissItself() {
    presenter.onDoneButtonClicked();

    verify(view).dismiss();
  }
}