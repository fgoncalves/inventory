package com.fred.inventory.presentation.listofproducts.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.MainActivity;
import com.fred.inventory.databinding.ProductListListItemBinding;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.presentation.listofproducts.modules.ListOfProductListsItemModule;
import com.fred.inventory.presentation.listofproducts.viewmodels.ListOfProductListsItemViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ListOfProductListsAdapterImpl
    extends RecyclerView.Adapter<ListOfProductListsAdapterImpl.ViewHolder>
    implements ListOfProductListsAdapter {
  private OnProductListDeletedListener onProductListDeletedListener;
  private List<ProductList> model = new ArrayList<>();

  @Inject public ListOfProductListsAdapterImpl() {
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ProductListListItemBinding binding =
        ProductListListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

    ViewHolder holder = new ViewHolder(binding.getRoot());
    MainActivity.scoped(new ListOfProductListsItemModule()).inject(holder);

    binding.setViewModel(holder.viewModel);

    return holder;
  }

  @Override public void onBindViewHolder(ViewHolder holder, final int position) {
    holder.viewModel.onBindViewHolder(model.get(position));
    holder.viewModel.setOnDeleteButtonClick(
        new ListOfProductListsItemViewModel.OnDeleteButtonClick() {
          @Override public void onDeleteClicked() {
            ProductList list = model.remove(position);
            notifyDataSetChanged();
            if (onProductListDeletedListener != null) {
              onProductListDeletedListener.onProductListDeleted(list);
            }
          }
        });
  }

  @Override public int getItemCount() {
    return model.size();
  }

  @Override public void setData(List<ProductList> model) {
    this.model = model;
    notifyDataSetChanged();
  }

  @Override public void setOnProductListDeletedListener(
      OnProductListDeletedListener onProductListDeletedListener) {
    this.onProductListDeletedListener = onProductListDeletedListener;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @Inject ListOfProductListsItemViewModel viewModel;

    public ViewHolder(View itemView) {
      super(itemView);
    }
  }
}
