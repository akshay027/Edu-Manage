package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.maxlore.edumanage.Models.TeacherModels.Academics.Session;
import com.maxlore.edumanage.R;

import java.util.ArrayList;

/**
 * Created by maxlore on 8/10/2016.
 */
public class AcademicsAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<Session> arrayList;

    public AcademicsAdapter(Activity activity, ArrayList<Session> arrayList) {
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

            convertView = inflater.inflate(R.layout.list_item_academics, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.text);
           /* holder.spinner1 = (Spinner) convertView.findViewById(R.id.Dailyspinner1);
           holder.tvTTSection = (TextView) convertView.findViewById(R.id.tvTTSection);
            holder.tvTTPeriod = (TextView) convertView.findViewById(R.id.tvTTPeriod);
            holder.tvTTSubject = (TextView) convertView.findViewById(R.id.tvTTSubject);*/

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }
        holder.textView.setText("" + arrayList.get(position));
       /* holder.tvTTStandard.setText("" + arrayList.get(position).getStandard());
        holder.tvTTSection.setText("" + arrayList.get(position).getSection());
        holder.tvTTPeriod.setText("" + arrayList.get(position).getPeriod());
        holder.tvTTSubject.setText("" + arrayList.get(position).getSubject());*/

        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }
}



