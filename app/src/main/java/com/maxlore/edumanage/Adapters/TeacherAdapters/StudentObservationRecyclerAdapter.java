package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Models.TeacherModels.StudentObservation.Observation;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.maxlore.edumanage.Utility.Constants.context;

/**
 * Created by maxlore on 8/22/2016.
 */
public class StudentObservationRecyclerAdapter extends RecyclerView.Adapter<StudentObservationRecyclerAdapter.ViewHolder> {

    private ArrayList<Observation> arrayList;
    private Activity activity;
    // public static OnItemClickListener mItemClickListener;

    public StudentObservationRecyclerAdapter(Activity activity, ArrayList<Observation> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student_observation, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Observation observation = arrayList.get(position);

        holder.tvTeacherName.setText("" + observation.getTeacherName());
        holder.tvReason.setText(" " + observation.getText());
        holder.tvStandard.setText("" + observation.getStandardsection());
        holder.tvremarkhidden.setText(" " + observation.getIsHiddenFromParent());
        holder.tvdate.setText(" " + observation.getDate());
        Picasso.with(context).load(observation.getImage()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.imageview);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView tvTeacherName, tvReason, tvStandard, tvremarkhidden, tvdate;
        public ImageView imageview;

        public ViewHolder(View v) {
            super(v);

            this.tvTeacherName = (TextView) v.findViewById(R.id.tvwteachersname);
            this.tvReason = (TextView) v.findViewById(R.id.tvwreason);
            this.tvStandard = (TextView) v.findViewById(R.id.tvwstandard);
            this.tvremarkhidden = (TextView) v.findViewById(R.id.tvwremarkhidden);
            this.imageview = (ImageView) v.findViewById(R.id.imageview);
            this.tvdate = (TextView) v.findViewById(R.id.tvdate);

            v.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                   /* case R.id.btnAck:
                        mItemClickListener.onAckClick(v, getAdapterPosition());
                        break;

                    default:
                        mItemClickListener.onItemClick(v, getAdapterPosition());
                }*/
            }
        }


    }
}
