package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Models.TeacherModels.Assignment;


import java.util.ArrayList;

/**
 * Created by Nikhil on 05-06-2016.
 */
public class DashBoardAssignmentAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<Assignment> arrayList;

    public DashBoardAssignmentAdapter(Activity activity, ArrayList<Assignment> arrayList) {
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

            convertView = inflater.inflate(R.layout.list_item_dashboard_assignment, null);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvAssTitle);
            holder.tvAssStandard = (TextView) convertView.findViewById(R.id.tvAssStandard);
            holder.tvAssSection = (TextView) convertView.findViewById(R.id.tvAssSection);
            holder.tvAssSubject = (TextView) convertView.findViewById(R.id.tvAssSubject);
           // holder.tvAssAssignment = (TextView) convertView.findViewById(R.id.tvAssAssignment);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.tvTitle.setText(arrayList.get(position).getTitle());
        holder.tvAssStandard.setText(arrayList.get(position).getStandard());
        holder.tvAssSection.setText(arrayList.get(position).getSection());
        holder.tvAssSubject.setText(arrayList.get(position).getSubject());
     //    holder.tvAssAssignment.setText(arrayList.get(position).getQuestion());

        return convertView;
    }

    static class ViewHolder {
        TextView tvTitle, tvAssStandard, tvAssSection, tvAssSubject, tvAssAssignment;
    }
}
