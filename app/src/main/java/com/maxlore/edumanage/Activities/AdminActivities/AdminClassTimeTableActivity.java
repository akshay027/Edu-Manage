package com.maxlore.edumanage.Activities.AdminActivities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Activities.TeacherActivities.TimeTableDateAdapter;
import com.maxlore.edumanage.Adapters.AdminAdapters.AdminClassNavigationTimeTableAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTableDetails.ClassTimeTableDetail;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTableDetails.ClassTimeTableDetailResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTableDetails.ClassTimeTableDetailStructure;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AdminClassTimeTableActivity extends AppCompatActivity {

    private ListView lvdate;
    private ListView lvperiod;
    private TextView time_tv;
    private List<String> list;
    private ArrayAdapter dayadapter;
    private AdminClassNavigationTimeTableAdapter timeTableAdapter;
    private List<ClassTimeTableDetail> arrayList;
    private ArrayList<ClassTimeTableDetailStructure> structArraylist;
    private ArrayList<ClassTimeTableDetail> timeTableDetailArrayList;
    private TextView noschedule_tv;
    private ArrayAdapter<String> adapter;
    private String id;
    private String currentDay;
    private ArrayList<String> categories;
    private String spiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvdate = (ListView) findViewById(R.id.lvDate);
        time_tv = (TextView) findViewById(R.id.dateday_tv);
        lvperiod = (ListView) findViewById(R.id.lvPeriod);

        noschedule_tv = (TextView) findViewById(R.id.tv_noSchedule);
        timeTableDetailArrayList = new ArrayList<>();
        arrayList = new ArrayList();
        structArraylist = new ArrayList<>();
        categories = new ArrayList<>();

        timeTableAdapter = new AdminClassNavigationTimeTableAdapter(this, arrayList);
        lvperiod.setAdapter(timeTableAdapter);
        timeTableAdapter.notifyDataSetChanged();

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        SimpleDateFormat format = new SimpleDateFormat("dd\n" + "EEEE");
        String[] days = new String[7];
        int weekdays = -calendar.get(GregorianCalendar.DAY_OF_WEEK) + 1;
        calendar.add(Calendar.DAY_OF_MONTH, weekdays);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        currentDay = sdf.format(d);

        ArrayList<String> arrayListDate = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            arrayListDate.add(days[i]);
        }

        final TimeTableDateAdapter dateAdapter = new TimeTableDateAdapter(this, arrayListDate);
        dateAdapter.setPosition(day - 1);

        lvdate.setAdapter(dateAdapter);

        try {
            Intent intent = getIntent();
            id = getIntent().getStringExtra("id");
            Log.e("structArraylist", "----" + structArraylist.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        timeTableDetail();
        lvdate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        currentDay = "Sunday";
                        dateAdapter.setPosition(0);
                        filterDayWise();
                        break;
                    case 1:
                        currentDay = "Monday";
                        dateAdapter.setPosition(1);
                        filterDayWise();
                        break;
                    case 2:
                        currentDay = "Tuesday";
                        dateAdapter.setPosition(2);
                        filterDayWise();
                        break;
                    case 3:
                        currentDay = "Wednesday";
                        dateAdapter.setPosition(3);
                        filterDayWise();
                        break;
                    case 4:
                        currentDay = "Thursday";
                        dateAdapter.setPosition(4);
                        filterDayWise();
                        break;
                    case 5:
                        currentDay = "Friday";
                        dateAdapter.setPosition(5);
                        filterDayWise();
                        break;
                    case 6:
                        currentDay = "Saturday";
                        dateAdapter.setPosition(6);
                        filterDayWise();
                        break;

                }

                dateAdapter.notifyDataSetChanged();
            }
        });
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

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);
            filterDayWise();
        }
    };

    public void timeTableDetail() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("user", "class_timetable");
                jsonObject.addProperty("section_id", id);
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().getClassTimeTableDetails(jsonObject, new Callback<ClassTimeTableDetailResponse>() {
                    @Override
                    public void success(ClassTimeTableDetailResponse timeTableResponse, Response response) {
                        try {
                            if (timeTableResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                structArraylist.clear();
                                structArraylist.addAll(timeTableResponse.getList());
                                Log.e("structArraylist", "-----" + structArraylist);
                                Log.e("arralist", "-----" + arrayList.toString());
                                Log.e("timeTableDetail", "-----" + timeTableDetailArrayList.toString());
                                updateSpinner();
                                saveTimeTableData();
                                timeTableAdapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(getApplicationContext(), timeTableResponse.getMessage(), Toast.LENGTH_LONG).show();
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

    private void saveTimeTableData() {
        try {
            list = new ArrayList<>();
            for (int i = 0; i < structArraylist.size(); i++) {
                ClassTimeTableDetailStructure timeTable = structArraylist.get(i);
                timeTableDetailArrayList.addAll(timeTable.getTimeTable());
                Log.e("timeTableDetailList", "-----" + timeTableDetailArrayList.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.timetablespinner, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spiner = categories.get(position);
                filterDayWise();
                timeTableAdapter.notifyDataSetChanged();
                Log.e("pos", "---" + spiner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return true;
    }

    private void updateSpinner() {
        categories.clear();
        for (int i = 0; i < structArraylist.size(); i++) {
            ClassTimeTableDetailStructure timeTableDetailStructure = structArraylist.get(i);
            categories.add(timeTableDetailStructure.getName());
        }
        adapter.notifyDataSetChanged();
    }

    private void filterDayWise() {
        try {
            if (structArraylist.size() > 0) {
                for (int i = 0; i < structArraylist.size(); i++) {
                    if (structArraylist.get(i).getName().equalsIgnoreCase(spiner)) {
                        timeTableDetailArrayList.clear();
                        timeTableDetailArrayList.addAll(structArraylist.get(i).getTimeTable());
                        Log.e("acvghsa=====", timeTableDetailArrayList + "");
                        break;
                    }
                }
                arrayList.clear();
                for (int i = 0; i < timeTableDetailArrayList.size(); i++) {
                    if (timeTableDetailArrayList.get(i).getDay().equalsIgnoreCase(currentDay)) {
                        arrayList.add(timeTableDetailArrayList.get(i));
                    }
                }
                Log.e("arralist", "-----" + arrayList.toString());
               // Collections.sort(arrayList);
                timeTableAdapter.notifyDataSetChanged();
            }
            if (arrayList.size() <= 0) {
                lvperiod.setVisibility(View.GONE);
                noschedule_tv.setVisibility(View.VISIBLE);
            } else {
                lvperiod.setVisibility(View.VISIBLE);
                noschedule_tv.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



