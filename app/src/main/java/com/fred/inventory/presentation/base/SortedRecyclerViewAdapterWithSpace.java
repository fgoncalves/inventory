package com.fred.inventory.presentation.base;

import android.support.v4.widget.Space;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.R;
import java.util.Comparator;

/**
 * Unfortunately it seems that the android framework provides little to no support for having the
 * recycler view on top of the floating action bar. Suggestions in the web seem to say we can do
 * this by adding a "footer" view which is a {@link Space} with the size of the fab.
 * <p/>
 * This adapter wraps the models in another model to be able to distinguish between normal models
 * and spaces. This should allow for more than one list backed by this adapter.
 * <p/>
 * Created by fred on 28.01.17.
 */

public abstract class SortedRecyclerViewAdapterWithSpace<MODEL, C extends Comparator<MODEL>> extends
    SortedRecyclerViewAdapter<MODEL, SortedRecyclerViewAdapterWithSpace.ViewHolder, Comparator<MODEL>> {
  private final static int SPACE_ITEM_TYPE = 123;
  private final static int WRAPPED_MODEL_ITEM_TYPE = 456;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    private final boolean isSpace;

    public ViewHolder(View itemView, boolean isSpace) {
      super(itemView);
      this.isSpace = isSpace;
    }

    public boolean isSpace() {
      return isSpace;
    }
  }

  public SortedRecyclerViewAdapterWithSpace(Class<MODEL> classModel,
      Comparator<MODEL> modelComparator) {
    super(classModel, modelComparator);
  }

  @Override public int getItemCount() {
    int itemCount = super.getItemCount();
    if (itemCount == 0) return 0;
    return itemCount + 1;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case SPACE_ITEM_TYPE:
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.space, parent, false);
        return new ViewHolder(view, true);
      default:
        return onWrappedCreateViewHolder(parent, viewType);
    }
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    if (!holder.isSpace()) onWrappedBindViewHolder(holder, position);
  }

  @Override public int getItemViewType(int position) {
    return position == sortedList.size() ? SPACE_ITEM_TYPE : WRAPPED_MODEL_ITEM_TYPE;
  }

  protected abstract ViewHolder onWrappedCreateViewHolder(ViewGroup parent, int viewType);

  protected abstract void onWrappedBindViewHolder(ViewHolder holder, int position);
}
