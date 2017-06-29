package sample.satsukies.transitionsamples.custom_shared;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.List;
import sample.satsukies.transitionsamples.R;
import sample.satsukies.transitionsamples.databinding.ItemContentBinding;

/**
 * Created by a14745 on 2017/06/29.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

  private AppCompatActivity context;
  private List<String> list;
  private int parentPosition;
  private MyViewHolder.Listener listener;

  private static final int LAYOUT_ID = R.layout.item_content;

  public static class MyViewHolder extends RecyclerView.ViewHolder {

    ItemContentBinding binding;
    Listener listener;

    public interface Listener {
      public void onClickItem(View v);
    }

    public MyViewHolder(View itemView) {
      super(itemView);

      binding = DataBindingUtil.bind(itemView);
    }

    public MyViewHolder(View itemView, Listener listener) {
      super(itemView);

      this.listener = listener;

      //binding
      binding = DataBindingUtil.bind(itemView);
    }

    public ItemContentBinding getBinding() {
      return binding;
    }
  }

  public RecyclerAdapter(AppCompatActivity context, int position, List<String> list,
      MyViewHolder.Listener listener) {
    this.context = context;
    parentPosition = position;
    this.list = list;
    this.listener = listener;
  }

  @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(LAYOUT_ID, parent, false);

    return new MyViewHolder(view);
  }

  @Override public void onBindViewHolder(final MyViewHolder holder, final int position) {
    holder.getBinding().contentImage.setOnClickListener(v -> {
      Toast.makeText(context, "toast:" + parentPosition + "-" + position, Toast.LENGTH_SHORT)
          .show();

      listener.onClickItem(holder.binding.contentImage);
    });
  }

  @Override public int getItemCount() {
    return list.size();
  }
}
