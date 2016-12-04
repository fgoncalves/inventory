package com.fred.inventory.presentation.listofproducts;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.databinding.ListOfProductsBinding;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.presentation.listofproducts.modules.ListOfProductListsModule;
import com.fred.inventory.presentation.listofproducts.viewmodels.ListOfProductListsViewModel;
import javax.inject.Inject;
import rx.Observable;
import rx.subjects.PublishSubject;

public class ListOfProductListsScreen extends BaseScreen {
  @Inject ListOfProductListsViewModel viewModel;

  private PublishSubject<ScreenEvent> events = PublishSubject.create();

  public static ListOfProductListsScreen newInstance() {
    return new ListOfProductListsScreen();
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ListOfProductsBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.list_of_products, container, false);

    MainActivity.scoped(new ListOfProductListsModule()).inject(this);
    binding.setViewModel(viewModel);

    return binding.getRoot();
  }

  //private void setEventSubscribers(ListOfProductListsViewImpl listOfProductListsView) {
  //  listOfProductListsView.interactions()
  //      .map(new Func1<ListOfProductListsView.ListOfProductListsEvent, ScreenEvent>() {
  //        @Override public ScreenEvent call(
  //            ListOfProductListsView.ListOfProductListsEvent listOfProductListsEvent) {
  //          if (listOfProductListsEvent
  //              == ListOfProductListsView.ListOfProductListsEvent.ADD_BUTTON_CLICKED) {
  //            return new ScreenEvent(ScreenEvent.Type.ADD_PRODUCT_LIST_SCREEN);
  //          }
  //          return new ScreenEvent(ScreenEvent.Type.NOOP);
  //        }
  //      })
  //      .subscribeOn(Schedulers.computation())
  //      .observeOn(AndroidSchedulers.mainThread())
  //      .subscribe(new Subscriber<ScreenEvent>() {
  //        @Override public void onCompleted() {
  //
  //        }
  //
  //        @Override public void onError(Throwable e) {
  //          Timber.e(e, "Failed to get events from screen");
  //        }
  //
  //        @Override public void onNext(ScreenEvent screenEvent) {
  //          events.onNext(screenEvent);
  //        }
  //      });
  //}

  @Override protected boolean handleBackPress() {
    return false;
  }

  @Override public Observable<ScreenEvent> screenEvents() {
    return events.asObservable();
  }
}
