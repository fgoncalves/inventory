package com.fred.inventory.di;

import com.fred.inventory.data.firebase.models.SupplyItem;
import com.fred.inventory.data.outpan.models.Product;
import com.fred.inventory.domain.modules.qualifiers.OutpanProductToProductTranslator;
import com.fred.inventory.domain.translators.Translator;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(library = true, complete = false) public class TranslatorsModule {
  @Provides @Singleton @OutpanProductToProductTranslator
  public Translator<Product, SupplyItem> providesOutpanProductToProductTranslator(
      com.fred.inventory.domain.translators.OutpanProductToProductTranslator translator) {
    return translator;
  }
}
