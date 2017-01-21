package com.fred.inventory.presentation.base;

/**
 * Generic interface for the clicks in the adapters
 */
public interface OnItemClickListener<T> {
  void onItemClicked(T item);
}
