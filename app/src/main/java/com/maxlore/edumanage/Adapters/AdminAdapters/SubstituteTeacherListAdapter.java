package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Activities.AdminActivities.SubstituteTeacherPageThree;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.TeacherSubstitutePagetwo.SubstituteTeacher;
import com.maxlore.edumanage.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.maxlore.edumanage.Utility.Constants.context;

public class SubstituteTeacherListAdapter extends RecyclerView.Adapter<SubstituteTeacherListAdapter.ViewHolder> {

    private ArrayList<SubstituteTeacher> arrayList;
    private SubstituteTeacherPageThree activity;
    public static OnItemClickListener mItemClickListener;
    private Integer sectionid;
    private Integer subjectid;

    public SubstituteTeacherListAdapter(Activity activity, ArrayList<SubstituteTeacher> arrayList, Integer sectionid, Integer subjectid) {
        this.arrayList = arrayList;
        this.activity = (SubstituteTeacherPageThree) activity;
        this.sectionid = sectionid;
        this.subjectid = subjectid;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_substitutepagetwoteacher, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        SubstituteTeacher substituteTeacher = arrayList.get(position);
        holder.tv_teacherfirstname.setText(substituteTeacher.getEmployeeFirstName());
        Picasso.with(context).load(substituteTeacher.getPhoto()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.imageview);
        holder.selectteacher1.setChecked(substituteTeacher.isSelected());
        if (substituteTeacher.getClasssectionid() == sectionid && substituteTeacher.getClasssubjectid() == subjectid) {
            if (substituteTeacher.getTeachersubstitutionid() == null) {
                substituteTeacher.setSelected(false);
            } else {
                substituteTeacher.setSelected(true);
            }
        } else {
            substituteTeacher.setSelected(false);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_teacherfirstname;
        RadioButton selectteacher1;
        private ImageView imageview;

        public ViewHolder(View v) {
            super(v);

            this.tv_teacherfirstname = (TextView) v.findViewById(R.id.tv_teacherfirstname);
            this.imageview = (ImageView) v.findViewById(R.id.imageview);
            this.selectteacher1 = (RadioButton) v.findViewById(R.id.selectteacher1);
            this.selectteacher1.setOnClickListener(this);
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

    public void SetOnItemClickListener(SubstituteTeacherListAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}