package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTable.TeacherTimeTable;
import com.maxlore.edumanage.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.maxlore.edumanage.Utility.Constants.context;

/**
 * Created by maxlore on 10/4/2016.
 */
public class AdminTeacherTimeTableAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Activity activity;
    private List<TeacherTimeTable> arrayList= null;

    public AdminTeacherTimeTableAdapter(Activity activity, List<TeacherTimeTable> arrayList) {
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
            convertView = inflater.inflate(R.layout.list_item_admin_assign_subject, null);
            holder = new ViewHolder();
            holder.tvteachername = (TextView) convertView.findViewById(R.id.tvteachername);
            holder.imageview = (ImageView) convertView.findViewById(R.id.imageview);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvteachername.setText(""+arrayList.get(position).getEmployeeName());
        Picasso.with(context).load(arrayList.get(position).getPhoto()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.imageview);
        return convertView;
    }

    static class ViewHolder {
        TextView tvteachername;
        ImageView imageview;
    }
}

