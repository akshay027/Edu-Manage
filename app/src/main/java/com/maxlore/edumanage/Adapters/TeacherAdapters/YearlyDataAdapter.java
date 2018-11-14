package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Models.TeacherModels.Academics.YearSessionPlan;


import java.util.ArrayList;

/**
 * Created by maxlore on 10/21/2016.
 */

public class YearlyDataAdapter extends BaseAdapter {


    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<YearSessionPlan> arrayList;

    public YearlyDataAdapter(Activity activity, ArrayList<YearSessionPlan> arrayList) {
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

            convertView = inflater.inflate(R.layout.list_item_academics_details_for_year, null);
            holder = new ViewHolder();
            holder.tvMonthname = (TextView) convertView.findViewById(R.id.tvmonthname);
            holder.tvTopicname = (TextView) convertView.findViewById(R.id.tvtopicname);
               /* holder.tvTopicno = (TextView) convertView.findViewById(R.id.tvtopicno);*/


            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.tvMonthname.setText("" + arrayList.get(position).getMonthName());
        holder.tvTopicname.setText("" + arrayList.get(position).getTopicName());
         /*   holder.tvTopicno.setText("" + arrayList.get(position).getTopicNo());*/


        return convertView;
    }

    static class ViewHolder {
        TextView tvMonthname, tvTopicname, tvTopicno;


    }

}


