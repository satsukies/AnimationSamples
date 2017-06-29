package sample.satsukies.transitionsamples.simple;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import sample.satsukies.transitionsamples.R;
import sample.satsukies.transitionsamples.databinding.ActivitySimpleFirstBinding;

public class SimpleFirstActivity extends AppCompatActivity {

  ActivitySimpleFirstBinding binding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_simple_first);
  }
}
