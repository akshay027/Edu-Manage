/*
package com.maxlore.edumanage.Activities.ParentActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.ParentAdapters.ParentFeesAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.ParentModels.ParentFees.ParentFeeResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentFees.ParentFeeStructure;
import com.maxlore.edumanage.Models.ParentModels.ParentFees.Parentfees;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ParentFeesActivity extends AppCompatActivity {

    private ParentFeesAdapter parentFeesAdapter;
    private ArrayList<Parentfees> parentfeesArrayList;
    private ArrayList<ParentFeeStructure> parentFeeStructureArrayList;
    private TextView totalamount, tv_nodata;
    private ListView feeslistview;
    private Button btnpayfees;
    private LinearLayout llfees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_fees);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        totalamount = (TextView) findViewById(R.id.totalfee);
        btnpayfees = (Button) findViewById(R.id.btnpayfees);
        llfees = (LinearLayout) findViewById(R.id.llfees);
        tv_nodata = (TextView) findViewById(R.id.tv_nodata);
        feeslistview = (ListView) findViewById(R.id.feelistView);

        parentFeeStructureArrayList = new ArrayList<>();
        parentfeesArrayList = new ArrayList<>();

        parentFeesAdapter = new ParentFeesAdapter(this, parentfeesArrayList);
        feeslistview.setAdapter(parentFeesAdapter);
        parentFeesAdapter.notifyDataSetChanged();
        btnpayfees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), FeesPaymentMOdule.class));
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            }
        });
        getFeesDetail();
    }

    private void bindData() {
        for (int i = 0; i < parentFeeStructureArrayList.size(); i++) {
            totalamount.setText(parentFeeStructureArrayList.get(i).getTotalFee());
            updateData(parentFeeStructureArrayList.get(i));
            break;

        }
    }

    private void updateData(ParentFeeStructure parentFeeStructure) {
        parentfeesArrayList.clear();
        parentfeesArrayList.addAll(parentFeeStructure.getFeeAssign());
        parentFeesAdapter.notifyDataSetChanged();
        if (parentfeesArrayList.size() <= 0) {
            tv_nodata.setVisibility(View.VISIBLE);
            feeslistview.setVisibility(View.GONE);
            llfees.setVisibility(View.GONE);
        } else {
            llfees.setVisibility(View.VISIBLE);
            tv_nodata.setVisibility(View.GONE);
            feeslistview.setVisibility(View.VISIBLE);
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
            alertBox();
        }

        return super.onOptionsItemSelected(item);
    }

    private AlertDialog alertBox() {
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


    private void getFeesDetail() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));

                RetrofitAPI.getInstance(this).getApi().getFeesDetails(params, new Callback<ParentFeeResponse>() {

                    @Override
                    public void success(ParentFeeResponse parentFeeResponse, Response response) {
                        try {
                            if (parentFeeResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                if (parentFeeResponse.getAllowPayment()) {
                                    btnpayfees.setVisibility(View.VISIBLE);
                                    parentfeesArrayList.clear();
                                    parentFeeStructureArrayList.add(parentFeeResponse.getFeesStructure());
                                    bindData();
                                    //updateSpinner();
                                    Log.e("data", "======" + parentFeeStructureArrayList);
                                } else {
                                    btnpayfees.setVisibility(View.GONE);
                                    parentfeesArrayList.clear();
                                    parentFeeStructureArrayList.add(parentFeeResponse.getFeesStructure());
                                    bindData();
                                }

                            } else if (parentFeeResponse.getStatus() == Constants.ERROR) {
                                btnpayfees.setVisibility(View.GONE);
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "" + parentFeeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "" + parentFeeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/
