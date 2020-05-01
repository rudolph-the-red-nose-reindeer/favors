package cis350.project.favor_app.ui.favorSearch;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cis350.project.favor_app.R;
import cis350.project.favor_app.data.model.Favor;

public class CustomListAdapterFavorSearch extends BaseAdapter {
    private List<Favor> listData;
    private LayoutInflater layoutInflater;
    public CustomListAdapterFavorSearch(Context aContext, List<Favor> listData) {
        this.listData = listData;
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
    @Override
    public View getView(int position, View v, ViewGroup vg) {
        ViewHolder holder;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.activity_favor_filter_row, null);
            holder = new ViewHolder();
            holder.uUsername = (TextView) v.findViewById(R.id.favor_filter_Search_username_tv);
            holder.uDetails = (TextView) v.findViewById(R.id.favor_filter_Search_detailes_tv);
            holder.uUrgency = (TextView) v.findViewById(R.id.favor_filter_Search_urgency_tv);
            holder.uDate = (TextView) v.findViewById(R.id.favor_filter_Search_date_tv);
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