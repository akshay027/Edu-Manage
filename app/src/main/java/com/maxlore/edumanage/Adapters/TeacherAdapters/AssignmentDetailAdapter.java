package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Activities.TeacherActivities.AssignmentDetail;
import com.maxlore.edumanage.Models.TeacherModels.AssignmentDetail.Student;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by maxlore on 11-Aug-16.
 */
public class AssignmentDetailAdapter extends RecyclerView.Adapter<AssignmentDetailAdapter.ViewHolder> {

    private ArrayList<Student> arrayList;
    private AssignmentDetail activity;
    public static OnItemClickListener mItemClickListener;
    private Context context;

    public AssignmentDetailAdapter(Activity activity, ArrayList<Student> arrayList, Context context) {
        this.arrayList = arrayList;
        this.activity = (AssignmentDetail) activity;
        this.context=context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_assignmentdetail, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Student student = arrayList.get(position);


        if (student.getStatus() == Constants.STATUSCOMPLETED) {
            holder.viewAssignmentcompletion.setBackgroundColor(activity.getResources().getColor(R.color.green));
        } else if (student.getStatus() == Constants.STATUSNOTCOMPLETED) {
            holder.viewAssignmentcompletion.setBackgroundColor(activity.getResources().getColor(R.color.red));
        } else {
            holder.viewAssignmentcompletion.setBackgroundColor(activity.getResources().getColor(R.color.textBlackLight));
        }


        holder.tv_contactNumber.setText(student.getContactNo());
        holder.tv_studentName.setText(student.getName());
        holder.tv_parentName.setText(student.getParentName());
        Picasso.with(context).load(student.getPhoto()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.imageview);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_studentName, tv_parentName, tv_contactNumber;
        View viewAssignmentcompletion;
        ImageView message_parent, imageview;
        ImageView call_parent;
        public LinearLayout llassignmentdetail;

        public ViewHolder(View v) {
            super(v);

            this.tv_studentName = (TextView) v.findViewById(R.id.tv_studentName);
            this.tv_parentName = (TextView) v.findViewById(R.id.tv_parentName);
            this.tv_contactNumber = (TextView) v.findViewById(R.id.tv_contactNumber);
            this.message_parent = (ImageView) v.findViewById(R.id.message_parent);
            this.imageview = (ImageView) v.findViewById(R.id.imageview);
            this.call_parent = (ImageView) v.findViewById(R.id.call_parent);
            this.viewAssignmentcompletion = (View) v.findViewById(R.id.viewAssignmentcompletion);
            this.llassignmentdetail = (LinearLayout) v.findViewById(R.id.llassignmentdetails);


            this.viewAssignmentcompletion.setOnClickListener(this);
            this.call_parent.setOnClickListener(this);
            this.message_parent.setOnClickListener(this);
            v.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.viewAssignmentcompletion:
                    mItemClickListener.onAssignmentcompleted(v, getAdapterPosition());
                    break;
                case R.id.message_parent:
                    mItemClickListener.onMessageParent(v, getAdapterPosition());
                    break;
                case R.id.call_parent:
                   /* call_parent.setTag(new Integer(getAdapterPosition()));*/
                    mItemClickListener.onCallParent(v, getAdapterPosition());
                    break;
                default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);

        public void onAssignmentcompleted(View view, int position);

        public void onMessageParent(View view, int position);

        public void onCallParent(View view, int position);
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
