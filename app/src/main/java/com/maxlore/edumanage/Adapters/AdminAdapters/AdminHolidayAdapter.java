package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminHolidays.Holiday;


import java.util.ArrayList;

/**
 * Created by maxlore on 11/24/2016.
 */
public class AdminHolidayAdapter extends RecyclerView.Adapter<AdminHolidayAdapter.ViewHolder> {

    private ArrayList<Holiday> arrayList;
    private Activity activity;
    public static OnItemClickListener mItemClickListener;

    public AdminHolidayAdapter(Activity activity, ArrayList<Holiday> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_admin_holiday, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
      /*  if (arrayList.get(position).getHolidayEndDate() == null) {
            holder.tvEndDate.setVisibility(View.INVISIBLE);
            holder.tv_to.setVisibility(View.INVISIBLE);
        } else {
            holder.tvEndDate.setVisibility(View.VISIBLE);
            holder.tv_to.setVisibility(View.VISIBLE);
        }*/
        holder.tvReason.setText("" + arrayList.get(position).getReason());
        holder.tvStartDate.setText("" + arrayList.get(position).getHolidayDate());
        //holder.tvEndDate.setText("" + arrayList.get(position).getHolidayEndDate());
        holder.tvtype.setText("" + arrayList.get(position).getHolidayType());
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView tvReason, tvStartDate, tvEndDate, tvtype, tv_to;
        public ImageView tvdelete, tvedit;

        public ViewHolder(View v) {
            super(v);

            this.tvReason = (TextView) v.findViewById(R.id.tvReason);
            this.tvStartDate = (TextView) v.findViewById(R.id.tvStartDate);
            //this.tvEndDate = (TextView) v.findViewById(R.id.tvEndDate);
            this.tvtype = (TextView) v.findViewById(R.id.tvtype);
            this.tv_to = (TextView) v.findViewById(R.id.tv_to);

            this.tvdelete = (ImageView) v.findViewById(R.id.tvdelete1);
            this.tvedit = (ImageView) v.findViewById(R.id.tvedit1);

            this.tvdelete.setOnClickListener(this);
            this.tvedit.setOnClickListener(this);

            v.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.tvedit1:
                    mItemClickListener.onEditClick(v, getAdapterPosition());
                    break;
                case R.id.tvdelete1:
                    mItemClickListener.onDeleteClick(v, getAdapterPosition());
                    break;
                default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
    public interface OnItemClickListener {

        public void onEditClick(View view, int position);

        public void onDeleteClick(View view, int position);

        public void onItemClick(View view, int position);

    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    public ArrayList<Holiday> getCurrentDada() {
        return this.arrayList;
    }

}

