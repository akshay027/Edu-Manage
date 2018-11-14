package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Activities.AdminActivities.AssignClassteacher;
import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.ClassteacherModel.ClassTeacher;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.maxlore.edumanage.Utility.Constants.context;

/**
 * Created by maxlore on 28-Sep-16.
 */
public class AssignClassTeacherAdapter extends RecyclerView.Adapter<AssignClassTeacherAdapter.ViewHolder> {

    private ArrayList<ClassTeacher> arrayList;
    private AssignClassteacher activity;
    public static OnItemClickListener mItemClickListener;

    public AssignClassTeacherAdapter(Activity activity, ArrayList<ClassTeacher> arrayList) {
        this.arrayList = arrayList;
        this.activity = (AssignClassteacher) activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_assignclassteacher, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        ClassTeacher classTeacher = arrayList.get(position);

        holder.tv_standard.setText(classTeacher.getStandardName());
        holder.tv_section.setText(classTeacher.getSectionName());
        Picasso.with(context).load(classTeacher.getPhoto()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.imageview);
        if (classTeacher.getClassTeacherStatus() == 0) {
            holder.tv_classteachername.setText("---------");
            holder.btnlayout.setVisibility(View.VISIBLE);
            holder.editdeletelv.setVisibility(View.GONE);
        } else if (classTeacher.getClassTeacherStatus() == 1){
            holder.tv_classteachername.setText(classTeacher.getClassTeacherName());
            holder.editdeletelv.setVisibility(View.VISIBLE);
            holder.btnlayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_standard, tv_section, tv_classteachername;
        ImageView edit_classteacher,imageview;
        ImageView delete_Classteacher;
        TextView Assign_classteacher;
        LinearLayout btnlayout,editdeletelv;

        public ViewHolder(View v) {
            super(v);

            this.tv_standard = (TextView) v.findViewById(R.id.tv_standard);
            this.btnlayout =(LinearLayout)v.findViewById(R.id.btnlayout);
            this.tv_section = (TextView) v.findViewById(R.id.tv_section);
            this.tv_classteachername = (TextView) v.findViewById(R.id.tv_classteachername);
            this.edit_classteacher = (ImageView) v.findViewById(R.id.edit_classteacher);
            this.delete_Classteacher = (ImageView) v.findViewById(R.id.delete_Classteacher);
            this.Assign_classteacher = (TextView) v.findViewById(R.id.Assign_classteacher);
            this.editdeletelv = (LinearLayout) v.findViewById(R.id.editdeletelv);
            this.imageview = (ImageView) v.findViewById(R.id.imageview);

            this.edit_classteacher.setOnClickListener(this);
            this.delete_Classteacher.setOnClickListener(this);
            this.Assign_classteacher.setOnClickListener(this);
            v.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.edit_classteacher:
                    mItemClickListener.onEditClassTeacher(v, getAdapterPosition());
                    break;
                case R.id.delete_Classteacher:
                    mItemClickListener.onDeleteClassTeacher(v, getAdapterPosition());
                    break;
                case R.id.Assign_classteacher:
                    mItemClickListener.OnAssignClassTeacher(v, getAdapterPosition());
                    break;
                default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);

        public void onEditClassTeacher(View view, int position);

        public void onDeleteClassTeacher(View view, int position);

        public void OnAssignClassTeacher(View view, int position);
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
