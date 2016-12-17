package com.fred.inventory.domain.usecases;

import android.support.annotation.NonNull;
import com.fred.inventory.data.outpan.services.ProductWebService;
import com.fred.inventory.domain.models.Product;
import com.fred.inventory.domain.translators.Translator;
import javax.inject.Inject;
import rx.Observable;

public class GetProductInfoFromCodeUseCaseImpl implements GetProductInfoFromCodeUseCase {
  private final ProductWebService service;
  private final Translator<com.fred.inventory.data.outpan.models.Product, Product> translator;

  @Inject public GetProductInfoFromCodeUseCaseImpl(ProductWebService service,
      @com.fred.inventory.domain.modules.qualifiers.OutpanProductToProductTranslator
          Translator<com.fred.inventory.data.outpan.models.Product, Product> translator) {
    this.service = service;
    this.translator = translator;
  }

  @Override public Observable<Product> info(@NonNull String code) {
    return service.get(code).map(translator::translate);
  }
}
