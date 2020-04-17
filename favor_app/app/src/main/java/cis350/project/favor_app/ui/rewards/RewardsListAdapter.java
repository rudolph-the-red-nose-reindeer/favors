package cis350.project.favor_app.ui.rewards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cis350.project.favor_app.R;
import cis350.project.favor_app.data.model.Reward;
import cis350.project.favor_app.util.ImageUtil;

public class RewardsListAdapter extends BaseAdapter {

    private List<Reward> list;
    private LayoutInflater inflater;

    public RewardsListAdapter(Context context, List<Reward> list) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Reward getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Reward item = getItem(i);
        View listRow = inflater.inflate(R.layout.reward_list_row, null, false);
        TextView nameView = listRow.findViewById(R.id.reward_list_name);
        ImageView photoView = listRow.findViewById(R.id.reward_list_image);
        TextView descriptionView = listRow.findViewById(R.id.reward_list_description);
        TextView pointsView = listRow.findViewById(R.id.reward_list_points);

        nameView.setText(item.getName());
        if (!item.getPhoto().equals("")) {
            photoView.setImageBitmap(ImageUtil.decodeBase64(item.getPhoto()));
        }
        descriptionView.setText(item.getDescription());
        pointsView.setText(String.valueOf(item.getPoints()));

        return listRow;
    }
}
