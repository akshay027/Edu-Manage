package com.maxlore.edumanage.Adapters.ParentAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maxlore.edumanage.Models.ParentModels.attendance.MonthYearList;
import com.maxlore.edumanage.R;

import java.util.ArrayList;

/**
 * Created by Nikhil on 9/11/2016.
 */
public class MonthYearAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<MonthYearList> arrayList;

    public MonthYearAdapter(Activity activity, ArrayList<MonthYearList> arrayList) {
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
            convertView = inflater.inflate(R.layout.list_item_month_year, null);
            holder = new ViewHolder();
            holder.tvMonthYear = (TextView) convertView.findViewById(R.id.tvMonthYear);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvMonthYear.setText("" + arrayList.get(position).getMonthName() + "-" + arrayList.get(position).getYearShort());
        if (arrayList.get(position).isSelected()) {
            holder.tvMonthYear.setTextColor(activity.getResources().getColor(R.color.white));
            holder.tvMonthYear.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.tvMonthYear.setTextColor(activity.getResources().getColor(R.color.black));
            holder.tvMonthYear.setBackgroundColor(activity.getResources().getColor(R.color.white));
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tvMonthYear;
    }
}