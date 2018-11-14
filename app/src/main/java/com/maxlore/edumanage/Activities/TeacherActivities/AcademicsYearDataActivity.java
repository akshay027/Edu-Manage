package com.maxlore.edumanage.Activities.TeacherActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.TeacherAdapters.YearlyDataAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.Academics.YearSessionPlan;
import com.maxlore.edumanage.Models.TeacherModels.Academics.YearSessionResponse;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AcademicsYearDataActivity extends AppCompatActivity {
    private YearlyDataAdapter yearlyDataAdapter;
    private ListView lvDailydetail, lvMonthdetail, lvYeardetail;
    private LinearLayout linearLayout1, linearLayout2, linearLayout3;
    private String secid, stdid, subid;
    private ArrayList<YearSessionPlan> yearSessionArraylist;
    private TextView nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academics_year_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        yearSessionArraylist = new ArrayList<>();
        lvYeardetail = (ListView) findViewById(R.id.lvYeadetail);
        nodata = (TextView) findViewById(R.id.nodata);
        yearlyDataAdapter = new YearlyDataAdapter(this, yearSessionArraylist);
        lvYeardetail.setAdapter(yearlyDataAdapter);
        yearlyDataAdapter.notifyDataSetChanged();

        try {

            Intent intent = getIntent();
            secid = getIntent().getStringExtra("secid");
            subid = getIntent().getStringExtra("subid");

        } catch (Exception e) {
            e.printStackTrace();
        }
        getYearData();

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


    public void getYearData() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");

                Map<String, String> params = new HashMap<String, String>();
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                params.put("type", Constants.YEARFRAGMENT);
                params.put("section_id", secid);
                params.put("subject_id", subid);

                RetrofitAPI.getInstance(this).getApi().getYearlyplan(params, new Callback<YearSessionResponse>() {
                    @Override
                    public void success(YearSessionResponse yearSessionResponse, Response response) {
                        try {
                            if (yearSessionResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                yearSessionArraylist.clear();
                                yearSessionArraylist.addAll(yearSessionResponse.getTeachingPlan());
                                lvYeardetail.setAdapter(yearlyDataAdapter);
                                yearlyDataAdapter.notifyDataSetChanged();
                                if (yearSessionArraylist.size() <= 0) {
                                    nodata.setVisibility(View.VISIBLE);
                                    lvYeardetail.setVisibility(View.GONE);
                                } else {
                                    nodata.setVisibility(View.GONE);
                                    lvYeardetail.setVisibility(View.VISIBLE);
                                }
                                Log.e("Example", "--------------- --" + yearSessionArraylist.toString());

                            } else {
                                Toast.makeText(AcademicsYearDataActivity.this, "" + yearSessionResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

