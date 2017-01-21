package com.fred.inventory.presentation.globalsearch.adapters;

import com.fred.inventory.domain.models.GlobalSearchResult;
import java.util.List;

/**
 * Global search list adapter
 */

public interface GlobalSearchRecyclerViewAdapter {
  void replaceAll(List<GlobalSearchResult> models);
}
