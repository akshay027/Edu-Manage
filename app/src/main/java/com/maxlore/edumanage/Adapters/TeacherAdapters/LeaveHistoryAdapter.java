package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Models.TeacherModels.Leaves.LeaveHistory;


import java.util.ArrayList;

/**
 * Created by Nikhil on 05-06-2016.
 */
public class LeaveHistoryAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<LeaveHistory> arrayList;

    public LeaveHistoryAdapter(Activity activity, ArrayList<LeaveHistory> arrayList) {
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
        LeaveHistory leaveHistory = arrayList.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_leave_history, null);
            holder = new ViewHolder();

            holder.tvReason = (TextView) convertView.findViewById(R.id.tvReason);
            holder.tvStartDate = (TextView) convertView.findViewById(R.id.tvStartDate);
            holder.tvEndDate = (TextView) convertView.findViewById(R.id.tvEndDate);
            holder.tvHalfDayLeave = (TextView) convertView.findViewById(R.id.tvHalfDayLeave);
            holder.tvRemark = (TextView) convertView.findViewById(R.id.tvacknowledgement);
            holder.tvLeaveType = (TextView) convertView.findViewById(R.id.tvLeaveType);
            holder.ivStatus = (ImageView) convertView.findViewById(R.id.ivStatus);
            holder.leavecancelled = (ImageView) convertView.findViewById(R.id.leavecancelled);
            holder.leaveonhold = (ImageView) convertView.findViewById(R.id.leaveonhold);
            holder.hdl = (TextView) convertView.findViewById(R.id.hdl);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvLeaveType.setText(arrayList.get(position).getEmployeeLeaveType());
        holder.tvReason.setText(arrayList.get(position).getReason());
        holder.tvStartDate.setText(arrayList.get(position).getStartDate());
        holder.tvEndDate.setText(arrayList.get(position).getEndDate());
        if (leaveHistory.getHalfDay()) {
            holder.hdl.setVisibility(View.VISIBLE);
            holder.tvHalfDayLeave.setVisibility(View.VISIBLE);
            holder.tvHalfDayLeave.setText("Yes");
        } else {

            holder.hdl.setVisibility(View.GONE);
            holder.tvHalfDayLeave.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(leaveHistory.getRemark())) {
            holder.tvRemark.setText("N/A");
        } else {
            holder.tvRemark.setText(leaveHistory.getRemark());
        }

        if (leaveHistory.getStatus() == null) {
            holder.leaveonhold.setVisibility(View.VISIBLE);
            holder.leavecancelled.setVisibility(View.GONE);
            holder.ivStatus.setVisibility(View.GONE);
        } else if (leaveHistory.getStatus()) {
            holder.leaveonhold.setVisibility(View.GONE);
            holder.leavecancelled.setVisibility(View.GONE);
            holder.ivStatus.setVisibility(View.VISIBLE);
        } else {
            holder.leaveonhold.setVisibility(View.GONE);
            holder.leavecancelled.setVisibility(View.VISIBLE);
            holder.ivStatus.setVisibility(View.GONE);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView ivStatus, leavecancelled, leaveonhold;
        TextView tvLeaveType, tvReason, tvStartDate, tvEndDate, tvHalfDayLeave, tvRemark, hdl;
    }
}
