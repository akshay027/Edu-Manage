package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminCalender.ExamSchedule;


import java.util.ArrayList;

/**
 * Created by maxlore on 3/28/2017.
 */

public class AdminCalenderExamScheduleAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<ExamSchedule> arrayList;

    public AdminCalenderExamScheduleAdapter(Activity activity, ArrayList<ExamSchedule> arrayList) {
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

            convertView = inflater.inflate(R.layout.list_item_admin_calender_exam_schedule, null);
            holder = new ViewHolder();
            holder.tvclassname = (TextView) convertView.findViewById(R.id.tvclassname);
            holder.tvSubjectname = (TextView) convertView.findViewById(R.id.tvSubjectname);
            holder.tvtiming = (TextView) convertView.findViewById(R.id.tvtiming);

            // holder.tvAssAssignment = (TextView) convertView.findViewById(R.id.tvAssAssignment);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.tvclassname.setText(arrayList.get(position).getClassName());
        holder.tvSubjectname.setText(arrayList.get(position).getSubject());
        holder.tvtiming.setText(arrayList.get(position).getTiming());


        return convertView;
    }

    static class ViewHolder {
        TextView tvclassname, tvSubjectname, tvtiming, tvAssSubject, tvAssAssignment;
    }
}
