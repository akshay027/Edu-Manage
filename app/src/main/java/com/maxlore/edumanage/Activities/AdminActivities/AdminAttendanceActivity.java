package com.maxlore.edumanage.Activities.AdminActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.AttendancefirstPageAdapter;
import com.maxlore.edumanage.Adapters.AdminAdapters.AttendancefirstPageStudent;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAttendance.AdminStudentAttendance;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAttendance.EmployeeAttendance;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAttendance.EmployeeAttendanceResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAttendance.StudentAttendanceResponse;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AdminAttendanceActivity extends AppCompatActivity {
    private RecyclerView rv_adminattendance;
    private ArrayList<String> categorieslist;
    private ArrayAdapter<String> spinnerAdapter;

    private List<EmployeeAttendance> employeeAttendanceList, finaldeptArraylist;
    private List<AdminStudentAttendance> adminStudentAttendanceList, studentArraylist;

    private AttendancefirstPageAdapter attendancefirstPageAdapter;
    private AttendancefirstPageStudent attendancefirstPageStudent;

    private int currentposition;

    private String spiner, dept_id;
    private TextView tv_noattendancedata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_attendance);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_noattendancedata = findViewById(R.id.tv_noattendancedata);
        rv_adminattendance = (RecyclerView) findViewById(R.id.rv_adminattendance);
        employeeAttendanceList = new ArrayList<>();
        adminStudentAttendanceList = new ArrayList<>();
        finaldeptArraylist = new ArrayList<>();
        studentArraylist = new ArrayList<>();

        rv_adminattendance.setHasFixedSize(true);
        rv_adminattendance.setLayoutManager(new LinearLayoutManager(this));
        categorieslist = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.parentleavespinner, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        categorieslist.add(Constants.ADMINATTENDANCECHARACTEREMP);
        categorieslist.add(Constants.ADMINATTENDANCECHARACTERSTU);
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorieslist);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_new);

        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                spiner = categorieslist.get(position);
                if (spiner == Constants.ADMINATTENDANCECHARACTEREMP) {
                    getDepartments();
                } else if (spiner == Constants.ADMINATTENDANCECHARACTERSTU) {
                    getStudentsClass();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return true;
    }

    private void bindDataforEmployee(List<EmployeeAttendance> employeeAttendances) {
        finaldeptArraylist.clear();
        employeeAttendanceList.clear();
        employeeAttendanceList.addAll(employeeAttendances);
        finaldeptArraylist.addAll(employeeAttendances);
    /*    if (employeeAttendanceList.size() <= 0) {
            tv_noattendancedata.setVisibility(View.VISIBLE);
            rv_adminattendance.setVisibility(View.GONE);
        } else {
            tv_noattendancedata.setVisibility(View.GONE);
            rv_adminattendance.setVisibility(View.VISIBLE);
        }*/
        attendancefirstPageAdapter.notifyDataSetChanged();
        attendancefirstPageAdapter.SetOnItemClickListener(new AttendancefirstPageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                currentposition = position;
                dept_id = employeeAttendanceList.get(currentposition).getDepartmentId().toString();
                Intent intent = new Intent(AdminAttendanceActivity.this, AdminAttendanceDetail.class);
                intent.putExtra("department selected", dept_id);
                startActivity(intent);
            }
        });
    }

    private void bindDataforStudent(List<AdminStudentAttendance> adminStudentAttendances) {
        studentArraylist.clear();
        adminStudentAttendanceList.clear();
        adminStudentAttendanceList.addAll(adminStudentAttendances);
        studentArraylist.addAll(adminStudentAttendances);
    /*    if (adminStudentAttendanceList.size() <= 0) {
            tv_noattendancedata.setVisibility(View.VISIBLE);
            rv_adminattendance.setVisibility(View.GONE);
        } else {
            tv_noattendancedata.setVisibility(View.GONE);
            rv_adminattendance.setVisibility(View.VISIBLE);
        }*/
        attendancefirstPageStudent.notifyDataSetChanged();
        attendancefirstPageStudent.SetOnItemClickListener(new AttendancefirstPageStudent.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                currentposition = position;
                dept_id = adminStudentAttendanceList.get(currentposition).getSectionId().toString();
                Intent intent = new Intent(AdminAttendanceActivity.this, AdminStudentAttendanceDetail.class);
                intent.putExtra("Section selected", dept_id);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            startActivity(new Intent(AdminAttendanceActivity.this, AdminLandingActivity.class));
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDepartments() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                attendancefirstPageAdapter = new AttendancefirstPageAdapter(AdminAttendanceActivity.this, employeeAttendanceList);
                rv_adminattendance.setAdapter(attendancefirstPageAdapter);
                attendancefirstPageAdapter.notifyDataSetChanged();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("user", Constants.ADMINATTENDANCECHARACTEREMP);
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().getDepartmentemployee(jsonObject, new Callback<EmployeeAttendanceResponse>() {
                    @Override
                    public void success(EmployeeAttendanceResponse employeeAttendanceResponse, Response response) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Log.e("jsonObject", "jsonObject --- " + employeeAttendanceResponse.toString());
                        bindDataforEmployee(employeeAttendanceResponse.getList());

                        try {
                            if (employeeAttendanceResponse == null) {
                                Toast.makeText(AdminAttendanceActivity.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (employeeAttendanceResponse.getStatus() == Constants.SUCCESS) {
                                Toast.makeText(AdminAttendanceActivity.this, employeeAttendanceResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }  else {
                                Toast.makeText(AdminAttendanceActivity.this, employeeAttendanceResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AdminAttendanceActivity.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
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

    private void getStudentsClass() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                attendancefirstPageStudent = new AttendancefirstPageStudent(AdminAttendanceActivity.this, adminStudentAttendanceList);
                rv_adminattendance.setAdapter(attendancefirstPageStudent);
                attendancefirstPageStudent.notifyDataSetChanged();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("user", Constants.ADMINATTENDANCECHARACTERSTU);
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().getClassstudent(jsonObject, new Callback<StudentAttendanceResponse>() {
                    @Override
                    public void success(StudentAttendanceResponse studentAttendanceResponse, Response response) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Log.e("jsonObject", "jsonObject --- " + studentAttendanceResponse.toString());
                        bindDataforStudent(studentAttendanceResponse.getList());

                        try {
                            if (studentAttendanceResponse == null) {
                                Toast.makeText(AdminAttendanceActivity.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (studentAttendanceResponse.getStatus() == Constants.SUCCESS) {
                                Toast.makeText(AdminAttendanceActivity.this, studentAttendanceResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(AdminAttendanceActivity.this, studentAttendanceResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AdminAttendanceActivity.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(AdminAttendanceActivity.this, AdminLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }
}
