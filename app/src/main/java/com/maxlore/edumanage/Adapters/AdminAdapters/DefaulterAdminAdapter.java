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
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels.DefaulterDatamodel;
import com.maxlore.edumanage.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.maxlore.edumanage.Utility.Constants.context;

/**
 * Created by Shrey on 24-Jul-17.
 */

public class DefaulterAdminAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Activity activity;
    private List<DefaulterDatamodel> arrayList;


    public DefaulterAdminAdapter(Activity activity, List<DefaulterDatamodel> arrayList) {
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

        DefaulterAdminAdapter.ViewHolder holder;

        DefaulterDatamodel defaulterDatamodel = arrayList.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_payment_defaulter, null);
            holder = new DefaulterAdminAdapter.ViewHolder();
            holder.tv_studentnamedefault = (TextView) convertView.findViewById(R.id.tv_studentnamedefault);
            holder.tv_classdefault = (TextView) convertView.findViewById(R.id.tv_classdefault);
            holder.tv_paymentfordefault = (TextView) convertView.findViewById(R.id.tv_paymentfordefault);
            holder.imageviewdefault = (ImageView) convertView.findViewById(R.id.imageviewdefault);

            convertView.setTag(holder);

        } else {
            holder = (DefaulterAdminAdapter.ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load( defaulterDatamodel.getPhoto()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.imageviewdefault);
        holder.tv_studentnamedefault.setText(("" + defaulterDatamodel.getStudentName()));
        holder.tv_classdefault.setText("" + defaulterDatamodel.getStudentClass());
/*        holder.tv_paymentfor.setText(("" + defaulterDatamodel.getPaymentFor()));*/
        return convertView;
    }

    static class ViewHolder {
        TextView tv_studentnamedefault;
        TextView tv_classdefault;
        TextView tv_paymentfordefault;
        ImageView imageviewdefault;

    }

}

