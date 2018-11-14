package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Models.TeacherModels.Leaves.StudentLeave;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.maxlore.edumanage.Utility.Constants.context;


/**
 * Created by Nikhil on 24-11-2015.
 */
public class StudentLeaveRecyclerAdapter extends RecyclerView.Adapter<StudentLeaveRecyclerAdapter.ViewHolder> {

    private ArrayList<StudentLeave> arrayList;
    private Activity activity;
    public static OnItemClickListener mItemClickListener;

    public StudentLeaveRecyclerAdapter(Activity activity, ArrayList<StudentLeave> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student_leave, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        StudentLeave studentLeave = arrayList.get(position);

        holder.tvName.setText(studentLeave.getName());
        holder.tvReason.setText(studentLeave.getReason());
        holder.tvStartDate.setText(studentLeave.getStartDate());
        holder.tvEndDate.setText(studentLeave.getEndDate());
        Picasso.with(context).load(arrayList.get(position).getPhoto()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.imageview);

        if (TextUtils.isEmpty(studentLeave.getAcknowledgement())) {
            holder.btnAckdeny.setVisibility(View.VISIBLE);
            holder.btnAckapprov.setVisibility(View.GONE);
            holder.tvrespond.setVisibility(View.VISIBLE);
            holder.tvAck.setVisibility(View.GONE);
        } else {
            holder.btnAckapprov.setVisibility(View.VISIBLE);
            holder.btnAckdeny.setVisibility(View.GONE);
            holder.tvrespond.setVisibility(View.GONE);
            holder.tvAck.setVisibility(View.VISIBLE);
            holder.tvAck.setText("" + studentLeave.getAcknowledgement());
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView tvName, tvReason, tvStartDate, tvEndDate, tvAck, tvrespond;
        ImageView btnAckdeny, btnAckapprov,imageview;


        public ViewHolder(View v) {
            super(v);

            this.tvName = (TextView) v.findViewById(R.id.tvName);
            this.tvReason = (TextView) v.findViewById(R.id.tvReason);
            this.tvStartDate = (TextView) v.findViewById(R.id.tvStartDate);
            this.tvEndDate = (TextView) v.findViewById(R.id.tvEndDate);

            this.tvAck = (TextView) v.findViewById(R.id.tvAck);
            this.tvrespond = (TextView) v.findViewById(R.id.tvrespond);
            this.btnAckdeny = (ImageView) v.findViewById(R.id.btnAckdeny);
            this.btnAckapprov = (ImageView) v.findViewById(R.id.btnAckapprov);
            this.imageview = (ImageView) v.findViewById(R.id.imageview);
            this.btnAckdeny.setOnClickListener(this);
            this.btnAckapprov.setOnClickListener(this);


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


    public ArrayList<StudentLeave> getCurrentDada() {
        return this.arrayList;
    }

}