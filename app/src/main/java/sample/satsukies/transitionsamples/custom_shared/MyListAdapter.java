package sample.satsukies.transitionsamples.custom_shared;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.List;
import sample.satsukies.transitionsamples.R;
import sample.satsukies.transitionsamples.databinding.ListItemBinding;

/**
 * Created by a14745 on 2017/06/29.
 */

public class MyListAdapter extends ArrayAdapter<String> {

  private AppCompatActivity context;
  private static final int LAYOUT_ID = R.layout.list_item;
  private List<String> list;
  private RecyclerAdapter adapter;
  private LinearLayoutManager manager;
  private Listener listener;

  public interface Listener {
    void onClickItem(View v);
  }

  public MyListAdapter(@NonNull AppCompatActivity context, @NonNull List<String> obj,
      Listener listener) {
    super(context, LAYOUT_ID, obj);

    this.context = context;
    list = obj;
    this.listener = listener;
  }

  @NonNull @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    //item
    String string = list.get(position);

    ListItemBinding listItemBinding;

    if (convertView == null) {
      listItemBinding =
          DataBindingUtil.inflate(LayoutInflater.from(context), LAYOUT_ID, parent, false);
    } else {
      listItemBinding = DataBindingUtil.getBinding(convertView);
    }

    listItemBinding.listTitle.setText(string + "番目のレイアウトだよ");

    manager = new LinearLayoutManager(context);
    manager.setOrientation(LinearLayoutManager.HORIZONTAL);

    adapter =
        new RecyclerAdapter(context, position, list, v -> listener.onClickItem(v));

    listItemBinding.contentList.setAdapter(adapter);
    listItemBinding.contentList.setLayoutManager(manager);

    return listItemBinding.getRoot();
  }
}
