package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminResignation;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.maxlore.edumanage.Utility.Constants.context;

/**
 * Created by maxlore on 10-Mar-17.
 */

public class AdminResignationAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Activity activity;
    private List<AdminResignation> arrayList;

    public AdminResignationAdapter(Activity activity, ArrayList<AdminResignation> arrayList) {
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
        AdminResignation adminResignation = arrayList.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_admin_tc, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_appliedon = (TextView) convertView.findViewById(R.id.tv_appliedon);
            holder.tv_empno = (TextView) convertView.findViewById(R.id.tv_empno);
            holder.imageview=(ImageView)convertView.findViewById(R.id.imageview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(("" + adminResignation.getEmployeeName()));

        holder.tv_appliedon.setText("" + adminResignation.getAppliedOn());
        holder.tv_empno.setText("" + adminResignation.getEmpCode());
        Picasso.with(context).load(adminResignation.getImage()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.imageview);


        return convertView;
    }

    static class ViewHolder {
        ImageView imageview;
        TextView tv_name, tvLeaveType, tvclass,tv_empno,tv_appliedon;
    }
}
