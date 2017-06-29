package sample.satsukies.transitionsamples.simple_shared_exclude;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import sample.satsukies.transitionsamples.R;
import sample.satsukies.transitionsamples.databinding.ActivityExcludeSharedFirstBinding;

public class ExcludeSharedFirstActivity extends AppCompatActivity {

  ActivityExcludeSharedFirstBinding binding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_exclude_shared_first);

    binding.buttonGo.setOnClickListener(v -> {
      ActivityOptionsCompat compat =
          ActivityOptionsCompat.makeSceneTransitionAnimation(this, binding.image,
              binding.image.getTransitionName());

      startActivity(new Intent(this, ExcludeSharedSecondActivity.class), compat.toBundle());
    });
  }
}
