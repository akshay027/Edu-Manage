package com.maxlore.edumanage.Adapters.ParentAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Activities.ParentActivities.ParentObservation;
import com.maxlore.edumanage.Models.ParentModels.ParentObservation.StudentObservation;

import com.maxlore.edumanage.Utility.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.maxlore.edumanage.Utility.Constants.context;


/**
 * Created by maxlore on 31-Aug-16.
 */
public class ParentObservationAdapter extends RecyclerView.Adapter<ParentObservationAdapter.ViewHolder> {
    private List<StudentObservation> arrayList;
    private ParentObservation activity;
    public static OnItemClickListener mItemClickListener;

    public ParentObservationAdapter(ParentObservation activity, List<StudentObservation> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_studentobservation, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        StudentObservation teacherObservation = arrayList.get(position);

        holder.tv_remarks.setText(teacherObservation.getText());
        holder.tv_teachername.setText(teacherObservation.getTeacherName());
        holder.tv_teachercontactNumber.setText(teacherObservation.getPhoneNo());
        holder.tv_dateobs.setText(teacherObservation.getDate());
        Picasso.with(context).load(teacherObservation.getImage()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.imageviewobs);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_remarks, tv_teachername, tv_teachercontactNumber, tv_dateobs;
        ImageView call_teacherobservation, imageviewobs;
        ImageView message_teacherobservation;

        public ViewHolder(View v) {
            super(v);

            this.tv_teachername = (TextView) v.findViewById(R.id.tv_teachername);
            this.tv_remarks = (TextView) v.findViewById(R.id.tv_remarks);
            this.tv_dateobs = (TextView) v.findViewById(R.id.tv_dateobs);
            this.imageviewobs = (ImageView) v.findViewById(R.id.imageviewobs);
            this.tv_teachercontactNumber = (TextView) v.findViewById(R.id.tv_teachercontactNumber);
            this.call_teacherobservation = (ImageView) v.findViewById(R.id.call_teacherobservation);
            this.message_teacherobservation = (ImageView) v.findViewById(R.id.message_teacherobservation);

            this.call_teacherobservation.setOnClickListener(this);
            this.message_teacherobservation.setOnClickListener(this);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.message_teacherobservation:
                    mItemClickListener.onMessageTeacher(v, getAdapterPosition());
                    break;
                case R.id.call_teacherobservation:
                    mItemClickListener.onCallTeacher(v, getAdapterPosition());
                    break;

            }
        }
    }

    public interface OnItemClickListener {
        public void onMessageTeacher(View view, int position);

        public void onCallTeacher(View view, int position);
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
