package sample.satsukies.transitionsamples.simple_shared;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import sample.satsukies.transitionsamples.R;
import sample.satsukies.transitionsamples.databinding.ActivitySimpleSharedSecondBinding;

public class SimpleSharedSecondActivity extends AppCompatActivity {

  ActivitySimpleSharedSecondBinding binding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_simple_shared_second);
  }
}
