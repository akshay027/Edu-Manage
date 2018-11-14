package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminLeave.AdminLeaveApplicantDetails;

import com.maxlore.edumanage.Utility.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.maxlore.edumanage.Utility.Constants.context;

/**
 * Created by maxlore on 11/8/2016.
 */

public class AdminLeaveApplicantAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Activity activity;
    private ArrayList<AdminLeaveApplicantDetails> arrayList;
    private String className = "";

    public AdminLeaveApplicantAdapter(Activity activity, ArrayList<AdminLeaveApplicantDetails> arrayList) {
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
        AdminLeaveApplicantDetails leave = arrayList.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_admin_leave_applicant, null);
            holder = new ViewHolder();
            holder.llTitle = (LinearLayout) convertView.findViewById(R.id.llTitle);
            holder.llremark = (LinearLayout) convertView.findViewById(R.id.llremark);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvStartDate = (TextView) convertView.findViewById(R.id.tvStartDate);
            holder.tvEndDate = (TextView) convertView.findViewById(R.id.tvEndDate);
            holder.tvHalfDayLeave = (TextView) convertView.findViewById(R.id.tvHalfDayLeave);
            holder.tvreason = (TextView) convertView.findViewById(R.id.tvreason);
            holder.tvremark = (TextView) convertView.findViewById(R.id.tvremark);

            holder.tvLeaveType = (TextView) convertView.findViewById(R.id.tvLeaveType);
            holder.ivStatus = (ImageView) convertView.findViewById(R.id.ivStatus);
            holder.leavecancelled = (ImageView) convertView.findViewById(R.id.leavecancelled);
            holder.leaveonhold = (ImageView) convertView.findViewById(R.id.leaveonhold);
            holder.imageview = (ImageView) convertView.findViewById(R.id.imageview);
            holder.hdl = (TextView) convertView.findViewById(R.id.hdl);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvreason.setText("" + leave.getReason());
        if (leave.getRemark() == null) {
            holder.llremark.setVisibility(View.GONE);
        } else {
            holder.llremark.setVisibility(View.VISIBLE);
            holder.tvremark.setText("" + leave.getRemark());
        }

        holder.tv_name.setText(("" + leave.getEmployeeName()));
        holder.tvLeaveType.setText("" + leave.getEmployeeLeaveType());
        holder.tvStartDate.setText("" + leave.getStartDateDisplay());
        holder.tvEndDate.setText("" + leave.getEndDateDisplay());
        Picasso.with(context).load(leave.getPhoto()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.imageview);
        if (arrayList.get(position).getHalfDay()) {
            holder.hdl.setVisibility(View.VISIBLE);
            holder.tvHalfDayLeave.setVisibility(View.VISIBLE);
            holder.tvHalfDayLeave.setText("Yes");
        } else {
            holder.hdl.setVisibility(View.GONE);
            holder.tvHalfDayLeave.setVisibility(View.GONE);
        }

       /* if (TextUtils.isEmpty(arrayList.get(position).getRemark())) {
            holder.tvRemark.setText("N/A");
        } else {
            holder.tvRemark.setText(arrayList.get(position).getRemark());
        }*/
        if (arrayList.get(position).getStatus() == null) {
            holder.leaveonhold.setVisibility(View.VISIBLE);
            holder.leavecancelled.setVisibility(View.GONE);

            holder.ivStatus.setVisibility(View.GONE);
        } else if (arrayList.get(position).getStatus() == Constants.TRUE) {
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
        ImageView leavecancelled, leaveonhold, ivStatus, imageview;
        LinearLayout llTitle, llremark;
        TextView tv_name, tvLeaveType, tvreason, tvStartDate, tvEndDate, tvHalfDayLeave, tvremark, hdl;
    }
}
