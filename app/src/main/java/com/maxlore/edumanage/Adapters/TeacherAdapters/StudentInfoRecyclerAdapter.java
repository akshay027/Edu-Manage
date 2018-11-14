package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Models.TeacherModels.StudentDetails.StudentInfo;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.maxlore.edumanage.Utility.Constants.context;

/**
 * Created by maxlore on 8/20/2016.
 */
public class StudentInfoRecyclerAdapter extends RecyclerView.Adapter<StudentInfoRecyclerAdapter.ViewHolder> {

    private ArrayList<StudentInfo> arrayList;
    private Activity activity;
    public static OnItemClickListener mItemClickListener;


    public StudentInfoRecyclerAdapter(Activity activity, ArrayList<StudentInfo> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        StudentInfo student = arrayList.get(position);

        holder.tvName.setText("" + student.getName());
        if (TextUtils.isEmpty(student.getParentName())) {
            holder.tvParentName.setVisibility(View.GONE);
        } else {
            holder.tvParentName.setText("" + student.getParentName());
            holder.tvParentName.setVisibility(View.VISIBLE);
        }
        Picasso.with(context).load(student.getPhoto()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.imageview);
        holder.tvContactNumber.setText("" + student.getContactNo());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView tvName, tvContactNumber, tvParentName;
        public ImageView ivCall, ivMessage, imageview;
        public ListView recyclerStudentinfo;

        public ViewHolder(View v) {
            super(v);

            this.tvName = (TextView) v.findViewById(R.id.tvname);
            this.tvContactNumber = (TextView) v.findViewById(R.id.tvContactNumber);
            this.tvParentName = (TextView) v.findViewById(R.id.tvParentName);
            this.imageview = (ImageView) v.findViewById(R.id.imageview);
            this.ivCall = (ImageView) v.findViewById(R.id.ivCall);
            this.ivMessage = (ImageView) v.findViewById(R.id.ivmessage);

            this.recyclerStudentinfo = (ListView) v.findViewById(R.id.recyclerStudentinfo);
            this.ivMessage.setOnClickListener(this);
            this.ivCall.setOnClickListener(this);


            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.ivCall:
                    mItemClickListener.onCallClick(v, getAdapterPosition());
                    break;
                case R.id.ivmessage:
                    mItemClickListener.onMessageClick(v, getAdapterPosition());
                    break;
                default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);

        public void onCallClick(View view, int position);

        public void onMessageClick(View view, int position);


    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public ArrayList<StudentInfo> getCurrentDada() {
        return this.arrayList;
    }

}

