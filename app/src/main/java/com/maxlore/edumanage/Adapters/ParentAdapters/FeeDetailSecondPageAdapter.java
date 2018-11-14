/*
package com.maxlore.edumanage.Adapters.ParentAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxlore.edumanage.Activities.ParentActivities.FeeDetailSecondPage;
import com.maxlore.edumanage.Models.ParentModels.FeeHistory.PaymentHistory;
import com.maxlore.edumanage.R;

import java.util.ArrayList;

*/
/**
 * Created by maxlore on 04-Nov-16.
 *//*


public class FeeDetailSecondPageAdapter extends RecyclerView.Adapter<FeeDetailSecondPageAdapter.ViewHolder> {

    private ArrayList<PaymentHistory> arrayList;
    private FeeDetailSecondPage activity;
    public static FeeDetailSecondPageAdapter.OnItemClickListener mItemClickListener;

    public FeeDetailSecondPageAdapter(Activity activity, ArrayList<PaymentHistory> arrayList) {
        this.arrayList = arrayList;
        this.activity = (FeeDetailSecondPage) activity;
    }


    @Override
    public FeeDetailSecondPageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_parent_fees_history_secondpage, parent, false);
        FeeDetailSecondPageAdapter.ViewHolder vh = new FeeDetailSecondPageAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(FeeDetailSecondPageAdapter.ViewHolder holder, final int position) {

        PaymentHistory paymentHistory = arrayList.get(position);
        holder.tv_recieptno.setText(paymentHistory.getReceiptNumber());
        holder.tv_recieptdate.setText(paymentHistory.getReceiptDate());
        holder.tv_amount.setText(paymentHistory.getAmount());
        if (paymentHistory.getRemarks()==null)
        {
            holder.tv_remarks.setVisibility(View.GONE);
            holder.tv_Nore.setVisibility(View.GONE);
        }
        else
        {
            holder.tv_remarks.setVisibility(View.VISIBLE);
            holder.tv_Nore.setVisibility(View.GONE);
            holder.tv_remarks.setText(paymentHistory.getRemarks());
        }
        holder.tv_mode.setText(paymentHistory.getMode());
        if (paymentHistory.getChequeNo()==null)
        {
            holder.tv_chequeno.setVisibility(View.GONE);
            holder.tv_Noche.setVisibility(View.GONE);
        }
        else
        {
            holder.tv_chequeno.setVisibility(View.VISIBLE);
            holder.tv_Noche.setVisibility(View.GONE);
            holder.tv_chequeno.setText(paymentHistory.getChequeNo());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_recieptno, tv_recieptdate, tv_amount, tv_remarks, tv_mode, tv_chequeno,tv_Nore,tv_Noche;

        public ViewHolder(View v) {
            super(v);

            this.tv_recieptno = (TextView) v.findViewById(R.id.tv_recieptno);
            this.tv_recieptdate = (TextView) v.findViewById(R.id.tv_recieptdate);
            this.tv_amount = (TextView) v.findViewById(R.id.tv_amount);
            this.tv_remarks = (TextView) v.findViewById(R.id.tv_remarks);
            this.tv_mode = (TextView) v.findViewById(R.id.tv_mode);
            this.tv_chequeno = (TextView) v.findViewById(R.id.tv_chequeno);
            this.tv_Nore = (TextView) v.findViewById(R.id.tv_Nore);
            this.tv_Noche = (TextView) v.findViewById(R.id.tv_Noche);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface OnItemClickListener {

    }

    public void SetOnItemClickListener(FeeDetailSecondPageAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
*/
