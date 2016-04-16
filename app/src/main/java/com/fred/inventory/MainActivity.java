package com.fred.inventory;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import butterknife.ButterKnife;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.presentation.listofproducts.ListOfProductListsScreen;
import com.fred.inventory.presentation.navigation.NavigationListener;
import com.fred.inventory.presentation.productlist.ProductListScreen;
import com.fred.inventory.utils.path.PathManager;
import dagger.ObjectGraph;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements NavigationListener {
  @Inject PathManager pathManager;

  private static ObjectGraph objectGraph;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    objectGraph = ObjectGraph.create(new RootModule(this));

    setContentView(R.layout.main_activity);
    ButterKnife.bind(this);

    inject();
    addListOfProductListsScreen();
    setStatusBarColor();
  }

  /**
   * Set the status bar color to another one. Method only works above api 21
   */
  private void setStatusBarColor() {
    Window window = getWindow();
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }
  }

  /**
   * Get the dependency graph scoped with the provided modules
   *
   * @param modules The modules to add to the graph already created
   * @return The scoped graph
   */
  public static ObjectGraph scoped(Object... modules) {
    return objectGraph.plus(modules);
  }

  @Override public void onBackPressed() {
    BaseScreen topScreen = (BaseScreen) pathManager.top();
    topScreen.onBackPressed();

    if (pathManager.back()) return;

    super.onBackPressed();
  }

  @Override public void onAddProductListButtonClicked() {
    final ProductListScreen screen = ProductListScreen.newInstance();
    screen.addNavigationListener(this);
    screen.lifeCycle()
        .filter(new Func1<BaseScreen.LifeCycle, Boolean>() {
          @Override public Boolean call(BaseScreen.LifeCycle lifeCycle) {
            return lifeCycle == BaseScreen.LifeCycle.ON_RESUME;
          }
        })
        .flatMap(new Func1<BaseScreen.LifeCycle, Observable<BaseScreen.ScreenEvent>>() {
          @Override public Observable<BaseScreen.ScreenEvent> call(BaseScreen.LifeCycle lifeCycle) {
            return screen.screenEvents();
          }
        })
        .observeOn(Schedulers.computation())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<BaseScreen.ScreenEvent>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {

          }

          @Override public void onNext(BaseScreen.ScreenEvent screenEvent) {
            switch (screenEvent) {
              case REMOVE:
                pathManager.back();
            }
          }
        });
    pathManager.go(screen, R.id.main_container);
  }

  private void addListOfProductListsScreen() {
    ListOfProductListsScreen screen = ListOfProductListsScreen.newInstance();
    screen.addNavigationListener(this);
    pathManager.single(screen, R.id.main_container);
  }

  private void inject() {
    scoped(new MainActivityModule()).inject(this);
  }
}
