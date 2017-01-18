package com.fred.inventory.presentation.globalsearch.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.MainActivity;
import com.fred.inventory.databinding.GlobalSearchListItemBinding;
import com.fred.inventory.domain.models.GlobalSearchResult;
import com.fred.inventory.presentation.base.SortedRecyclerViewAdapter;
import com.fred.inventory.presentation.globalsearch.adapters.comparators.GlobalSearchResultComparator;
import com.fred.inventory.presentation.globalsearch.modules.GlobalSearchListItemModule;
import com.fred.inventory.presentation.globalsearch.viewmodels.GlobalSearchListItemViewModel;
import javax.inject.Inject;

public class GlobalSearchRecyclerViewAdapterImpl extends
    SortedRecyclerViewAdapter<GlobalSearchResult, GlobalSearchRecyclerViewAdapterImpl.ViewHolder, GlobalSearchResultComparator>
    implements GlobalSearchRecyclerViewAdapter {

  @Inject public GlobalSearchRecyclerViewAdapterImpl(GlobalSearchResultComparator comparator) {
    super(GlobalSearchResult.class, comparator);
  }

  @Override
  protected boolean areContentsTheSame(GlobalSearchResult oldItem, GlobalSearchResult newItem) {
    return oldItem.equals(newItem);
  }

  @Override protected boolean areItemsTheSame(GlobalSearchResult item1, GlobalSearchResult item2) {
    return item1.equals(item2);
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    GlobalSearchListItemBinding binding =
        GlobalSearchListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent,
            false);

    ViewHolder holder = new ViewHolder(binding.getRoot());
    MainActivity.scoped(new GlobalSearchListItemModule()).inject(holder);

    binding.setViewModel(holder.viewModel);

    return holder;
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.viewModel.onBindViewHolder(sortedList.get(position));
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @Inject GlobalSearchListItemViewModel viewModel;

    public ViewHolder(View itemView) {
      super(itemView);
    }
  }
}
