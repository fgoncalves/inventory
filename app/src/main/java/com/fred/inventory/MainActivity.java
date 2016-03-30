package com.fred.inventory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.presentation.listofproducts.ListOfProductListsScreen;
import com.fred.inventory.presentation.navigation.NavigationListener;
import com.fred.inventory.presentation.productlist.ProductListScreen;
import com.fred.inventory.utils.path.PathManager;
import dagger.ObjectGraph;
import javax.inject.Inject;

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
    ProductListScreen screen = ProductListScreen.newInstance();
    screen.addNavigationListener(this);
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
