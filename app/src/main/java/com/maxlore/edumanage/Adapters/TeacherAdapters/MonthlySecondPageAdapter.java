package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.TextView;

import com.maxlore.edumanage.Models.TeacherModels.Academics.DailyTopic;
import com.maxlore.edumanage.R;

import java.util.ArrayList;

/**
 * Created by maxlore on 22-Oct-16.
 */

public class MonthlySecondPageAdapter extends RecyclerView.Adapter<MonthlySecondPageAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<DailyTopic> arrayList;
    public static MonthlySecondPageAdapter.OnItemClickListener mItemClickListener;

    public MonthlySecondPageAdapter(Activity activity, ArrayList<DailyTopic> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MonthlySecondPageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_academics_details_for_month_secondpage, parent, false);
        MonthlySecondPageAdapter.ViewHolder vh = new MonthlySecondPageAdapter.ViewHolder(v);

        return vh;
    }


    @Override
    public void onBindViewHolder(MonthlySecondPageAdapter.ViewHolder holder, final int position) {

        DailyTopic dailyTopic = arrayList.get(position);

        holder.tv_Date.setText(dailyTopic.getDate());
        holder.tv_topicnodetail.setText(dailyTopic.getLessonName());

    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        EditText  tv_topicnodetail;
        TextView tv_Date;
        View canceladdsubtopics;


        public ViewHolder(View v) {
            super(v);

            this.tv_Date = (TextView) v.findViewById(R.id.tv_Date);

            this.tv_topicnodetail = (EditText) v.findViewById(R.id.tv_topicnodetail);

            this.canceladdsubtopics = (View) v.findViewById(R.id.canceladdsubtopics);
            ViewParent parent = canceladdsubtopics.getParent();
            parent.clearChildFocus(canceladdsubtopics);
            this.canceladdsubtopics.setOnClickListener(this);
            this.tv_Date.setOnClickListener(this);
            this.tv_topicnodetail.setFocusable(true);
            this.tv_topicnodetail.requestFocus();
            this.tv_topicnodetail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mItemClickListener.onAddSubTopic(s+"", getAdapterPosition());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.canceladdsubtopics:
                    mItemClickListener.onCancel(v, getAdapterPosition());
                    break;
                case R.id.tv_Date:
                    mItemClickListener.onDateSelect(v,getAdapterPosition());
                    break;

                default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        public void onCancel(View view, int position);

        public void onItemClick(View view, int position);

        public void onAddSubTopic(String s,int position);

        public void onDateSelect(View view,int position);

    }

    public void SetOnItemClickListener(MonthlySecondPageAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}

