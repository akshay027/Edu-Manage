package com.maxlore.edumanage.Activities.AdminActivities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.AttendanceSecondPageStudent;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AttendanceDetail.StudentClassAttendance;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AttendanceDetail.StudentsResponse;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AdminStudentAttendanceDetail extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView attendanceRecyclerView;
    private ArrayList<StudentClassAttendance> attendanceArrayList, searchAttendanceArrayList;
    private AttendanceSecondPageStudent attendanceRecyclerAdapter;
    private String date, selectedtime, currentdate;
    private ProgressBar progress;
    private TextView tvDate, tvAM, tvPM, ivPresent, ivAbsent, ivNoclass;
    private EditText etSearch;
    private CheckBox checkbox_selectall;
    private boolean isHoliday = false, isSelectedAm = true, presentAll, absentAll, amPm;
    private Handler handler;
    private String pos;
    public static final int TIME_OUT = 1000;
    private int currentPosition, att_id, getpmvalue, getvalue;
    private LinearLayout llAmPm;
    private int check = 0;
    private TextView tv_noattendancedata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student_attendance_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progress = (ProgressBar) findViewById(R.id.progress);

        tv_noattendancedata = findViewById(R.id.tv_noattendancedata);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvAM = (TextView) findViewById(R.id.tvAM);
        tvPM = (TextView) findViewById(R.id.tvPM);
        pos = getIntent().getStringExtra("Section selected");
        tvAM.setOnClickListener(this);
        tvPM.setOnClickListener(this);
        ivPresent = (TextView) findViewById(R.id.ivPresent);
        ivAbsent = (TextView) findViewById(R.id.ivAbsent);
        ivNoclass = (TextView) findViewById(R.id.ivNoclass);
        checkbox_selectall = (CheckBox) findViewById(R.id.checkbox_selectall);
        ivPresent.setOnClickListener(this);
        ivAbsent.setOnClickListener(this);
        ivNoclass.setOnClickListener(this);
        tvAM.setOnClickListener(this);
        tvPM.setOnClickListener(this);
        checkbox_selectall.setOnClickListener(this);
        llAmPm = (LinearLayout) findViewById(R.id.llAMPM);

        etSearch = (EditText) findViewById(R.id.etSearch);

        attendanceRecyclerView = (RecyclerView) findViewById(R.id.attendanceRecyclerView);
        attendanceArrayList = new ArrayList<>();
        searchAttendanceArrayList = new ArrayList<>();
        attendanceRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        attendanceRecyclerView.setLayoutManager(llm);

        handler = new Handler();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });

        date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        currentdate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        Log.e("date", "date-- " + date);

        tvDate.setText(date);

        getAttendanceDetails(true);

        checkbox_selectall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < attendanceArrayList.size(); i++) {
                        attendanceArrayList.get(i).setCheck_box(1);
                    }
                    attendanceRecyclerAdapter.notifyDataSetChanged();
                } else {
                    for (int i = 0; i < attendanceArrayList.size(); i++) {
                        attendanceArrayList.get(i).setCheck_box(0);
                    }
                    attendanceRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.length() > 2) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            filterSearch(s.toString());
                        }
                    }, TIME_OUT);
                } else {
                    if (searchAttendanceArrayList.size() > 0) {
                        attendanceArrayList.clear();
                        attendanceArrayList.addAll(searchAttendanceArrayList);
                        attendanceRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void filterSearch(String search) {
        try {
            attendanceArrayList.clear();
            for (int i = 0; i < searchAttendanceArrayList.size(); i++) {
                StudentClassAttendance attendance = searchAttendanceArrayList.get(i);
                if (attendance.getStudentName().toLowerCase().contains(search.toLowerCase())) {
                    attendanceArrayList.add(attendance);
                }
            }
            if (attendanceArrayList.size() <= 0) {
                etSearch.setError("No Record found");
            }
            attendanceRecyclerAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAM:
                selectAM();
                break;
            case R.id.tvPM:
                selectPM();
                break;
            case R.id.ivAbsent:
                confirmation(2);
                break;
            case R.id.ivPresent:
                confirmation(1);
                break;
            case R.id.ivNoclass:
                confirmation(3);
                break;
        }

    }

 /*   private void selectPresentAll() {

        if (presentAll) {
            ivPresentAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.att_unseleceted));
            presentAll = false;
        } else {
            ivPresentAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.att_present));
            presentAll = true;
            confirmationForMarkAllStudent(true);
        }
        if (absentAll) {
            ivAbsentAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.att_unseleceted));
            absentAll = false;
        }
    }

    private void selectAbsentAll() {

        if (absentAll) {
            ivAbsentAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.att_unseleceted));
            absentAll = false;
        } else {
            ivAbsentAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.att_absent));
            absentAll = true;
            confirmationForMarkAllStudent(false);
        }
        if (presentAll) {
            ivPresentAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.att_unseleceted));
            presentAll = false;
        }
    }*/

    private void selectAM() {
        isSelectedAm = true;
        check = 0;
        for (int i = 0; i < attendanceArrayList.size(); i++) {
            attendanceArrayList.get(i).setPMSelected(false);
        }
        attendanceRecyclerAdapter.notifyDataSetChanged();
        tvAM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tvAM.setTextColor(getResources().getColor(R.color.white));

        tvPM.setBackgroundColor(getResources().getColor(R.color.white));
        tvPM.setTextColor(getResources().getColor(R.color.textBlackDark));
    }

    private void selectPM() {
        isSelectedAm = false;
        check = 1;
        for (int i = 0; i < attendanceArrayList.size(); i++) {
            attendanceArrayList.get(i).setPMSelected(true);
        }
        attendanceRecyclerAdapter.notifyDataSetChanged();
        tvPM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tvPM.setTextColor(getResources().getColor(R.color.white));

        tvAM.setBackgroundColor(getResources().getColor(R.color.white));
        tvAM.setTextColor(getResources().getColor(R.color.textBlackDark));

    }


    private void selectDate() {

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                monthOfYear = monthOfYear + 1;

                String month, day;

                if (monthOfYear < 10) {
                    month = "0" + String.valueOf(monthOfYear);
                } else {
                    month = String.valueOf(monthOfYear);
                }

                if (dayOfMonth < 10) {
                    day = "0" + String.valueOf(dayOfMonth);
                } else {
                    day = String.valueOf(dayOfMonth);
                }

                tvDate.setText(day + "-" + month + "-" + String.valueOf(year));
                date = String.valueOf(year) + "-" + month + "-" + day;
                getAttendanceDetails(false);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();

    }


    /*   private void getAttendanceDetails(final boolean showProgressbar) {

           if (UIUtil.isInternetAvailable(this)) {

               if (showProgressbar) {
                   UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
               } else {
                   progress.setVisibility(View.VISIBLE);
               }
               JsonObject jsonObject = new JsonObject();
               JsonObject jsonObjectnew = new JsonObject();
               jsonObjectnew.addProperty("date", date);
               jsonObject.addProperty("user", "Student");
               jsonObject.addProperty("date", date);
               jsonObject.add("attendance", jsonObjectnew);
               jsonObject.addProperty("section_id", pos);
               try {

                   RetrofitAPI.getInstance(this).getApi().getStudentAdminAttendanceDetails(jsonObject, new Callback<StudentsResponse>() {
                       @Override
                       public void success(StudentsResponse attendanceResponse, Response response) {
                           UIUtil.stopProgressDialog(getApplicationContext());
                           if (showProgressbar) {
                               UIUtil.stopProgressDialog(getApplicationContext());
                           } else {
                               progress.setVisibility(View.GONE);
                           }
   //                    Log.e("API", "dashboardResponses" + attendanceResponse.toString());
   //                    Log.e("API", "dashboardResponses" + response.getBody());
                           if (attendanceResponse.getStatus() == Constants.SUCCESS) {
                               amPm = attendanceResponse.getAttendanceType();
                               bindDataforAttendance(attendanceResponse.getList(), attendanceResponse.getAttendanceType());
                               final Handler handler = new Handler();
                               handler.postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       attendanceRecyclerAdapter.notifyDataSetChanged();
                                       if (check == 1) {
                                           isSelectedAm = false;
                                           for (int i = 0; i < attendanceArrayList.size(); i++) {
                                               attendanceArrayList.get(i).setPMSelected(true);
                                           }
                                           attendanceRecyclerAdapter.notifyDataSetChanged();
                                           tvPM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                           tvPM.setTextColor(getResources().getColor(R.color.white));

                                           tvAM.setBackgroundColor(getResources().getColor(R.color.white));
                                           tvAM.setTextColor(getResources().getColor(R.color.textBlackDark));
                                       } else {
                                           isSelectedAm = true;
                                           for (int i = 0; i < attendanceArrayList.size(); i++) {
                                               attendanceArrayList.get(i).setPMSelected(false);
                                           }
                                           attendanceRecyclerAdapter.notifyDataSetChanged();
                                           tvAM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                           tvAM.setTextColor(getResources().getColor(R.color.white));

                                           tvPM.setBackgroundColor(getResources().getColor(R.color.white));
                                           tvPM.setTextColor(getResources().getColor(R.color.textBlackDark));
                                       }

                       }

                       @Override
                       public void failure(RetrofitError error) {

                           if (showProgressbar) {
                               UIUtil.stopProgressDialog(getApplicationContext());
                           } else {
                               progress.setVisibility(View.GONE);
                           }
                       }
                   });
               } catch (Exception e) {
                   e.printStackTrace();
               }
           } else {
               Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
           }


       }*/
    private void getAttendanceDetails(final boolean showProgressbar) {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                if (showProgressbar) {
                    UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                } else {
                    progress.setVisibility(View.VISIBLE);
                }
                Map<String, String> params = new HashMap<String, String>();
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                JsonObject jsonObject = new JsonObject();
                JsonObject jsonObjectnew = new JsonObject();
                params.put("user", "Student");
                params.put("section_id", pos);
                params.put("date", date);

                try {

                    RetrofitAPI.getInstance(this).getApi().getStudentAdminAttendanceDetails(params, new Callback<StudentsResponse>() {
                        @Override
                        public void success(StudentsResponse attendanceResponse, Response response) {
                            if (showProgressbar) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                            } else {
                                progress.setVisibility(View.GONE);
                            }
//                    Log.e("API", "dashboardResponses" + attendanceResponse.toString());
//                    Log.e("API", "dashboardResponses" + response.getBody());
                            if (attendanceResponse.getStatus() == Constants.SUCCESS) {
                                amPm = attendanceResponse.getAttendanceType();
                                bindDataforAttendance(attendanceResponse.getList(), attendanceResponse.getAttendanceType());
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        attendanceRecyclerAdapter.notifyDataSetChanged();
                                        if (check == 1) {
                                            isSelectedAm = false;
                                            for (int i = 0; i < attendanceArrayList.size(); i++) {
                                                attendanceArrayList.get(i).setPMSelected(true);
                                            }
                                            attendanceRecyclerAdapter.notifyDataSetChanged();
                                            tvPM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                            tvPM.setTextColor(getResources().getColor(R.color.white));

                                            tvAM.setBackgroundColor(getResources().getColor(R.color.white));
                                            tvAM.setTextColor(getResources().getColor(R.color.textBlackDark));
                                        } else {
                                            isSelectedAm = true;
                                            for (int i = 0; i < attendanceArrayList.size(); i++) {
                                                attendanceArrayList.get(i).setPMSelected(false);
                                            }
                                            attendanceRecyclerAdapter.notifyDataSetChanged();
                                            tvAM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                            tvAM.setTextColor(getResources().getColor(R.color.white));

                                            tvPM.setBackgroundColor(getResources().getColor(R.color.white));
                                            tvPM.setTextColor(getResources().getColor(R.color.textBlackDark));
                                        }
                                    }
                                }, 200);
                            } else {
                                Toast.makeText(getApplicationContext(), "" + attendanceResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {

                            if (showProgressbar) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                            } else {
                                progress.setVisibility(View.GONE);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void bindDataforAttendance(List<StudentClassAttendance> studentAttendance, boolean isAmPm) {
        try {
            if (amPm) {
                llAmPm.setVisibility(View.GONE);
            } else {
                llAmPm.setVisibility(View.VISIBLE);
            }

            attendanceArrayList.clear();
            searchAttendanceArrayList.clear();
            searchAttendanceArrayList.addAll(studentAttendance);
            attendanceArrayList.addAll(studentAttendance);
            attendanceRecyclerAdapter = new AttendanceSecondPageStudent(this, attendanceArrayList, isAmPm, isHoliday, AdminStudentAttendanceDetail.this);
            attendanceRecyclerView.setAdapter(attendanceRecyclerAdapter);
            if (attendanceArrayList.size() <= 0) {
                tv_noattendancedata.setVisibility(View.VISIBLE);
                attendanceRecyclerView.setVisibility(View.GONE);
            } else {
                tv_noattendancedata.setVisibility(View.GONE);
                attendanceRecyclerView.setVisibility(View.VISIBLE);
            }
            attendanceRecyclerAdapter.SetOnItemClickListener(new AttendanceSecondPageStudent.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Log.e("onItemClick", "onItemClick ===== p-" + position);
                    if (attendanceArrayList.get(position).getCheck_box() == 0) {
                        attendanceArrayList.get(position).setCheck_box(1);
                    } else {
                        attendanceArrayList.get(position).setCheck_box(0);
                    }
                    attendanceRecyclerAdapter.notifyDataSetChanged();
                }
            });
            attendanceRecyclerAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

           /* @Override
            public void onAMClick(View view, int position) {
                StudentClassAttendance attendance = attendanceArrayList.get(position);
                attendance.setPMSelected(false);
                attendanceArrayList.set(position, attendance);
                getvalue = attendance.getAttendancePm();
                getvalue = getvalue - 1;
                selectedtime = "AM";
                attendanceRecyclerAdapter.notifyDataSetChanged();
                Log.e("onItemClick", "onAMClick ===== p-" + position);
            }

            @Override
            public void onPMClick(View view, int position) {
                StudentClassAttendance attendance = attendanceArrayList.get(position);
                attendance.setPMSelected(true);
                getvalue = attendance.getAttendance();
                getvalue = getvalue - 1;
                selectedtime = "PM";
                attendanceArrayList.set(position, attendance);
                attendanceRecyclerAdapter.notifyDataSetChanged();
                Log.e("onItemClick", "onPMClick ===== p-" + position);
            }*/


    private void confirmation(final int position) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        currentPosition = position;
//        AlertDialog.Builder builder =
//                new AlertDialog.Builder(this, R.style.AppTheme_AppBarOverlay);
        final StudentClassAttendance attendance = attendanceArrayList.get(0);
        /*if (check == 0) {
            Toast.makeText(this, "Please Select a Staff Member", Toast.LENGTH_SHORT).show();
        }*/ /*else if (check > 0) {*/
        if (currentdate.equals(tvDate.getText())) {
            if (attendance.getAttendance() != Constants.WORKINGDAYNOTASSIGN) {
                /*if (attendance.getAttendance() == Constants.PRESENT || attendance.getAttendance() == Constants.ABSENT || attendance.getAttendance() == Constants.HALFDAYATTEN || attendance.getAttendance() == Constants.LEAVEDAY || attendance.getAttendance() == Constants.NONWORKINGDAY || attendance.getAttendance() == Constants.SCHOOLHOLIDAY) {*/
                builder.setTitle("Confirmation");
                String ap = "";
                if (position == 1) {
                    ap = "Present";
                } else if (position == 2) {
                    ap = "Absent";
                } else if (position == 3) {
                    ap = "No Class";
                }

                //if condition for absent
                String message = "Do you want to mark " + ap + "?";
                builder.setMessage(message);
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /// TODO call Api here
                        markAttendance(currentPosition);

                    }
                });
                builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        } else {
            Toast.makeText(this, "Attendance Cannot be marked for this day...", Toast.LENGTH_SHORT).show();
        }
    }

    private void markAttendance(final int position) {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");

                final StudentClassAttendance attendance = attendanceArrayList.get(currentPosition);
                JsonObject jsonObject = new JsonObject();
                JsonObject jsonObjectnew = new JsonObject();
                jsonObject.add("student_id", getstudentid());
                jsonObjectnew.addProperty("date", date);
                jsonObject.addProperty("attendance_id", attendance.getAttendanceId());
                jsonObject.addProperty("date", date);
                jsonObject.addProperty("user", "Student");
                jsonObject.add("attendance", jsonObjectnew);
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                if (attendance.isPMSelected()) {
                    if (position == 1) {
                        jsonObject.addProperty("present_pm", 1);
                    } else if (position == 2) {
                        jsonObject.addProperty("present_pm", 0);
                    } else if (position == 3) {
                        jsonObject.addProperty("present_pm", 3);
                    }
                } else {
                    if (position == 1) {
                        jsonObject.addProperty("present", 1);
                    } else if (position == 2) {
                        jsonObject.addProperty("present", 0);
                    } else if (position == 3) {
                        jsonObject.addProperty("present", 3);
                    }
                }
                RetrofitAPI.getInstance(this).getApi().markEmployeeAttendance(jsonObject, new Callback<JSONObject>() {
                    @Override
                    public void success(JSONObject object, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Log.e("API", "markAttendance" + object.toString());
                            final Handler handler = new Handler();
                            UIUtil.startProgressDialog(getApplicationContext(), "Please Wait.. Marking Attendance");
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    UIUtil.stopProgressDialog(getApplicationContext());
                                    attendanceRecyclerAdapter.notifyDataSetChanged();
                                    getAttendanceDetails(true);
                                }
                            }, 2000);
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

    private JsonArray getstudentid() {
        JsonArray selectedemployeeid = new JsonArray();
        for (int i = 0; i < attendanceArrayList.size(); i++) {
            if (attendanceArrayList.get(i).getCheck_box() == 1) {
                selectedemployeeid.add(new JsonPrimitive(attendanceArrayList.get(i).getStudentId()));
            }
        }
        return selectedemployeeid;
    }
}
