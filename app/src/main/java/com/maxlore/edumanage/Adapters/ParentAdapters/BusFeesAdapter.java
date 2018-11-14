package com.maxlore.edumanage.Adapters.ParentAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxlore.edumanage.Models.ParentModels.ParentFees.TermBusFees;
import com.maxlore.edumanage.R;

import java.util.List;

/**
 * Created by maxlore on 21-Aug-17.
 */

public class BusFeesAdapter extends RecyclerView.Adapter<BusFeesAdapter.ViewHolder> {

    private List<TermBusFees> arrayList;
    private Activity activity;
    public static BusFeesAdapter.OnItemClickListener mItemClickListener;

    public BusFeesAdapter(Activity activity, List<TermBusFees> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public BusFeesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_fine_data, parent, false);
        BusFeesAdapter.ViewHolder vh = new BusFeesAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(BusFeesAdapter.ViewHolder holder, final int position) {

        TermBusFees termBusFees = arrayList.get(position);

        holder.tv_totalamount.setText(termBusFees.getFeeTypeFeeAmount().toString());
        holder.tv_paidamount.setText(termBusFees.getFeeTypeFeePaid().toString());
        holder.tv_remainingamount.setText(termBusFees.getFeeTypeFeeBalance().toString());
        holder.tv_latefee.setVisibility(View.GONE);
        holder.tv_term_name.setText(termBusFees.getName());
        if (termBusFees.getFeeTypeFeeBalance().toString().equalsIgnoreCase("0.0")) {
            holder.btnpayfeesa.setVisibility(View.GONE);
            holder.btnfeecom.setVisibility(View.VISIBLE);
            holder.etamountfees1.setVisibility(View.GONE);
        } else {
            holder.btnpayfeesa.setVisibility(View.VISIBLE);
            holder.btnfeecom.setVisibility(View.GONE);
            holder.etamountfees1.setVisibility(View.VISIBLE);
        }
        if (termBusFees.getSplitPay()) {
            holder.etamountfees1.setVisibility(View.VISIBLE);
        } else {
            holder.etamountfees1.setVisibility(View.GONE);
        }
        holder.concession.setVisibility(View.GONE);
        holder.tv_concession.setVisibility(View.GONE);
        holder.late.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_totalamount, tv_paidamount, tv_remainingamount, tv_latefee, tv_term_name, tv_concession;
        TextView btnpayfeesa, btnfeecom, etamountfees1, concession, late;

        public ViewHolder(View v) {
            super(v);
            this.etamountfees1 = (TextView) v.findViewById(R.id.etamountfees1);
            this.concession = (TextView) v.findViewById(R.id.concession);
            this.late = (TextView) v.findViewById(R.id.late);
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

    public void SetOnItemClickListener(BusFeesAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
