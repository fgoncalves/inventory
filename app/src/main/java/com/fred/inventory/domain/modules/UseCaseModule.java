package com.fred.inventory.domain.modules;

import com.fred.inventory.domain.usecases.ListAllProductListsUseCase;
import com.fred.inventory.domain.usecases.ListAllProductListsUseCaseImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(complete = false, library = true) public class UseCaseModule {
  @Provides @Singleton public ListAllProductListsUseCase providesListAllProductListsUseCase(
      ListAllProductListsUseCaseImpl listAllProductListsUseCase) {
    return listAllProductListsUseCase;
  }
}
