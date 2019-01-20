package com.fred.inventory.presentation.supplieslist.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import com.fred.inventory.R;
import com.fred.inventory.data.firebase.models.SuppliesList;
import com.fred.inventory.data.firebase.models.SupplyItem;
import com.fred.inventory.domain.usecases.CreateOrUpdateSuppliesListUserCase;
import com.fred.inventory.domain.usecases.CreateOrUpdateSupplyItemUseCase;
import com.fred.inventory.domain.usecases.GetItemsDatabaseReference;
import com.fred.inventory.domain.usecases.GetProductInfoFromCodeUseCase;
import com.fred.inventory.domain.usecases.GetSuppliesListUseCase;
import com.fred.inventory.presentation.items.SupplyItemScreen;
import com.fred.inventory.presentation.supplieslist.adapters.SuppliesListRecyclerViewAdapter;
import com.fred.inventory.presentation.widgets.clicktoedittext.ClickToEditTextView;
import com.fred.inventory.utils.StringUtils;
import com.fred.inventory.utils.binding.Observable;
import com.fred.inventory.utils.binding.Observer;
import com.fred.inventory.utils.binding.widgets.OneTimeTextWatcher;
import com.fred.inventory.utils.path.PathManager;
import com.fred.inventory.utils.rx.RxSubscriptionPool;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import com.google.auto.value.AutoValue;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

