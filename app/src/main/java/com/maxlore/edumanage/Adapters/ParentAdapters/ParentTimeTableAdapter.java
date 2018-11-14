package com.maxlore.edumanage.Adapters.ParentAdapters;

/**
 * Created by maxlore on 29-Aug-16.
 */

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

import java.util.ArrayList;

public class ParentTimeTableAdapter extends BaseAdapter {
   private final LayoutInflater inflater;
   private Activity activity;
   private ArrayList<TimeTable> arrayList;


   public ParentTimeTableAdapter(Activity activity, ArrayList<TimeTable> arrayList) {
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
           convertView = inflater.inflate(R.layout.list_item_parent_time_table, null);
           holder = new ViewHolder();
           holder.period_tv = (TextView) convertView.findViewById(R.id.PeriodNo_tv);
           holder.starttime_tv = (TextView) convertView.findViewById(R.id.StartTime_tv);
           holder.subject_tv = (TextView) convertView.findViewById(R.id.Subject_tv);
           holder.class_tv = (TextView) convertView.findViewById(R.id.Class_tv);
           holder.endtime_tv = (TextView) convertView.findViewById(R.id.EndTime_tv);
           holder.section_tv = (TextView) convertView.findViewById(R.id.Section_tv);
           holder.classteacher_tv = (TextView) convertView.findViewById(R.id.ClassTeacher_tv);

           convertView.setTag(holder);

       } else {
           holder = (ViewHolder) convertView.getTag();
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
           holder.endtime_tv.setText("");
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
       } else {
           holder.subject_tv.setText("" + timeTable.getSubject());
       }
       if (TextUtils.isEmpty(timeTable.getStartTime())) {
           holder.starttime_tv.setText("--");
       } else {
           holder.starttime_tv.setText("" + timeTable.getStartTime());
       }
       if (TextUtils.isEmpty(timeTable.getTeacher())) {
           holder.classteacher_tv.setText("--");
       } else {
           holder.classteacher_tv.setText("" + timeTable.getTeacher());
       }
       return convertView;

   }

   static class ViewHolder {
       TextView period_tv, subject_tv, starttime_tv, endtime_tv, class_tv, section_tv, classteacher_tv;
   }
}

