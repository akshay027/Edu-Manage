package com.maxlore.edumanage.Adapters.ParentAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxlore.edumanage.Models.ParentModels.ParentFees.AcademicBusData;
import com.maxlore.edumanage.R;

import java.util.List;

/**
 * Created by maxlore on 22-Aug-17.
 */

public class AcademicBusfeeAdapter extends RecyclerView.Adapter<AcademicBusfeeAdapter.ViewHolder> {

    private List<AcademicBusData> arrayList;
    private Activity activity;
    public static AcademicBusfeeAdapter.OnItemClickListener mItemClickListener;

    public AcademicBusfeeAdapter(Activity activity, List<AcademicBusData> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public AcademicBusfeeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_bus_fee_academic_data, parent, false);
        AcademicBusfeeAdapter.ViewHolder vh = new AcademicBusfeeAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(AcademicBusfeeAdapter.ViewHolder holder, final int position) {

        AcademicBusData termFeeData = arrayList.get(position);

        holder.tv_totalamount.setText(termFeeData.getAcademicFeeAmount().toString());
        holder.tv_paidamount.setText(termFeeData.getPaid().toString());
        holder.tv_remainingamount.setText(termFeeData.getBalance().toString());
        holder.tv_latefee.setText(termFeeData.getLateFeeFineAmount().toString());
        holder.tv_term_name.setText(termFeeData.getName());
        holder.tv_busfeeamounta.setText(termFeeData.getBusFeeAmount().toString());
        if (termFeeData.getBalance().toString().equalsIgnoreCase("0.0")) {
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
        TextView tv_totalamount, tv_paidamount, tv_remainingamount, tv_latefee, tv_term_name, tv_concession,tv_busfeeamounta;
        TextView btnpayfeesa, btnfeecom, etamountfees1;

        public ViewHolder(View v) {
            super(v);
            this.etamountfees1 = (TextView) v.findViewById(R.id.etamountfees1);
            this.tv_busfeeamounta = (TextView) v.findViewById(R.id.tv_busfeeamounta);
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
                    mItemClickListener.onPaymentFines(v, getAdapterPosition(), String.valueOf(etamountfees1.getText()));
                    break;
            }
        }
    }

    public interface OnItemClickListener {

        public void onPaymentFines(View view, int position, String amount);

    }

    public void SetOnItemClickListener(AcademicBusfeeAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
