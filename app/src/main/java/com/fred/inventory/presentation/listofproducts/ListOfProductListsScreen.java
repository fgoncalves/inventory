package com.fred.inventory.presentation.listofproducts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fred.inventory.R;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.presentation.listofproducts.views.ListOfProductListsView;
import com.fred.inventory.presentation.listofproducts.views.ListOfProductListsViewImpl;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import timber.log.Timber;

public class ListOfProductListsScreen extends BaseScreen {
  private PublishSubject<ScreenEvent> events = PublishSubject.create();

  public static ListOfProductListsScreen newInstance() {
    return new ListOfProductListsScreen();
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ListOfProductListsViewImpl listOfProductListsView =
        (ListOfProductListsViewImpl) inflater.inflate(R.layout.list_of_products, null);
    setEventSubscribers(listOfProductListsView);
    return listOfProductListsView;
  }

  private void setEventSubscribers(ListOfProductListsViewImpl listOfProductListsView) {
    listOfProductListsView.interactions()
        .map(new Func1<ListOfProductListsView.ListOfProductListsEvent, ScreenEvent>() {
          @Override public ScreenEvent call(
              ListOfProductListsView.ListOfProductListsEvent listOfProductListsEvent) {
            if (listOfProductListsEvent
                == ListOfProductListsView.ListOfProductListsEvent.ADD_BUTTON_CLICKED) {
              return new ScreenEvent(ScreenEvent.Type.ADD_PRODUCT_LIST_SCREEN);
            }
            return new ScreenEvent(ScreenEvent.Type.NOOP);
          }
        })
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<ScreenEvent>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {
            Timber.e(e, "Failed to get events from screen");
          }

          @Override public void onNext(ScreenEvent screenEvent) {
            events.onNext(screenEvent);
          }
        });
  }

  @Override protected boolean handleBackPress() {
    return false;
  }

  @Override public Observable<ScreenEvent> screenEvents() {
    return events.asObservable();
  }
}
