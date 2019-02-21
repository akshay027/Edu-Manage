package com.maxlore.edumanage.Adapters.AdminAdapters;
//AttendanceSecondPageAdapter
/**
 * Created by maxlore on 06-Oct-16.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Activities.AdminActivities.AdminAttendanceDetail;
import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AttendanceDetail.DepartmentAttendance;

import com.maxlore.edumanage.Utility.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Nikhil on 24-11-2015.
 */
public class AttendanceSecondPageAdapter extends RecyclerView.Adapter<AttendanceSecondPageAdapter.ViewHolder> {

    private ArrayList<DepartmentAttendance> arrayList;
    private AdminAttendanceDetail activity;
    private Context contexta;
    public static OnItemClickListener mItemClickListener;
    private boolean isHoliday;

    public AttendanceSecondPageAdapter(Activity activity, ArrayList<DepartmentAttendance> arrayList, boolean isHoliday, Context context) {
        this.arrayList = arrayList;
        this.contexta = context;
        this.isHoliday = isHoliday;
        this.activity = (AdminAttendanceDetail) activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_attendance_admin, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        DepartmentAttendance attendance = arrayList.get(position);
        Picasso.with(contexta).load(attendance.getPhoto()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.imageview);
        if (attendance.getAttendance() == Constants.PRESENT) {
            holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.presentatten));
           /* holder.tv_atttype.setText("PRESENT");*/
        } else if (attendance.getAttendance() == Constants.ABSENT) {
            holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.absentatten));
            /*holder.tv_atttype.setText("ABSENT");*/
        } else if (attendance.getAttendance() == Constants.HALFDAYATTEN) {
            holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.halfdayatten));
            /*holder.tv_atttype.setText("HOLIDAY");*/
        } else if (attendance.getAttendance() == Constants.NONWORKINGDAY) {
            holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.nonworkingdayatten));
           /* holder.tv_atttype.setText("NO WORK DAY TODAY");*/
        } else if ((attendance.getAttendance() == Constants.FUTUREDATE)) {
            holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.skyBluelight));
         /*   holder.tv_atttype.setText("FUTURE DATE");*/
        } else if ((attendance.getAttendance() == Constants.SCHOOLHOLIDAY)) {
            holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.holidayatten));
           /* holder.tv_atttype.setText("HOLIDAY MARK");*/
        } else if ((attendance.getAttendance() == Constants.WORKINGDAYNOTASSIGN)) {
            holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.textBlackDark));
           /* holder.tv_atttype.setText("PREVIOUS WORK DAY");*/
        } else if ((attendance.getAttendance() == Constants.LEAVEDAY)) {
            holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.leaveatten));
         /* holder.tv_atttype.setText("LEAVE APPLIED");*/
        }
        else if ((attendance.getAttendance() == Constants.NORMAL_DAYS)) {
            holder.viewPresentAbsent.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border_style));
            /* holder.tv_atttype.setText("LEAVE APPLIED");*/
        }
        holder.tvStudent.setText(attendance.getEmployeeName());
        if (attendance.getCheck_box() == 1) {
            holder.checkbox_atten.setChecked(true);
        } else if (attendance.getCheck_box() == 0) {
            holder.checkbox_atten.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView tvStudent, tv_atttype;
        public View viewPresentAbsent;
        public CheckBox checkbox_atten;
        public LinearLayout llAMPM;
        private ImageView imageview;

        public ViewHolder(View v) {
            super(v);

            this.tvStudent = (TextView) v.findViewById(R.id.tvStudent);
            this.imageview = (ImageView) v.findViewById(R.id.imageview);
            this.checkbox_atten = (CheckBox) v.findViewById(R.id.checkbox_atten);
     /*       this.tv_atttype = (TextView) v.findViewById(R.id.tv_atttype);*/
            this.viewPresentAbsent = (View) v.findViewById(R.id.viewPresentAbsent1);

            this.viewPresentAbsent.setOnClickListener(this);
            this.checkbox_atten.setOnClickListener(this);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public ArrayList<DepartmentAttendance> getCurrentDada() {
        return this.arrayList;
    }
}

