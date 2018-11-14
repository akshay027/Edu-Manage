package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxlore.edumanage.Models.TeacherModels.StudentDetails.StudentDetail;
import com.maxlore.edumanage.R;

import java.util.ArrayList;

/**
 * Created by maxlore on 8/18/2016.
 */public class OwnRecyclerAdapter extends RecyclerView.Adapter<OwnRecyclerAdapter.ViewHolder> {

    private ArrayList<StudentDetail> arrayList;
    private Activity activity;
    public static OnItemClickListener mItemClickListener;
    private boolean isMorning;

    public OwnRecyclerAdapter(Activity activity, ArrayList<StudentDetail> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student_details, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {



        holder.tvstandard.setText("" + arrayList.get(position).getStandardName());
        holder.tvsection.setText("" + arrayList.get(position).getSectionName());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView tvstandard,tvsection;
        public ImageView imageView ;

        public ViewHolder(View v) {
            super(v);

            this.tvstandard = (TextView) v.findViewById(R.id.tvstandard);
            this.tvsection = (TextView) v.findViewById(R.id.tvsection);
            this.imageView = (ImageView) v.findViewById(R.id.image);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
               /* case R.id:
                    mItemClickListener.onItemClick(v, getAdapterPosition());
                    break;
                default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());*/
            }
        }
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);


    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public ArrayList<StudentDetail> getCurrentDada() {
        return this.arrayList;
    }
}

