package com.fred.inventory.presentation.productlist.adapters;

import android.support.v7.util.SortedList;
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
  private final SortedList<Product> sortedList =
      new SortedList<>(Product.class, new SortedList.Callback<Product>() {
        @Override public int compare(Product o1, Product o2) {
          return o1.getName().compareToIgnoreCase(o2.getName());
        }

        @Override public void onChanged(int position, int count) {
          notifyItemRangeChanged(position, count);
        }

        @Override public boolean areContentsTheSame(Product oldItem, Product newItem) {
          return oldItem.getName().equals(newItem.getName());
        }

        @Override public boolean areItemsTheSame(Product item1, Product item2) {
          return item1.getId().equals(item2.getId());
        }

        @Override public void onInserted(int position, int count) {
          notifyItemRangeInserted(position, count);
        }

        @Override public void onRemoved(int position, int count) {
          notifyItemRangeRemoved(position, count);
        }

        @Override public void onMoved(int fromPosition, int toPosition) {
          notifyItemMoved(fromPosition, toPosition);
        }
      });
  private OnProductDeletedListener onProductDeletedListener;
  private OnItemClickListener onItemClickListener;

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

  @Override public int getItemCount() {
    return sortedList.size();
  }

  @Override public List<Product> getItems() {
    List<Product> products = new ArrayList<>();
    for (int i = 0; i < sortedList.size(); i++)
      products.add(sortedList.get(i));
    return products;
  }

  @Override
  public void setOnProductDeletedListener(OnProductDeletedListener onProductDeletedListener) {
    this.onProductDeletedListener = onProductDeletedListener;
  }

  @Override public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  @Override public void add(Product model) {
    sortedList.add(model);
  }

  @Override public void remove(Product model) {
    sortedList.remove(model);
  }

  @Override public void add(List<Product> models) {
    sortedList.addAll(models);
  }

  @Override public void remove(List<Product> models) {
    sortedList.beginBatchedUpdates();
    for (Product model : models) {
      sortedList.remove(model);
    }
    sortedList.endBatchedUpdates();
  }

  @Override public void replaceAll(List<Product> models) {
    sortedList.beginBatchedUpdates();
    for (int i = sortedList.size() - 1; i >= 0; i--) {
      final Product model = sortedList.get(i);
      if (!models.contains(model)) {
        sortedList.remove(model);
      }
    }
    sortedList.addAll(models);
    sortedList.endBatchedUpdates();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @Inject ProductListRecyclerViewItemViewModel viewModel;

    public ViewHolder(View itemView) {
      super(itemView);
    }
  }
}
