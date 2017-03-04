package com.fred.inventory.di;

import com.fred.inventory.domain.usecases.CreateOrUpdateSuppliesListUserCase;
import com.fred.inventory.domain.usecases.CreateOrUpdateSuppliesListUserCaseImpl;
import com.fred.inventory.domain.usecases.CreateOrUpdateSupplyItemUseCase;
import com.fred.inventory.domain.usecases.CreateOrUpdateSupplyItemUseCaseImpl;
import com.fred.inventory.domain.usecases.DeleteSuppliesListUseCase;
import com.fred.inventory.domain.usecases.DeleteSuppliesListUseCaseImpl;
import com.fred.inventory.domain.usecases.GetItemsDatabaseReference;
import com.fred.inventory.domain.usecases.GetItemsDatabaseReferenceImpl;
import com.fred.inventory.domain.usecases.GetOrCreateUserUseCase;
import com.fred.inventory.domain.usecases.GetOrCreateUserUseCaseImpl;
import com.fred.inventory.domain.usecases.GetProductInfoFromCodeUseCase;
import com.fred.inventory.domain.usecases.GetProductInfoFromCodeUseCaseImpl;
import com.fred.inventory.domain.usecases.GetSuppliesListUseCase;
import com.fred.inventory.domain.usecases.GetSuppliesListUseCaseImpl;
import com.fred.inventory.domain.usecases.GetSupplyItemUseCase;
import com.fred.inventory.domain.usecases.GetSupplyItemUseCaseImpl;
import com.fred.inventory.domain.usecases.GetUserSuppliesListDatabaseReferenceUseCase;
import com.fred.inventory.domain.usecases.GetUserSuppliesListDatabaseReferenceUseCaseImpl;
import com.fred.inventory.domain.usecases.RemoveItemFromSuppliesListUseCase;
import com.fred.inventory.domain.usecases.RemoveItemFromSuppliesListUseCaseImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(complete = false, library = true) public class UseCaseModule {
  @Provides @Singleton public GetOrCreateUserUseCase providesGetOrCreateUserUseCase(
      GetOrCreateUserUseCaseImpl getOrCreateUserUseCase) {
    return getOrCreateUserUseCase;
  }

  @Provides @Singleton public CreateOrUpdateSuppliesListUserCase providesCreateSuppliesListUserCase(
      CreateOrUpdateSuppliesListUserCaseImpl userCase) {
    return userCase;
  }

  @Provides @Singleton
  public GetUserSuppliesListDatabaseReferenceUseCase providesGetUserSuppliesListDatabaseReferenceUseCase(
      GetUserSuppliesListDatabaseReferenceUseCaseImpl useCase) {
    return useCase;
  }

  @Provides @Singleton public DeleteSuppliesListUseCase providesDeleteSuppliesListUseCase(
      DeleteSuppliesListUseCaseImpl deleteSuppliesListUseCase) {
    return deleteSuppliesListUseCase;
  }

  @Provides @Singleton
  public GetSuppliesListUseCase providesGetSuppliesListUseCase(GetSuppliesListUseCaseImpl useCase) {
    return useCase;
  }

  @Provides @Singleton public GetItemsDatabaseReference providesGetItemsDatabaseReference(
      GetItemsDatabaseReferenceImpl useCase) {
    return useCase;
  }

  @Provides @Singleton
  public CreateOrUpdateSupplyItemUseCase providesCreateOrUpdateSupplyItemUseCase(
      CreateOrUpdateSupplyItemUseCaseImpl useCase) {
    return useCase;
  }

  @Provides @Singleton
  public GetSupplyItemUseCase providesGetSupplyItemUseCase(GetSupplyItemUseCaseImpl useCase) {
    return useCase;
  }

  @Provides @Singleton public RemoveItemFromSuppliesListUseCase providesRemoveItemFromSuppliesList(
      RemoveItemFromSuppliesListUseCaseImpl useCase) {
    return useCase;
  }

  @Provides @Singleton public GetProductInfoFromCodeUseCase getProductInfoFromCodeUseCase(
      GetProductInfoFromCodeUseCaseImpl useCase) {
    return useCase;
  }
}
