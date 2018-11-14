package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.maxlore.edumanage.R;

import java.util.ArrayList;

/**
 * Created by maxlore on 27-Jul-17.
 */

public class DaysAdapterPaymentAdmin extends BaseAdapter {

    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<String> arrayList;
    private int position = 1;

    public DaysAdapterPaymentAdmin(Activity activity, ArrayList<String> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

        DaysAdapterPaymentAdmin.ViewHolder holder;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.list_item_time_table_date, null);
            holder = new DaysAdapterPaymentAdmin.ViewHolder();
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);

            convertView.setTag(holder);

        } else {

            holder = (DaysAdapterPaymentAdmin.ViewHolder) convertView.getTag();

        }

        holder.tvDate.setText("" + arrayList.get(position));
        Log.e("postion", "this : " + this.position + " gggg : " + position);

        return convertView;
    }

    static class ViewHolder {
        TextView tvDate;
    }
}