public class SuppliesListViewModelImpl
    implements SuppliesListViewModel, SuppliesListRecyclerViewAdapter.OnItemClickListener {
  private static final int PRODUCT_LIST_NAME_VIEW = 0;
  private static final int SEARCH_EDIT_TEXT_VIEW = 1;
  private final ObservableField<String> suppliesListName = new ObservableField<>();
  private final ObservableField<ClickToEditTextView.Mode> productListNameMode =
      new ObservableField<>(ClickToEditTextView.Mode.TEXT);
  private final OneTimeTextWatcher productListNameTextWatcher =
      new OneTimeTextWatcher(suppliesListName);
  private final ObservableInt toolbarDisplayedChild = new ObservableInt(0);
  private final GetSuppliesListUseCase getSuppliesListUseCase;
  private final GetItemsDatabaseReference getItemsDatabaseReference;
  private final CreateOrUpdateSuppliesListUserCase createOrUpdateSuppliesListUserCase;
  private final CreateOrUpdateSupplyItemUseCase createOrUpdateSupplyItemUseCase;
  private final SchedulerTransformer transformer;
  private final RxSubscriptionPool rxSubscriptionPool;
  private final ObservableInt listVisibility = new ObservableInt(View.GONE);
  private final ObservableInt emptyListVisibility = new ObservableInt(View.GONE);
  private final ObservableInt progressBarVisibility = new ObservableInt(View.GONE);
  private final ObservableInt recyclerViewScrollPosition = new ObservableInt(0);
  private final ObservableField<String> searchQuery = new ObservableField<>();
  private final Observable<String> productListIdObservable = Observable.create();
  private final PathManager pathManager;
  private final SuppliesListRecyclerViewAdapter adapter;
  private final GetProductInfoFromCodeUseCase getProductInfoFromCodeUseCase;
  private final TextWatcher searchQueryTextWatcher = new TextWatcher() {
    @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override public void afterTextChanged(Editable editable) {
      if (suppliesList == null) return;

      String query = editable.toString();
      List<SupplyItem> supplyItems = filter(suppliesList.items(), query);
      adapter.replaceAll(supplyItems);
      toggleListViewVisibility();
      recyclerViewScrollPosition.set(0);
    }
  };
  private final ChildEventListener itemsChildEventListener = new ChildEventListener() {
    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
      SupplyItem supplyItem = SupplyItem.create(dataSnapshot);
      adapter.add(supplyItem);
      addSupplyItem(supplyItem);
      toggleListViewVisibility();
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
      SupplyItem supplyItem = SupplyItem.create(dataSnapshot);
      adapter.remove(supplyItem);
      removeSupplyItem(supplyItem);
      toggleListViewVisibility();
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override public void onCancelled(DatabaseError databaseError) {

    }
  };

  private SuppliesList suppliesList;
  private OnScanBarCodeButtonClickListener scanBarCodeButtonClickListener;
  private DatabaseReference databaseReference;

  @Inject public SuppliesListViewModelImpl(GetSuppliesListUseCase getSuppliesListUseCase,
      GetItemsDatabaseReference getItemsDatabaseReference,
      CreateOrUpdateSuppliesListUserCase createOrUpdateSuppliesListUserCase,
      CreateOrUpdateSupplyItemUseCase createOrUpdateSupplyItemUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer transformer,
      RxSubscriptionPool rxSubscriptionPool, PathManager pathManager,
      SuppliesListRecyclerViewAdapter adapter,
      GetProductInfoFromCodeUseCase getProductInfoFromCodeUseCase) {
    this.getSuppliesListUseCase = getSuppliesListUseCase;
    this.getItemsDatabaseReference = getItemsDatabaseReference;
    this.createOrUpdateSuppliesListUserCase = createOrUpdateSuppliesListUserCase;
    this.createOrUpdateSupplyItemUseCase = createOrUpdateSupplyItemUseCase;
    this.transformer = transformer;
    this.rxSubscriptionPool = rxSubscriptionPool;
    this.pathManager = pathManager;
    this.adapter = adapter;
    this.getProductInfoFromCodeUseCase = getProductInfoFromCodeUseCase;
  }

  @Override public void forProductList(String uuid) {
    productListIdObservable.set(uuid);
  }

  @Override public void onActivityCreated() {
    adapter.setOnItemClickListener(this);

    if (productListIdObservable.get() == null) {
      emptyListVisibility.set(View.VISIBLE);
      productListNameMode.set(ClickToEditTextView.Mode.EDIT);
      // TODO: put input in text view
      return;
    }

    progressBarVisibility.set(View.VISIBLE);
    queryForProductList();
  }

  @Override public void onDestroyView() {
    rxSubscriptionPool.unsubscribeFrom(getClass().getCanonicalName());
    if (databaseReference != null) databaseReference.removeEventListener(itemsChildEventListener);
  }

  @Override public ObservableField<String> productListName() {
    return suppliesListName;
  }

  @Override public TextWatcher productListTextWatcher() {
    return productListNameTextWatcher;
  }

  @Override public ObservableInt progressBarVisibility() {
    return progressBarVisibility;
  }

  private void queryForProductList() {
    Subscription subscription = getSuppliesListUseCase.get(productListIdObservable.get())
        .compose(transformer.applySchedulers())
        .subscribe(new SuppliesListSubscriber());

    rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
  }

  @Override public void onDoneButtonClick(View view) {
    final String name = suppliesListName.get();
    if (StringUtils.isBlank(name)) {
      // TODO: Set error
      return;
    }

    productListNameMode.set(ClickToEditTextView.Mode.TEXT);

    Subscription subscription =
        createOrUpdateSuppliesList(name).subscribe(value -> pathManager.back(),
            e -> Timber.d(e, "Failed to save product list"));

    rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
  }

  @Override
  public void setOnScanBarCodeButtonClickListener(OnScanBarCodeButtonClickListener listener) {
    this.scanBarCodeButtonClickListener = listener;
  }

  @Override public void onScanBarCodeButtonClick(View view) {
    String name = suppliesListName.get();
    if (StringUtils.isBlank(name)) {
      // TODO: Show error here
      return;
    }

    if (scanBarCodeButtonClickListener != null) {
      scanBarCodeButtonClickListener.onScanBarCodeButtonClicked();
    }
  }

  @Override public void onAddButtonClick(View view) {
    String name = suppliesListName.get();
    if (StringUtils.isBlank(name)) {
      // TODO: Show error here
      return;
    }

    Subscription subscription = createOrUpdateSuppliesList(name).subscribe(value -> {
      goToItemScreen(value.uuid(), null);
    }, e -> Timber.d(e, "Failed to save product list"));

    rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
  }

  @Override public ObservableInt emptyListVisibility() {
    return emptyListVisibility;
  }

  @Override public ObservableInt itemListVisibility() {
    return listVisibility;
  }

  @Override public ObservableInt toolBarDisplayedChild() {
    return toolbarDisplayedChild;
  }

  @Override public ObservableInt recyclerViewScrollPosition() {
    return recyclerViewScrollPosition;
  }

  @Override public void unbindProductListIdObserver(Observer<String> observer) {
    productListIdObservable.unbind(observer);
  }

  @Override public void bindProductListIdObserver(Observer<String> observer) {
    productListIdObservable.bind(observer);
  }

  @Override public RecyclerView.Adapter<?> productListRecyclerViewAdapter() {
    return (RecyclerView.Adapter<?>) adapter;
  }

  @Override public boolean onHomeButtonPressed() {
    if (toolbarDisplayedChild.get() == PRODUCT_LIST_NAME_VIEW) return false;
    searchQuery.set(""); // Clear search so entire list is shown
    toolbarDisplayedChild.set(PRODUCT_LIST_NAME_VIEW);
    return true;
  }

  @Override public ObservableField<String> searchQuery() {
    return searchQuery;
  }

  @Override public TextWatcher searchQueryTextWatcher() {
    return searchQueryTextWatcher;
  }

  @Override public void onItemClicked(SupplyItem supplyItem) {
    goToItemScreen(suppliesList.uuid(), supplyItem.uuid());
  }

  @Override public void onCodeScanned(String barcode) {
    Subscription subscription = getProductInfoFromCodeUseCase.info(barcode).flatMap(supplyItem -> {
      String name = suppliesListName.get();
      return createOrUpdateSuppliesList(name).flatMap(
          suppliesList -> createOrUpdateSupplyItemUseCase.createOrUpdate(suppliesList.uuid(),
              supplyItem)).map(item -> SuppliesListAndItem.create(suppliesList, item));
    }).compose(transformer.applySchedulers()).subscribe(suppliesListAndItem -> {
      suppliesList = suppliesListAndItem.suppliesList();
      goToItemScreen(suppliesListAndItem.suppliesList().uuid(),
          suppliesListAndItem.supplyItem().uuid());
    }, e -> Timber.e(e, "Failed use barcode scanner for adding supply item"));

    rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
  }

  @Override public void onSearchButtonClicked(View actionView) {
    toolbarDisplayedChild.set(SEARCH_EDIT_TEXT_VIEW);
  }

  @Override public ObservableField<ClickToEditTextView.Mode> productListNameMode() {
    return productListNameMode;
  }

  private void goToItemScreen(String suppliesListUuid, String supplyItemUuid) {
    SupplyItemScreen supplyItemScreen;
    if (supplyItemUuid == null) {
      supplyItemScreen = SupplyItemScreen.newInstance(suppliesListUuid);
    } else {
      supplyItemScreen = SupplyItemScreen.newInstance(suppliesListUuid, supplyItemUuid);
    }
    productListIdObservable.set(suppliesList.uuid());
    pathManager.go(supplyItemScreen, R.id.main_container);
  }

  private List<SupplyItem> filter(Map<String, SupplyItem> supplyItems, String query) {
    if (StringUtils.isBlank(query)) return new ArrayList<>(supplyItems.values());

    List<SupplyItem> filteredProducts = new ArrayList<>();
    for (SupplyItem supplyItem : supplyItems.values()) {
      if (supplyItem.name().toLowerCase().contains(query.toLowerCase())) {
        filteredProducts.add(supplyItem);
      }
    }
    return filteredProducts;
  }

  /**
   * This method will grab the given name, the current list's uui and items and try to create it or
   * update it.
   *
   * @param name The new name of the list
   */
  private rx.Observable<SuppliesList> createOrUpdateSuppliesList(String name) {
    String uuid = suppliesList == null ? "" : suppliesList.uuid();
    Map<String, SupplyItem> items = suppliesList == null ? new HashMap<>() : suppliesList.items();
    SuppliesList suppliesList = SuppliesList.create(uuid, name, items);
    return createOrUpdateSuppliesListUserCase.createOrUpdate(suppliesList)
        .compose(transformer.applySchedulers());
  }

  private class SuppliesListSubscriber extends Subscriber<SuppliesList> {

    @Override public void onCompleted() {
      progressBarVisibility.set(View.GONE);
    }

    @Override public void onError(Throwable e) {
      progressBarVisibility.set(View.GONE);
      Timber.e(e, "Failed to get the product list");
    }

    @Override public void onNext(SuppliesList suppliesList) {
      SuppliesListViewModelImpl.this.suppliesList = suppliesList;
      suppliesListName.set(suppliesList.name());
      productListIdObservable.set(suppliesList.uuid());

      // Show edit text view if name is empty
      if (StringUtils.isBlank(suppliesList.name())) {
        productListNameMode.set(ClickToEditTextView.Mode.EDIT);
      }

      adapter.setSuppliesList(suppliesList);
      getItemsDatabaseReference.get(suppliesList.uuid())
          .compose(transformer.applySchedulers())
          .subscribe(databaseReference -> {
            databaseReference.addChildEventListener(itemsChildEventListener);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
              @Override public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isEmpty = dataSnapshot.getChildrenCount() == 0;
                progressBarVisibility.set(View.GONE);
                emptyListVisibility.set(isEmpty ? View.VISIBLE : View.GONE);
                listVisibility.set(isEmpty ? View.GONE : View.VISIBLE);
              }

              @Override public void onCancelled(DatabaseError databaseError) {

              }
            });
            SuppliesListViewModelImpl.this.databaseReference = databaseReference;
          }, throwable -> {
            // TODO: show error
            Timber.e(throwable,
                "Failed to get database reference for items in list: " + suppliesList.uuid());
          });
      //, () -> {
      //      progressBarVisibility.set(View.GONE);
      //      if (adapter.getItemCount() == 0) {
      //        emptyListVisibility.set(View.VISIBLE);
      //        listVisibility.set(View.GONE);
      //      }
      //    });
    }
  }

  /**
   * Remove the given item from the supplies list
   *
   * @param supplyItem Item to remove
   */
  private void removeSupplyItem(SupplyItem supplyItem) {
    Map<String, SupplyItem> items = suppliesList.items();
    Map<String, SupplyItem> supplyItems;
    if (items != null) {
      supplyItems = new HashMap<>(items);
    } else {
      supplyItems = new HashMap<>();
    }
    supplyItems.remove(supplyItem.uuid());
    suppliesList = SuppliesList.create(suppliesList.uuid(), suppliesList.name(), supplyItems);
  }

  /**
   * Add the given item from the supplies list
   *
   * @param supplyItem The item to add
   */
  private void addSupplyItem(SupplyItem supplyItem) {
    Map<String, SupplyItem> items = suppliesList.items();
    Map<String, SupplyItem> supplyItems;
    if (items != null) {
      supplyItems = new HashMap<>(items);
    } else {
      supplyItems = new HashMap<>();
    }
    supplyItems.put(supplyItem.uuid(), supplyItem);
    suppliesList = SuppliesList.create(suppliesList.uuid(), suppliesList.name(), supplyItems);
  }

  /**
   * Hide or show the list view depending on the number of items in the adapter
   */
  private void toggleListViewVisibility() {
    boolean hasItems = adapter.getItemCount() > 0;
    emptyListVisibility.set(hasItems ? View.GONE : View.VISIBLE);
    listVisibility.set(hasItems ? View.VISIBLE : View.GONE);
  }

  @AutoValue static abstract class SuppliesListAndItem {
    public abstract SuppliesList suppliesList();

    public abstract SupplyItem supplyItem();

    public static SuppliesListAndItem create(SuppliesList suppliesList, SupplyItem supplyItem) {
      return new AutoValue_SuppliesListViewModelImpl_SuppliesListAndItem(suppliesList, supplyItem);
    }
  }
}
