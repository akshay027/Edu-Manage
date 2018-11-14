package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxlore.edumanage.Models.TeacherModels.Academics.DailySessionPlan;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;

import java.util.ArrayList;

public class DailyRecyclerAdapter extends RecyclerView.Adapter<DailyRecyclerAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<DailySessionPlan> arrayList;
    public static OnItemClickListener mItemClickListener;

    public DailyRecyclerAdapter(Activity activity, ArrayList<DailySessionPlan> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_academics_details_for_daily, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(DailyRecyclerAdapter.ViewHolder holder, int position) {
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
        if (TextUtils.isEmpty(arrayList.toString())) {
            holder.tvstatus.setVisibility(View.VISIBLE);
            holder.tvstatus.setText("Session Plan Not Created");

        } else {
            holder.tvMonthname.setText("" + arrayList.get(position).getMonthName());
            holder.tvLessonname.setText("" + arrayList.get(position).getLessonName());
            holder.tvTopicname.setText("" + arrayList.get(position).getTopicName());
            holder.tvSubTopicname.setText("" + arrayList.get(position).getSubTopicName());
            holder.tvTopicno.setText("" + arrayList.get(position).getTopicNo());
            holder.tvDate.setText("" + arrayList.get(position).getDate());
            holder.tvWeek.setText("" + arrayList.get(position).getWeek());
            holder.tvsubtopicno.setText("" + arrayList.get(position).getSubTopicNumber());
        }
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvMonthname, tvLessonname, tvTopicname, tvSubTopicname, tvTopicno, tvDate, tvWeek, remark_tv,tvstatus,tvsubtopicno;
        View remark;


        public ViewHolder(View v) {
            super(v);

            this.tvMonthname = (TextView) v.findViewById(R.id.tvmonthname);
            this.tvLessonname = (TextView) v.findViewById(R.id.tvlessonname);
            this.tvTopicname = (TextView) v.findViewById(R.id.tv_topicname);
            this.tvSubTopicname = (TextView) v.findViewById(R.id.tvsubtopicname);
            this.tvTopicno = (TextView) v.findViewById(R.id.tvtopicno);
            this.tvDate = (TextView) v.findViewById(R.id.tvDate);
            this.tvWeek = (TextView) v.findViewById(R.id.tv_week);
            this.remark = (View) v.findViewById(R.id.remarkview_daily);
            this.remark_tv = (TextView) v.findViewById(R.id.remark_tv_daily);
            this.tvstatus = (TextView) v.findViewById(R.id.status_tv);
            this.tvsubtopicno = (TextView) v.findViewById(R.id.tvsubtopicno);

            this.remark.setOnClickListener(this);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.remarkview_daily:
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