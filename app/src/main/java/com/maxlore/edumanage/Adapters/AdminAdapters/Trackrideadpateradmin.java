/*
package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxlore.edumanage.Activities.AdminActivities.Admintrackridepageone;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTrackride.AdminBusDetail;
import com.maxlore.edumanage.R;

import java.util.ArrayList;

*/
/**
 * Created by maxlore on 15-Mar-17.
 *//*


public class Trackrideadpateradmin extends RecyclerView.Adapter<Trackrideadpateradmin.ViewHolder> {

    private ArrayList<AdminBusDetail> arrayList;
    private Admintrackridepageone activity;
    public static OnItemClickListener mItemClickListener;

    public Trackrideadpateradmin(Activity activity, ArrayList<AdminBusDetail> arrayList) {
        this.arrayList = arrayList;
        this.activity = (Admintrackridepageone) activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_totalbuses, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        AdminBusDetail adminBusDetail = arrayList.get(position);
        if (adminBusDetail.getStatus()==true) {
            holder.busno.setText(adminBusDetail.getBusNo());
            holder.llll.setVisibility(View.GONE);
        }
        else
        {
            holder.busnored.setText(adminBusDetail.getBusNo());
            holder.lll.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView busno,busnored;
        LinearLayout lll,llll;

        public ViewHolder(View v) {
            super(v);
            this.busnored = (TextView) v.findViewById(R.id.busnored);
            this.busno = (TextView) v.findViewById(R.id.busno);
            this.lll = (LinearLayout) v.findViewById(R.id.lll);
            this.llll = (LinearLayout) v.findViewById(R.id.llll);
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
*/
