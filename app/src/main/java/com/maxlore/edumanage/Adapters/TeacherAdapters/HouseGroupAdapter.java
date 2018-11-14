package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.maxlore.edumanage.Models.TeacherModels.HouseGroup;
import com.maxlore.edumanage.R;

import java.util.ArrayList;

/**
 * Created by Nikhil on 05-06-2016.
 */
public class HouseGroupAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<HouseGroup> arrayList;

    public HouseGroupAdapter(Activity activity, ArrayList<HouseGroup> arrayList) {
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

            convertView = inflater.inflate(R.layout.list_item_house_group, null);
            holder = new ViewHolder();
            holder.tvHG = (TextView) convertView.findViewById(R.id.tvHG);
            holder.tvHGPoint = (TextView) convertView.findViewById(R.id.tvHGPoint);
            holder.tvHGStandard = (TextView) convertView.findViewById(R.id.tvHGStandard);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.tvHG.setText("" + arrayList.get(position).getName());
        holder.tvHGPoint.setText("" + arrayList.get(position).getPoints());
        holder.tvHGStandard.setText("" + arrayList.get(position).getStudentCount());

        return convertView;
    }

    static class ViewHolder {
        TextView tvHG, tvHGPoint, tvHGStandard;
    }
}
