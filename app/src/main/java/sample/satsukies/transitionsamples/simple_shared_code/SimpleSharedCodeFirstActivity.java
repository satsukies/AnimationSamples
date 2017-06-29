package sample.satsukies.transitionsamples.simple_shared_code;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import sample.satsukies.transitionsamples.R;
import sample.satsukies.transitionsamples.databinding.ActivitySimpleSharedCodeFirstBinding;

public class SimpleSharedCodeFirstActivity extends AppCompatActivity {

  ActivitySimpleSharedCodeFirstBinding binding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_simple_shared_code_first);

    //add transition name (= android:transitionName)
    binding.image.setTransitionName("image_code");

    binding.buttonGo.setOnClickListener(v -> {
      ActivityOptionsCompat compat =
          ActivityOptionsCompat.makeSceneTransitionAnimation(this, binding.image,
              binding.image.getTransitionName());

      startActivity(new Intent(this, SimpleSharedCodeSecondActivity.class), compat.toBundle());
    });
  }
}
