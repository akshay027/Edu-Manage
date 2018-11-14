package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Activities.AdminActivities.AssignSubstituteTeacherActivity;
import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.SubstituteTeacher.Absentlist;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.maxlore.edumanage.Utility.Constants.context;

/**
 * Created by maxlore on 17-Oct-16.
 */

/**
 * Created by maxlore on 28-Sep-16.
 */
public class SubstituteTeacherPageone extends RecyclerView.Adapter<SubstituteTeacherPageone.ViewHolder> {

    private ArrayList<Absentlist> arrayList;
    private AssignSubstituteTeacherActivity activity;
    public static OnItemClickListener mItemClickListener;

    public SubstituteTeacherPageone(Activity activity, ArrayList<Absentlist> arrayList) {
        this.arrayList = arrayList;
        this.activity = (AssignSubstituteTeacherActivity) activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_absentteacher, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Absentlist absentlist = arrayList.get(position);

        holder.tvabsentteachername.setText(absentlist.getEmployeeName());
        Picasso.with(context).load(absentlist.getImage()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.imageview);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvabsentteachername;
        ImageView imageview;

        public ViewHolder(View v) {
            super(v);
            this.imageview = (ImageView) v.findViewById(R.id.imageview);
            this.tvabsentteachername = (TextView) v.findViewById(R.id.tvabsentteachername);
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
}
