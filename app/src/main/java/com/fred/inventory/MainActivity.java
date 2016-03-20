package com.fred.inventory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.fred.inventory.presentation.productlist.ListOfProductListsScreen;
import com.fred.inventory.utils.path.PathManager;
import dagger.ObjectGraph;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
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

  private void addListOfProductListsScreen() {
    pathManager.single(ListOfProductListsScreen.newInstance(), R.id.main_container);
  }

  private void inject() {
    scoped(new MainActivityModule()).inject(this);
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
}
