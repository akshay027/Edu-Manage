package com.maxlore.edumanage.Activities.TeacherActivities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AttendanceDetail.StudentClassAttendance;
import com.maxlore.edumanage.Models.StatusResponseClass;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.TeacherAdapters.AttendanceRecyclerAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.AttendanceResponse;
import com.maxlore.edumanage.Models.TeacherModels.StudentAttendance;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.json.JSONObject;

import java.text.ParseException;
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

public class AttendanceActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView attendanceRecyclerView;
    private ArrayList<StudentAttendance> attendanceArrayList, searchAttendanceArrayList;
    private AttendanceRecyclerAdapter attendanceRecyclerAdapter;
    private String date, currentdate;
    private ProgressBar progress;
    private TextView tvDate, tvAM, tvPM, ivPresent, ivAbsent, ivNoclass;
    private EditText etSearch;
    private CheckBox checkbox_selectall, checkbox_dummy;
    private boolean isHoliday = false, isSelectedAm = true, presentAll, absentAll, amPm;
    private Handler handler;
    public static final int TIME_OUT = 2000;
    private int currentPosition, getvalue;
    private LinearLayout llAmPm, selectView, footerview;
    private int cb = 0, status = 1, check = 0;
    private CharSequence searchTextCount;
    private TextView tv_noattendancedata;
    private JsonArray selectedemployeeid = new JsonArray();
    private Animation myAnim;
    private String reasonEditText;
    private Date EndTime;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progress = (ProgressBar) findViewById(R.id.progress);

        tv_noattendancedata = findViewById(R.id.tv_noattendancedata);
        selectView = findViewById(R.id.selectView);
        footerview = findViewById(R.id.footerview);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvAM = (TextView) findViewById(R.id.tvAM);
        tvPM = (TextView) findViewById(R.id.tvPM);
        ivPresent = (TextView) findViewById(R.id.ivPresent);
        ivAbsent = (TextView) findViewById(R.id.ivAbsent);
        ivNoclass = (TextView) findViewById(R.id.ivNoclass);
        checkbox_selectall = (CheckBox) findViewById(R.id.checkbox_selectall);
        checkbox_dummy = (CheckBox) findViewById(R.id.checkbox_dummy);
        llAmPm = (LinearLayout) findViewById(R.id.llAMPM);
        etSearch = (EditText) findViewById(R.id.etSearch);
        attendanceRecyclerView = (RecyclerView) findViewById(R.id.attendanceRecyclerView);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        checkbox_selectall.setVisibility(View.VISIBLE);
        checkbox_dummy.setVisibility(View.GONE);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        formattedDate = df.format(c);

        ivPresent.setOnClickListener(this);
        ivAbsent.setOnClickListener(this);
        ivNoclass.setOnClickListener(this);
        tvAM.setOnClickListener(this);
        tvPM.setOnClickListener(this);
        checkbox_selectall.setOnClickListener(this);
        checkbox_dummy.setOnClickListener(this);
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
        checkbox_dummy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    selectedemployeeid = new JsonArray();
                    cb = 0;
                    //check whether variable defined in model class i.e. checkbox for all the data is true or false
                    // if true anywhere run below condition else show select a teacher or make checkbox of all seceted set as false.

                    for (int i = 0; i < attendanceArrayList.size(); i++) {
                        attendanceArrayList.get(i).setCheck_box(1);
                        cb = cb + 1;
                        selectedemployeeid.add(new JsonPrimitive(attendanceArrayList.get(i).getStudentId()));
                    }
                    Log.e("select all checkbox", "****** " + selectedemployeeid);
                    Log.e("select cb", "****** " + cb);
                    attendanceRecyclerAdapter.notifyDataSetChanged();
                    checkbox_selectall.setChecked(true);
                    checkbox_selectall.setVisibility(View.VISIBLE);
                    checkbox_dummy.setVisibility(View.GONE);

                }

            }
        });
        checkbox_selectall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectedemployeeid = new JsonArray();
                cb = 0;

                if (isChecked) {
                    //check whether variable defined in model class i.e. checkbox for all the data is true or false
                    // if true anywhere run below condition else show select a teacher or make checkbox of all seceted set as false.

                    for (int i = 0; i < attendanceArrayList.size(); i++) {
                        attendanceArrayList.get(i).setCheck_box(1);
                        cb = cb + 1;
                        selectedemployeeid.add(new JsonPrimitive(attendanceArrayList.get(i).getStudentId()));
                    }
                    Log.e("select all checkbox", "****** " + selectedemployeeid);
                    Log.e("select cb", "****** " + cb);
                    attendanceRecyclerAdapter.notifyDataSetChanged();
                } else {
                    for (int i = 0; i < attendanceArrayList.size(); i++) {
                        attendanceArrayList.get(i).setCheck_box(0);
                        cb = 0;
                        selectedemployeeid.remove(new JsonPrimitive(attendanceArrayList.get(i).getStudentId()));
                    }
                    Log.e("Unselect all checkbox", "****** " + selectedemployeeid);
                    Log.e("Unselect cb", "****** " + cb);
                    attendanceRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });
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
                searchTextCount = s;
                if (s.length() == 0) {
                    if (status == 1) {
                        checkbox_selectall.setVisibility(View.VISIBLE);
                        checkbox_dummy.setVisibility(View.GONE);
                    } else if (status == 0) {
                        checkbox_selectall.setVisibility(View.GONE);
                        checkbox_dummy.setVisibility(View.VISIBLE);
                    }

                } else {
                    checkbox_selectall.setVisibility(View.GONE);
                    checkbox_dummy.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(s) && s.length() > 0) {
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
            // checkbox_selectall.setVisibility(View.GONE);
            attendanceArrayList.clear();
            for (int i = 0; i < searchAttendanceArrayList.size(); i++) {
                StudentAttendance attendance = searchAttendanceArrayList.get(i);
                if (attendance.getStudentName().toLowerCase().contains(search.toLowerCase())) {
                    attendanceArrayList.add(attendance);
                }
            }
            if (attendanceArrayList.size() <= 0) {
                etSearch.setError("No Record Found");
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
            startActivity(new Intent(AttendanceActivity.this, TeacherLandingActivity.class));
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
        startActivity(new Intent(AttendanceActivity.this, TeacherLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAM:
                v.startAnimation(myAnim);
                selectAM();
                break;
            case R.id.tvPM:
                v.startAnimation(myAnim);
                selectPM();
                break;
            case R.id.ivAbsent:
                v.startAnimation(myAnim);
                absentConfirmation(2);
                //confirmation(2);
                break;
            case R.id.ivPresent:
                v.startAnimation(myAnim);
                confirmation(1);
                break;
            case R.id.ivNoclass:
                v.startAnimation(myAnim);
                confirmation(3);
                break;
        }

    }

    /*private void selectPresentAll() {
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
        for (int i = 0; i < attendanceArrayList.size(); i++) {
            attendanceArrayList.get(i).setPMSelected(true);
        }
        check = 1;
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

                //// for validation
//                try {
//                    String str_date = day + "-" + month + "-" + String.valueOf(year);
//                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//                    Date date = (Date) formatter.parse(str_date);
//                    fromDate = date.getTime();
//                    System.out.println("Today is " + date.getTime());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                tvDate.setText(day + "-" + month + "-" + String.valueOf(year));
                //date = String.valueOf(year) + "-" + month + "-" + day;
                date = String.valueOf(day) + "-" + month + "-" + year;
                getAttendanceDetails(false);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
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
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                params.put("user", "Student");
                params.put("date", date);

                try {

                    RetrofitAPI.getInstance(this).getApi().getAttendanceDetails(params, new Callback<AttendanceResponse>() {
                        @Override
                        public void success(AttendanceResponse attendanceResponse, Response response) {
                            if (showProgressbar) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                            } else {
                                progress.setVisibility(View.GONE);
                            }
//                    Log.e("API", "dashboardResponses" + attendanceResponse.toString());
//                    Log.e("API", "dashboardResponses" + response.getBody());
                            if (attendanceResponse.getStatus() == Constants.SUCCESS) {
                                etSearch.setText("");
                                checkbox_selectall.setChecked(false);
                                checkbox_selectall.setVisibility(View.VISIBLE);
                                checkbox_dummy.setVisibility(View.GONE);
                                cb = 0;
                                amPm = attendanceResponse.getAttendanceType();
                                bindData(attendanceResponse.getList(), attendanceResponse.getAttendanceType());
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
                                UIUtil.stopProgressDialog(getApplicationContext());
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

    private void bindData(List<StudentAttendance> studentAttendance, boolean isAmPm) {
        Log.e("sdfhsgfdjhnhdfjmfjghf", "onItemClick ===== akshay-");
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
            attendanceRecyclerAdapter = new AttendanceRecyclerAdapter(this, attendanceArrayList, isAmPm, isHoliday);
            attendanceRecyclerView.setAdapter(attendanceRecyclerAdapter);

            if (attendanceArrayList.size() <= 0) {
                selectView.setVisibility(View.GONE);
                footerview.setVisibility(View.GONE);
                tv_noattendancedata.setVisibility(View.VISIBLE);
                attendanceRecyclerView.setVisibility(View.GONE);
            } else {
                selectView.setVisibility(View.VISIBLE);
                footerview.setVisibility(View.VISIBLE);
                tv_noattendancedata.setVisibility(View.GONE);
                attendanceRecyclerView.setVisibility(View.VISIBLE);
            }
            try {
                EndTime = dateFormat.parse("12:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date CurrentTime = null;
            try {
                CurrentTime = dateFormat.parse(dateFormat.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // if (formattedDate.equals(date)||formattedDate.compareTo(date)<0||CurrentTime.before(EndTime)) {
            if (formattedDate.equals(date) && CurrentTime.before(EndTime)) {
                tvAM.setEnabled(true);
                tvAM.setClickable(true);
                tvPM.setEnabled(false);
                tvPM.setClickable(false);
                selectAM();
                //Toast.makeText(getApplicationContext(), "Please Select PM field", Toast.LENGTH_SHORT).show();
            } else {
                tvAM.setEnabled(true);
                tvAM.setClickable(true);
                tvPM.setEnabled(true);
                tvPM.setClickable(true);
                selectAM();
            }
            attendanceRecyclerAdapter.SetOnItemClickListener(new AttendanceRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Log.e("onItemClick", "onItemClick ===== p-" + position);
                    if (attendanceArrayList.get(position).getCheck_box() == 0) {
                        attendanceArrayList.get(position).setCheck_box(1);
                        cb = cb + 1;
                        selectedemployeeid.add(new JsonPrimitive(attendanceArrayList.get(position).getStudentId()));
                        Log.e("first", "onItemClick ===== p-" + selectedemployeeid);
                        Log.e("first cb", "****** " + cb);
                    } else {
                        if (searchTextCount.length() == 0) {
                            Log.e("searchTextCount1", "***** " + searchTextCount);
                            checkbox_selectall.setVisibility(View.GONE);
                            checkbox_dummy.setVisibility(View.VISIBLE);
                            checkbox_dummy.setChecked(false);
                            attendanceArrayList.get(position).setCheck_box(0);
                            cb = cb - 1;
                            selectedemployeeid.remove(new JsonPrimitive(attendanceArrayList.get(position).getStudentId()));
                            Log.e("second", "onItemClick ===== p-" + selectedemployeeid);
                            Log.e("second cb", "****** " + cb);
                        } else {
                            Log.e("searchTextCount2", "***** " + searchTextCount);
                            checkbox_selectall.setVisibility(View.GONE);
                            checkbox_dummy.setVisibility(View.GONE);
                            checkbox_dummy.setChecked(false);
                            attendanceArrayList.get(position).setCheck_box(0);
                            cb = cb - 1;
                            selectedemployeeid.remove(new JsonPrimitive(attendanceArrayList.get(position).getStudentId()));
                            Log.e("second", "onItemClick ===== p-" + selectedemployeeid);
                            Log.e("second cb", "****** " + cb);
                            status = 0;

                        }
                       /* checkbox_selectall.setVisibility(View.GONE);
                        checkbox_dummy.setVisibility(View.VISIBLE);
                        checkbox_dummy.setChecked(false);
                        attendanceArrayList.get(position).setCheck_box(0);
                        cb = cb - 1;
                        selectedemployeeid.remove(new JsonPrimitive(attendanceArrayList.get(position).getStudentId()));
                        Log.e("second", "onItemClick ===== p-" + selectedemployeeid);
                        Log.e("second cb", "****** " + cb);*/
                    }

                    attendanceRecyclerAdapter.notifyDataSetChanged();
                }
            });
            // attendanceRecyclerAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void absentConfirmation(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        final EditText edittext = new EditText(this);
        currentPosition = position;
        final StudentAttendance attendance = attendanceArrayList.get(0);
        if (cb > 0) {
            if (attendance.getAttendance() != Constants.WORKINGDAYNOTASSIGN) {
                builder.setTitle("Confirmation");
                String status = "";
                status = "Absent";
                edittext.setHint("Enter Reason for absent");
                // String message = "Do you want to mark " + status + "?";
                String message = "Do you want to mark Attendance ?";
                builder.setMessage(message);
                builder.setView(edittext);

                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        reasonEditText = edittext.getText().toString();

                        markAttendance(currentPosition);

                    }
                });

                builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });
                builder.show();
            }
        } else {
            Toast.makeText(this, "Please select atleast one student", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmation(final int position) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setCancelable(false);
        currentPosition = position;
//        AlertDialog.Builder builder =
//                new AlertDialog.Builder(this, R.style.AppTheme_AppBarOverlay);
        final StudentAttendance attendance = attendanceArrayList.get(0);
        if (cb > 0) {
            // if (currentdate.equals(tvDate.getText())) {
            if (attendance.getAttendance() != Constants.WORKINGDAYNOTASSIGN) {
                /*if (attendance.getAttendance() == Constants.PRESENT || attendance.getAttendance() == Constants.ABSENT || attendance.getAttendance() == Constants.HALFDAYATTEN || attendance.getAttendance() == Constants.LEAVEDAY || attendance.getAttendance() == Constants.NONWORKINGDAY || attendance.getAttendance() == Constants.SCHOOLHOLIDAY) {*/
                builder.setTitle("Confirmation");
                String status = "";
                if (position == 1) {
                    status = "Present";
                } else if (position == 2) {
                    status = "Absent";
                } else if (position == 3) {
                    status = "No Class";
                }

                //if condition for absent
                // String message = "Do you want to mark " + status + "?";
                String message = "Do you want to mark Attendance ?";
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
           /* } else {
                Toast.makeText(this, "Attendance Cannot be marked for this day...", Toast.LENGTH_SHORT).show();
            }*/
        } else {
            Toast.makeText(this, "Please select atleast one Student", Toast.LENGTH_SHORT).show();
        }
    }

    private void markAttendance(final int position) {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(getApplicationContext(), "Please Wait.. Marking Attendance");

                //final StudentAttendance attendance = attendanceArrayList.get(currentPosition);
                JsonObject jsonObject = new JsonObject();
                JsonObject jsonObjectnew = new JsonObject();
                jsonObject.add("student_id", selectedemployeeid);
                // jsonObject.add("student_id", getstudentid());
                jsonObjectnew.addProperty("date", date);
                jsonObject.addProperty("date", date);
                jsonObject.addProperty("user", "Student");
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                jsonObject.add("attendance", jsonObjectnew);
                // if (attendance.isPMSelected()) {
                if (currentPosition == 2) {
                    jsonObject.addProperty("reason", reasonEditText);
                }
                if (isSelectedAm == false) {
                    if (position == 1) {
                        jsonObject.addProperty("present_pm", 1);//present
                    } else if (position == 2) {
                        jsonObject.addProperty("present_pm", 0);//absent
                    } else if (position == 3) {
                        jsonObject.addProperty("present_pm", 3);//noclass
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
                RetrofitAPI.getInstance(this).getApi().markAttendance(jsonObject, new Callback<StatusResponseClass>() {
                    @Override
                    public void success(final StatusResponseClass object, Response response) {
                        if (object.getStatus() == Constants.SUCCESS) {
                            try {
                                UIUtil.startProgressDialog(getApplicationContext(), "Please Wait.. Marking Attendance");
                                Log.e("API", "markAttendance" + object.toString());
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        UIUtil.stopProgressDialog(getApplicationContext());
                                        attendanceRecyclerAdapter.notifyDataSetChanged();
                                        getAttendanceDetails(true);
                                        Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
                                        //checkbox_selectall.toggle();
                                        selectedemployeeid = new JsonArray();
                                    }
                                }, 500);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
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

   /* private JsonArray getstudentid() {
        JsonArray selectedemployeeid = new JsonArray();
        for (int i = 0; i < attendanceArrayList.size(); i++) {
            if (attendanceArrayList.get(i).getCheck_box() == 1) {
                selectedemployeeid.add(new JsonPrimitive(attendanceArrayList.get(i).getStudentId()));
            }
        }
        return selectedemployeeid;
    }*/
}
   /* private void confirmationForMarkAllStudent(final boolean isPresent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

//        AlertDialog.Builder builder =
//                new AlertDialog.Builder(this, R.style.AppTheme_AppBarOverlay);

        builder.setTitle("Confirmation");
        String str;
        if (isPresent) {
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
                amPm = isPresent;
                markAttendanceForAll(isPresent);

            }
        });
        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Log.e("ff", "fffffffffffffffffffffffffffffffffff");
                ivAbsentAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.att_unseleceted));
                absentAll = false;

                ivPresentAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.att_unseleceted));
                presentAll = false;
                dialog.dismiss();
            }
        });
        builder.show();
    }


    private void markAttendanceForAll(boolean mark) {
        if (UIUtil.isInternetAvailable(this)) {
            UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");

            JsonObject jsonObject = new JsonObject();
            JsonObject jsonObjectnew = new JsonObject();
            jsonObject.addProperty("mark_all", true);
            jsonObjectnew.addProperty("date", date);
            jsonObject.add("attendance", jsonObjectnew);
            jsonObject.addProperty("date", date);
            //
            // jsonObject.addProperty("user", "Teacher");

            if (!amPm) {
                if (isSelectedAm) {
                    jsonObject.addProperty("present", mark);
                } else {
                    jsonObject.addProperty("present_pm", mark);
                }
            } else if (amPm) {
                if (isSelectedAm) {
                    jsonObject.addProperty("present", mark);
                } else {
                    jsonObject.addProperty("present_pm", mark);
                }
            } else {
                jsonObject.addProperty("present", mark);
            }


            RetrofitAPI.getInstance(this).getApi().markAttendance(jsonObject, new Callback<JSONObject>() {
                @Override
                public void success(JSONObject object, Response response) {
                    UIUtil.stopProgressDialog(getApplicationContext());

                    Log.e("API", "markAttendance" + object.toString());

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
*/

