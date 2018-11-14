package com.maxlore.edumanage.Adapters.ParentAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Activities.ParentActivities.PaymentHistoryActivity;
import com.maxlore.edumanage.Models.ParentModels.ParentFees.PayHistoryData;


import java.util.ArrayList;

public class ParentFeesHistoryAdapter extends RecyclerView.Adapter<ParentFeesHistoryAdapter.ViewHolder> {

    private ArrayList<PayHistoryData> arrayList;
    private PaymentHistoryActivity activity;

    public ParentFeesHistoryAdapter(Activity activity, ArrayList<PayHistoryData> arrayList) {
        this.arrayList = arrayList;
        this.activity = (PaymentHistoryActivity) activity;
    }


    @Override
    public ParentFeesHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_parent_fees_history, parent, false);
        ParentFeesHistoryAdapter.ViewHolder vh = new ParentFeesHistoryAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ParentFeesHistoryAdapter.ViewHolder holder, final int position) {

        PayHistoryData feeHistory = arrayList.get(position);
        holder.tv_paymentfor.setText(feeHistory.getPaymentFor());
        holder.tv_mode.setText(feeHistory.getMode());
        holder.tv_amount.setText(feeHistory.getAmount());
        holder.tv_recieptno.setText(feeHistory.getRecieptNo());
        holder.tv_recieptdate.setText(feeHistory.getRecieptDate());
        if (feeHistory.getStatus().equalsIgnoreCase("true")) {
            holder.paymentapproved.setVisibility(View.VISIBLE);
            holder.paymentcancelled.setVisibility(View.GONE);
            holder.paymentpending.setVisibility(View.GONE);
        } else if (feeHistory.getStatus().equalsIgnoreCase("false")) {
            holder.paymentapproved.setVisibility(View.GONE);
            holder.paymentpending.setVisibility(View.GONE);
            holder.paymentcancelled.setVisibility(View.VISIBLE);
        } else {
            holder.paymentpending.setVisibility(View.GONE);
            holder.paymentcancelled.setVisibility(View.VISIBLE);
            holder.paymentapproved.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_paymentfor, tv_mode, tv_amount, tv_recieptno, tv_recieptdate;
        ImageView paymentapproved, paymentcancelled, paymentpending;

        public ViewHolder(View v) {
            super(v);
            this.tv_paymentfor = (TextView) v.findViewById(R.id.tv_paymentfor);
            this.tv_mode = (TextView) v.findViewById(R.id.tv_mode);
            this.tv_amount = (TextView) v.findViewById(R.id.tv_amount);
            this.tv_recieptno = (TextView) v.findViewById(R.id.tv_recieptno);
            this.tv_recieptdate = (TextView) v.findViewById(R.id.tv_recieptdate);

            this.paymentapproved = (ImageView) v.findViewById(R.id.paymentapproved);
            this.paymentcancelled = (ImageView) v.findViewById(R.id.paymentcancelled);
            this.paymentpending = (ImageView) v.findViewById(R.id.paymentpending);

        }
    }
}
