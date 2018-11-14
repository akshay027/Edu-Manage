package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Models.TeacherModels.Academics.DailySessionPlan;

import com.maxlore.edumanage.Utility.Constants;

import java.util.ArrayList;

/**
 * Created by maxlore on 10/21/2016.
 */

public class DailyDataAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<DailySessionPlan> arrayList;

    public DailyDataAdapter(Activity activity, ArrayList<DailySessionPlan> arrayList) {
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

            convertView = inflater.inflate(R.layout.list_item_academics_details_for_daily, null);
            holder = new ViewHolder();
            holder.tvMonthname = (TextView) convertView.findViewById(R.id.tvmonthname);
            holder.tvLessonname = (TextView) convertView.findViewById(R.id.tvlessonname);
            holder.tvTopicname = (TextView) convertView.findViewById(R.id.tv_topicname);
            holder.tvSubTopicname = (TextView) convertView.findViewById(R.id.tvsubtopicname);
          /*  holder.tvTopicno = (TextView) convertView.findViewById(R.id.tvtopicno);*/
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvWeek = (TextView) convertView.findViewById(R.id.tv_week);
            holder.remark = (View) convertView.findViewById(R.id.remarkview_daily);
            holder.remark_tv = (TextView) convertView.findViewById(R.id.remark_tv_daily);
            holder.tvstatus = (TextView) convertView.findViewById(R.id.status_tv);
        /*    holder.tvsubtopicno = (TextView) convertView.findViewById(R.id.tvsubtopicno);*/
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        if (arrayList.get(position).getRemark() == (Constants.NULL_REMARK)) {
            holder.remark.setBackgroundColor(activity.getResources().getColor(R.color.gray_light));
            holder.remark_tv.setText("NA");
        } else if (arrayList.get(position).getRemark() == (Constants.UPDATE_REMARK)) {
            holder.remark.setBackgroundColor(activity.getResources().getColor(R.color.skyBlue));
            holder.remark_tv.setText("Not Started");
        } else if (arrayList.get(position).getRemark() == (Constants.DATA_REMARK)) {
            holder.remark.setBackgroundColor(activity.getResources().getColor(R.color.red_dark));
            holder.remark_tv.setText("On Progress");
        } else if (arrayList.get(position).getRemark() == (Constants.NORMAL_REMARK)) {
            holder.remark.setBackgroundColor(activity.getResources().getColor(R.color.green_dark));
            holder.remark_tv.setText("Completed");
        }
        if (TextUtils.isEmpty(arrayList.toString())) {
            holder.tvstatus.setVisibility(View.VISIBLE);
            holder.tvstatus.setText("Session Plan Not Created");

        } else {
            holder.tvMonthname.setText("" + arrayList.get(position).getMonthName());
            holder.tvLessonname.setText("" + arrayList.get(position).getLessonName());
            holder.tvTopicname.setText("" + arrayList.get(position).getTopicName());
            holder.tvSubTopicname.setText("" + arrayList.get(position).getSubTopicName());
          /*  holder.tvTopicno.setText("" + arrayList.get(position).getTopicNo());*/
            holder.tvDate.setText("" + arrayList.get(position).getDate());
            holder.tvWeek.setText("" + arrayList.get(position).getWeek());
         /*   holder.tvsubtopicno.setText("" + arrayList.get(position).getSubTopicNumber());*/
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tvMonthname, tvLessonname, tvsubtopicno, tvTopicname, tvSubTopicname, tvTopicno, tvDate, tvWeek, remark_tv, tvstatus;
        View remark;


    }


}



