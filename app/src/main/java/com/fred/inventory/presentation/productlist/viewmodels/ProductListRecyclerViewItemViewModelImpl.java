package com.fred.inventory.presentation.productlist.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;
import com.fred.inventory.data.firebase.models.SuppliesList;
import com.fred.inventory.data.firebase.models.SupplyItem;
import com.fred.inventory.domain.usecases.RemoveItemFromSuppliesListUseCase;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import javax.inject.Inject;
import timber.log.Timber;

public class ProductListRecyclerViewItemViewModelImpl
    implements ProductListRecyclerViewItemViewModel {
  private final ObservableField<String> productName = new ObservableField<>();
  private final ObservableField<String> productQuantityLabel = new ObservableField<>();
  private final ObservableInt quantity = new ObservableInt();
  private final ObservableInt progressBarVisibility = new ObservableInt();
  private final RemoveItemFromSuppliesListUseCase removeItemFromSuppliesListUseCase;
  private final SchedulerTransformer transformer;
  private final View.OnClickListener deleteButtonClickListener = new View.OnClickListener() {
    @Override public void onClick(View view) {
      removeItemFromSuppliesListUseCase.remove(suppliesList.uuid(), supplyItem)
          .compose(transformer.applySchedulers())
          .subscribe(value -> {
          }, throwable -> Timber.e(throwable, "Failed to delete item from supplyItem list"));
    }
  };
  private final View.OnClickListener itemClickListener = new View.OnClickListener() {
    @Override public void onClick(View view) {
      if (onItemClickListener != null) onItemClickListener.onClicked();
    }
  };
  private OnItemClickListener onItemClickListener;
  private SupplyItem supplyItem;
  private SuppliesList suppliesList;

  @Inject public ProductListRecyclerViewItemViewModelImpl(
      RemoveItemFromSuppliesListUseCase removeItemFromSuppliesListUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer transformer) {
    this.removeItemFromSuppliesListUseCase = removeItemFromSuppliesListUseCase;
    this.transformer = transformer;
  }

  @Override public void onBindViewHolder(SuppliesList suppliesList, SupplyItem supplyItem) {
    this.supplyItem = supplyItem;
    this.suppliesList = suppliesList;
    productName.set(supplyItem.name());
    productQuantityLabel.set(supplyItem.quantityLabel());
    quantity.set(supplyItem.quantity());
    if (supplyItem.unit()) {
      progressBarVisibility.set(View.GONE);
    } else {
      progressBarVisibility.set(View.VISIBLE);
    }
  }

  @Override public ObservableField<String> productName() {
    return productName;
  }

  @Override public ObservableField<String> productQuantityLabel() {
    return productQuantityLabel;
  }

  @Override public ObservableInt quantity() {
    return quantity;
  }

  @Override public ObservableInt progressBarVisibility() {
    return progressBarVisibility;
  }

  @Override public View.OnClickListener deleteButtonClickListener() {
    return deleteButtonClickListener;
  }

  @Override public View.OnClickListener itemClickListener() {
    return itemClickListener;
  }

  @Override public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
}
