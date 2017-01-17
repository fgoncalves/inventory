package com.fred.inventory.presentation.productlist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.MainActivity;
import com.fred.inventory.databinding.ProductListItemBinding;
import com.fred.inventory.domain.models.Product;
import com.fred.inventory.presentation.base.SortedRecyclerViewAdapter;
import com.fred.inventory.presentation.productlist.modules.ProductListItemModule;
import com.fred.inventory.presentation.productlist.viewmodels.ProductListRecyclerViewItemViewModel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;

public class ProductListRecyclerViewAdapterImpl extends
    SortedRecyclerViewAdapter<Product, ProductListRecyclerViewAdapterImpl.ViewHolder, Comparator<Product>>
    implements ProductListRecyclerViewAdapter {

  @Override public boolean areContentsTheSame(Product oldItem, Product newItem) {
    return oldItem.getName().equals(newItem.getName());
  }

  @Override public boolean areItemsTheSame(Product item1, Product item2) {
    return item1.getId().equals(item2.getId());
  }

  private OnProductDeletedListener onProductDeletedListener;
  private OnItemClickListener onItemClickListener;

  @Inject public ProductListRecyclerViewAdapterImpl(Comparator<Product> comparator) {
    super(Product.class, comparator);
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ProductListItemBinding binding =
        ProductListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

    ViewHolder holder = new ViewHolder(binding.getRoot());
    MainActivity.scoped(new ProductListItemModule()).inject(holder);

    binding.setViewModel(holder.viewModel);

    return holder;
  }

  @Override public void onBindViewHolder(final ViewHolder holder, int position) {
    final Product product = sortedList.get(position);
    holder.viewModel.onBindViewHolder(product);

    holder.viewModel.setOnDeleteListener(() -> {
      remove(product);
      if (onProductDeletedListener != null) onProductDeletedListener.onProductDeleted(product);
    });
    holder.viewModel.setOnItemClickListener(() -> {
      if (onItemClickListener != null) {
        onItemClickListener.onItemClicked(sortedList.get(holder.getAdapterPosition()));
      }
    });
  }

  @Override
  public void setOnProductDeletedListener(OnProductDeletedListener onProductDeletedListener) {
    this.onProductDeletedListener = onProductDeletedListener;
  }

  @Override public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @Inject ProductListRecyclerViewItemViewModel viewModel;

    public ViewHolder(View itemView) {
      super(itemView);
    }
  }
}
