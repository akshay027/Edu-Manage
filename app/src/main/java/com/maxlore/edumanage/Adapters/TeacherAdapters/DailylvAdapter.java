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

public class DailylvAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<Session> arrayList;

    public DailylvAdapter(Activity activity, ArrayList<Session> arrayList) {
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

        YearlvAdapter.ViewHolder holder;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.list_item_academics_details, null);
            holder = new YearlvAdapter.ViewHolder();


            holder.tvSubjectname = (TextView) convertView.findViewById(R.id.tvSubjectname);
            holder.tvstandard = (TextView) convertView.findViewById(R.id.tvstandard);
            holder.tvsection = (TextView) convertView.findViewById(R.id.tvsection);
            //holder.image=(ImageView)convertView.findViewById(R.id.image);



            convertView.setTag(holder);

        } else {

            holder = (YearlvAdapter.ViewHolder) convertView.getTag();

        }


        holder.tvSubjectname.setText("" + arrayList.get(position).getSubjectName());
        holder.tvstandard.setText("" + arrayList.get(position).getStandardName());
        holder.tvsection.setText("" + arrayList.get(position).getSectionName());




        return convertView;
    }

    static class ViewHolder {
        TextView tvSubjectname, tvstandard, tvsection;
      //  ImageView image;


    }

}
