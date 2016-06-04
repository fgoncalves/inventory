package com.fred.inventory.presentation.listofproducts.viewmodels;

import android.content.Context;
import com.fred.inventory.R;
import com.fred.inventory.domain.models.Product;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.utils.binding.Observer;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListOfProductListsItemViewModelImplTest {
  @Mock Observer<String> productListNameObserver;
  @Mock Observer<String> infoTextObserver;
  @Mock Context context;

  private ListOfProductListsItemViewModelImpl viewModel;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    viewModel = new ListOfProductListsItemViewModelImpl(context);

    viewModel.bindProductListNameObserver(productListNameObserver);
    viewModel.bindInfoTextObserver(infoTextObserver);
  }

  @Test public void bindProductList_shouldUpdateObserversWithCorrectModelData() {
    ProductList productList = emptyProductList();
    when(context.getString(R.string.number_of_items, productList.getProducts().size())).thenReturn(
        productList.getProducts().size() + " items");

    viewModel.bindProductList(productList);

    verify(productListNameObserver).update(productList.getName());
    verify(infoTextObserver).update("0 items");
  }

  @Test public void unbindProductListNameObserver_shouldNotUpdateTheObserver() {
    ProductList productList = emptyProductList();
    when(context.getString(R.string.number_of_items, productList.getProducts().size())).thenReturn(
        productList.getProducts().size() + " items");

    viewModel.unbindProductListNameObserver(productListNameObserver);
    viewModel.bindProductList(productList);

    verify(productListNameObserver, never()).update(productList.getName());
  }

  /**
   * Create an empty product list
   *
   * @return An empty product list
   */
  private ProductList emptyProductList() {
    ProductList productList = new ProductList();
    String expectedName = "This is my list name";
    String infoText = "0 items";
    productList.setName(expectedName);
    productList.setProducts(new ArrayList<Product>());
    return productList;
  }
}