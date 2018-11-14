package com.maxlore.edumanage.Activities.TeacherActivities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.maxlore.edumanage.Adapters.TeacherAdapters.NavigationTimeTableAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.ParentModels.ParentTimeTable.ParentTimeTableStructure;
import com.maxlore.edumanage.Models.TeacherModels.TimeTable.TimeTableResponse;
import com.maxlore.edumanage.Models.TeacherModels.database.TimeTable;
import com.maxlore.edumanage.Models.TeacherModels.database.TimeTableStructure;

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

public class TimeTableActivity extends AppCompatActivity {

    private ListView lvdate;
    private ListView lvperiod;
    private TextView time_tv;
    private NavigationTimeTableAdapter timeTableAdapter;
    private ArrayList arrayList;
    private ArrayList<TimeTable> finalArrayList;
    private TextView noschedule_tv;
    private String currentDay;
    private List<String> list;
    public ArrayList<TimeTable> timeTableArrayList;
    public ArrayList<TimeTableStructure> timeTableSpinnerArrayList;
    private ArrayAdapter<String> structureAdapter;
    private ArrayAdapter<String> adapter;
    private String selectType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        lvdate = (ListView) findViewById(R.id.lvDate);
        time_tv = (TextView) findViewById(R.id.dateday_tv);
        lvperiod = (ListView) findViewById(R.id.lvPeriod);

        noschedule_tv = (TextView) findViewById(R.id.tv_noSchedule);
        finalArrayList = new ArrayList<>();
        arrayList = new ArrayList();
        list = new ArrayList<>();
        timeTableArrayList = new ArrayList<>();
        timeTableSpinnerArrayList = new ArrayList<>();
        timeTableAdapter = new NavigationTimeTableAdapter(this, arrayList);
        lvperiod.setAdapter(timeTableAdapter);
        timeTableAdapter.notifyDataSetChanged();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //     example=new Example(example.getDay(),example.getPeriod(),example.getStartTime(),example.getSubject(),example.getCode(),example.getClass_(),example.getSection());
        // example.save();

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        SimpleDateFormat format = new SimpleDateFormat("dd\n" + "EEE");
        String[] days = new String[7];
        int weekdays = -calendar.get(GregorianCalendar.DAY_OF_WEEK) + 1;
        calendar.add(Calendar.DAY_OF_MONTH, weekdays);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        currentDay = sdf.format(d);

        ArrayList<String> arrayListDate = new ArrayList<>();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("custom-event-name"));
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            arrayListDate.add(days[i]);
        }
        final TimeTableDateAdapter dateAdapter = new TimeTableDateAdapter(this, arrayListDate);
        dateAdapter.setPosition(day - 1);
        if (!PreferencesManger.getBooleanFields(this, Constants.Pref.KEY_TIME_TABLE_DOWNLOAD_OWN))
            getTimeTableDetail();

        lvdate.setAdapter(dateAdapter);
        lvdate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        currentDay = "Sunday";
                        filterDayWise();
                        dateAdapter.setPosition(0);
                        break;
                    case 1:
                        currentDay = "Monday";
                        filterDayWise();
                        dateAdapter.setPosition(1);
                        break;
                    case 2:
                        currentDay = "Tuesday";
                        filterDayWise();
                        dateAdapter.setPosition(2);
                        break;
                    case 3:
                        currentDay = "Wednesday";
                        filterDayWise();
                        dateAdapter.setPosition(3);
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
        filterDayWise();
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_attendance, menu);
        return true;
    }*/

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

    public void getTimeTableDetail() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().getTimeTableData(params, new Callback<TimeTableResponse>() {
                    @Override
                    public void success(TimeTableResponse timeTableResponse, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            if (timeTableResponse.getStatus() == Constants.SUCCESS) {
                                //saveTimeTableData(timeTableResponse.getTimeTableStructure());
                                timeTableSpinnerArrayList.clear();
                                timeTableSpinnerArrayList.addAll(timeTableResponse.getTimeTableStructure());
                                Log.e("arrylist", "--------" + timeTableSpinnerArrayList);
                                updateSpinner();
                            } else {
                                Toast.makeText(getApplicationContext(), timeTableResponse.getMessage(), Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // sectionArrayList= new ArrayList<Section>();
              /*  arrayList.clear();
                finalArrayList.clear();
               finalArrayList.addAll(timeTableResponse);
                //arrayList.addAll(students);
                Log.e("Example", " --" + students.toString());
                timeTableAdapter.notifyDataSetChanged();*/
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

    private void saveTimeTableData(List<TimeTableStructure> timeTableStructures) {
        try {
            TimeTableStructure.deleteAll(TimeTable.class);
            TimeTable.deleteAll(TimeTable.class, "OWN_CLASS = ? ", new String[]{"0"});
            TimeTableStructure.saveInTx(timeTableStructures);
            for (int i = 0; i < timeTableStructures.size(); i++) {
                TimeTableStructure timeTableStructure = timeTableStructures.get(i);
                List<TimeTable> timeTableList = timeTableStructure.getTimeTable();
                TimeTable.saveInTx(timeTableList);
            }
            PreferencesManger.addBooleanFields(this, Constants.Pref.KEY_TIME_TABLE_DOWNLOAD_OWN, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateSpinner() {
        list.clear();

        for (int i = 0; i < timeTableSpinnerArrayList.size(); i++) {
            list.add(timeTableSpinnerArrayList.get(i).getName());

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

    private void sendMessage() {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("custom-event-name");
        intent.putExtra("message", "This is my message!");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void filterDayWise() {
        try {

            Log.e("checkkkkkk", "hnj");
            Log.e("ttttttttttttat", selectType);
            //  Log.e("pppp", selectedStudent + "positi");

            if (timeTableSpinnerArrayList.size() > 0) {
             /*   ParentTimeTable parentTimeTable = parentTimeTableArrayList.get(selectedStudent);
                List<ParentTimeTableStructure> tableStructures = parentTimeTable.getTimeTableStructure();*/
                for (int i = 0; i < timeTableSpinnerArrayList.size(); i++) {
                    if (timeTableSpinnerArrayList.get(i).getName().equalsIgnoreCase(selectType)) {
                        timeTableArrayList.clear();
                        timeTableArrayList.addAll(timeTableSpinnerArrayList.get(i).getTimeTable());
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

    /*  private void filterDayWise() {
          try {
              arrayList.clear();
              final String structure = PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_CURRENT_TIME_TABLE_STRUCTURE_ID);
              arrayList.addAll(TimeTable.find(TimeTable.class, "STRUCTURE_ID = ? AND DAY = ? AND OWN_CLASS = ? ", new String[]{structure, currentDay, "0"}));
              Log.e("data", "arrayList -- " + arrayList.toString());
              timeTableAdapter.notifyDataSetChanged();

       *//*  for (int i = 0; i < finalArrayList.size(); i++) {
            if (finalArrayList.get(i).getDay().equalsIgnoreCase(day)) {
                arrayList.add(finalArrayList.get(i));
            }
        }
       timeTableAdapter.notifyDataSetChanged();*//*

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
*/
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

