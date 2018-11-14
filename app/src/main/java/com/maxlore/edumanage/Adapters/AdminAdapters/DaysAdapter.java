package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.maxlore.edumanage.Activities.AdminActivities.FilterOptionsActivity;
import com.maxlore.edumanage.R;

import java.util.ArrayList;

/**
 * Created by Shrey on 26-Jul-17.
 */

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ViewHolder> {

    private ArrayList<String> arrayList;
    private FilterOptionsActivity activity;
    public static DaysAdapter.OnItemClickListener mItemClickListener;

    public DaysAdapter(Activity activity, ArrayList<String> arrayList) {
        this.arrayList = arrayList;
        this.activity = (FilterOptionsActivity) activity;
    }

    @Override
    public DaysAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daysfilteritem, parent, false);
        DaysAdapter.ViewHolder vh = new DaysAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(DaysAdapter.ViewHolder holder, final int position) {
        String data = arrayList.get(position);
        holder.tv_type.setText(data);
       /* Picasso.with(context).load(Constants.HostImage + substituteTeacher.getPhoto()).resize(50, 50)
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
        }*/

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_type;
        RadioButton checkBox;
        private ImageView imageview;

        public ViewHolder(View v) {
            super(v);

            this.tv_type = (TextView) v.findViewById(R.id.tv_type);
            this.checkBox = (RadioButton) v.findViewById(R.id.checkbox);
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

    public void SetOnItemClickListener(DaysAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}