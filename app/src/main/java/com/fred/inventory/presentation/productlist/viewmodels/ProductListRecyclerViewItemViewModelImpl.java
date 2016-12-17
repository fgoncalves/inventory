package com.fred.inventory.presentation.productlist.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;
import com.fred.inventory.domain.models.Product;
import com.fred.inventory.domain.usecases.DeleteProductUseCase;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import java.util.Date;
import javax.inject.Inject;
import rx.functions.Action1;
import timber.log.Timber;

public class ProductListRecyclerViewItemViewModelImpl
    implements ProductListRecyclerViewItemViewModel {
  private final ObservableField<String> productName = new ObservableField<>();
  private final ObservableField<String> productQuantityLabel = new ObservableField<>();
  private final ObservableInt quantity = new ObservableInt();
  private final ObservableInt progressBarVisibility = new ObservableInt();
  private final ObservableField<Date> expirationDate = new ObservableField<>();
  private final ObservableInt expirationDateVisibility = new ObservableInt();
  private final DeleteProductUseCase deleteProductUseCase;
  private final SchedulerTransformer transformer;
  private final View.OnClickListener deleteButtonClickListener = new View.OnClickListener() {
    @Override public void onClick(View view) {
      deleteProductUseCase.delete(product)
          .compose(transformer.<Void>applySchedulers())
          .subscribe(aVoid -> {
            if (deleteListener != null) deleteListener.onDelete();
          }, throwable -> Timber.e(throwable, "Failed to delete item from product list"));
    }
  };
  private final View.OnClickListener itemClickListener = new View.OnClickListener() {
    @Override public void onClick(View view) {
      if (onItemClickListener != null) onItemClickListener.onClicked();
    }
  };
  private OnDeleteListener deleteListener;
  private OnItemClickListener onItemClickListener;
  private Product product;

  @Inject public ProductListRecyclerViewItemViewModelImpl(DeleteProductUseCase deleteProductUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer transformer) {
    this.deleteProductUseCase = deleteProductUseCase;
    this.transformer = transformer;
  }

  @Override public void onBindViewHolder(Product product) {
    this.product = product;
    productName.set(product.getName());
    productQuantityLabel.set(product.getQuantityLabel());
    quantity.set(product.getQuantity());
    if (product.isUnit()) {
      progressBarVisibility.set(View.GONE);
    } else {
      progressBarVisibility.set(View.VISIBLE);
    }
    if (product.getExpirationDate() != null) {
      expirationDate.set(product.getExpirationDate());
      expirationDateVisibility.set(View.VISIBLE);
    } else {
      expirationDateVisibility.set(View.GONE);
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

  @Override public ObservableField<Date> expirationDate() {
    return expirationDate;
  }

  @Override public ObservableInt expirationDateVisibility() {
    return expirationDateVisibility;
  }

  @Override public View.OnClickListener deleteButtonClickListener() {
    return deleteButtonClickListener;
  }

  @Override public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
    deleteListener = onDeleteListener;
  }

  @Override public View.OnClickListener itemClickListener() {
    return itemClickListener;
  }

  @Override public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
}
