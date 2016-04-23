package com.fred.inventory.presentation.listofproducts;

import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.presentation.listofproducts.presenters.ListOfProductListsItemPresenterImpl;
import com.fred.inventory.presentation.listofproducts.views.ListOfProductListsItemView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class ListOfProductListsItemPresenterImplTest {
  @Mock ListOfProductListsItemView view;

  private ListOfProductListsItemPresenterImpl presenter;
  private ProductList productList;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    productList = new ProductList();
    productList.setName("Some Name");

    presenter = new ListOfProductListsItemPresenterImpl(view);
    presenter.attachModel(productList);
  }

  @Test
  public void onAttachedToWindow_shouldTellViewToDisplayProductListName() {
    presenter.onAttachedToWindow();

    verify(view).displayProductListName(productList.getName());
  }

  @Test
  public void onAttachedToWindow_shouldTellViewToNumberOfProducts() {
    presenter.onAttachedToWindow();

    verify(view).displayNumberOfProducts(productList.getProducts().size());
  }

  @Test
  public void onAttachedToWindow_shouldTellViewToDisplayAnEmptyProductListNameIfOneIsNotPresent() {
    productList.setName(null);
    presenter.attachModel(productList);

    presenter.onAttachedToWindow();

    verify(view).displayProductListName("");
  }
}
