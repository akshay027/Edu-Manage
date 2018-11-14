/*
package com.maxlore.edumanage.Activities.ParentActivities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.ParentAdapters.FeeDetailSecondPageAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.ParentModels.FeeHistory.FeeHistoryStudent;
import com.maxlore.edumanage.Models.ParentModels.FeeHistory.PaymentHistory;
import com.maxlore.edumanage.Models.ParentModels.FeeHistory.PaymentHistoryResponse;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FeeDetailSecondPage extends AppCompatActivity {
    private FeeDetailSecondPageAdapter feeDetailSecondPageAdapter;
    private RecyclerView payhistorysecondrecycler;
    private ArrayList<PaymentHistory> paymentHistorieslist, arrayList;
    private Handler handler;
    public int text;
    private ArrayList<FeeHistoryStudent> studentArrayList;
    private final Context context = this;
    private ArrayAdapter<String> spinneradapter;
    private ArrayList<String> categories;
    private TextView totalfee, totalfeepaid, totalfeedue,tv_nodata;
    public static final int TIME_OUT = 1000;
    private String spiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_detail_second_page);
        payhistorysecondrecycler = (RecyclerView) findViewById(R.id.payhistorysecondrecycler);

        totalfee = (TextView) findViewById(R.id.totalfee_tv);
        totalfeepaid = (TextView) findViewById(R.id.totalfeepaid_tv);
        totalfeedue = (TextView) findViewById(R.id.totalfeedue_tv);
        tv_nodata= (TextView)findViewById(R.id.tv_nodata);

        paymentHistorieslist = new ArrayList<>();
        arrayList = new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        payhistorysecondrecycler.setLayoutManager(llm);

        studentArrayList = new ArrayList<>();

        handler = new Handler();

        feeDetailSecondPageAdapter = new FeeDetailSecondPageAdapter(this, paymentHistorieslist);
        payhistorysecondrecycler.setAdapter(feeDetailSecondPageAdapter);
        categories = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFeeDetail();
    }

    private void getFeeDetail() {
        try{if (UIUtil.isInternetAvailable(this)) {
            UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
            Intent intent = getIntent();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("student_id", getIntent().getStringExtra("stuid"));
            jsonObject.addProperty("fee_type_id", getIntent().getStringExtra("feeid"));
            jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(),Constants.Pref.KEY_BRANCH_ID));
            RetrofitAPI.getInstance(this).getApi().paymentHistoryMethod(jsonObject, new Callback<PaymentHistoryResponse>() {

                @Override
                public void success(PaymentHistoryResponse paymentHistoryResponse, Response response)

                {try{
                    if (paymentHistoryResponse.getStatus() == Constants.SUCCESS) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        paymentHistorieslist.addAll(paymentHistoryResponse.getPaymentHistory());
                        if (paymentHistorieslist.size()<=0)
                        {
                            payhistorysecondrecycler.setVisibility(View.GONE);
                            tv_nodata.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            payhistorysecondrecycler.setVisibility(View.VISIBLE);
                            tv_nodata.setVisibility(View.GONE);
                        }
                        feeDetailSecondPageAdapter.notifyDataSetChanged();
                        totalfee.setText("");
                        totalfeedue.setText("");
                        totalfeepaid.setText("");
                      */
/*  totalfee.setText(getIntent().getStringExtra("tot"));
                        totalfeepaid.setText(getIntent().getStringExtra("deposit"));
                        totalfeedue.setText(getIntent().getStringExtra("due"));*//*


                        totalfee.setText(paymentHistoryResponse.getTotalFee().toString());
                        totalfeepaid.setText(paymentHistoryResponse.getFeeDeposited().toString());
                        totalfeedue.setText(paymentHistoryResponse.getFeeDue().toString());
                    } else {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Toast.makeText(getApplicationContext(), "" + paymentHistoryResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }} catch (Exception e) {
                    e.printStackTrace();
                }
                }

                @Override
                public void failure(RetrofitError error) {
                    UIUtil.stopProgressDialog(getApplicationContext());
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Connect to internet.", Toast.LENGTH_LONG).show();
        }} catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_attendance, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
        }
        if (id == R.id.app_info) {
            alertboxpopup();
        }

        return super.onOptionsItemSelected(item);
    }

    private AlertDialog alertboxpopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View promptView = inflater.inflate(R.layout.list_item_pop_app_info, null);


        builder.setView(promptView)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return builder.create();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }


}*/
