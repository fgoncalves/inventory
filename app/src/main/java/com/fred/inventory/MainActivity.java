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

public class MainActivity extends AppCompatActivity {
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.listOfProducts) ListOfProductListsViewImpl listOfProductListsView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.list_of_products);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    setClickListeners();
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
