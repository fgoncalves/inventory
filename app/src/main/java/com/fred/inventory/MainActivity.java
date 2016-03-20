package com.fred.inventory;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fred.inventory.presentation.productlist.ListOfProductListsView;
import com.fred.inventory.presentation.productlist.ListOfProductListsViewImpl;
import dagger.ObjectGraph;

public class MainActivity extends AppCompatActivity {
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.listOfProducts) ListOfProductListsViewImpl listOfProductListsView;

  private static ObjectGraph objectGraph;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    objectGraph = ObjectGraph.create(new RootModule(this));

    setContentView(R.layout.list_of_products);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    setClickListeners();
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

  private void setClickListeners() {
    listOfProductListsView.addListOfProductListsClickListener(
        new ListOfProductListsView.ListOfProductListsClickListener() {
          @Override public void onAddButtonClicked() {
            Toast.makeText(getContext(), "This theory works fine", Toast.LENGTH_SHORT).show();
          }
        });
  }

  protected Context getContext() {
    return this;
  }
}
