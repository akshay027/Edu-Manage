package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.maxlore.edumanage.Models.TeacherModels.Leaves.LeaveCategory;
import com.maxlore.edumanage.R;

import java.util.ArrayList;

/**
 * Created by Nikhil on 05-06-2016.
 */
public class LeaveTypeAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<LeaveCategory> arrayList;

    public LeaveTypeAdapter(Activity activity, ArrayList<LeaveCategory> arrayList) {
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
            convertView = inflater.inflate(R.layout.list_item_leave_cart, null);
            holder = new ViewHolder();
            holder.tvCode = (TextView) convertView.findViewById(R.id.tvCode);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvMaxLeave = (TextView) convertView.findViewById(R.id.tvMaxLeave);
            holder.tvTaken = (TextView) convertView.findViewById(R.id.tvTaken);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvCode.setText(arrayList.get(position).getCode());
        holder.tvName.setText(arrayList.get(position).getName());
        holder.tvMaxLeave.setText(arrayList.get(position).getMaxLeave());
        holder.tvTaken.setText(arrayList.get(position).getAvailableLeave());

        return convertView;
    }

    static class ViewHolder {
        TextView tvCode, tvName, tvMaxLeave, tvTaken;
    }
}
