package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAssignSubject.AssignSubject;


import java.util.List;

/**
 * Created by maxlore on 9/27/2016.
 */
public class AdminAssignSubjectDetailsAdapter extends RecyclerView.Adapter<AdminAssignSubjectDetailsAdapter.ViewHolder> {

    private List<AssignSubject> arrayList;
    private Activity activity;
    public static OnItemClickListener mItemClickListener;

    public AdminAssignSubjectDetailsAdapter(Activity activity, List<AssignSubject> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_admin_assign_subject_details, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        AssignSubject assignSubject = arrayList.get(position);

        holder.tvSubjectname.setText("" + assignSubject.getSubjectName());
        if (assignSubject.getFlag() == 0) {
            holder.cheakbox.setChecked(false);
        } else if ((assignSubject.getFlag() == 1)) {
            holder.cheakbox.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvSubjectname;
        CheckBox cheakbox;


        public ViewHolder(View v) {
            super(v);

            this.tvSubjectname = (TextView) v.findViewById(R.id.tvSubjectname);
            this.cheakbox = (CheckBox) v.findViewById(R.id.cheakbox);

            this.cheakbox.setOnClickListener(this);
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

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}




