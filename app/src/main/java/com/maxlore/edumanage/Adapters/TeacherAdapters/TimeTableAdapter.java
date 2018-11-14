package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Models.TeacherModels.DashboardTimeTable;


import java.util.ArrayList;

/**
 * Created by Nikhil on 05-06-2016.
 */
public class TimeTableAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<DashboardTimeTable> arrayList;

    public TimeTableAdapter(Activity activity, ArrayList<DashboardTimeTable> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return arrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.list_item_time_table, null);
            holder = new ViewHolder();
            holder.tvTTStandard = (TextView) convertView.findViewById(R.id.tvTTStandard);
            holder.tvTTPeriod = (TextView) convertView.findViewById(R.id.tvTTPeriod);
            holder.tvTTSubject = (TextView) convertView.findViewById(R.id.tvTTSubject);
            holder.tvTTtiming = (TextView) convertView.findViewById(R.id.tvTTtiming);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.tvTTStandard.setText("" + arrayList.get(position).getClassName());
        holder.tvTTPeriod.setText("" + arrayList.get(position).getPeriod());
        holder.tvTTSubject.setText("" + arrayList.get(position).getSubject());
        holder.tvTTtiming.setText("" + arrayList.get(position).getTiming());
        return convertView;
    }

    static class ViewHolder {
        TextView tvTTStandard, tvTTPeriod, tvTTSubject,tvTTtiming;
    }
}
