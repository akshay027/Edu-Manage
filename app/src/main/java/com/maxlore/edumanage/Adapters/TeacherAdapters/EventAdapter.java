package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Models.TeacherModels.Event;


import java.util.ArrayList;

/**
 * Created by Nikhil on 05-06-2016.
 */
public class EventAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<Event> arrayList;

    public EventAdapter(Activity activity, ArrayList<Event> arrayList) {
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
            convertView = inflater.inflate(R.layout.list_item_event, null);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvTitle.setText(arrayList.get(position).getTitle());
        holder.tvDate.setText(arrayList.get(position).getDate());

        return convertView;
    }

    static class ViewHolder {
        TextView tvTitle, tvDate;
    }
}
