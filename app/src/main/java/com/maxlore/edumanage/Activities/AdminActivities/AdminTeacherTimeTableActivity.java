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
import com.maxlore.edumanage.Adapters.AdminAdapters.AdminTeacherNavigationTimeTableAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTableDetails.TeacherTimeTableDetailResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTableDetails.TeacherTimeTableDetailStructure;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTableDetails.TeacherTimeTableDetails;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AdminTeacherTimeTableActivity extends AppCompatActivity {
    private ListView lvdate;
    private ListView lvperiod;
    private TextView time_tv;
    private List<String> list;
    private ArrayAdapter dayadapter;
    private AdminTeacherNavigationTimeTableAdapter timeTableAdapter;
    private List<TeacherTimeTableDetails> arrayList;
    private ArrayList<TeacherTimeTableDetailStructure> structArraylist;
    private ArrayList<TeacherTimeTableDetails> finalArrayList;
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

        finalArrayList = new ArrayList<>();
        arrayList = new ArrayList();
        structArraylist = new ArrayList<>();
        categories = new ArrayList<>();

        timeTableAdapter = new AdminTeacherNavigationTimeTableAdapter(this, arrayList);
        lvperiod.setAdapter(timeTableAdapter);
        timeTableAdapter.notifyDataSetChanged();
        dayadapter = new ArrayAdapter(this, R.layout.list_item_date_days, R.id.dateday_tv);


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
                        filterDayWise();
                        dateAdapter.setPosition(4);
                        break;
                    case 5:
                        currentDay = "Friday";
                        filterDayWise();
                        dateAdapter.setPosition(5);
                        break;
                    case 6:
                        currentDay = "Saturday";
                        filterDayWise();
                        dateAdapter.setPosition(6);
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


    public void timeTableDetail() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                JsonObject jsonObjectnew = new JsonObject();
                jsonObjectnew.addProperty("user", "teacher_timetable");
                jsonObjectnew.addProperty("employee_id", id);
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObjectnew.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObjectnew.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().getTeacherTimeTableDetails(jsonObjectnew, new Callback<TeacherTimeTableDetailResponse>() {
                    @Override
                    public void success(TeacherTimeTableDetailResponse timeTableResponse, Response response) {
                        try {
                            if (timeTableResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                structArraylist.clear();
                                structArraylist.addAll(timeTableResponse.getList());
                                Log.e("structArraylist", "-----" + structArraylist);
                                Log.e("arralist", "-----" + arrayList.toString());
                                Log.e("finalArrayList", "-----" + finalArrayList.toString());
                                updateSpinner();
                                saveTimeTableData();
                                timeTableAdapter.notifyDataSetChanged();
                                filterDayWise();

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
                TeacherTimeTableDetailStructure timeTable = structArraylist.get(i);
                finalArrayList.addAll(timeTable.getTimeTable());
                Log.e("finalArrayList", "-----" + finalArrayList.toString());
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
            TeacherTimeTableDetailStructure timeTableDetailStructure = structArraylist.get(i);
            categories.add(timeTableDetailStructure.getName());
        }
        adapter.notifyDataSetChanged();
    }

    private void filterDayWise() {
       /* arrayList.clear();
        arrayList.addAll(TimeTable.find(TimeTable.class, "STRUCTURE_ID = ? AND DAY = ? AND OWN_CLASS = ? ", new String[]{"1", day, "0"}));
        Log.e("data", "arrayList -- " + arrayList.toString());
        timeTableAdapter.notifyDataSetChanged();*/
        try {
            if (structArraylist.size() > 0) {
                for (int i = 0; i < structArraylist.size(); i++) {
                    if (structArraylist.get(i).getName().equalsIgnoreCase(spiner)) {
                        finalArrayList.clear();
                        finalArrayList.addAll(structArraylist.get(i).getTimeTable());
                        Log.e("acvghsa=====", finalArrayList + "");
                        break;
                    }
                }
                arrayList.clear();
                for (int i = 0; i < finalArrayList.size(); i++) {
                    if (finalArrayList.get(i).getDay().equalsIgnoreCase(currentDay)) {
                        arrayList.add(finalArrayList.get(i));
                    }
                }
                Log.e("arralist", "-----" + arrayList.toString());
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

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);
            filterDayWise();
        }
    };
}

