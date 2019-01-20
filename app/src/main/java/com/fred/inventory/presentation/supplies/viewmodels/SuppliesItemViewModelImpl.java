package com.fred.inventory.presentation.supplies.viewmodels;

import android.content.Context;
import android.databinding.ObservableField;
import android.support.v7.app.AlertDialog;
import android.view.View;
import com.fred.inventory.R;
import com.fred.inventory.data.firebase.models.SuppliesList;
import com.fred.inventory.domain.usecases.DeleteSuppliesListUseCase;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import javax.inject.Inject;
import timber.log.Timber;

public class SuppliesItemViewModelImpl implements SuppliesItemViewModel {
  private final ObservableField<String> productListNameObservable = new ObservableField<>();
  private final ObservableField<String> infoTextObservable = new ObservableField<>();
  private final ObservableField<String> descriptionObservable = new ObservableField<>();
  private final View.OnClickListener deleteButtonClickListener = new View.OnClickListener() {
    @Override public void onClick(View view) {
      new AlertDialog.Builder(context).setMessage(R.string.deletion_confirmation_supplies_list)
          .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.dismiss())
          .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
            dialogInterface.dismiss();
            deleteSuppliesListUseCase.delete(suppliesList.uuid())
                .compose(transformer.applySchedulers())
                .subscribe(aVoid -> {
                }, throwable -> Timber.e(throwable, "Failed to delete product list"));
          })
          .show();
    }
  };
  private final View.OnClickListener itemClickListener = new View.OnClickListener() {
    @Override public void onClick(View view) {
      if (onItemClickListener != null) onItemClickListener.onItemClicked();
    }
  };
  private final Context context;
  private final DeleteSuppliesListUseCase deleteSuppliesListUseCase;
  private final SchedulerTransformer transformer;
  private OnItemClickListener onItemClickListener;
  private SuppliesList suppliesList;

  @Inject public SuppliesItemViewModelImpl(Context context,
      DeleteSuppliesListUseCase deleteSuppliesListUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer transformer) {
    this.context = context;
    this.deleteSuppliesListUseCase = deleteSuppliesListUseCase;
    this.transformer = transformer;
  }

  @Override public void onBindViewHolder(SuppliesList suppliesList) {
    this.suppliesList = suppliesList;
    productListNameObservable.set(suppliesList.name());

    infoTextObservable.set(context.getString(R.string.number_of_items,
        suppliesList.items() == null ? 0 : suppliesList.items().size()));

    descriptionObservable.set(suppliesList.description());
  }

  @Override public ObservableField<String> itemNameObservable() {
    return productListNameObservable;
  }

  @Override public ObservableField<String> infoTextObservable() {
    return infoTextObservable;
  }

  @Override public View.OnClickListener deleteButtonClickListener() {
    return deleteButtonClickListener;
  }

  @Override public ObservableField<String> description() {
    return descriptionObservable;
  }

  @Override public View.OnClickListener itemClickListener() {
    return itemClickListener;
  }

  @Override public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
}
