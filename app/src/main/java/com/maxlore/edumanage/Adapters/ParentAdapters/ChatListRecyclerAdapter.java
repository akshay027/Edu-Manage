package com.maxlore.edumanage.Adapters.ParentAdapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.maxlore.edumanage.Models.TeacherModels.ChatDriver;
import com.maxlore.edumanage.R;

import java.util.ArrayList;


/**
 * Created by Nikhil on 24-11-2015.
 */
public class ChatListRecyclerAdapter extends RecyclerView.Adapter<ChatListRecyclerAdapter.ViewHolder> {

    private ArrayList<ChatDriver> arrayList;
    private Activity activity;
    private boolean isAmPm, isHoliday;
    static OnItemClickListener mItemClickListener;

    public ChatListRecyclerAdapter(Activity activity, ArrayList<ChatDriver> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat_list, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        ChatDriver chatDriver = arrayList.get(position);

      /*  holder.tvLogo.setText("" + chatDriver.getDriverName());*/
        holder.tvDriverName.setText(chatDriver.getDriverName());
        holder.tvStudent.setText(chatDriver.getName());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView tvDriverName, tvStudent, tvLogo;
        public LinearLayout llMessage;

        public ViewHolder(View v) {
            super(v);

            this.tvDriverName = (TextView) v.findViewById(R.id.tvDriverName);
            this.tvStudent = (TextView) v.findViewById(R.id.tvStudent);
            this.tvLogo = (TextView) v.findViewById(R.id.tvLogo);
            v.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            if (mItemClickListener != null) {
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