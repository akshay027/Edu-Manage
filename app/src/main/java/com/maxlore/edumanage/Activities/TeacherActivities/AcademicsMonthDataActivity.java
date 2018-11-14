package com.maxlore.edumanage.Activities.TeacherActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.TeacherAdapters.MonthlyDataAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.Academics.MonthSessionPlan;
import com.maxlore.edumanage.Models.TeacherModels.Academics.MonthSessionResponse;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AcademicsMonthDataActivity extends AppCompatActivity {
    private MonthlyDataAdapter monthlyDataAdapter;
    private RecyclerView rvMonthdetail;
    private String secid, stdid, subid;
    private TextView nodata;
    private ArrayList<MonthSessionPlan> monthSessionArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academics_month_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvMonthdetail = (RecyclerView) findViewById(R.id.rvMonthdetail);
        nodata = (TextView) findViewById(R.id.nodata);

        monthSessionArrayList = new ArrayList<>();
        monthlyDataAdapter = new MonthlyDataAdapter(this, monthSessionArrayList);

        rvMonthdetail.setAdapter(monthlyDataAdapter);
        rvMonthdetail.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvMonthdetail.setLayoutManager(llm);
        monthlyDataAdapter.notifyDataSetChanged();
        try {

            Intent intent = getIntent();
            secid = getIntent().getStringExtra("secid");
            subid = getIntent().getStringExtra("subid");

        } catch (Exception e) {
            e.printStackTrace();
        }
        getMonthData();
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


    public void getMonthData() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                params.put("type", Constants.MONTHFRAGMENT);
                params.put("section_id", secid);
                params.put("subject_id", subid);
                RetrofitAPI.getInstance(this).getApi().getMonthAcademicPlan(params, new Callback<MonthSessionResponse>() {
                    @Override
                    public void success(final MonthSessionResponse monthSessionResponse, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            if (monthSessionResponse.getStatus() == Constants.SUCCESS) {
                                monthSessionArrayList.clear();
                                monthSessionArrayList.addAll(monthSessionResponse.getSessionPlan());
                                rvMonthdetail.setAdapter(monthlyDataAdapter);
                                monthlyDataAdapter.notifyDataSetChanged();

                                if (monthSessionArrayList.size() <= 0) {
                                    nodata.setVisibility(View.VISIBLE);
                                    rvMonthdetail.setVisibility(View.GONE);
                                } else {
                                    nodata.setVisibility(View.GONE);
                                    rvMonthdetail.setVisibility(View.VISIBLE);
                                }
                                Log.e("Example", "--------------- --" + monthSessionArrayList.toString());
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
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
