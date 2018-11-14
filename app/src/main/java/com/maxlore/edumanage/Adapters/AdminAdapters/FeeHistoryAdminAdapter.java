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
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels.PaymentHistorymodel;
import com.maxlore.edumanage.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.maxlore.edumanage.Utility.Constants.context;

/**
 * Created by Shrey on 21-Jul-17.
 */

public class FeeHistoryAdminAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Activity activity;
    private List<PaymentHistorymodel> arrayList;


    public FeeHistoryAdminAdapter(Activity activity, List<PaymentHistorymodel> arrayList) {
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

        FeeHistoryAdminAdapter.ViewHolder holder;

        PaymentHistorymodel paymentHistorymodel = arrayList.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_payment_history_data, null);
            holder = new FeeHistoryAdminAdapter.ViewHolder();
            holder.tv_studentname = (TextView) convertView.findViewById(R.id.tv_studentname);
            holder.tv_class = (TextView) convertView.findViewById(R.id.tv_class);
            holder.tv_paymentfor = (TextView) convertView.findViewById(R.id.tv_paymentfor);
            holder.tv_recieptno = (TextView) convertView.findViewById(R.id.tv_recieptno);
            holder.tv_mode = (TextView) convertView.findViewById(R.id.tv_mode);
            holder.tv_recieptdate = (TextView) convertView.findViewById(R.id.tv_recieptdate);
            holder.tv_amount = (TextView) convertView.findViewById(R.id.tv_amount);
            holder.imageview = (ImageView) convertView.findViewById(R.id.imageview);
            holder.paymentsuccess = (ImageView) convertView.findViewById(R.id.paymentsuccess);
            holder.paymentunsuccess = (ImageView) convertView.findViewById(R.id.paymentunsuccess);
            holder.paymentpending = (ImageView) convertView.findViewById(R.id.paymentpending);
            convertView.setTag(holder);

        } else {
            holder = (FeeHistoryAdminAdapter.ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(paymentHistorymodel.getPhoto()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.imageview);
        holder.tv_studentname.setText(("" + paymentHistorymodel.getStudentName()));
        holder.tv_class.setText("" + paymentHistorymodel.getStudentClass());
        holder.tv_paymentfor.setText(("" + paymentHistorymodel.getPaymentFor()));
        holder.tv_recieptno.setText("" + paymentHistorymodel.getRecieptNo());
        holder.tv_mode.setText(("" + paymentHistorymodel.getMode()));
        holder.tv_recieptdate.setText("" + paymentHistorymodel.getRecieptDate());
        holder.tv_amount.setText("" + paymentHistorymodel.getAmount());
        if (paymentHistorymodel.getStatus().equalsIgnoreCase("true"))
        {
            holder.paymentsuccess.setVisibility(View.VISIBLE);
            holder.paymentunsuccess.setVisibility(View.GONE);
            holder.paymentpending.setVisibility(View.GONE);
        }
       else if (paymentHistorymodel.getStatus().equalsIgnoreCase("false"))
        {
            holder.paymentsuccess.setVisibility(View.GONE);
            holder.paymentunsuccess.setVisibility(View.VISIBLE);
            holder.paymentpending.setVisibility(View.GONE);
        }
        else if (paymentHistorymodel.getStatus().equalsIgnoreCase("pending"))
        {
            holder.paymentsuccess.setVisibility(View.GONE);
            holder.paymentunsuccess.setVisibility(View.GONE);
            holder.paymentpending.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tv_studentname;
        TextView tv_class;
        TextView tv_paymentfor;
        TextView tv_recieptno;
        TextView tv_mode;
        TextView tv_recieptdate;
        TextView tv_amount;
        ImageView imageview,paymentsuccess,paymentunsuccess,paymentpending;

    }

}

