package com.fred.inventory.presentation.listofproducts.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.MainActivity;
import com.fred.inventory.data.firebase.models.SuppliesList;
import com.fred.inventory.databinding.ProductListListItemBinding;
import com.fred.inventory.presentation.base.SortedRecyclerViewAdapterWithSpace;
import com.fred.inventory.presentation.listofproducts.modules.ListOfProductListsItemModule;
import com.fred.inventory.presentation.listofproducts.viewmodels.ListOfProductListsItemViewModel;
import java.util.Comparator;
import javax.inject.Inject;

public class ListOfProductListsAdapterImpl
    extends SortedRecyclerViewAdapterWithSpace<SuppliesList, Comparator<SuppliesList>>
    implements ListOfProductListsAdapter {
  private OnItemClickListener onItemClickListener;

  @Inject public ListOfProductListsAdapterImpl(Comparator<SuppliesList> comparator) {
    super(SuppliesList.class, comparator);
  }

  @Override
  public void onWrappedBindViewHolder(final SortedRecyclerViewAdapterWithSpace.ViewHolder holder,
      final int position) {
    ViewHolder productListViewHolder = (ViewHolder) holder;
    final SuppliesList suppliesList = sortedList.get(position);
    productListViewHolder.viewModel.onBindViewHolder(suppliesList);
    productListViewHolder.viewModel.setOnItemClickListener(() -> {
      if (onItemClickListener != null) {
        onItemClickListener.onItemClicked(sortedList.get(holder.getAdapterPosition()));
      }
    });
  }

  @Override public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  @Override protected SortedRecyclerViewAdapterWithSpace.ViewHolder onWrappedCreateViewHolder(
      ViewGroup parent, int viewType) {
    ProductListListItemBinding binding =
        ProductListListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

    ViewHolder holder = new ViewHolder(binding.getRoot());
    MainActivity.scoped(new ListOfProductListsItemModule()).inject(holder);

    binding.setViewModel(holder.viewModel);

    return holder;
  }

  @Override protected boolean areContentsTheSame(SuppliesList oldItem, SuppliesList newItem) {
    return oldItem.name().equals(newItem.name());
  }

  @Override protected boolean areItemsTheSame(SuppliesList item1, SuppliesList item2) {
    return item1.uuid().equals(item2.uuid());
  }

  public static class ViewHolder extends SortedRecyclerViewAdapterWithSpace.ViewHolder {
    @Inject ListOfProductListsItemViewModel viewModel;

    public ViewHolder(View itemView) {
      super(itemView, false);
    }
  }
}
