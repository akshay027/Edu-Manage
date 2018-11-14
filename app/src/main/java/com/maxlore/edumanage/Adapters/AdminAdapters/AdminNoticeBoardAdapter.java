package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.Notice;


import java.util.ArrayList;

/**
 * Created by maxlore on 9/23/2016.
 */
public class AdminNoticeBoardAdapter extends RecyclerView.Adapter<AdminNoticeBoardAdapter.ViewHolder> {

    private ArrayList<Notice> arrayList;
    private Activity activity;
    public static OnItemClickListener mItemClickListener;


    public AdminNoticeBoardAdapter(Activity activity, ArrayList<Notice> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_admin_notice_board, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Notice notice = arrayList.get(position);

        holder.tvTitleName.setText("" + notice.getTitle());

        holder.tvNoticeDate.setText("" + notice.getPostedOn());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView tvTitleName, tvNoticeDate;
        public ImageView tvedit, tvdelete;


        public ViewHolder(View v) {
            super(v);

            this.tvTitleName = (TextView) v.findViewById(R.id.tvTitleName);
            this.tvNoticeDate = (TextView) v.findViewById(R.id.tvNoticeDate);


            this.tvedit = (ImageView) v.findViewById(R.id.tvedit);
            this.tvdelete = (ImageView) v.findViewById(R.id.tvdelete);


            this.tvdelete.setOnClickListener(this);
            this.tvedit.setOnClickListener(this);


            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.tvdelete:
                    mItemClickListener.onDeleteClick(v, getAdapterPosition());
                    break;
                case R.id.tvedit:
                    mItemClickListener.onEditClick(v, getAdapterPosition());
                    break;
                default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {

        public void onDeleteClick(View view, int position);

        public void onEditClick(View view, int position);

        public void onItemClick(View view, int position);


    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public ArrayList<Notice> getCurrentDada() {
        return this.arrayList;
    }

}
