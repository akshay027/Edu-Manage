package com.maxlore.edumanage.Adapters.ParentAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxlore.edumanage.Models.ParentModels.ParentFees.TermFeeData;
import com.maxlore.edumanage.R;

import java.util.List;

/**
 * Created by Shrey on 01-Aug-17.
 */

public class FeeFinesAdapter extends RecyclerView.Adapter<FeeFinesAdapter.ViewHolder> {

    private List<TermFeeData> arrayList;
    private Activity activity;
    public static FeeFinesAdapter.OnItemClickListener mItemClickListener;


    public FeeFinesAdapter(Activity activity, List<TermFeeData> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public FeeFinesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_fine_data, parent, false);
        FeeFinesAdapter.ViewHolder vh = new FeeFinesAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(FeeFinesAdapter.ViewHolder holder, final int position) {

        TermFeeData termFeeData = arrayList.get(position);

        holder.tv_totalamount.setText(termFeeData.getAmount().toString());
        holder.tv_paidamount.setText(termFeeData.getPaid().toString());
        holder.tv_remainingamount.setText(termFeeData.getBalance().toString());
        holder.tv_latefee.setText(termFeeData.getLateFeeFineAmount().toString());
        holder.tv_term_name.setText(termFeeData.getName());
        if (termFeeData.getBalance().toString().equalsIgnoreCase("0.0") || termFeeData.getBalance().toString().equalsIgnoreCase("0")) {
            holder.btnpayfeesa.setVisibility(View.GONE);
            holder.btnfeecom.setVisibility(View.VISIBLE);
            holder.etamountfees1.setVisibility(View.GONE);
        } else {
            holder.btnpayfeesa.setVisibility(View.VISIBLE);
            holder.btnfeecom.setVisibility(View.GONE);
            holder.etamountfees1.setVisibility(View.VISIBLE);
        }
        if (termFeeData.getSplitPay()) {
            holder.etamountfees1.setVisibility(View.VISIBLE);

        } else {
            holder.etamountfees1.setVisibility(View.GONE);


        }
        holder.tv_concession.setText(termFeeData.getConsessionAmount().toString());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_totalamount, tv_paidamount, tv_remainingamount, tv_latefee, tv_term_name, tv_concession;
        TextView btnpayfeesa, btnfeecom, etamountfees1;

        public ViewHolder(View v) {
            super(v);
            this.etamountfees1 = (TextView) v.findViewById(R.id.etamountfees1);
            this.tv_totalamount = (TextView) v.findViewById(R.id.tv_totalamounta);
            this.tv_paidamount = (TextView) v.findViewById(R.id.tv_paidamounta);
            this.tv_remainingamount = (TextView) v.findViewById(R.id.tv_remainingamounta);
            this.btnpayfeesa = (TextView) v.findViewById(R.id.btnpayfeesa);
            this.tv_latefee = (TextView) v.findViewById(R.id.tv_latefeea);
            this.tv_term_name = (TextView) v.findViewById(R.id.tv_term_namea);
            this.tv_concession = (TextView) v.findViewById(R.id.tv_concessiona);
            this.btnfeecom = (TextView) v.findViewById(R.id.btnfeecom);
            this.btnpayfeesa.setOnClickListener(this);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btnpayfeesa:
                    mItemClickListener.onPaymentFines(v, getAdapterPosition(), String.valueOf(etamountfees1.getText().toString()));
                    break;
            }
        }
    }

    public interface OnItemClickListener {

        public void onPaymentFines(View view, int position, String amount);

    }

    public void SetOnItemClickListener(FeeFinesAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
