package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxlore.edumanage.Models.TeacherModels.Academics.WeekSessionPlan;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;

import java.util.ArrayList;

/**
 * Created by maxlore on 8/18/2016.
 */
public class WeekRecyclerAdapter extends RecyclerView.Adapter<WeekRecyclerAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<WeekSessionPlan> arrayList;
    public static OnItemClickListener mItemClickListener;

    public WeekRecyclerAdapter(Activity activity, ArrayList<WeekSessionPlan> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_academics_details_for_week, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(WeekRecyclerAdapter.ViewHolder holder, int position) {
        if (arrayList.get(position).getRemark() == (Constants.NULL_REMARK)) {
            holder.remark.setBackgroundColor(activity.getResources().getColor(R.color.gray_light));
            holder.remark_tv.setText("NA");
        } else if (arrayList.get(position).getRemark() == (Constants.UPDATE_REMARK)) {
            holder.remark.setBackgroundColor(activity.getResources().getColor(R.color.skyBlue));
            holder.remark_tv.setText("Not Started");
        } else if (arrayList.get(position).getRemark() == (Constants.DATA_REMARK)) {
            holder.remark.setBackgroundColor(activity.getResources().getColor(R.color.red_dark));
            holder.remark_tv.setText("On Progress");
        } else if (arrayList.get(position).getRemark() == (Constants.NORMAL_REMARK)) {
            holder.remark.setBackgroundColor(activity.getResources().getColor(R.color.green_dark));
            holder.remark_tv.setText("Completed");
        }
        holder.tvMonthname.setText("" + arrayList.get(position).getMonthName());
        holder.tvTopicname.setText("" + arrayList.get(position).getTopicName());
        holder.tvSubTopicname.setText("" + arrayList.get(position).getSubTopicName());
        holder.tvTopicno.setText("" + arrayList.get(position).getTopicNo());
        holder.tvWeek.setText("" + arrayList.get(position).getWeek());
        holder.tvsubtopicno.setText("" + arrayList.get(position).getSubtopicnumber());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvMonthname, tvTopicname, tvSubTopicname, tvTopicno, tvWeek, remark_tv, tvsubtopicno;
        View remark;

        public ViewHolder(View v) {
            super(v);

            this.tvMonthname = (TextView) v.findViewById(R.id.tvmonthname);
            this.tvTopicname = (TextView) v.findViewById(R.id.tvtopicname);
            this.tvSubTopicname = (TextView) v.findViewById(R.id.tvsubtopicname);
            this.tvTopicno = (TextView) v.findViewById(R.id.tvtopicno);
            this.tvWeek = (TextView) v.findViewById(R.id.tv_week);
            this.remark = (View) v.findViewById(R.id.remarkview_week);
            this.remark_tv = (TextView) v.findViewById(R.id.remark_tv_week);
            this.tvsubtopicno = (TextView) v.findViewById(R.id.tvsubtopicnoweek);
            this.remark.setOnClickListener(this);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.remarkview_week:
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

