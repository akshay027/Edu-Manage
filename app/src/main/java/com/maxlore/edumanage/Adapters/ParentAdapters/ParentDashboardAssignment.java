package com.maxlore.edumanage.Adapters.ParentAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maxlore.edumanage.Models.ParentModels.ParentAssignment;
import com.maxlore.edumanage.R;

import java.util.ArrayList;

/**
 * Created by Nikhil on 05-06-2016.
 */
public class ParentDashboardAssignment extends BaseAdapter {

    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<ParentAssignment> arrayList;

    public ParentDashboardAssignment(Activity activity, ArrayList<ParentAssignment> arrayList) {
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

            convertView = inflater.inflate(R.layout.list_item_parent_dashboard_assignment, null);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvAssTitle);
            holder.tvAssignTeacher = (TextView) convertView.findViewById(R.id.tvAssTeacher);
            holder.tvAssignEndDate = (TextView) convertView.findViewById(R.id.tvAssEndDate);
            holder.tvAssignSubject = (TextView) convertView.findViewById(R.id.tvAssSubject);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.tvTitle.setText(arrayList.get(position).getTitle());
        holder.tvAssignTeacher.setText(arrayList.get(position).getTeacherName());
        holder.tvAssignEndDate.setText(arrayList.get(position).getEndDate());
        holder.tvAssignSubject.setText(arrayList.get(position).getSubject());

        return convertView;
    }

    static class ViewHolder {
        TextView tvTitle, tvAssignTeacher, tvAssignEndDate, tvAssignSubject;
    }
}
