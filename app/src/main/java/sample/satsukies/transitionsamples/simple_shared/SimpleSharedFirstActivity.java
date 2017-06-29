package sample.satsukies.transitionsamples.simple_shared;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import sample.satsukies.transitionsamples.R;
import sample.satsukies.transitionsamples.databinding.ActivitySimpleSharedFirstBinding;

public class SimpleSharedFirstActivity extends AppCompatActivity {

  ActivitySimpleSharedFirstBinding binding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_simple_shared_first);

    binding.buttonGo.setOnClickListener(v -> {
      ActivityOptionsCompat compat =
          ActivityOptionsCompat.makeSceneTransitionAnimation(SimpleSharedFirstActivity.this,
              binding.image, binding.image.getTransitionName());

      startActivity(new Intent(this, SimpleSharedSecondActivity.class), compat.toBundle());
    });
  }
}
