package com.fred.inventory.domain.transformers;

import com.fred.inventory.domain.models.Product;
import com.fred.inventory.domain.models.ProductList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class ProductListToDBProductListTranslatorTest {
  @Mock ProductToDBProductTranslator productToDBProductTranslator;

  private ProductListToDBProductListTranslator productListToDBProductListTranslator;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    productListToDBProductListTranslator =
        new ProductListToDBProductListTranslator(productToDBProductTranslator);
  }

  @Test public void translate_shouldCreateADBModelWithoutAnyProductsButWithName() {
    ProductList productList = new ProductList();
    String expectedName = "product list";
    productList.setName(expectedName);

    com.fred.inventory.data.db.models.ProductList result =
        productListToDBProductListTranslator.translate(productList);

    assertThat(result.getName()).isEqualTo(expectedName);
    assertThat(result.getProducts()).isEmpty();
  }

  @Test public void translate_shouldCreateADBModelWithNameAndProducts() {
    when(productToDBProductTranslator.translate(any(Product.class))).thenAnswer(
        new Answer<com.fred.inventory.data.db.models.Product>() {
          @Override
          public com.fred.inventory.data.db.models.Product answer(InvocationOnMock invocationOnMock)
              throws Throwable {
            return new com.fred.inventory.data.db.models.Product();
          }
        });

    ProductList productList = new ProductList();
    String expectedName = "product list";
    productList.setName(expectedName);
    productList.getProducts().add(new Product());

    com.fred.inventory.data.db.models.ProductList result =
        productListToDBProductListTranslator.translate(productList);

    assertThat(result.getName()).isEqualTo(expectedName);
    assertThat(result.getProducts()).isNotEmpty();
  }
}