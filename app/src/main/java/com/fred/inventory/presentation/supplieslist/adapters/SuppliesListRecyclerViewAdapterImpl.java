package com.fred.inventory.presentation.supplieslist.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.MainActivity;
import com.fred.inventory.data.firebase.models.SuppliesList;
import com.fred.inventory.data.firebase.models.SupplyItem;
import com.fred.inventory.databinding.ProductListItemBinding;
import com.fred.inventory.presentation.base.SortedRecyclerViewAdapterWithSpace;
import com.fred.inventory.di.SuppliesListItemModule;
import com.fred.inventory.presentation.supplieslist.viewmodels.SuppliesListRecyclerViewItemViewModel;
import java.util.Comparator;
import javax.inject.Inject;

public class SuppliesListRecyclerViewAdapterImpl
    extends SortedRecyclerViewAdapterWithSpace<SupplyItem, Comparator<SupplyItem>>
    implements SuppliesListRecyclerViewAdapter {
  private SuppliesList suppliesList;

  @Override public boolean areContentsTheSame(SupplyItem oldItem, SupplyItem newItem) {
    return oldItem.name().equals(newItem.name());
  }

  @Override public boolean areItemsTheSame(SupplyItem item1, SupplyItem item2) {
    return item1.uuid().equals(item2.uuid());
  }

  private OnItemClickListener onItemClickListener;

  @Inject public SuppliesListRecyclerViewAdapterImpl(Comparator<SupplyItem> comparator) {
    super(SupplyItem.class, comparator);
  }

  @Override protected SortedRecyclerViewAdapterWithSpace.ViewHolder onWrappedCreateViewHolder(
      ViewGroup parent, int viewType) {
    ProductListItemBinding binding =
        ProductListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

    ViewHolder holder = new ViewHolder(binding.getRoot());
    MainActivity.scoped(new SuppliesListItemModule()).inject(holder);

    binding.setViewModel(holder.viewModel);

    return holder;
  }

  @Override
  protected void onWrappedBindViewHolder(SortedRecyclerViewAdapterWithSpace.ViewHolder holder,
      int position) {
    final SupplyItem supplyItem = sortedList.get(position);
    ViewHolder productListViewHolder = (ViewHolder) holder;
    productListViewHolder.viewModel.onBindViewHolder(suppliesList, supplyItem);

    productListViewHolder.viewModel.setOnItemClickListener(() -> {
      if (onItemClickListener != null) {
        onItemClickListener.onItemClicked(sortedList.get(holder.getAdapterPosition()));
      }
    });
  }

  @Override public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  @Override public void setSuppliesList(SuppliesList suppliesList) {
    this.suppliesList = suppliesList;
  }

  public static class ViewHolder extends SortedRecyclerViewAdapterWithSpace.ViewHolder {
    @Inject SuppliesListRecyclerViewItemViewModel viewModel;

    public ViewHolder(View itemView) {
      super(itemView, false);
    }
  }
}
