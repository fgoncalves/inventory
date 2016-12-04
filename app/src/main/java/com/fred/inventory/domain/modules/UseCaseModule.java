package com.fred.inventory.domain.modules;

import com.fred.inventory.domain.usecases.DeleteProductListUseCase;
import com.fred.inventory.domain.usecases.DeleteProductListUseCaseImpl;
import com.fred.inventory.domain.usecases.DeleteProductUseCase;
import com.fred.inventory.domain.usecases.DeleteProductUseCaseImpl;
import com.fred.inventory.domain.usecases.GetProductListUseCase;
import com.fred.inventory.domain.usecases.GetProductListUseCaseImpl;
import com.fred.inventory.domain.usecases.ListAllProductListsUseCase;
import com.fred.inventory.domain.usecases.ListAllProductListsUseCaseImpl;
import com.fred.inventory.domain.usecases.SaveProductListInLocalStorageUseCase;
import com.fred.inventory.domain.usecases.SaveProductListInLocalStorageUseCaseImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(complete = false, library = true) public class UseCaseModule {
  @Provides @Singleton public ListAllProductListsUseCase providesListAllProductListsUseCase(
      ListAllProductListsUseCaseImpl listAllProductListsUseCase) {
    return listAllProductListsUseCase;
  }

  @Provides @Singleton public GetProductListUseCase providesGetProductListUseCase(
      GetProductListUseCaseImpl getProductListUseCase) {
    return getProductListUseCase;
  }

  @Provides @Singleton public SaveProductListInLocalStorageUseCase providesGetProductListUseCase(
      SaveProductListInLocalStorageUseCaseImpl saveProductListInLocalStorageUseCase) {
    return saveProductListInLocalStorageUseCase;
  }

  @Provides @Singleton
  public DeleteProductUseCase providesDeleteProductUseCase(DeleteProductUseCaseImpl useCase) {
    return useCase;
  }

  @Provides @Singleton public DeleteProductListUseCase providesDeleteProductListUseCase(
      DeleteProductListUseCaseImpl useCase) {
    return useCase;
  }
}
