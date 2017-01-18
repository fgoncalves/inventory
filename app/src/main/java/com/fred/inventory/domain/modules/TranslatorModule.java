package com.fred.inventory.domain.modules;

import com.fred.inventory.domain.models.GlobalSearchResult;
import com.fred.inventory.domain.models.Image;
import com.fred.inventory.domain.models.Product;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.modules.qualifiers.DBImageToImageTranslator;
import com.fred.inventory.domain.modules.qualifiers.DBProductListListToGlobalSearchResultListTranslator;
import com.fred.inventory.domain.modules.qualifiers.DBProductListToProductListTranslator;
import com.fred.inventory.domain.modules.qualifiers.DBProductToProductTranslator;
import com.fred.inventory.domain.modules.qualifiers.ImageToDBImageTranslator;
import com.fred.inventory.domain.modules.qualifiers.OutpanProductToProductTranslator;
import com.fred.inventory.domain.modules.qualifiers.ProductListToDBProductListTranslator;
import com.fred.inventory.domain.modules.qualifiers.ProductToDBProductTranslator;
import com.fred.inventory.domain.translators.Translator;
import dagger.Module;
import dagger.Provides;
import java.util.List;
import javax.inject.Singleton;

@Module(complete = false, library = true) public class TranslatorModule {
  @Provides @Singleton @DBImageToImageTranslator
  public Translator<com.fred.inventory.data.db.models.Image, Image> providesDBImageToImagetTranslator() {
    return new com.fred.inventory.domain.translators.DBImageToImageTranslator();
  }

  @Provides @Singleton @DBProductToProductTranslator
  public Translator<com.fred.inventory.data.db.models.Product, Product> providesDBProductToProductTranslator(
      com.fred.inventory.domain.translators.DBProductToProductTranslator translator) {
    return translator;
  }

  @Provides @Singleton @DBProductListToProductListTranslator
  public Translator<com.fred.inventory.data.db.models.ProductList, ProductList> providesDBProductToProductTranslator(
      com.fred.inventory.domain.translators.DBProductListToProductListTranslator translator) {
    return translator;
  }

  @Provides @Singleton @ImageToDBImageTranslator
  public Translator<Image, com.fred.inventory.data.db.models.Image> providesImageToDBImageTranslator() {
    return new com.fred.inventory.domain.translators.ImageToDBImageTranslator();
  }

  @Provides @Singleton @ProductToDBProductTranslator
  public Translator<Product, com.fred.inventory.data.db.models.Product> providesProductToDBProductTranslator(
      com.fred.inventory.domain.translators.ProductToDBProductTranslator translator) {
    return translator;
  }

  @Provides @Singleton @ProductListToDBProductListTranslator
  public Translator<ProductList, com.fred.inventory.data.db.models.ProductList> providesProductListToDBProductListTranslator(
      com.fred.inventory.domain.translators.ProductListToDBProductListTranslator translator) {
    return translator;
  }

  @Provides @Singleton @OutpanProductToProductTranslator
  public Translator<com.fred.inventory.data.outpan.models.Product, Product> providesOutpanProductToProductTranslator(
      com.fred.inventory.domain.translators.OutpanProductToProductTranslator translator) {
    return translator;
  }

  @Provides @Singleton @DBProductListListToGlobalSearchResultListTranslator
  public Translator<List<com.fred.inventory.data.db.models.ProductList>, List<GlobalSearchResult>> providesOutpanProductToProductTranslator(
      com.fred.inventory.domain.translators.DBProductListListToGlobalSearchResultListTranslator translator) {
    return translator;
  }
}
