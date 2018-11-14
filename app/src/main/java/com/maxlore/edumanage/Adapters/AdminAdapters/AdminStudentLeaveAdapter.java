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
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAbsentLeave;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.maxlore.edumanage.Utility.Constants.context;

/**
 * Created by maxlore on 10-Mar-17.
 */

public class AdminStudentLeaveAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Activity activity;
    private List<AdminAbsentLeave> arrayList;

    public AdminStudentLeaveAdapter(Activity activity, ArrayList<AdminAbsentLeave> arrayList) {
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
        AdminStudentLeaveAdapter.ViewHolder holder;
        AdminAbsentLeave adminAbsentLeave= arrayList.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_admin_student_leave, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);

            holder.tvclass = (TextView) convertView.findViewById(R.id.tvclass);
            holder.tv_empno = (TextView) convertView.findViewById(R.id.tv_empno);
            holder.imageview=(ImageView)convertView.findViewById(R.id.imageview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(("" + adminAbsentLeave.getName()));
        holder.tvclass.setText("" + adminAbsentLeave.getStudentClass());
        holder.tv_empno.setText("" + adminAbsentLeave.getAdmissionNumber());
        Picasso.with(context).load(adminAbsentLeave.getPhoto()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.imageview);


        return convertView;
    }

    static class ViewHolder {
        ImageView imageview;
        TextView tv_name, tvLeaveType, tvclass,tv_empno;
    }
}
