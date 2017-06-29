package sample.satsukies.transitionsamples;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import sample.satsukies.transitionsamples.databinding.ActivityMainBinding;
import sample.satsukies.transitionsamples.simple.SimpleFirstActivity;
import sample.satsukies.transitionsamples.simple_shared.SimpleSharedFirstActivity;
import sample.satsukies.transitionsamples.simple_shared_code.SimpleSharedCodeFirstActivity;
import sample.satsukies.transitionsamples.simple_shared_exclude.ExcludeSharedFirstActivity;

public class MainActivity extends AppCompatActivity {

  ActivityMainBinding binding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    binding.buttonSimpleTransition.setOnClickListener(
        v -> startActivity(new Intent(this, SimpleFirstActivity.class)));

    binding.buttonSimpleShared.setOnClickListener(
        v -> startActivity(new Intent(this, SimpleSharedFirstActivity.class)));

    binding.buttonSimpleSharedCode.setOnClickListener(
        v -> startActivity(new Intent(this, SimpleSharedCodeFirstActivity.class)));

    binding.buttonExcludeShared.setOnClickListener(
        v -> startActivity(new Intent(this, ExcludeSharedFirstActivity.class)));
  }
}
