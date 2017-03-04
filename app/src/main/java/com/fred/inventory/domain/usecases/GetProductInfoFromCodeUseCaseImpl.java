package com.fred.inventory.domain.usecases;

import android.support.annotation.NonNull;
import com.fred.inventory.data.firebase.models.SupplyItem;
import com.fred.inventory.data.outpan.services.SupplyItemWebService;
import com.fred.inventory.domain.translators.Translator;
import javax.inject.Inject;
import rx.Observable;

public class GetProductInfoFromCodeUseCaseImpl implements GetProductInfoFromCodeUseCase {
  private final SupplyItemWebService service;
  private final Translator<com.fred.inventory.data.outpan.models.Product, SupplyItem> translator;

  @Inject public GetProductInfoFromCodeUseCaseImpl(SupplyItemWebService service,
      @com.fred.inventory.domain.modules.qualifiers.OutpanProductToProductTranslator
          Translator<com.fred.inventory.data.outpan.models.Product, SupplyItem> translator) {
    this.service = service;
    this.translator = translator;
  }

  @Override public Observable<SupplyItem> info(@NonNull String code) {
    return service.get(code).map(translator::translate);
  }
}
