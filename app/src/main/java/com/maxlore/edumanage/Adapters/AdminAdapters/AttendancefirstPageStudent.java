package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAttendance.AdminStudentAttendance;


import java.util.List;

/**
 * Created by maxlore on 04-Oct-16.
 */

/**
 * Created by maxlore on 8/31/2016.
 */
public class AttendancefirstPageStudent extends RecyclerView.Adapter<AttendancefirstPageStudent.ViewHolder> {

    private List<AdminStudentAttendance> arrayList;
    private Activity activity;
    public static OnItemClickListener mItemClickListener;

    public AttendancefirstPageStudent(Activity activity, List<AdminStudentAttendance> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_admin_studentattendance_firstpage, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        AdminStudentAttendance adminStudentAttendance = arrayList.get(position);

        holder.tv_section.setText(adminStudentAttendance.getSectionName());
        holder.tv_standard.setText(adminStudentAttendance.getStandardName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_standard, tv_section;

        public ViewHolder(View v) {
            super(v);

            this.tv_standard = (TextView) v.findViewById(R.id.tv_standard);
            this.tv_section = (TextView) v.findViewById(R.id.tv_section);
            v.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
       /*         case R.id.message_parent:
                    mItemClickListener.onMessageParent(v, getAdapterPosition());
                    break;
                case R.id.call_parent:
                   *//* call_parent.setTag(new Integer(getAdapterPosition()));*//*
                    mItemClickListener.onCallParent(v, getAdapterPosition());
                    break;*/
                default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);

/*        public void onMessageParent(View view, int position);

        public void onCallParent(View view, int position);*/
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
