package com.fred.inventory.presentation.items.viewmodels;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.SpinnerAdapter;
import com.fred.inventory.R;
import com.fred.inventory.data.firebase.models.SupplyItem;
import com.fred.inventory.domain.usecases.CreateOrUpdateSupplyItemUseCase;
import com.fred.inventory.domain.usecases.GetSupplyItemUseCase;
import com.fred.inventory.utils.StringUtils;
import com.fred.inventory.utils.binding.widgets.OneTimeTextWatcher;
import com.fred.inventory.utils.path.PathManager;
import com.fred.inventory.utils.rx.RxSubscriptionPool;
import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

public class SupplyItemViewModelImpl implements SupplyItemViewModel {
  private final ObservableField<Date> expirationDate = new ObservableField<>();
  private final ObservableField<String> itemName = new ObservableField<>();
  private final ObservableField<String> itemNameError = new ObservableField<>();
  private final ObservableInt seekBarVisibility = new ObservableInt(View.GONE);
  private final TextWatcher itemNameTextWatcher = new OneTimeTextWatcher(itemNameObservable());

  private final Context context;
  private final GetSupplyItemUseCase getSupplyItemUseCase;
  private final CreateOrUpdateSupplyItemUseCase createOrUpdateSupplyItemUseCase;
  private final SchedulerTransformer transformer;
  private final RxSubscriptionPool rxSubscriptionPool;
  private final DatePickerDialog.OnDateSetListener dateSetListener =
      (view, year, monthOfYear, dayOfMonth) -> {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, monthOfYear, dayOfMonth);
        expirationDate.set(calendar.getTime());
      };
  private final PathManager pathManager;
  private final ArrayAdapter<CharSequence> itemSpinnerAdapter;
  private final AdapterView.OnItemSelectedListener spinnerOnItemSelectedListener =
      new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
          // Selected item(s)
          if (position == 0) {
            seekBarVisibility.set(View.GONE);
            isUnit = true;
          } else {
            seekBarVisibility.set(View.VISIBLE);
            isUnit = false;
          }
        }

        @Override public void onNothingSelected(AdapterView<?> adapterView) {
          seekBarVisibility.set(View.GONE);
        }
      };
  private final SeekBar.OnSeekBarChangeListener seekBarChangeListener =
      new SeekBar.OnSeekBarChangeListener() {
        @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
          if (progress == seekBarProgress.get()) return;
          seekBarProgress.set(progress);
        }

        @Override public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override public void onStopTrackingTouch(SeekBar seekBar) {

        }
      };
  private final ObservableField<String> itemQuantityLabel = new ObservableField<>();
  private final ObservableInt seekBarProgress = new ObservableInt(0);
  private final ObservableInt spinnerSelection = new ObservableInt(0);
  private final TextWatcher itemQuantityLabelTextWatcher =
      new OneTimeTextWatcher(itemQuantityLabel);

  private String supplyListUuid;
  private String supplyItemId;
  private boolean isUnit;
  private SupplyItem supplyItem;

  @Inject public SupplyItemViewModelImpl(Context context, GetSupplyItemUseCase getSupplyItemUseCase,
      CreateOrUpdateSupplyItemUseCase createOrUpdateSupplyItemUseCase,
      @IOToUiSchedulerTransformer SchedulerTransformer transformer,
      RxSubscriptionPool rxSubscriptionPool, PathManager pathManager) {
    this.context = context;
    this.getSupplyItemUseCase = getSupplyItemUseCase;
    this.createOrUpdateSupplyItemUseCase = createOrUpdateSupplyItemUseCase;
    this.transformer = transformer;
    this.rxSubscriptionPool = rxSubscriptionPool;
    this.pathManager = pathManager;
    this.itemSpinnerAdapter = ArrayAdapter.createFromResource(context, R.array.item_types,
        android.R.layout.simple_spinner_item);
    itemSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
  }

  @Override public void onResume() {
    if (supplyListUuid != null && supplyItemId != null) {
      Subscription subscription = getSupplyItemUseCase.get(supplyListUuid, supplyItemId)
          .compose(transformer.applySchedulers())
          .subscribe(new SupplyItemSubscriber());

      rxSubscriptionPool.addSubscription(getClass().getCanonicalName(), subscription);
    }
  }

  @Override public void onPause() {
    rxSubscriptionPool.unsubscribeFrom(getClass().getCanonicalName());
  }

  @Override public void forProductList(String suppliesListId) {
    this.supplyListUuid = suppliesListId;
  }

  @Override public void forProduct(String supplyItemId) {
    this.supplyItemId = supplyItemId;
  }

  @Override public ObservableInt spinnerSelection() {
    return spinnerSelection;
  }

  @Override public void onEditExpireDateButtonClick(View view) {
    Calendar calendar = Calendar.getInstance();
    if (expirationDate.get() != null) {
      calendar.clear();
      calendar.setTime(expirationDate.get());
    }
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    new DatePickerDialog(context, dateSetListener, year, month, dayOfMonth).show();
  }

  @Override public ObservableField<Date> expirationDateObservable() {
    return expirationDate;
  }

  @Override public ObservableField<String> itemNameObservable() {
    return itemName;
  }

  @Override public void onDoneButtonClick(View view) {
    if (!checkInput()) return;

    SupplyItem supplyItem = createFromInput();

    createOrUpdateSupplyItemUseCase.createOrUpdate(supplyListUuid, supplyItem)
        .compose(transformer.applySchedulers())
        .subscribe(value -> pathManager.back(), e -> Timber.e(e, "Failed to save product in db"));
  }

  @Override public ObservableField<String> itemNameError() {
    return itemNameError;
  }

  @Override public TextWatcher itemNameTextWatcher() {
    return itemNameTextWatcher;
  }

  @Override public ObservableInt seekBarVisibility() {
    return seekBarVisibility;
  }

  @Override public SpinnerAdapter itemSpinnerAdapter() {
    return itemSpinnerAdapter;
  }

  @Override public AdapterView.OnItemSelectedListener spinnerOnItemSelectedListener() {
    return spinnerOnItemSelectedListener;
  }

  @Override public TextWatcher itemQuantityLabelTextWatcher() {
    return itemQuantityLabelTextWatcher;
  }

  @Override public ObservableField<String> itemQuantityLabel() {
    return itemQuantityLabel;
  }

  @Override public ObservableInt seekBarProgress() {
    return seekBarProgress;
  }

  @Override public SeekBar.OnSeekBarChangeListener seekBarChangeListener() {
    return seekBarChangeListener;
  }

  private boolean checkInput() {
    boolean valid = true;
    if (StringUtils.isBlank(itemName.get())) {
      itemNameError.set(context.getString(R.string.mandatory_field));
      valid = false;
    }
    return valid;
  }

  private SupplyItem createFromInput() {
    String uuid = supplyItem == null ? "" : supplyItem.uuid();
    String barcode = supplyItem == null ? "" : supplyItem.barcode();
    return SupplyItem.create(uuid, itemName.get(), seekBarProgress.get(), isUnit, barcode,
        itemQuantityLabel.get(), expirationDate.get());
  }

  private class SupplyItemSubscriber extends Subscriber<SupplyItem> {
    @Override public void onCompleted() {
    }

    @Override public void onError(Throwable e) {
      Timber.e(e, "Failed to retrieve product list from local storage");
      // TODO: make view show something
    }

    @Override public void onNext(SupplyItem supplyItem) {
      SupplyItemViewModelImpl.this.supplyItem = supplyItem;
      itemName.set(supplyItem.name());
      expirationDate.set(supplyItem.expirationDate());
      itemQuantityLabel.set(supplyItem.quantityLabel());
      seekBarProgress.set(supplyItem.quantity());
      isUnit = supplyItem.unit();
      if (isUnit) {
        seekBarVisibility.set(View.GONE);
        spinnerSelection.set(0);
      } else {
        seekBarVisibility.set(View.VISIBLE);
        spinnerSelection.set(1);
      }
    }
  }
}
