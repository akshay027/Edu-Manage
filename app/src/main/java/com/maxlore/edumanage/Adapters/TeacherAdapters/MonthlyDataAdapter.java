package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxlore.edumanage.Models.TeacherModels.Academics.MonthSessionPlan;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;

import java.util.ArrayList;

/**
 * Created by maxlore on 10/21/2016.
 */

public class MonthlyDataAdapter extends RecyclerView.Adapter<MonthlyDataAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<MonthSessionPlan> arrayList;
    public static MonthlyDataAdapter.OnItemClickListener mItemClickListener;

    public MonthlyDataAdapter(Activity activity, ArrayList<MonthSessionPlan> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MonthlyDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_academics_details_for_month, parent, false);
        MonthlyDataAdapter.ViewHolder vh = new MonthlyDataAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(MonthlyDataAdapter.ViewHolder holder, final int position) {

        MonthSessionPlan monthSessionPlan = arrayList.get(position);


        if (arrayList.get(position).getRemark() == (Constants.NULL_REMARK)) {
            holder.remark_tv.setText("NA");
        } else if (arrayList.get(position).getRemark() == (Constants.UPDATE_REMARK)) {
            holder.remark_tv.setText("Not Started");
        } else if (arrayList.get(position).getRemark() == (Constants.DATA_REMARK)) {
            holder.remark_tv.setText("On Progress");
        } else if (arrayList.get(position).getRemark() == (Constants.NORMAL_REMARK)) {
            holder.remark_tv.setText("Completed");
        }
        holder.tvMonthname.setText("" + arrayList.get(position).getMonthName());
        holder.tvTopicname.setText("" + arrayList.get(position).getTopicName());
        holder.tvSubTopicname.setText("" + arrayList.get(position).getSubTopicName());
        holder.tvTopicno.setText("" + arrayList.get(position).getTopicNo());
        holder.tvWeek.setText("" + arrayList.get(position).getWeek());
        holder.tvsubtopicno.setText("" + arrayList.get(position).getSubTopicNumber());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvMonthname, tvTopicname, tvSubTopicname, tvsubtopicno, tvTopicno, tvWeek, remark_tv;

        public ViewHolder(View v) {
            super(v);

            this.tvMonthname = (TextView) v.findViewById(R.id.tvmonthname);
            this.tvTopicname = (TextView) v.findViewById(R.id.tvtopicname);
            this.tvSubTopicname = (TextView) v.findViewById(R.id.tvsubtopicname);
            this.tvTopicno = (TextView) v.findViewById(R.id.tvtopicno);
            this.tvWeek = (TextView) v.findViewById(R.id.tv_week);
            this.remark_tv = (TextView) v.findViewById(R.id.remark_tv_month);
            this.tvsubtopicno = (TextView) v.findViewById(R.id.tvsubtopicno);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
               /* case R.id.addsubtopics:
                    mItemClickListener.onCreatingsubtopic(v, getAdapterPosition());
                    break;*/
               /* default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());*/
            }
        }
    }

    public interface OnItemClickListener {

       /* public void onItemClick(View view, int position);

        public void onCreatingsubtopic(View view, int position);*/

    }

    public void SetOnItemClickListener(MonthlyDataAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}

