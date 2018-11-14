package com.maxlore.edumanage.Activities.TeacherActivities;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.maxlore.edumanage.R;

import java.util.ArrayList;

/**
 * Created by Nikhil on 05-06-2016.
 */
public class TimeTableDateAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<String> arrayList;
    private int position = 1;

    public TimeTableDateAdapter(Activity activity, ArrayList<String> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

            convertView = inflater.inflate(R.layout.list_item_time_table_date, null);
            holder = new ViewHolder();
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.tvDate.setText("" + arrayList.get(position));
        Log.e("postion", "this : " + this.position + " gggg : " + position);

        if (this.position == position) {
            holder.tvDate.setBackgroundColor(activity.getResources().getColor(R.color.headingcolor));
        } else {
            holder.tvDate.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tvDate;
    }
}
