package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAttendance.EmployeeAttendance;


import java.util.List;

/**
 * Created by maxlore on 8/31/2016.
 */
public class AttendancefirstPageAdapter extends RecyclerView.Adapter<AttendancefirstPageAdapter.ViewHolder> {

    private List<EmployeeAttendance> arrayList;
    private Activity activity;
    public static OnItemClickListener mItemClickListener;

    public AttendancefirstPageAdapter(Activity activity, List<EmployeeAttendance> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_admin_attendance_firstpage, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        EmployeeAttendance employeeAttendance = arrayList.get(position);
        holder.tv_departmenttype.setText(employeeAttendance.getDepartmentName());
        holder.tv_empcount.setText(employeeAttendance.getStaffcount());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_departmenttype,tv_empcount;

        public ViewHolder(View v) {
            super(v);

            this.tv_departmenttype = (TextView) v.findViewById(R.id.tv_departmenttype);
            this.tv_empcount = (TextView) v.findViewById(R.id.tv_empcount);

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
}
