package sample.satsukies.transitionsamples.simple_shared_exclude;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import sample.satsukies.transitionsamples.R;
import sample.satsukies.transitionsamples.databinding.ActivityExcludeSharedSecondBinding;

public class ExcludeSharedSecondActivity extends AppCompatActivity {

  ActivityExcludeSharedSecondBinding binding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_exclude_shared_second);
  }
}
