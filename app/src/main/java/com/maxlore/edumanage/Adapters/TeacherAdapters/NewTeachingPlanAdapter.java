package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Activities.TeacherActivities.NewTeachingPlanActivity;
import com.maxlore.edumanage.Models.TeacherModels.Newteachingplan.NewTeachingPlanData;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.maxlore.edumanage.Utility.Constants.context;

/**
 * Created by maxlore on 11-Sep-17.
 */

public class NewTeachingPlanAdapter extends RecyclerView.Adapter<NewTeachingPlanAdapter.ViewHolder> {

    private ArrayList<NewTeachingPlanData> arrayList;

    public static NewTeachingPlanAdapter.OnItemClickListener mItemClickListener;
    private NewTeachingPlanActivity activity;

    public NewTeachingPlanAdapter(Activity activity, ArrayList<NewTeachingPlanData> arrayList) {
        this.activity = (NewTeachingPlanActivity) activity;
        this.arrayList = arrayList;
    }

    @Override
    public NewTeachingPlanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_teachingplan, parent, false);
        NewTeachingPlanAdapter.ViewHolder vh = new NewTeachingPlanAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(NewTeachingPlanAdapter.ViewHolder holder, final int position) {

        NewTeachingPlanData newTeachingPlanData = arrayList.get(position);

        Picasso.with(context).load(newTeachingPlanData.getImage()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.image_teachingplan);
        holder.teachingplan_teachername.setText(newTeachingPlanData.getTeacherName());
        holder.teachingplan_subject.setText(newTeachingPlanData.getSubject());
        holder.teachingplan_classname.setText(newTeachingPlanData.getClassName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView teachingplan_teachername, teachingplan_subject, teachingplan_classname;
        public Button teachingplan_yearly, teachingplan_monthly, teachingplan_daily;
        ImageView image_teachingplan;

        public ViewHolder(View v) {
            super(v);

            this.teachingplan_teachername = (TextView) v.findViewById(R.id.teachingplan_teachername);
            this.teachingplan_subject = (TextView) v.findViewById(R.id.teachingplan_subject);
            this.teachingplan_classname = (TextView) v.findViewById(R.id.teachingplan_classname);

            this.teachingplan_yearly = (Button) v.findViewById(R.id.teachingplan_yearly);
            this.teachingplan_monthly = (Button) v.findViewById(R.id.teachingplan_monthly);
            this.teachingplan_daily = (Button) v.findViewById(R.id.teachingplan_daily);

            this.image_teachingplan = (ImageView) v.findViewById(R.id.image_teachingplan);

            this.teachingplan_yearly.setOnClickListener(this);
            this.teachingplan_monthly.setOnClickListener(this);
            this.teachingplan_daily.setOnClickListener(this);

            v.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.teachingplan_yearly:
                    mItemClickListener.onyearlyselect(v, getAdapterPosition());
                    break;
                case R.id.teachingplan_monthly:
                    mItemClickListener.onmonthlyselect(v, getAdapterPosition());
                    break;
                case R.id.teachingplan_daily:
                    mItemClickListener.ondailyselect(v, getAdapterPosition());
                    break;
            }
        }
    }

    public interface OnItemClickListener {
        public void onyearlyselect(View view, int position);

        public void onmonthlyselect(View view, int position);

        public void ondailyselect(View view, int position);

    }

    public void SetOnItemClickListener(NewTeachingPlanAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}