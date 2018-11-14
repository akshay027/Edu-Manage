package com.maxlore.edumanage.Adapters.ParentAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Models.ParentModels.ParentLeaves.ParentLeaveHistory;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by maxlore on 8/26/2016.
 */
public class ParentLeaveHistoryAdapter extends RecyclerView.Adapter<ParentLeaveHistoryAdapter.ViewHolder> {


    private Activity activity;
    private ArrayList<ParentLeaveHistory> arrayList;
    public static ParentLeaveHistoryAdapter.OnItemClickListener mItemClickListener;
    boolean isSelectedAll;

    public ParentLeaveHistoryAdapter(Activity activity, ArrayList<ParentLeaveHistory> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public ParentLeaveHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_parent_leave_history, parent, false);
        ParentLeaveHistoryAdapter.ViewHolder vh = new ParentLeaveHistoryAdapter.ViewHolder(v);

        return vh;
    }

    public void selectAll() {
        Log.e("onClickSelectAll", "yes");
        isSelectedAll = true;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ParentLeaveHistoryAdapter.ViewHolder holder, final int position) {

        ParentLeaveHistory activeAssignment = arrayList.get(position);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String curDate = sdf.format(date.getTime());
// print the date in your log cat
        Log.d("CUR_DATE", curDate);

        if (arrayList.get(position).getFromDate().compareTo(curDate) < 0 || arrayList.get(position).getToDate().compareTo(curDate) < 0) {
            holder.tvdelete.setVisibility(View.GONE);
            holder.tvedit.setVisibility(View.GONE);
        } else {
            holder.tvdelete.setVisibility(View.VISIBLE);
            holder.tvedit.setVisibility(View.VISIBLE);
        }
        if (arrayList.get(position).getIsHalfDay()) {
            holder.tvEndDate.setVisibility(View.GONE);
            holder.tv_to.setVisibility(View.GONE);
            holder.tvishalf.setVisibility(View.VISIBLE);
            holder.haldaytv.setVisibility(View.VISIBLE);
            holder.tvishalf.setText((CharSequence) arrayList.get(position).getMeridiem());
        } else {
            holder.tvEndDate.setVisibility(View.VISIBLE);
            holder.tv_to.setVisibility(View.VISIBLE);
            holder.tvishalf.setVisibility(View.GONE);
            holder.haldaytv.setVisibility(View.GONE);
        }
        holder.tvReason.setText(arrayList.get(position).getReason());
        holder.tvStartDate.setText(arrayList.get(position).getFromDate());
        holder.tvEndDate.setText(arrayList.get(position).getToDate());
        if (TextUtils.isEmpty(activeAssignment.getAcknowledgement())) {
            holder.tvacknowledgement.setText(" N/A");
        } else {
            holder.tvacknowledgement.setText(activeAssignment.getAcknowledgement());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView tvedit, tvdelete;
        TextView tvLeaveType, tvReason, tvStartDate, tvEndDate, tvacknowledgement, tv_to, haldaytv, tvishalf;

        public ViewHolder(View v) {
            super(v);
            this.tvReason = (TextView) v.findViewById(R.id.tvReason);
            this.tvStartDate = (TextView) v.findViewById(R.id.tvStartDate);
            this.tvEndDate = (TextView) v.findViewById(R.id.tvEndDate);
            this.tvacknowledgement = (TextView) v.findViewById(R.id.tvacknowledgement);
            this.tv_to = (TextView) v.findViewById(R.id.tv_to);
            this.haldaytv = (TextView) v.findViewById(R.id.haldaytv);
            this.tvishalf = (TextView) v.findViewById(R.id.tvishalf);

            this.tvedit = (ImageView) v.findViewById(R.id.tvedit);
            this.tvdelete = (ImageView) v.findViewById(R.id.tvdelete);

            this.tvedit.setOnClickListener(this);
            this.tvdelete.setOnClickListener(this);
            v.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvdelete:
                    mItemClickListener.ondeleteClick(v, getAdapterPosition());
                    break;
                case R.id.tvedit:
                    mItemClickListener.oneditClick(v, getAdapterPosition());

                default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);

        public void ondeleteClick(View view, int position);

        public void oneditClick(View view, int position);


    }

    public void SetOnItemClickListener(ParentLeaveHistoryAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}