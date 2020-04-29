package cis350.project.favor_app.ui.favorFeed;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cis350.project.favor_app.R;
import cis350.project.favor_app.data.model.Favor;

public class CustomListAdapter extends BaseAdapter {
    private ArrayList<Favor> listData;
    private LayoutInflater layoutInflater;
    public CustomListAdapter(Context aContext, List<Favor> listData) {
        this.listData = new ArrayList<Favor>(listData);
        Log.d("list", listData.toString());
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View v, ViewGroup vg) {
        ViewHolder holder;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.favor_feed_list_row, null);
            holder = new ViewHolder();
            holder.uUsername = (TextView) v.findViewById(R.id.favor_feed_username);
            holder.uDetails = (TextView) v.findViewById(R.id.favor_feed_details);
            holder.uUrgency = (TextView) v.findViewById(R.id.favor_feed_urgency);
            holder.uDate = (TextView) v.findViewById(R.id.favor_feed_date);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.uUsername.setText(listData.get(position).getUsername());
        holder.uDetails.setText(listData.get(position).getDetails());
        holder.uUrgency.setText(String.valueOf(listData.get(position).getUrgency()));
        holder.uDate.setText(listData.get(position).getDate());
        return v;
    }
    static class ViewHolder {
        TextView uUsername;
        TextView uDetails;
        TextView uUrgency;
        TextView uDate;
    }
}