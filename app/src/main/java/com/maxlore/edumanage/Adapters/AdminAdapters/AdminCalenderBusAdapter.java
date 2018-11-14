package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminCalender.AdminBus;

import com.maxlore.edumanage.Utility.Constants;

import java.util.ArrayList;

/**
 * Created by maxlore on 3/28/2017.
 */

public class AdminCalenderBusAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<AdminBus> arrayList;

    public AdminCalenderBusAdapter(Activity activity, ArrayList<AdminBus> arrayList) {
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

            convertView = inflater.inflate(R.layout.list_item_admin_calender_bus, null);
            holder = new ViewHolder();
            holder.tvbusno = (TextView) convertView.findViewById(R.id.tvbusno);
            holder.tvPermit = (TextView) convertView.findViewById(R.id.tvPermit);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.tvbusno.setText(arrayList.get(position).getBusNo());
        if (arrayList.get(position).getBreakFitnessUpto() == (String.valueOf(Constants.TRUE))) {
            holder.tvPermit.setText("Break fitness");
        } else if (arrayList.get(position).getInsuranceUpto() == (String.valueOf(Constants.TRUE))) {
            holder.tvPermit.setText("Insurance");
        } else if (arrayList.get(position).getPermitUpto() == (String.valueOf(Constants.TRUE))) {
            holder.tvPermit.setText("Permit");
        } else if (arrayList.get(position).getPollutionUpto() == (String.valueOf(Constants.TRUE))) {
            holder.tvPermit.setText("Pollution");
        } else if (arrayList.get(position).getTaxPaidUpto() == (String.valueOf(Constants.TRUE))) {
            holder.tvPermit.setText("Tax");
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tvbusno, tvPermit, tvAssSection, tvAssSubject, tvAssAssignment;
    }
}

