package sample.satsukies.transitionsamples.custom_shared;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import sample.satsukies.transitionsamples.R;
import sample.satsukies.transitionsamples.databinding.ActivityCustomSharedFirstBinding;
import sample.satsukies.transitionsamples.databinding.ActivityCustomSharedSecondBinding;

public class CustomSharedSecondActivity extends AppCompatActivity {

  ActivityCustomSharedSecondBinding binding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_custom_shared_second);
  }
}
