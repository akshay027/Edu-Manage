package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminCalender.CalenderHoliday;


import java.util.ArrayList;

/**
 * Created by maxlore on 3/28/2017.
 */

public class AdminCalenderHolidayAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<CalenderHoliday> arrayList;

    public AdminCalenderHolidayAdapter(Activity activity, ArrayList<CalenderHoliday> arrayList) {
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

            convertView = inflater.inflate(R.layout.list_item_admin_calender_holiday, null);
            holder = new ViewHolder();
            holder.tvtype = (TextView) convertView.findViewById(R.id.tvtype);
            holder.tvReason = (TextView) convertView.findViewById(R.id.tvReason);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.tvtype.setText(arrayList.get(position).getHolidayType());
        holder.tvReason.setText(arrayList.get(position).getReason());


        return convertView;
    }

    static class ViewHolder {
        TextView tvtype, tvReason;
    }
}

