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
import com.maxlore.edumanage.Adapters.AdminAdapters.AttendanceSecondPageAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AttendanceDetail.DepartmentAttendance;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AttendanceDetail.TeachersResponse;

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

public class AdminAttendanceDetail extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView attendanceRecyclerView;
    private ArrayList<DepartmentAttendance> attendanceArrayList, searchAttendanceArrayList;
    private AttendanceSecondPageAdapter attendanceRecyclerAdapter;
    private String date, currentdate;
    private String pos;
    private CheckBox checkbox_selectall;
    private ProgressBar progress;
    private TextView tvDate, ivPresent, ivAbsent, ivNoclass;
    private EditText etSearch;
    private boolean isHoliday = false, presentAll, absentAll;
    private Handler handler;
    public static final int TIME_OUT = 1000;
    private int currentPosition;
    private LinearLayout llAmPm;
    private int check = 0;
    private TextView tv_noattendancedata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_attendance_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progress = (ProgressBar) findViewById(R.id.progress);
        checkbox_selectall = (CheckBox) findViewById(R.id.checkbox_selectall);
        tvDate = (TextView) findViewById(R.id.tvDate);
        pos = getIntent().getStringExtra("department selected");

        tv_noattendancedata = findViewById(R.id.tv_noattendancedata);
        ivPresent = (TextView) findViewById(R.id.ivPresent);
        ivAbsent = (TextView) findViewById(R.id.ivAbsent);
        ivNoclass = (TextView) findViewById(R.id.ivNoclass);
        attendanceRecyclerView = (RecyclerView) findViewById(R.id.attendanceRecyclerView);

        ivPresent.setOnClickListener(this);
        ivAbsent.setOnClickListener(this);
        ivNoclass.setOnClickListener(this);
        checkbox_selectall.setOnClickListener(this);
        etSearch = (EditText) findViewById(R.id.etSearch);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        checkbox_selectall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    for (int i = 0; i < attendanceArrayList.size(); i++) {
                        attendanceArrayList.get(i).setCheck_box(1);
                        check = check + 1;
                    }
                    Log.e("check +", "====" + check);
                    attendanceRecyclerAdapter.notifyDataSetChanged();
                } else if (isChecked == false) {
                    for (int i = 0; i < attendanceArrayList.size(); i++) {
                        attendanceArrayList.get(i).setCheck_box(0);
                        check = check - 1;
                    }
                    Log.e("check -", "====" + check);
                    attendanceRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });

        attendanceArrayList = new ArrayList<>();
        searchAttendanceArrayList = new ArrayList<>();
        attendanceRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        attendanceRecyclerView.setLayoutManager(llm);
        handler = new Handler();

        date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        currentdate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        Log.e("date", "date-- " + date);

        tvDate.setText(date);

        getAttendanceDetails(true);
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
                DepartmentAttendance attendance = searchAttendanceArrayList.get(i);
                if (attendance.getEmployeeName().toLowerCase().contains(search.toLowerCase())) {
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

/*    private void selectPresentAll() {
        if (presentAll) {
            ivPresentAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.att_unseleceted));
            presentAll = false;
        } else {
            ivPresentAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.att_present));
            presentAll = true;
            confirmationForMarkAllStudent(1);
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
            confirmationForMarkAllStudent(0);
        }
        if (presentAll) {
            ivPresentAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.att_unseleceted));
            presentAll = false;
        }
    }*/

    private void selectDate() {

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Calendar cal = Calendar.getInstance();
                cal.roll(Calendar.DATE, -1);

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
                date = day + "-" + month + "-" + String.valueOf(year);
                getAttendanceDetails(false);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

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
                params.put("user", "Employee");
                params.put("date", date);
                params.put("department_id", pos);

                try {

                    RetrofitAPI.getInstance(this).getApi().getempstuattendance(params, new Callback<TeachersResponse>() {
                        @Override
                        public void success(TeachersResponse teachersResponse, Response response) {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            if (showProgressbar) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                            } else {
                                progress.setVisibility(View.GONE);
                            }
                            if (teachersResponse.getStatus() == Constants.SUCCESS) {
                                bindDataforAttendance(teachersResponse.getList());
                                checkbox_selectall.setChecked(false);
                                attendanceRecyclerAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getApplicationContext(), "" + teachersResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            UIUtil.stopProgressDialog(getApplicationContext());
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

    private void bindDataforAttendance(List<DepartmentAttendance> studentAttendance) {

        attendanceArrayList.clear();
        searchAttendanceArrayList.clear();
        searchAttendanceArrayList.addAll(studentAttendance);
        attendanceArrayList.addAll(studentAttendance);
        attendanceRecyclerAdapter = new AttendanceSecondPageAdapter(this, attendanceArrayList, isHoliday, AdminAttendanceDetail.this);
        attendanceRecyclerView.setAdapter(attendanceRecyclerAdapter);

        if (attendanceArrayList.size() <= 0) {
            tv_noattendancedata.setVisibility(View.VISIBLE);
            attendanceRecyclerView.setVisibility(View.GONE);
        } else {
            tv_noattendancedata.setVisibility(View.GONE);
            attendanceRecyclerView.setVisibility(View.VISIBLE);
        }

        attendanceRecyclerAdapter.SetOnItemClickListener(new AttendanceSecondPageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e("onItemClick", "onItemClick ===== p-" + position);
                if (attendanceArrayList.get(position).getCheck_box() == 0) {
                    attendanceArrayList.get(position).setCheck_box(1);
                    check = check + 1;
                } else {
                    attendanceArrayList.get(position).setCheck_box(0);
                    check = check - 1;
                }
                attendanceRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }


    private void confirmation(final int position) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        currentPosition = position;
        final DepartmentAttendance attendance = attendanceArrayList.get(0);
        Log.e("ashuydvbhjdv :", currentdate);
        Log.e("ashuydvbhjdvajkhdvb :", tvDate.getText().toString());
        if (check == 0) {
            Toast.makeText(this, "Please Select a Staff Member", Toast.LENGTH_SHORT).show();
        } else if (check > 0) {
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

        /* if (attendance.getAttendance() == Constants.WEEKNOTASSIGN || attendance.getAttendance() == Constants.NOWORKDAYTODAY || attendance.getAttendance() == Constants.HOLIDAY) {*/

       /* else if (attendance.getAttendance() == Constants.WORKINGDAYNOTASSIGN) {
            Toast.makeText(this, "Working Day Not Assigned...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Attendance cannot be marked for this Day...", Toast.LENGTH_SHORT).show();
        }*/
    }

    private void markAttendance(final int position) {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
/*
            final DepartmentAttendance attendance = attendanceArrayList.get(currentPosition);*/

                JsonObject jsonObject = new JsonObject();
                JsonObject jsonObjectnew = new JsonObject();
                jsonObject.add("employee_id", getEmployeeId());
                jsonObject.addProperty("user", "Employee");
                if (position == 1) {
                    jsonObject.addProperty("present", 1);
                } else if (position == 2) {
                    jsonObject.addProperty("present", 0);
                } else if (position == 3) {
                    jsonObject.addProperty("present", 3);
                }
                jsonObjectnew.addProperty("date", date);
                jsonObject.addProperty("date", date);
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                jsonObject.add("attendance", jsonObjectnew);

                RetrofitAPI.getInstance(this).getApi().markEmployeeAttendance(jsonObject, new Callback<JSONObject>() {
                    @Override
                    public void success(JSONObject object, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Log.e("API", "markAttendance" + object.toString());
                   /* attendance.setAttendance(attendance.getAttendance() == Constants.PRESENT ? Constants.ABSENT : Constants.PRESENT);

                    attendanceArrayList.set(currentPosition, attendance);
                    attendanceRecyclerAdapter.notifyDataSetChanged();*/
                            final Handler handler = new Handler();
                            UIUtil.startProgressDialog(getApplicationContext(), "Please Wait.. Marking Attendance");
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    UIUtil.stopProgressDialog(getApplicationContext());
                                    attendanceRecyclerAdapter.notifyDataSetChanged();
                                    //check = 0;
                                    // checkbox_selectall.setChecked(false);
                                    checkbox_selectall.toggle();
                                    getAttendanceDetails(true);
                                }
                            }, 100);

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

    /* private void confirmationForMarkAllStudent(final int isPresent) {
         AlertDialog.Builder builder = new AlertDialog.Builder(this);

         builder.setTitle("Confirmation");
         String str;
         if (isPresent == 1) {
             str = "Present";
         } else {
             str = "Absent";
         }
         String message = "Do you want to mark all student " + str + " ?";
         builder.setMessage(message);
         builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 /// TODO call Api here
                 markAttendanceForAll(isPresent);

             }
         });
         builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {

                 Log.e("ff", "ffffffffffff");
                 ivAbsentAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.att_unseleceted));
                 absentAll = false;

                 ivPresentAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.att_unseleceted));
                 presentAll = false;
                 dialog.dismiss();
             }
         });
         builder.show();
     }


     private void markAttendanceForAll(int mark) {
         if (UIUtil.isInternetAvailable(this)) {
             UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");

             JsonObject jsonObject = new JsonObject();
             JsonObject jsonObjectnew = new JsonObject();
             jsonObject.addProperty("mark_all", true);
             jsonObject.addProperty("user", "Employee");
             jsonObject.addProperty("department_id", pos);
             jsonObjectnew.addProperty("date", date);
             jsonObject.add("attendance", jsonObjectnew);
             jsonObject.addProperty("present", mark);
             jsonObject.addProperty("date", date);
             RetrofitAPI.getInstance(this).getApi().markEmployeeAttendance(jsonObject, new Callback<JSONObject>() {
                 @Override
                 public void success(JSONObject object, Response response) {
                     UIUtil.stopProgressDialog(getApplicationContext());
                     Log.e("API", "markAttendance" + object.toString());
                     attendanceRecyclerAdapter.notifyDataSetChanged();
                     handler.postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             getAttendanceDetails(false);
                         }
                     }, TIME_OUT);
                 }

                 @Override
                 public void failure(RetrofitError error) {

                     UIUtil.stopProgressDialog(getApplicationContext());
                 }
             });
         } else {
             Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
         }
     }*/
    private JsonArray getEmployeeId() {
        JsonArray selectedemployeeid = new JsonArray();
        for (int i = 0; i < attendanceArrayList.size(); i++) {
            if (attendanceArrayList.get(i).getCheck_box() == 1) {
                selectedemployeeid.add(new JsonPrimitive(attendanceArrayList.get(i).getEmployeeId()));

            }
        }
        return selectedemployeeid;
    }
  /*   private JsonArray getattendanceid() {
        JsonArray selectedattenid = new JsonArray();
        for (int i = 0; i < attendanceArrayList.size(); i++) {
            if (attendanceArrayList.get(i).getCheck_box() == 1) {
                selectedattenid.add(new JsonPrimitive(attendanceArrayList.get(i).getAttendanceId()));
            }
        }
        return selectedattenid;
    }*/
}
