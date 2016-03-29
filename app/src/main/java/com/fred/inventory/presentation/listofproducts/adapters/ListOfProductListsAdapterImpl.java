package com.fred.inventory.presentation.listofproducts.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.R;
import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.presentation.listofproducts.views.ListOfProductListsItemView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ListOfProductListsAdapterImpl
    extends RecyclerView.Adapter<ListOfProductListsAdapterImpl.ViewHolder>
    implements ListOfProductListsAdapter {
  private List<ProductList> model = new ArrayList<>();

  @Inject public ListOfProductListsAdapterImpl() {
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = View.inflate(parent.getContext(), R.layout.product_list_list_item, null);

    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.mainView.displayProductList(model.get(position));
  }

  @Override public int getItemCount() {
    return model.size();
  }

  @Override public void attachModel(List<ProductList> model) {
    this.model = model;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    ListOfProductListsItemView mainView;

    public ViewHolder(View itemView) {
      super(itemView);
      mainView = (ListOfProductListsItemView) itemView;
    }
  }
}
