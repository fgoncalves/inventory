package com.fred.inventory.presentation.productlist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.MainActivity;
import com.fred.inventory.databinding.ProductListItemBinding;
import com.fred.inventory.domain.models.Product;
import com.fred.inventory.presentation.productlist.modules.ProductListItemModule;
import com.fred.inventory.presentation.productlist.viewmodels.ProductListRecyclerViewItemViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ProductListRecyclerViewAdapterImpl
    extends RecyclerView.Adapter<ProductListRecyclerViewAdapterImpl.ViewHolder>
    implements ProductListRecyclerViewAdapter {
  private OnProductDeletedListener onProductDeletedListener;
  private OnItemClickListener onItemClickListener;
  private List<Product> products = new ArrayList<>();

  @Inject public ProductListRecyclerViewAdapterImpl() {
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
    final Product product = products.get(position);
    holder.viewModel.onBindViewHolder(product);

    holder.viewModel.setOnDeleteListener(
        new ProductListRecyclerViewItemViewModel.OnDeleteListener() {
          @Override public void onDelete() {
            Product item = products.remove(holder.getAdapterPosition());
            notifyDataSetChanged();
            if (onProductDeletedListener != null) onProductDeletedListener.onProductDeleted(item);
          }
        });
    holder.viewModel.setOnItemClickListener(
        new ProductListRecyclerViewItemViewModel.OnItemClickListener() {
          @Override public void onClicked() {
            if (onItemClickListener != null) {
              onItemClickListener.onItemClicked(products.get(holder.getAdapterPosition()));
            }
          }
        });
  }

  @Override public int getItemCount() {
    return products.size();
  }

  @Override public void setData(List<Product> products) {
    this.products = products;
    notifyDataSetChanged();
  }

  @Override public List<Product> getItems() {
    return products;
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
