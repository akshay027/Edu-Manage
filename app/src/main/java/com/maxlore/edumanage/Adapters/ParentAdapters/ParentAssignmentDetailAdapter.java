package com.maxlore.edumanage.Adapters.ParentAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Models.ParentModels.ParentAssignmentDetails.ParentAssignment;

import com.maxlore.edumanage.Utility.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.maxlore.edumanage.Utility.Constants.context;

/**
 * Created by maxlore on 8/31/2016.
 */
public class ParentAssignmentDetailAdapter extends RecyclerView.Adapter<ParentAssignmentDetailAdapter.ViewHolder> {

    private List<ParentAssignment> arrayList;
    private Activity activity;
    public static OnItemClickListener mItemClickListener;

    public ParentAssignmentDetailAdapter(Activity activity, List<ParentAssignment> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_parent_assignment, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        ParentAssignment parentAssignment = arrayList.get(position);


        holder.tv_Tittle.setText(parentAssignment.getTitle());
        holder.tv_TeacherName.setText(parentAssignment.getTeacherName());
        holder.tv_Subject.setText(parentAssignment.getSubject());
        holder.tvDate.setText(parentAssignment.getDueDate());
        Picasso.with(context).load(parentAssignment.getPhoto()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.image_viewassign);
        if (parentAssignment.getOffline() == (Constants.FALSE)) {
            holder.tvOffline.setText("NO");
        } else if (parentAssignment.getOffline() == (Constants.TRUE)) {
            holder.tvOffline.setText("YES");
        }

        if (parentAssignment.getFlag().equalsIgnoreCase("true")) {
            holder.assignsubmitted.setVisibility(View.VISIBLE);
            holder.assignnot.setVisibility(View.GONE);
            holder.assignpending.setVisibility(View.GONE);
        } else if (parentAssignment.getFlag().equalsIgnoreCase("false")) {
            holder.assignsubmitted.setVisibility(View.GONE);
            holder.assignpending.setVisibility(View.GONE);
            holder.assignnot.setVisibility(View.VISIBLE);
        } else {
            holder.assignsubmitted.setVisibility(View.GONE);
            holder.assignpending.setVisibility(View.VISIBLE);
            holder.assignnot.setVisibility(View.GONE);
        }
        if (parentAssignment.getFileName().equalsIgnoreCase("No attachment.")) {
            holder.downloadassignment.setVisibility(View.GONE);
        } else {
            holder.downloadassignment.setVisibility(View.VISIBLE);
        }
       /* if (parentAssignment.getFlag() == (Constants.FALSE)) {
            holder.tvAssigned.setText("NO");
        } else if (parentAssignment.getFlag() == (Constants.TRUE)) {
            holder.tvAssigned.setText("YES");
        }
*/
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_TeacherName, tv_Tittle, tv_Subject, tvOffline, tvAssigned, tvDate;
        ImageView assignsubmitted, assignnot, assignpending;
        ImageView image_viewassign, downloadassignment;
        ImageView call_parent;


        public ViewHolder(View v) {
            super(v);

            this.tv_TeacherName = (TextView) v.findViewById(R.id.tv_TeacherNameName);
            this.tv_Tittle = (TextView) v.findViewById(R.id.tv_tittleName);
            /*this.message_parent = (ImageView) v.findViewById(R.id.message_parent);
            this.call_parent = (ImageView) v.findViewById(R.id.call_parent);*/
            this.tv_Subject = (TextView) v.findViewById(R.id.tvSubject);
            this.tvOffline = (TextView) v.findViewById(R.id.tvOffline);
            //  this.tvAssigned = (TextView) v.findViewById(R.id.tvAssigned);
            this.tvDate = (TextView) v.findViewById(R.id.tvDate);
            this.image_viewassign = (ImageView) v.findViewById(R.id.image_viewassign);
            this.downloadassignment = (ImageView) v.findViewById(R.id.downloadassignment);

            this.assignsubmitted = (ImageView) v.findViewById(R.id.assignsubmitted);
            this.assignnot = (ImageView) v.findViewById(R.id.assignnot);
            this.assignpending = (ImageView) v.findViewById(R.id.assignpending);

            this.downloadassignment.setOnClickListener(this);
             /*this.message_parent.setOnClickListener(this);*/
            v.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.downloadassignment:
                    mItemClickListener.onDownload(v, getAdapterPosition());
                    break;
            /*     case R.id.call_parent:
                   *//* call_parent.setTag(new Integer(getAdapterPosition()));*//*
                    mItemClickListener.onCallParent(v
                   , getAdapterPosition());
                    break;*/
                default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);

        public void onDownload(View view, int position);


     /* public void onCallParent(View view, int position);*/
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
