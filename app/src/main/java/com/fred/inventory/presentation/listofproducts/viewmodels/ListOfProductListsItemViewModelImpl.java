package com.fred.inventory.presentation.listofproducts.viewmodels;

import android.content.Context;
import android.databinding.ObservableField;
import android.view.View;
import com.fred.inventory.R;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.domain.usecases.DeleteProductListUseCase;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import javax.inject.Inject;
import rx.functions.Action1;
import timber.log.Timber;

public class ListOfProductListsItemViewModelImpl implements ListOfProductListsItemViewModel {
  private final ObservableField<String> productListNameObservable = new ObservableField<>();
  private final ObservableField<String> infoTextObservable = new ObservableField<>();
  private final View.OnClickListener deleteButtonClickListener = new View.OnClickListener() {
    @Override public void onClick(View view) {
      deleteProductListUseCase.delete(productList)
          .compose(transformer.<Void>applySchedulers())
          .subscribe(new Action1<Void>() {
            @Override public void call(Void aVoid) {
              if (onDeleteButtonClick != null) onDeleteButtonClick.onDeleteClicked();
            }
          }, new Action1<Throwable>() {
            @Override public void call(Throwable throwable) {
              Timber.e(throwable, "Failed to delte product list");
              // TODO: Do something in the ui
            }
          });
    }
  };
  private final View.OnClickListener itemClickListener = new View.OnClickListener() {
    @Override public void onClick(View view) {
      if (onItemClickListener != null) onItemClickListener.onItemClicked();
    }
  };
  private final Context context;
  private final DeleteProductListUseCase deleteProductListUseCase;
  private final SchedulerTransformer transformer;
  private OnDeleteButtonClick onDeleteButtonClick;
  private OnItemClickListener onItemClickListener;
  private ProductList productList;

  @Inject public ListOfProductListsItemViewModelImpl(Context context,
      DeleteProductListUseCase deleteProductListUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer transformer) {
    this.context = context;
    this.deleteProductListUseCase = deleteProductListUseCase;
    this.transformer = transformer;
  }

  @Override public void onBindViewHolder(ProductList productList) {
    this.productList = productList;
    productListNameObservable.set(productList.getName());
    infoTextObservable.set(
        context.getString(R.string.number_of_items, productList.getProducts().size()));
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

  @Override public View.OnClickListener itemClickListener() {
    return itemClickListener;
  }

  @Override public void setOnDeleteButtonClick(OnDeleteButtonClick onDeleteButtonClick) {
    this.onDeleteButtonClick = onDeleteButtonClick;
  }

  @Override public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
}
