package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Activities.AdminActivities.AdminDepartmentHeadfirstPage;
import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.DepartmentHead;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.maxlore.edumanage.Utility.Constants.context;

/**
 * Created by maxlore on 26-Sep-17.
 */

public class DepartmentheadAdapter extends RecyclerView.Adapter<DepartmentheadAdapter.ViewHolder> {

    private ArrayList<DepartmentHead> arrayList;
    private AdminDepartmentHeadfirstPage activity;
    public static OnItemClickListener mItemClickListener;

    public DepartmentheadAdapter(Activity activity, ArrayList<DepartmentHead> arrayList) {
        this.arrayList = arrayList;
        this.activity = (AdminDepartmentHeadfirstPage) activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_deprthead, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        DepartmentHead departmentHead = arrayList.get(position);
        holder.tv_deptname.setText(departmentHead.getDepartmentName());
        Picasso.with(context).load(departmentHead.getPhoto()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.imageview);
        if (departmentHead.getDepartmentHeadStatus() == 0) {
            holder.tv_deptheadname.setText("---------");
            holder.btnlayout.setVisibility(View.VISIBLE);
            holder.editdeletelv.setVisibility(View.GONE);
        } else if (departmentHead.getDepartmentHeadStatus() == 1) {
            holder.tv_deptheadname.setText(departmentHead.getDepartmentHeadName());
            holder.editdeletelv.setVisibility(View.VISIBLE);
            holder.btnlayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_deptheadname, tv_deptname;
        ImageView edit_depthead, imageview;
        ImageView delete_depthead;
        TextView Assign_depthead;
        LinearLayout btnlayout, editdeletelv;

        public ViewHolder(View v) {
            super(v);

            this.tv_deptheadname = (TextView) v.findViewById(R.id.tv_deptheadname);
            this.btnlayout = (LinearLayout) v.findViewById(R.id.btnlayout);
            this.tv_deptname = (TextView) v.findViewById(R.id.tv_deptname);
            this.edit_depthead = (ImageView) v.findViewById(R.id.edit_depthead);
            this.delete_depthead = (ImageView) v.findViewById(R.id.delete_depthead);
            this.Assign_depthead = (TextView) v.findViewById(R.id.Assign_depthead);
            this.editdeletelv = (LinearLayout) v.findViewById(R.id.editdeletelv);
            this.imageview = (ImageView) v.findViewById(R.id.imageview);

            this.edit_depthead.setOnClickListener(this);
            this.delete_depthead.setOnClickListener(this);
            this.Assign_depthead.setOnClickListener(this);
            v.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.edit_depthead:
                    mItemClickListener.onEditDeptHead(v, getAdapterPosition());
                    break;
                case R.id.delete_depthead:
                    mItemClickListener.onDeleteDeptHead(v, getAdapterPosition());
                    break;
                case R.id.Assign_depthead:
                    mItemClickListener.OnAssignDeptHead(v, getAdapterPosition());
                    break;
                default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);

        public void onEditDeptHead(View view, int position);

        public void onDeleteDeptHead(View view, int position);

        public void OnAssignDeptHead(View view, int position);
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
