package com.fred.inventory.presentation.base;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import java.util.Comparator;
import java.util.List;

/**
 * Recycler view adapter which is already backed up by a sorted list.
 */

public abstract class SortedRecyclerViewAdapter<MODEL, VH extends RecyclerView.ViewHolder, C extends Comparator<MODEL>>
    extends RecyclerView.Adapter<VH> {
  protected final C modelComparator;
  protected final SortedList.Callback<MODEL> callback = new SortedList.Callback<MODEL>() {
    @Override public int compare(MODEL o1, MODEL o2) {
      return modelComparator.compare(o1, o2);
    }

    @Override public void onChanged(int position, int count) {
      notifyItemRangeChanged(position, count);
    }

    @Override public boolean areContentsTheSame(MODEL oldItem, MODEL newItem) {
      return SortedRecyclerViewAdapter.this.areContentsTheSame(oldItem, newItem);
    }

    @Override public boolean areItemsTheSame(MODEL item1, MODEL item2) {
      return SortedRecyclerViewAdapter.this.areItemsTheSame(item1, item2);
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
  };
  protected final SortedList<MODEL> sortedList;

  protected SortedRecyclerViewAdapter(Class<MODEL> classModel, C modelComparator) {
    this.modelComparator = modelComparator;
    this.sortedList = new SortedList<>(classModel, callback);
  }

  public void add(MODEL model) {
    sortedList.add(model);
  }

  public void remove(MODEL model) {
    sortedList.remove(model);
  }

  public void add(List<MODEL> models) {
    sortedList.addAll(models);
  }

  public void remove(List<MODEL> models) {
    sortedList.beginBatchedUpdates();
    for (MODEL model : models) {
      sortedList.remove(model);
    }
    sortedList.endBatchedUpdates();
  }

  public void replaceAll(List<MODEL> models) {
    sortedList.beginBatchedUpdates();
    for (int i = sortedList.size() - 1; i >= 0; i--) {
      final MODEL model = sortedList.get(i);
      if (!models.contains(model)) {
        sortedList.remove(model);
      }
    }
    sortedList.addAll(models);
    sortedList.endBatchedUpdates();
  }

  protected abstract boolean areContentsTheSame(MODEL oldItem, MODEL newItem);

  protected abstract boolean areItemsTheSame(MODEL item1, MODEL item2);
}
