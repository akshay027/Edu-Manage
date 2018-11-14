package com.maxlore.edumanage.Adapters.ParentAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxlore.edumanage.Models.ParentModels.ParentTransfercertificate.TransferList;
import com.maxlore.edumanage.R;

import java.util.List;

/**
 * Created by maxlore on 8/28/2016.
 */
public class ParentTransferCertificatelistItemAdapter extends RecyclerView.Adapter<ParentTransferCertificatelistItemAdapter.ViewHolder> {

    private Activity activity;
    private List<TransferList> arrayList;
    public static OnItemClickListener mItemClickListener;


    public ParentTransferCertificatelistItemAdapter(Activity activity, List<TransferList> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_transfer_certificate_item, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ParentTransferCertificatelistItemAdapter.ViewHolder holder, int position) {

        if (arrayList.get(position).getStatus().equalsIgnoreCase("rejected")) {
            holder.leaveonhold.setVisibility(View.GONE);
            holder.leavecancelled.setVisibility(View.VISIBLE);
            holder.remarkview.setVisibility(View.GONE);
            holder.ivStatus.setVisibility(View.GONE);

        }
        if (arrayList.get(position).getStatus().equalsIgnoreCase("approved")) {
            holder.leaveonhold.setVisibility(View.GONE);
            holder.leavecancelled.setVisibility(View.GONE);
            holder.remarkview.setVisibility(View.GONE);

            holder.ivStatus.setVisibility(View.VISIBLE);
        } else if (arrayList.get(position).getStatus().equalsIgnoreCase("pending")) {
            holder.leaveonhold.setVisibility(View.VISIBLE);
            holder.leavecancelled.setVisibility(View.GONE);
            holder.ivStatus.setVisibility(View.GONE);
            holder.remarkview.setVisibility(View.VISIBLE);
        }
        holder.tvAppliedBy.setText(arrayList.get(position).getAppliedBy());
        holder.tvApplyDate.setText(arrayList.get(position).getAppliedDate());
        holder.tvReason.setText(arrayList.get(position).getReason());
        holder.tvRemark.setText(arrayList.get(position).getremark());
        holder.tvissueDate.setText(arrayList.get(position).getIssuedDate());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvStudentname, tvAppliedBy, tvApplyDate, tvReason, tvcancel, tvissueDate, tvRemark;
        ImageView remarkview;
        Button applybtn;
        ImageView leavecancelled, leaveonhold, ivStatus, imageview;

        public ViewHolder(View v) {
            super(v);
            this.ivStatus = (ImageView) v.findViewById(R.id.ivStatus);
            this.tvRemark = (TextView) v.findViewById(R.id.tvRemark);
            this.leavecancelled = (ImageView) v.findViewById(R.id.leavecancelled);
            this.leaveonhold = (ImageView) v.findViewById(R.id.leaveonhold);
            this.tvStudentname = (TextView) v.findViewById(R.id.tvName);
            this.tvAppliedBy = (TextView) v.findViewById(R.id.tvAppliedBy);
            this.tvApplyDate = (TextView) v.findViewById(R.id.tvApplyDate);
            this.tvissueDate = (TextView) v.findViewById(R.id.tvissueDate);
            this.tvReason = (TextView) v.findViewById(R.id.tvReason);
            this.remarkview = (ImageView) v.findViewById(R.id.remarkview);
            this.remarkview.setOnClickListener(this);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.remarkview:
                    mItemClickListener.onAckClick(v, getAdapterPosition());
                    break;
            }
        }
    }

    public interface OnItemClickListener {
        public void onAckClick(View s, int position);
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
