package com.fred.inventory.presentation.productlist;

import com.fred.inventory.domain.models.ProductList;
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
  }

  @Test
  public void attachModel_shouldTellViewToDisplayProductListName() {
    presenter.attachModel(productList);

    verify(view).displayProductListName(productList.getName());
  }

  @Test
  public void attachModel_shouldTellViewToDisplayAnEmptyProductListNameIfOneIsNotPresent() {
    productList.setName(null);

    presenter.attachModel(productList);

    verify(view).displayProductListName("");
  }
}
