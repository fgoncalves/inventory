package com.fred.inventory;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.fred.inventory.databinding.NavHeaderMainBinding;
import com.fred.inventory.presentation.base.BaseScreen;
import com.fred.inventory.presentation.globalsearch.GlobalSearchScreen;
import com.fred.inventory.presentation.login.LoginScreen;
import com.fred.inventory.presentation.navbar.NavBarHeaderViewModel;
import com.fred.inventory.utils.path.PathManager;
import com.google.firebase.auth.FirebaseAuth;
import dagger.ObjectGraph;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
  @Inject PathManager pathManager;
  @Inject NavBarHeaderViewModel navBarHeaderViewModel;
  @Inject FirebaseAuth auth;

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
    addLoginScreen();
  }

  @Override protected void onStart() {
    super.onStart();
    navBarHeaderViewModel.onStart();
  }

  @Override protected void onStop() {
    super.onStop();
    navBarHeaderViewModel.onStop();
  }

  private void setupNavigationView() {
    NavHeaderMainBinding navHeaderMainBinding =
        DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.nav_header_main, navigationView,
            false);
    navHeaderMainBinding.setViewModel(navBarHeaderViewModel);
    navigationView.addHeaderView(navHeaderMainBinding.getRoot());
    navigationView.setNavigationItemSelectedListener(item -> {
      drawerLayout.closeDrawer(GravityCompat.START);
      item.setChecked(false);
      if (item.getItemId() == R.id.drawer_search) {
        pathManager.go(GlobalSearchScreen.newInstance(), R.id.main_container);
        return true;
      }
      if (item.getItemId() == R.id.drawer_logout) {
        auth.signOut();
        pathManager.single(LoginScreen.newInstance(), R.id.main_container);
        return true;
      }
      return false;
    });
    // Set version name in the menu dingy
    ((TextView) navigationView.findViewById(R.id.drawer_version_name)).setText(
        BuildConfig.VERSION_NAME);
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

  public DrawerLayout getDrawerLayout() {
    return drawerLayout;
  }

  private void addLoginScreen() {
    final LoginScreen screen = LoginScreen.newInstance();
    pathManager.single(screen, R.id.main_container);
  }

  private void inject() {
    scoped(new MainActivityModule()).inject(this);
  }
}
