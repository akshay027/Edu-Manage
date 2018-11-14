package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Activities.AdminActivities.FilterOptionsActivity;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels.PaymentStatusModel;


import java.util.ArrayList;

/**
 * Created by Shrey on 26-Jul-17.
 */


public class PaymentStatusAdapter extends RecyclerView.Adapter<PaymentStatusAdapter.ViewHolder> {

    private ArrayList<PaymentStatusModel> arrayList;
    private FilterOptionsActivity activity;
    public static PaymentStatusAdapter.OnItemClickListener mItemClickListener;

    public PaymentStatusAdapter(Activity activity, ArrayList<PaymentStatusModel> arrayList) {
        this.arrayList = arrayList;
        this.activity = (FilterOptionsActivity) activity;
    }

    @Override
    public PaymentStatusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filteritem, parent, false);
        PaymentStatusAdapter.ViewHolder vh = new PaymentStatusAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PaymentStatusAdapter.ViewHolder holder, final int position) {
        PaymentStatusModel paymentStatusModel = arrayList.get(position);
        holder.tv_type.setText(paymentStatusModel.getName());
        if (paymentStatusModel.getCheck_box() == 1) {
            holder.checkBox.setChecked(true);
        } else if (paymentStatusModel.getCheck_box() == 0) {
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_type;
        CheckBox checkBox;

        public ViewHolder(View v) {
            super(v);

            this.tv_type = (TextView) v.findViewById(R.id.tv_type);
            this.checkBox = (CheckBox) v.findViewById(R.id.checkbox);
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

    public void SetOnItemClickListener(PaymentStatusAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}