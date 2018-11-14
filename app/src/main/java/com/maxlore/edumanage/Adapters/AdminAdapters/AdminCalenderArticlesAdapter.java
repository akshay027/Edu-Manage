package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminCalender.Article;


import java.util.ArrayList;

/**
 * Created by maxlore on 3/28/2017.
 */

public class AdminCalenderArticlesAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<Article> arrayList;

    public AdminCalenderArticlesAdapter(Activity activity, ArrayList<Article> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return arrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.list_item_admin_calender_articles, null);
            holder = new ViewHolder();
            holder.tvname = (TextView) convertView.findViewById(R.id.tvname);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.tvname.setText(arrayList.get(position).getName());
        holder.tvDescription.setText(arrayList.get(position).getDescription());

        //    holder.tvAssAssignment.setText(arrayList.get(position).getQuestion());

        return convertView;
    }

    static class ViewHolder {
        TextView tvname, tvDescription;
    }
}
