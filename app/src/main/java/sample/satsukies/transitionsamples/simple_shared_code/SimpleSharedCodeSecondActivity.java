package sample.satsukies.transitionsamples.simple_shared_code;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import sample.satsukies.transitionsamples.R;
import sample.satsukies.transitionsamples.databinding.ActivitySimpleSharedCodeSecondBinding;

public class SimpleSharedCodeSecondActivity extends AppCompatActivity {

  ActivitySimpleSharedCodeSecondBinding binding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_simple_shared_code_second);

    //set transition name
    binding.image.setTransitionName("image_code");
  }
}
