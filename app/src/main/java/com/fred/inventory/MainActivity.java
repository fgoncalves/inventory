package com.fred.inventory;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.presentation.globalsearch.GlobalSearchScreen;
import com.fred.inventory.presentation.listofproducts.ListOfProductListsScreen;
import com.fred.inventory.utils.path.PathManager;
import dagger.ObjectGraph;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
  @Inject PathManager pathManager;

  private static ObjectGraph objectGraph;
  private NavigationView navigationView;
  private DrawerLayout drawerLayout;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    objectGraph = ObjectGraph.create(new RootModule(this));

    setContentView(R.layout.main_activity);
    inject();
    setStatusBarColor();

    if (savedInstanceState != null) {
      pathManager.restorePath(savedInstanceState, R.id.main_container);
      return;
    }

    navigationView = (NavigationView) findViewById(R.id.drawer);
    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

    setupNavigationView();
    addListOfProductListsScreen();
  }

  private void setupNavigationView() {
    navigationView.setNavigationItemSelectedListener(item -> {
      drawerLayout.closeDrawer(GravityCompat.START);
      if (item.getItemId() == R.id.drawer_search) {
        pathManager.go(GlobalSearchScreen.newInstance(), R.id.main_container);
        return true;
      }
      return false;
    });
  }

  /**
   * Set the status bar color to another one. Method only works above api 21
   */
  private void setStatusBarColor() {
    Window window = getWindow();
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
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

    finish();
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    pathManager.savePath(outState);
  }

  private void addListOfProductListsScreen() {
    final ListOfProductListsScreen screen = ListOfProductListsScreen.newInstance();
    pathManager.single(screen, R.id.main_container);
  }

  private void inject() {
    scoped(new MainActivityModule()).inject(this);
  }
}
