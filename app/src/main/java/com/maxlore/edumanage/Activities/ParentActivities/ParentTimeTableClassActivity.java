package com.maxlore.edumanage.Activities.ParentActivities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.maxlore.edumanage.Adapters.ParentAdapters.ParentTimeTableAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.ParentModels.ParentTimeTable.ParentTimeTable;
import com.maxlore.edumanage.Models.ParentModels.ParentTimeTable.ParentTimeTableResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentTimeTable.ParentTimeTableStructure;
import com.maxlore.edumanage.Models.TeacherModels.database.TimeTable;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ParentTimeTableClassActivity extends AppCompatActivity {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView lvdate;
    private ListView lvperiod;
    private TextView time_tv;
    private ArrayAdapter dayadapter;
    private TextView tv_periodtime, tv_period, noschedule_tv, periodTeacher;
    private String currentDay;
    private String selectType;
    private ArrayAdapter<String> parenttimetablespinAdapter;
    private ParentTimeTableAdapter parentTimeTableAdapter;
    private ArrayList<TimeTable> arrayListtimetable;
    private ArrayList<TimeTable> timeTableArrayList;
    private ArrayList arrayList;
    private ParentTimeTableAdapter timeTableAdapter;
    private int day;
    private ArrayList<ParentTimeTable> parentTimeTablesList = new ArrayList<>();
    private int selectedStudent = 0;

    private List<String> list;
    public ArrayList<ParentTimeTableStructure> parentTimeTableArrayList;
    private ArrayAdapter<String> structureAdapter;

    private ViewPager mViewPager;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_time_table_class);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvdate = (ListView) findViewById(R.id.lvDate);
        time_tv = (TextView) findViewById(R.id.dateday_tv);
        lvperiod = (ListView) findViewById(R.id.lvPeriod);
        periodTeacher = (TextView) findViewById(R.id.ClassTeacher_tv);
        noschedule_tv = (TextView) findViewById(R.id.tv_noSchedule);

        timeTableArrayList = new ArrayList<>();
        arrayList = new ArrayList();

        timeTableAdapter = new ParentTimeTableAdapter(this, arrayList);
        lvperiod.setAdapter(timeTableAdapter);
        timeTableAdapter.notifyDataSetChanged();

        list = new ArrayList<String>();
        parentTimeTableArrayList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        SimpleDateFormat format = new SimpleDateFormat("dd\n" + "EEEE");
        String[] days = new String[7];
        int weekdays = -calendar.get(GregorianCalendar.DAY_OF_WEEK) + 1;
        calendar.add(Calendar.DAY_OF_MONTH, weekdays);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        currentDay = sdf.format(d);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("custom-event-name"));
        ArrayList<String> arrayListDate = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            arrayListDate.add(days[i]);
        }
        final TimeTableDateAdapter dateAdapter = new TimeTableDateAdapter(this, arrayListDate);
        dateAdapter.setPosition(day - 1);

        lvdate.setAdapter(dateAdapter);
        dateAdapter.notifyDataSetChanged();

        getParentTimeTableData();
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
        filterDayWise();
    }

    public void getParentTimeTableData() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().getParentTimeTableData(params, new Callback<ParentTimeTableResponse>() {
                    @Override
                    public void success(ParentTimeTableResponse parentTimeTableResponse, Response response) {
                        try {
                            if (parentTimeTableResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                parentTimeTableArrayList.clear();
                                parentTimeTableArrayList.addAll(parentTimeTableResponse.getParentTimetable());
                                Log.e("arrylist", "--------" + parentTimeTableArrayList);
                                updateSpinner();
                                //  updateFragment();
                            } else {
                                Toast.makeText(getApplicationContext(), "" + parentTimeTableResponse.getMessage(), Toast.LENGTH_SHORT).show();
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


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.timetablespinner, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectType = list.get(position);
                sendMessage();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return true;
    }*/


    private void updateSpinner() {
        list.clear();

        for (int i = 0; i < parentTimeTableArrayList.size(); i++) {
            list.add(parentTimeTableArrayList.get(i).getName());

        }
        adapter.notifyDataSetChanged();
        Log.e("bbbbbbbbbb", list + "");
    }

    private void addIfNotExist(String s) {
        if (!list.contains(s)) {
            list.add(s);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.parentdashboardspinner, menu);

        MenuItem item = menu.findItem(R.id.parentdashboardspinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_new);
        adapter.notifyDataSetChanged();
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectType = list.get(position);
                sendMessage();
                filterDayWise();
                timeTableAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return true;
    }

    public void filterDayWise() {
        try {

            Log.e("checkkkkkk", "hnj");
            Log.e("ttttttttttttat", selectType);
            Log.e("pppp", selectedStudent + "positi");

            if (parentTimeTableArrayList.size() > 0) {
             /*   ParentTimeTable parentTimeTable = parentTimeTableArrayList.get(selectedStudent);
                List<ParentTimeTableStructure> tableStructures = parentTimeTable.getTimeTableStructure();*/
                for (int i = 0; i < parentTimeTableArrayList.size(); i++) {
                    if (parentTimeTableArrayList.get(i).getName().equalsIgnoreCase(selectType)) {
                        timeTableArrayList.clear();
                        timeTableArrayList.addAll(parentTimeTableArrayList.get(i).getTimeTable());
                        Log.e("acvghsa=====", timeTableArrayList + "");
                        break;
                    }
                }
                arrayList.clear();
                for (int i = 0; i < timeTableArrayList.size(); i++) {
                    if (timeTableArrayList.get(i).getDay().equalsIgnoreCase(currentDay)) {
                        arrayList.add(timeTableArrayList.get(i));
                    }
                }
                timeTableAdapter.notifyDataSetChanged();
            }
            if (arrayList.size() <= 0) {
                lvperiod.setVisibility(GONE);
                noschedule_tv.setVisibility(VISIBLE);

            } else {
                lvperiod.setVisibility(VISIBLE);
                noschedule_tv.setVisibility(GONE);
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


    private void sendMessage() {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("custom-event-name");
        intent.putExtra("message", "This is my message!");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            startActivity(new Intent(ParentTimeTableClassActivity.this, ParentLandingActivity.class));
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(ParentTimeTableClassActivity.this, ParentLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }

}