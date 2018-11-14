package com.maxlore.edumanage.Activities.TeacherActivities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.AdminCalenderArticlesAdapter;
import com.maxlore.edumanage.Adapters.AdminAdapters.AdminCalenderExamScheduleAdapter;
import com.maxlore.edumanage.Adapters.AdminAdapters.AdminCalenderFeeTypeAdapter;
import com.maxlore.edumanage.Adapters.AdminAdapters.AdminCalenderHolidayAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminCalender.AdminCalenderResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminCalender.AdminEvents;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminCalender.Article;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminCalender.CalenderHoliday;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminCalender.ExamSchedule;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminCalender.FeeType;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CalenderEventActivity extends AppCompatActivity {
    private AdminCalenderHolidayAdapter adminCalenderHolidayAdapter;
    private AdminCalenderArticlesAdapter adminCalenderArticlesAdapter;
    private AdminCalenderExamScheduleAdapter adminCalenderExamScheduleAdapter;
    private AdminCalenderFeeTypeAdapter adminCalenderFeeTypeAdapter;

    private ArrayList<CalenderHoliday> calenderHolidayArrayList;
    private ArrayList<Article> articleArrayList;
    private ArrayList<ExamSchedule> examScheduleArrayList;
    private ArrayList<FeeType> feeTypeArrayList;
    private ArrayList<String> arrayList;
    private String date;
    private ListView lvholidays, lvArticles, lvExamSchedule, lvFeeType, lvBuses;
    private TextView tvErrorholiday, tvErrorArticles, tvErrorexamschedule, tvErrorfeetype, tvErrorbus, tvError;
    private LinearLayout llfee, llholiday, llarticles, llexamschedule;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_event);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBar = getSupportActionBar();

        tvError = (TextView) findViewById(R.id.tvError);
        lvFeeType = (ListView) findViewById(R.id.lvFeeType);
        lvholidays = (ListView) findViewById(R.id.lvholidays);
        lvArticles = (ListView) findViewById(R.id.lvArticles);
        lvExamSchedule = (ListView) findViewById(R.id.lvExamSchedule);

        tvErrorholiday = (TextView) findViewById(R.id.tvErrorholiday);
        tvErrorArticles = (TextView) findViewById(R.id.tvErrorArticles);
        tvErrorexamschedule = (TextView) findViewById(R.id.tvErrorexamschedule);
        tvErrorfeetype = (TextView) findViewById(R.id.tvErrorfeetype);

        llfee = (LinearLayout) findViewById(R.id.llfee);
        llholiday = (LinearLayout) findViewById(R.id.llholiday);
        llarticles = (LinearLayout) findViewById(R.id.llarticles);
        llexamschedule = (LinearLayout) findViewById(R.id.llexamschedule);

        feeTypeArrayList = new ArrayList<>();
        articleArrayList = new ArrayList<>();
        examScheduleArrayList = new ArrayList<>();
        calenderHolidayArrayList = new ArrayList<>();

        adminCalenderHolidayAdapter = new AdminCalenderHolidayAdapter(this, calenderHolidayArrayList);
        adminCalenderFeeTypeAdapter = new AdminCalenderFeeTypeAdapter(this, feeTypeArrayList);
        adminCalenderArticlesAdapter = new AdminCalenderArticlesAdapter(this, articleArrayList);
        adminCalenderExamScheduleAdapter = new AdminCalenderExamScheduleAdapter(this, examScheduleArrayList);

        lvholidays.setAdapter(adminCalenderHolidayAdapter);
        lvFeeType.setAdapter(adminCalenderFeeTypeAdapter);
        lvArticles.setAdapter(adminCalenderArticlesAdapter);
        lvExamSchedule.setAdapter(adminCalenderExamScheduleAdapter);

        adminCalenderHolidayAdapter.notifyDataSetChanged();
        adminCalenderFeeTypeAdapter.notifyDataSetChanged();
        adminCalenderArticlesAdapter.notifyDataSetChanged();
        adminCalenderExamScheduleAdapter.notifyDataSetChanged();
        try {

            Intent intent = getIntent();
            date = getIntent().getStringExtra("date");

        } catch (Exception e) {
            e.printStackTrace();
        }
        actionBar.setTitle(date);

        getCalenderDetails();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }


    private void getCalenderDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("date", date);
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                RetrofitAPI.getInstance(this).getApi().getCalenderOthersDetails(params, new Callback<AdminCalenderResponse>() {
                    @Override
                    public void success(AdminCalenderResponse dashRes, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Log.e("API", "dashboardResponses" + dashRes.toString());
                            Log.e("API", "dashboardResponses" + response.getBody());
                            if (dashRes.getStatus() == Constants.SUCCESS) {
                                bindData(dashRes.getEvents());
                            } else {
                                Toast.makeText(getApplicationContext(), "" + dashRes.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void bindData(AdminEvents adminEvents) {
        try {
            calenderHolidayArrayList.clear();
            calenderHolidayArrayList.addAll(adminEvents.getHolidays());
            adminCalenderHolidayAdapter.notifyDataSetChanged();
            //setListViewHeightBasedOnChildren(lvEvent);

            if (calenderHolidayArrayList.size() == 0) {
                //tvErrorholiday.setVisibility(View.VISIBLE);
                // lvholidays.setVisibility(View.GONE);
                llholiday.setVisibility(View.GONE);
            } else {
                //tvErrorholiday.setVisibility(View.GONE);
                // lvholidays.setVisibility(View.VISIBLE);
                llholiday.setVisibility(View.VISIBLE);
            }

            articleArrayList.clear();
            articleArrayList.addAll(adminEvents.getArticles());
            adminCalenderArticlesAdapter.notifyDataSetChanged();


            if (articleArrayList.size() == 0) {
                // tvErrorArticles.setVisibility(View.VISIBLE);
                //lvArticles.setVisibility(View.GONE);
                llarticles.setVisibility(View.GONE);
            } else {
                //tvErrorArticles.setVisibility(View.GONE);
                //lvArticles.setVisibility(View.VISIBLE);
                llarticles.setVisibility(View.VISIBLE);
            }


            feeTypeArrayList.clear();
            feeTypeArrayList.addAll(adminEvents.getFeeTypes());
            adminCalenderFeeTypeAdapter.notifyDataSetChanged();
            // setListViewHeightBasedOnChildren(lvholidays);*/

            if (feeTypeArrayList.size() == 0) {
                //tvErrorfeetype.setVisibility(View.VISIBLE);
                //lvFeeType.setVisibility(View.GONE);
                llfee.setVisibility(View.GONE);
            } else {
                tvErrorfeetype.setVisibility(View.GONE);
                lvFeeType.setVisibility(View.VISIBLE);
                llfee.setVisibility(View.VISIBLE);
            }
            examScheduleArrayList.clear();
            examScheduleArrayList.addAll(adminEvents.getExamSchedules());
            adminCalenderExamScheduleAdapter.notifyDataSetChanged();
            //setListViewHeightBasedOnChildren(lvTimeTable);

            if (examScheduleArrayList.size() == 0) {
                //tvErrorexamschedule.setVisibility(View.VISIBLE);
                //lvExamSchedule.setVisibility(View.GONE);
                llexamschedule.setVisibility(View.GONE);
            } else {
                //tvErrorexamschedule.setVisibility(View.GONE);
                //lvExamSchedule.setVisibility(View.VISIBLE);
                llexamschedule.setVisibility(View.VISIBLE);
            }

            if (calenderHolidayArrayList.size() == 0 && articleArrayList.size() == 0 && feeTypeArrayList.size() == 0 &&
                    examScheduleArrayList.size() == 0) {
                tvError.setVisibility(View.VISIBLE);
            } else {
                tvError.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
