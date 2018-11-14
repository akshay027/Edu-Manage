package com.maxlore.edumanage.Adapters.ParentAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Activities.ParentActivities.TeacherprofileActivity;
import com.maxlore.edumanage.Models.ParentModels.ParentTeacherProfile.TeacherProfile;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.maxlore.edumanage.Utility.Constants.context;

public class TeacherProfileAdapter extends RecyclerView.Adapter<TeacherProfileAdapter.ViewHolder> {
    private List<TeacherProfile> arrayList;
    private TeacherprofileActivity activity;
    public static OnItemClickListener mItemClickListener;
    private List<String> subjectlist;

    public TeacherProfileAdapter(TeacherprofileActivity activity, List<TeacherProfile> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_parent_teacher_profile, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        TeacherProfile teacher = arrayList.get(position);

        subjectlist = new ArrayList<>();
        holder.tv_teachercontactno.setText(teacher.getPhone());
        holder.tv_teachername.setText(teacher.getName());
        // holder.tv_personalmail.setText(teacher.getPersonalEmail());
        holder.tv_schoolmail.setText(teacher.getSchoolEmail());

        holder.tv_subjectname.setText(teacher.getSubject().toString().replace("[", "").replace("]", ""));
        Picasso.with(context).load(teacher.getPhoto()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.image_viewteacherprofile);

        if (teacher.getClassTeacher() == Constants.TRUE) {
            holder.tv_classteacher.setVisibility(View.VISIBLE);

        } else {
            holder.tv_classteacher.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_teachername, tv_personalmail, tv_schoolmail, tv_teachercontactno, tv_subjectname, tv_classteacher;
        ImageView message_teacher, image_viewteacherprofile;
        ImageView call_teacher;

        public ViewHolder(View v) {
            super(v);

            this.tv_teachername = (TextView) v.findViewById(R.id.tv_teachername);
            // this.tv_personalmail = (TextView) v.findViewById(R.id.tv_personalmail);
            this.tv_schoolmail = (TextView) v.findViewById(R.id.tv_schoolmail);
            this.tv_teachercontactno = (TextView) v.findViewById(R.id.tv_teachercontactNumber);
            this.tv_subjectname = (TextView) v.findViewById(R.id.tv_subjectname);
            this.tv_classteacher = (TextView) v.findViewById(R.id.tv_classteacher);
            this.message_teacher = (ImageView) v.findViewById(R.id.message_teacher);
            this.image_viewteacherprofile = (ImageView) v.findViewById(R.id.image_viewteacherprofile);
            this.call_teacher = (ImageView) v.findViewById(R.id.call_teacher);

            this.call_teacher.setOnClickListener(this);
            this.message_teacher.setOnClickListener(this);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.message_teacher:
                    mItemClickListener.onMessageTeacher(v, getAdapterPosition());
                    break;
                case R.id.call_teacher:
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
