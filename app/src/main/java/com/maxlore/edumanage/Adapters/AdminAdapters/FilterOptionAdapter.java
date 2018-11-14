package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.maxlore.edumanage.Activities.AdminActivities.FilterOptionsActivity;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels.PaymentForModel;
import com.maxlore.edumanage.R;

import java.util.ArrayList;

/**
 * Created by Shrey on 25-Jul-17.
 */

public class FilterOptionAdapter extends RecyclerView.Adapter<FilterOptionAdapter.ViewHolder> {

    private ArrayList<PaymentForModel> arrayList;
    private FilterOptionsActivity activity;
    public static FilterOptionAdapter.OnItemClickListener mItemClickListener;

    public FilterOptionAdapter(Activity activity, ArrayList<PaymentForModel> arrayList) {
        this.arrayList = arrayList;
        this.activity = (FilterOptionsActivity) activity;
    }

    @Override
    public FilterOptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filteritem, parent, false);
        FilterOptionAdapter.ViewHolder vh = new FilterOptionAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(FilterOptionAdapter.ViewHolder holder, final int position) {
        PaymentForModel PaymentForModel = arrayList.get(position);
        holder.tv_type.setText(PaymentForModel.getName());
        if (PaymentForModel.getCheck_box() == 1) {
            holder.checkBox.setChecked(true);
        } else if (PaymentForModel.getCheck_box() == 0) {
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

    public void SetOnItemClickListener(FilterOptionAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}