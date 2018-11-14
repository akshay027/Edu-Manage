package com.maxlore.edumanage.Adapters.ParentAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxlore.edumanage.Models.ParentModels.ParentFees.Parentfees;
import com.maxlore.edumanage.R;

import java.util.ArrayList;

/**
 * Created by maxlore on 9/8/2016.
 */
public class ParentFeesAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Activity activity;
    private ArrayList<Parentfees> arrayList;
    private String className = "";

    public ParentFeesAdapter(Activity activity, ArrayList<Parentfees> arrayList) {
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
        Parentfees parentfees = arrayList.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_parent_fees, null);
            holder = new ViewHolder();
            holder.llTitle = (LinearLayout) convertView.findViewById(R.id.ll_Title);
            holder.tvname = (TextView) convertView.findViewById(R.id.tv_componentname);
            holder.tvamount = (TextView) convertView.findViewById(R.id.tv_amount);
            holder.tvfeetype = (TextView) convertView.findViewById(R.id.tv_feetype);
            holder.tvfeeamount = (TextView) convertView.findViewById(R.id.tv_feeamount);
            holder.headinglv = (LinearLayout) convertView.findViewById(R.id.headinglv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!parentfees.isA()) {
            parentfees.setA(true);
            if (className.equalsIgnoreCase(parentfees.getFeeType() + parentfees.getFeeTypeAmount())) {
                holder.llTitle.setVisibility(View.GONE);
                holder.headinglv.setVisibility(View.GONE);
            } else {
                className = parentfees.getFeeType() + parentfees.getFeeTypeAmount();
                holder.llTitle.setVisibility(View.VISIBLE);
                holder.headinglv.setVisibility(View.VISIBLE);
                parentfees.setB(true);
            }
        } else {
            if (parentfees.isB()) {
                holder.llTitle.setVisibility(View.VISIBLE);
                holder.headinglv.setVisibility(View.VISIBLE);
            } else {
                holder.llTitle.setVisibility(View.GONE);
                holder.headinglv.setVisibility(View.GONE);
            }
        }


        holder.tvname.setText("" + parentfees.getName());
        holder.tvamount.setText("" + parentfees.getComponentAmount());
        holder.tvfeetype.setText("" + parentfees.getFeeType());
        holder.tvfeeamount.setText("" + parentfees.getFeeTypeAmount());


        return convertView;
    }

    static class ViewHolder {
        TextView tvname, tvamount, tvfeetype, tvfeeamount;
        LinearLayout llTitle, headinglv;
    }
}


