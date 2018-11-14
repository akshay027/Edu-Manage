package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Activities.TeacherActivities.AttendanceActivity;
import com.maxlore.edumanage.Models.TeacherModels.StudentAttendance;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.maxlore.edumanage.Utility.Constants.context;


/**
 * Created by Nikhil on 24-11-2015.
 */
public class AttendanceRecyclerAdapter extends RecyclerView.Adapter<AttendanceRecyclerAdapter.ViewHolder> {

    private ArrayList<StudentAttendance> arrayList;
    private AttendanceActivity activity;
    public static OnItemClickListener mItemClickListener;
    private boolean isAmPm;
    private boolean isHoliday;

    public AttendanceRecyclerAdapter(Activity activity, ArrayList<StudentAttendance> arrayList, boolean isAmPm, boolean isHoliday) {
        this.arrayList = arrayList;
        this.isAmPm = isAmPm;
        this.isHoliday = isHoliday;
        this.activity = (AttendanceActivity) activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_attendance, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        StudentAttendance attendance = arrayList.get(position);

        if (isAmPm) {
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
                holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.sender));
         /*   holder.tv_atttype.setText("FUTURE DATE");*/
            } else if ((attendance.getAttendance() == Constants.SCHOOLHOLIDAY)) {
                holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.holidayatten));
           /* holder.tv_atttype.setText("HOLIDAY MARK");*/
            } else if ((attendance.getAttendance() == Constants.STUDENTTIMETABLENOTASSIGN)) {
                holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.textBlackDark));
           /* holder.tv_atttype.setText("PREVIOUS WORK DAY");*/
            } else if ((attendance.getAttendance() == Constants.LEAVEDAY)) {
                holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.leaveatten));
         /* holder.tv_atttype.setText("LEAVE APPLIED");*/
            }
        } else {
            if (attendance.isPMSelected()) {
                if (attendance.getAttendancePm() == Constants.PRESENT) {
                    holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.presentatten));
           /* holder.tv_atttype.setText("PRESENT");*/
                } else if (attendance.getAttendancePm() == Constants.ABSENT) {
                    holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.absentatten));
            /*holder.tv_atttype.setText("ABSENT");*/
                } else if (attendance.getAttendancePm() == Constants.HALFDAYATTEN) {
                    holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.halfdayatten));
            /*holder.tv_atttype.setText("HOLIDAY");*/
                } else if (attendance.getAttendancePm() == Constants.NONWORKINGDAY) {
                    holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.nonworkingdayatten));
           /* holder.tv_atttype.setText("NO WORK DAY TODAY");*/
                } else if ((attendance.getAttendancePm() == Constants.FUTUREDATE)) {
                    holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.sender));
         /*   holder.tv_atttype.setText("FUTURE DATE");*/
                } else if ((attendance.getAttendancePm() == Constants.SCHOOLHOLIDAY)) {
                    holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.holidayatten));
           /* holder.tv_atttype.setText("HOLIDAY MARK");*/
                } else if ((attendance.getAttendancePm() == Constants.STUDENTTIMETABLENOTASSIGN)) {
                    holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.textBlackDark));
           /* holder.tv_atttype.setText("PREVIOUS WORK DAY");*/
                } else if ((attendance.getAttendancePm() == Constants.LEAVEDAY)) {
                    holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.leaveatten));
         /* holder.tv_atttype.setText("LEAVE APPLIED");*/
                }
            } else {
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
                    holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.sender));
         /*   holder.tv_atttype.setText("FUTURE DATE");*/
                } else if ((attendance.getAttendance() == Constants.SCHOOLHOLIDAY)) {
                    holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.holidayatten));
           /* holder.tv_atttype.setText("HOLIDAY MARK");*/
                } else if ((attendance.getAttendance() == Constants.STUDENTTIMETABLENOTASSIGN)) {
                    holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.textBlackDark));
           /* holder.tv_atttype.setText("PREVIOUS WORK DAY");*/
                } else if ((attendance.getAttendance() == Constants.LEAVEDAY)) {
                    holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.leaveatten));
         /* holder.tv_atttype.setText("LEAVE APPLIED");*/
                }
            }
        }
        if (attendance.getCheck_box() == 1) {
            holder.checkbox_atten.setChecked(true);
        } else if (attendance.getCheck_box() == 0) {
            holder.checkbox_atten.setChecked(false);
        }       //    STUDENTWORKNOTASSIGN
//        if (isHoliday && !attendance.getMark()) {
//            holder.viewPresentAbsent.setBackgroundColor(activity.getResources().getColor(R.color.textBlackLight));
//        }
        Picasso.with(context).load(attendance.getPhoto()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.imageview);
        holder.tvStudent.setText(attendance.getStudentName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView tvStudent, tvAM, tvPM;
        public View viewPresentAbsent;
        public CheckBox checkbox_atten;
        public LinearLayout llAMPM;
        ImageView imageview;

        public ViewHolder(View v) {
            super(v);

            this.tvStudent = (TextView) v.findViewById(R.id.tvStudent);
            this.imageview = (ImageView) v.findViewById(R.id.imageview);

            this.checkbox_atten = (CheckBox) v.findViewById(R.id.checkbox_atten);

            this.viewPresentAbsent = (View) v.findViewById(R.id.viewPresentAbsent1);

            //this.checkbox_atten.setOnClickListener(this);
            //this.viewPresentAbsent.setOnClickListener(this);

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


    public ArrayList<StudentAttendance> getCurrentDada() {
        return this.arrayList;
    }

}