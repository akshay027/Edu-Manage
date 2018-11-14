package com.maxlore.edumanage.Adapters.ParentAdapters;


import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxlore.edumanage.Activities.ParentActivities.Parentsyllabus;
import com.maxlore.edumanage.Models.ParentModels.ParentSyllabus.ParentSyllabus;
import com.maxlore.edumanage.R;

import java.util.List;


public class ParentSyllabusAdapter extends RecyclerView.Adapter<ParentSyllabusAdapter.ViewHolder> {
    private List<ParentSyllabus> arrayList;
    private Parentsyllabus activity;
    public static OnItemClickListener mItemClickListener;

    public ParentSyllabusAdapter(Parentsyllabus activity, List<ParentSyllabus> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_parent_syllabus, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        ParentSyllabus syllabu = arrayList.get(position);


        holder.tv_subject.setText(syllabu.getSubjectName());
        holder.tv_subjectcode.setText(syllabu.getCode());
        /*if (syllabu.getSyllabus() == "Syllabus not created.") {
            holder.tv_syllabuspdf.setText(syllabu.getSyllabus());
        } else {*/
        if (TextUtils.isEmpty(syllabu.getFilename())) {
            holder.downloadlv.setVisibility(View.GONE);

        } else {
            holder.downloadlv.setVisibility(View.VISIBLE);
        }
        if (syllabu.getFilename().equalsIgnoreCase("Syllabus not created.")) {
            holder.tv_syllabuspdf.setClickable(false);

        } else {
            holder.tv_syllabuspdf.setClickable(true);
        }

        holder.tv_syllabuspdf.setText(syllabu.getFilename());

        if (syllabu.getHasPracticle() == true) {
            holder.tv_haspractical.setText("YES");
        } else {
            holder.tv_haspractical.setText("NO");
        }


        // holder.tv_haspractical.setText(syllabu.getHasPracticle());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_subject, tv_subjectcode, tv_syllabuspdf, tv_haspractical;
        LinearLayout downloadlv;

        public ViewHolder(View v) {
            super(v);

            this.tv_subject = (TextView) v.findViewById(R.id.tv_subject);
            this.tv_subjectcode = (TextView) v.findViewById(R.id.tv_subjectcode);
            this.tv_syllabuspdf = (TextView) v.findViewById(R.id.tv_syllabuspdf);
            this.tv_haspractical = (TextView) v.findViewById(R.id.tv_haspractical);
            this.downloadlv = (LinearLayout) v.findViewById(R.id.downloadlv);

            this.tv_syllabuspdf.setOnClickListener(this);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.tv_syllabuspdf:
                    mItemClickListener.onDownloadSyllabusTeacher(v, getAdapterPosition());
                    break;
                /*case R.id.call_teacher:
                    mItemClickListener.onCallTeacher(v, getAdapterPosition());
                    break;*/

            }
        }
    }

    public interface OnItemClickListener {

        public void onDownloadSyllabusTeacher(View view, int position);

      /*  public void onCallTeacher(View view, int position);*/
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
