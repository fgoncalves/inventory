package com.fred.inventory;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fred.inventory.data.datakick.models.Item;
import com.fred.inventory.data.datakick.services.ItemService;
import dagger.ObjectGraph;
import javax.inject.Inject;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
  @Bind(R.id.productId) EditText productId;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.fab) FloatingActionButton fab;
  @Inject ItemService itemService;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);

    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show();
      }
    });

    ObjectGraph.create(new RootModule()).plus(new ExperimentModule()).inject(this);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @OnClick(R.id.fetchDetailsButton) public void fetchProductData() {
    String text = productId.getText().toString().trim();
    if (text.isEmpty()) return;

    itemService.get(text)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Subscriber<Item>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {
            Toast.makeText(MainActivity.this, "Not found", Toast.LENGTH_SHORT);
          }

          @Override public void onNext(Item item) {
            Toast.makeText(MainActivity.this, "FOUND!!!", Toast.LENGTH_SHORT);
          }
        });
  }
}
