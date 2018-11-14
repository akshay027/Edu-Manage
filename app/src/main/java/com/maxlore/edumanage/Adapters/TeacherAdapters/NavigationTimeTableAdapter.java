package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maxlore.edumanage.Models.TeacherModels.database.TimeTable;
import com.maxlore.edumanage.R;

import java.util.List;


public class NavigationTimeTableAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Activity activity;
    private List<TimeTable> arrayList;


    public NavigationTimeTableAdapter(Activity activity, List<TimeTable> arrayList) {
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

        TimeTable timeTable = arrayList.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_time_table2, null);
            holder = new ViewHolder();
            holder.period_tv = (TextView) convertView.findViewById(R.id.PeriodNo_tv);
            holder.starttime_tv = (TextView) convertView.findViewById(R.id.StartTime_tv);
            holder.subject_tv = (TextView) convertView.findViewById(R.id.Subject_tv);
            holder.class_tv = (TextView) convertView.findViewById(R.id.Class_tv);
            holder.endtime_tv = (TextView) convertView.findViewById(R.id.EndTime_tv);
            holder.section_tv = (TextView) convertView.findViewById(R.id.Section_tv);
            holder.classteacher_tv = (TextView) convertView.findViewById(R.id.ClassTeacher_tv);
            holder.viewSeprate = (View) convertView.findViewById(R.id.viewSeprate);
            holder.brac1 = (TextView) convertView.findViewById(R.id.brac1);
            holder.brac2 = (TextView) convertView.findViewById(R.id.brac2);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }
        if (timeTable.getOwnClass() == 1) {
            holder.classteacher_tv.setVisibility(View.VISIBLE);
            holder.brac1.setVisibility(View.VISIBLE);
            holder.brac2.setVisibility(View.VISIBLE);
        } else {
            holder.classteacher_tv.setVisibility(View.GONE);
            holder.brac1.setVisibility(View.GONE);
            holder.brac2.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(timeTable.getClass_())) {
            holder.class_tv.setText("--");
        } else {
            holder.class_tv.setText("" + timeTable.getClass_());
        }
        if (TextUtils.isEmpty(timeTable.getSection())) {
            holder.section_tv.setText("");
        } else {
            holder.section_tv.setText("" + timeTable.getSection());
        }
        if (TextUtils.isEmpty(timeTable.getEndTime())) {
            holder.endtime_tv.setText("--");
        } else {
            holder.endtime_tv.setText("" + timeTable.getEndTime());
        }
        if (TextUtils.isEmpty(timeTable.getPeriod())) {
            holder.period_tv.setText("--");
        } else {
            holder.period_tv.setText("" + timeTable.getPeriod());
        }
        if (TextUtils.isEmpty(timeTable.getSubject())) {
            holder.subject_tv.setText("--");
            /*holder.subject_tv.setTextColor(activity.getResources().getColor(R.color.gray_light));*/
        } else {
            holder.subject_tv.setText("" + timeTable.getSubject());
        }
        if (TextUtils.isEmpty(timeTable.getStartTime())) {
            holder.starttime_tv.setText("--");
        } else {
            holder.starttime_tv.setText("" + timeTable.getStartTime());
        }
        if (TextUtils.isEmpty(timeTable.getTeacher())) {
            holder.classteacher_tv.setText("");
            holder.brac1.setVisibility(View.GONE);
            holder.brac2.setVisibility(View.GONE);
        } else {
            holder.classteacher_tv.setText("" + timeTable.getTeacher());
        }

        return convertView;
    }

    static class ViewHolder {
        TextView period_tv;
        TextView subject_tv;
        TextView starttime_tv;
        TextView endtime_tv;
        TextView class_tv;
        TextView section_tv;
        TextView classteacher_tv,brac1,brac2;
        View viewSeprate;
    }

}

