package com.maxlore.edumanage.Adapters.ParentAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxlore.edumanage.Models.ParentModels.attendance.StudentDayAttendanceDetail;
import com.maxlore.edumanage.R;

import java.util.ArrayList;

/**
 * Created by Nikhil on 9/11/2016.
 */
public class AttendanceForMonthAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<StudentDayAttendanceDetail> arrayList;
    String currentdate;

    public AttendanceForMonthAdapter(Activity activity, ArrayList<StudentDayAttendanceDetail> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
        this.currentdate = currentdate;
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

        StudentDayAttendanceDetail detail = arrayList.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_student_attendance, null);
            holder = new ViewHolder();
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvColor = (TextView) convertView.findViewById(R.id.tvColor);
            holder.tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
            holder.llattendance = (LinearLayout) convertView.findViewById(R.id.llattendance);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        switch (detail.getAttendanceStatus()) {
            case 1:
                holder.tvColor.setBackgroundColor(activity.getResources().getColor(R.color.green));
                holder.tvStatus.setText("Present");
                break;
            case 2:
                holder.tvColor.setBackgroundColor(activity.getResources().getColor(R.color.red));
                holder.tvStatus.setText("Absent");
                break;
            case 3:
                holder.tvColor.setBackgroundColor(activity.getResources().getColor(R.color.gray_light));
                holder.tvStatus.setText("Weekend");
                break;
            case 4:
                holder.tvColor.setBackgroundColor(activity.getResources().getColor(R.color.black));
                holder.tvStatus.setText("Not Mark");
                break;
            case 5:
                holder.tvColor.setBackgroundColor(activity.getResources().getColor(R.color.holiday));
                holder.tvStatus.setText("Holiday");
                break;
            case 15:
                holder.tvColor.setBackgroundColor(activity.getResources().getColor(R.color.present_am));
                holder.tvStatus.setText("Present AM");
                break;
            case 16:
                holder.tvColor.setBackgroundColor(activity.getResources().getColor(R.color.present_pm));
                holder.tvStatus.setText("Present PM");
                break;
        }
        if (detail.isCurrentday()) {
            holder.llattendance.setBackgroundColor(activity.getResources().getColor(R.color.attendancecolor));
            holder.tvStatus.setTextColor(activity.getResources().getColor(R.color.black));
            holder.tvDate.setTextColor(activity.getResources().getColor(R.color.black));
        }
        else {
            holder.llattendance.setBackgroundColor(activity.getResources().getColor(R.color.white));
        }
        holder.tvDate.setText(detail.getDate());

        return convertView;
    }

    static class ViewHolder {
        TextView tvDate, tvColor, tvStatus;
        LinearLayout llattendance;
    }
}