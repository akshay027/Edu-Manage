package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTable.ClassTimeTable;

import com.squareup.picasso.Picasso;

import java.util.List;

import static com.maxlore.edumanage.Utility.Constants.context;

/**
 * Created by maxlore on 10/4/2016.
 */
public class AdminClassTimeTableAdapter extends RecyclerView.Adapter<AdminClassTimeTableAdapter.ViewHolder> {

    private List<ClassTimeTable> arrayList;
    private Activity activity;
    public static OnItemClickListener mItemClickListener;
    private boolean isMorning;

    public AdminClassTimeTableAdapter(Activity activity, List<ClassTimeTable> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_admin_class_timetable, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.tvstandard.setText("" + arrayList.get(position).getStandardName());
        holder.tvsection.setText("" + arrayList.get(position).getSectionName());
        holder.tvTeachername.setText("" + arrayList.get(position).getClassTeacherName());
        Picasso.with(context).load(arrayList.get(position).getPhoto()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.imageview);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView tvstandard, tvsection, tvTeachername;
        public ImageView imageview;


        public ViewHolder(View v) {
            super(v);

            this.tvstandard = (TextView) v.findViewById(R.id.tvstandard);
            this.tvsection = (TextView) v.findViewById(R.id.tvsection);
            this.imageview = (ImageView) v.findViewById(R.id.imageview);
            this.tvTeachername = (TextView) v.findViewById(R.id.tvTeachername);

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


    public List<ClassTimeTable> getCurrentDada() {
        return this.arrayList;
    }
}
