package sample.satsukies.transitionsamples.custom_shared;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.util.Arrays;
import java.util.List;
import sample.satsukies.transitionsamples.R;
import sample.satsukies.transitionsamples.databinding.ActivityCustomSharedFirstBinding;

public class CustomSharedFirstActivity extends AppCompatActivity {

  MyListAdapter adapter;
  List<String> arrayList = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

  ActivityCustomSharedFirstBinding binding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_custom_shared_first);

    adapter = new MyListAdapter(this, arrayList, v -> {
      ActivityOptionsCompat optionsCompat =
          ActivityOptionsCompat.makeSceneTransitionAnimation(CustomSharedFirstActivity.this,
              new Pair<>(v, v.getTransitionName()));

      Intent intent =
          new Intent(CustomSharedFirstActivity.this, CustomSharedSecondActivity.class);
      startActivity(intent, optionsCompat.toBundle());
    });

    binding.listView.setAdapter(adapter);
  }
}
