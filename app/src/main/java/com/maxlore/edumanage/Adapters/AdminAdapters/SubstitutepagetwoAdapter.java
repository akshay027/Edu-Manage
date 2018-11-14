package com.maxlore.edumanage.Adapters.AdminAdapters;

/**
 * Created by maxlore on 18-Oct-16.
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Activities.AdminActivities.SubstituteTeacherPagetwo;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.TeacherSubstitutePagetwo.SubstituteSubject;


import java.util.ArrayList;

/**
 * Created by maxlore on 17-Oct-16.
 */

/**
 * Created by maxlore on 28-Sep-16.
 */
public class SubstitutepagetwoAdapter extends RecyclerView.Adapter<SubstitutepagetwoAdapter.ViewHolder> {

    private ArrayList<SubstituteSubject> arrayList;
    private SubstituteTeacherPagetwo activity;
    public static OnItemClickListener mItemClickListener;

    public SubstitutepagetwoAdapter(Activity activity, ArrayList<SubstituteSubject> arrayList) {
        this.arrayList = arrayList;
        this.activity = (SubstituteTeacherPagetwo) activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_substitutepagetwoadapter, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        SubstituteSubject substituteSubject = arrayList.get(position);
        holder.Subject_tv.setText(substituteSubject.getSubject());
        holder.tv_fromtime.setText(substituteSubject.getFromTime());
        holder.tv_totime.setText(substituteSubject.getToTime());
        holder.tv_standard.setText(substituteSubject.getStandard());
        holder.tv_section.setText(substituteSubject.getSection());
        holder.tv_period.setText(substituteSubject.getPeriod());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_standard, tv_section, Subject_tv, tv_fromtime, tv_totime,tv_period;

        public ViewHolder(View v) {
            super(v);

            this.tv_standard = (TextView) v.findViewById(R.id.tv_standard);
            this.tv_section = (TextView) v.findViewById(R.id.tv_section);
            this.Subject_tv = (TextView) v.findViewById(R.id.Subject_tv);
            this.tv_fromtime = (TextView) v.findViewById(R.id.tv_fromtime);
            this.tv_totime = (TextView) v.findViewById(R.id.tv_totime);
            this.tv_period = (TextView) v.findViewById(R.id.tv_period);

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
