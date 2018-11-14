/*
package com.maxlore.inmegh.Activities.ParentActivities;

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
import android.widget.Toast;

import com.maxlore.inmegh.API.RetrofitAPI;
import com.maxlore.inmegh.Adapters.ParentAdapters.AttendanceForMonthAdapter;
import com.maxlore.inmegh.Adapters.ParentAdapters.MonthYearAdapter;
import com.maxlore.inmegh.Database.PreferencesManger;
import com.maxlore.inmegh.Models.ParentModels.attendance.MonthYearList;
import com.maxlore.inmegh.Models.ParentModels.attendance.ParentAttendance;
import com.maxlore.inmegh.Models.ParentModels.attendance.ParentAttendanceResponse;
import com.maxlore.inmegh.Models.ParentModels.attendance.StudentDayAttendanceDetail;
import com.maxlore.inmegh.Models.ParentModels.attendance.StudentList;
import com.maxlore.inmegh.R;
import com.maxlore.inmegh.Utility.Constants;
import com.maxlore.inmegh.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ParentAttendanceActivity extends AppCompatActivity {

    private ListView lvMonthYear, lvAttendance;
    private ArrayAdapter<String> spinneradapter;
    private ArrayList<String> studentList;
    private ArrayList<MonthYearList> monthYearLists;
    private ArrayList<StudentList> studentListArrayList;
    private ArrayList<StudentDayAttendanceDetail> dayAttendanceDetails;
    private ArrayList<ParentAttendanceResponse> arrayList;
    private MonthYearAdapter monthYearAdapter;
    private AttendanceForMonthAdapter adapter;
    private String selectedStudent;
    private int year, month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_attendance);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvMonthYear = (ListView) findViewById(R.id.lvMonthYear);
        lvAttendance = (ListView) findViewById(R.id.lvAttendance);

        monthYearLists = new ArrayList<>();
        studentList = new ArrayList<>();
        arrayList = new ArrayList<>();
        studentListArrayList = new ArrayList<>();
        dayAttendanceDetails = new ArrayList<>();

        monthYearAdapter = new MonthYearAdapter(this, monthYearLists);
        adapter = new AttendanceForMonthAdapter(this, dayAttendanceDetails);

        lvMonthYear.setAdapter(monthYearAdapter);
        lvAttendance.setAdapter(adapter);

        Calendar c = Calendar.getInstance();
        Date date = new Date();
//        month = date.getMonth();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        getAttendanceForMonth();
        getMonthYearFromAttendance();


        lvMonthYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MonthYearList monthYearList = monthYearLists.get(position);
                month = monthYearList.getMonth();
                year = monthYearList.getYear();
                markCurrentMonth();
                getAttendanceForMonth();

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

    private void getMonthYearFromAttendance() {

        if (UIUtil.isInternetAvailable(this)) {

            UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");

            RetrofitAPI.getInstance(this).getApi().getParentAttendance(new Callback<ParentAttendance>() {
                @Override
                public void success(ParentAttendance parentAttendance, Response response) {

                    if (parentAttendance.getStatus() == Constants.SUCCESS) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        monthYearLists.addAll(parentAttendance.getMonthYearList());
                        monthYearAdapter.notifyDataSetChanged();
                        markCurrentMonth();
                        studentListArrayList.clear();
                        studentListArrayList.addAll(parentAttendance.getStudentList());
                        //updateSpinner();
                        getAttendanceForMonth();
                    } else {
                        Toast.makeText(getApplicationContext(), "" + parentAttendance.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    UIUtil.stopProgressDialog(getApplicationContext());
                }
            });
        } else {
            Toast.makeText(this, "Please connect Internet", Toast.LENGTH_LONG).show();
        }
    }

    private void markCurrentMonth() {
        for (int i = 0; i < monthYearLists.size(); i++) {
            MonthYearList list = monthYearLists.get(i);
            if (monthYearLists.get(i).getMonth() == month) {
                list.setSelected(true);
            } else {
                list.setSelected(false);
            }
            monthYearLists.set(i, list);
        }
        monthYearAdapter.notifyDataSetChanged();

    }

    */
/*  private void updateSpinner() {
          studentList.clear();
          for (int i = 0; i < studentListArrayList.size(); i++) {
              studentList.add(studentListArrayList.get(i).getName());
          }
          spinneradapter.notifyDataSetChanged();
      }
  *//*

    private void getAttendanceForMonth() {
        if (UIUtil.isInternetAvailable(this)) {

            UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("month", month);
            jsonObject.addProperty("year", year);
            jsonObject.addProperty("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));

            RetrofitAPI.getInstance(this).getApi().getStudentAttendanceDetails(jsonObject, new Callback<ParentAttendanceResponse>() {
                @Override
                public void success(ParentAttendanceResponse parentAttendanceResponse, Response response) {
                    if (parentAttendanceResponse.getStatus() == Constants.SUCCESS) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        dayAttendanceDetails.clear();
                        dayAttendanceDetails.addAll(parentAttendanceResponse.getStudentDayAttendanceDetail());
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), "" + parentAttendanceResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    UIUtil.stopProgressDialog(getApplicationContext());
                }
            });
        } else {
            Toast.makeText(this, "Please connect Internet", Toast.LENGTH_LONG).show();
        }
    }



*/
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.parentleavespinner, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        spinneradapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, studentList);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinneradapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStudent = studentList.get(position);
                Log.e("selectposition", "====" + selectedStudent);
                getAttendanceForMonth();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return true;
    }

}*//*

}*/
