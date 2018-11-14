package com.maxlore.edumanage.Activities.TeacherActivities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.Leaves.Leave;
import com.maxlore.edumanage.Models.TeacherModels.Leaves.LeaveCategory;
import com.maxlore.edumanage.Models.TeacherModels.Leaves.LeaveHistory;
import com.maxlore.edumanage.Models.TeacherModels.Leaves.LeaveResponse;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TeacherEditLeaveActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvFromDate, tvToDate, tvHalfDate;
    private EditText etReason;
    private Spinner spinnerLeaveCat;
    private TextView btnproceed, btnCancel;
    private Switch halfDaySwitch;
    private long fromDate;
    private ArrayAdapter<String> adapter;
    private ArrayList<LeaveHistory> leaveHistoryArrayList;
    private ArrayList<LeaveCategory> leaveCategoryArrayList;
    private Boolean isHalfDay, lop, holiday, weekday, radiobuttonStatus, halfday, status, ispermissioncheck;
    private String name, remark, leavetype, fromdate, enddate, fromdate1, enddate1, reason, date1, date2, leaveid;
    private LinearLayout llHalfDay, llToDate, llFromDate, lllayout;
    private int id, oldleaveid;
    private RadioGroup radiogroup;
    private ArrayList<String> leavetypearraylist;
    private Integer selectedCategory;
    private View fromview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_edit_leave);
        etReason = (EditText) findViewById(R.id.etReason);

        fromview = (View) findViewById(R.id.fromview);
        tvFromDate = (TextView) findViewById(R.id.tvFromDate);
        tvToDate = (TextView) findViewById(R.id.tvToDate);
        tvHalfDate = (TextView) findViewById(R.id.tvHalfDate);

        leaveCategoryArrayList = new ArrayList<>();
        leaveHistoryArrayList = new ArrayList<>();

        leavetypearraylist = new ArrayList<>();

        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        tvHalfDate.setOnClickListener(this);
        tvFromDate.setOnClickListener(this);
        tvToDate.setOnClickListener(this);

        btnproceed = (TextView) findViewById(R.id.btnproceed);
        btnproceed.setOnClickListener(this);
        btnCancel = (TextView) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

        llHalfDay = (LinearLayout) findViewById(R.id.llHalfDay);
        llToDate = (LinearLayout) findViewById(R.id.llToDate);
        llFromDate = (LinearLayout) findViewById(R.id.llFromDate);
        spinnerLeaveCat = (Spinner) findViewById(R.id.spinnerLeaveCat);
        lllayout = (LinearLayout) findViewById(R.id.lllayout);

        halfDaySwitch = (Switch) findViewById(R.id.halfDaySwitch);

        try {
            Intent intent = getIntent();
            leaveid = getIntent().getStringExtra("lvid");
            reason = intent.getStringExtra(Constants.REASON);
            leavetype = intent.getStringExtra("leavetype");
            fromdate = intent.getStringExtra("fromdate");
            enddate = intent.getStringExtra("todate");
            halfday = getIntent().getBooleanExtra(Constants.HALFDAY, false);
            tvFromDate.setText(fromdate);
            tvToDate.setText(enddate);
            tvHalfDate.setText(fromdate);
            etReason.setText(reason);
            leaveCategoryArrayList = (ArrayList<LeaveCategory>) getIntent().getSerializableExtra("types");

            Log.e("=======", "Today in dd-MM-yyyy format : " + fromdate);
            Log.e("=======", "Today in dd-MM-yyyy format : " + enddate);

            if (halfday == Constants.TRUE) {
                halfDaySwitch.setChecked(true);
                isHalfDay = true;
                llHalfDay.setVisibility(View.VISIBLE);
                llFromDate.setVisibility(View.GONE);
                llToDate.setVisibility(View.GONE);
                fromview.setVisibility(View.GONE);
            } else if (halfday == Constants.FALSE) {
                halfDaySwitch.setChecked(false);
                llHalfDay.setVisibility(View.GONE);
                llFromDate.setVisibility(View.VISIBLE);
                isHalfDay = false;
                fromview.setVisibility(View.VISIBLE);
                llToDate.setVisibility(View.VISIBLE);
            }
            leavetypearraylist = new ArrayList<>();
            for (int i = 0; i < leaveCategoryArrayList.size(); i++) {
                leavetypearraylist.add(leaveCategoryArrayList.get(i).getName());
            }
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, leavetypearraylist);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerLeaveCat.setAdapter(adapter);
            spinnerLeaveCat.setSelection(getselection());

            spinnerLeaveCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    if (leaveCategoryArrayList.get(position).getLeavetypeper()) {
                        ispermissioncheck = false;
                        Log.e("=======", "falseeeeeeeeeeeeeeeeeee : " + enddate);
                        selectedCategory = leaveCategoryArrayList.get(position).getId();
                        lllayout.setVisibility(View.VISIBLE);
                        llHalfDay.setVisibility(View.GONE);
                        llFromDate.setVisibility(View.VISIBLE);
                        llToDate.setVisibility(View.VISIBLE);
                    } else {
                        ispermissioncheck = true;
                        isHalfDay = false;
                        Log.e("=======", "truedeeeeeeeeeeeeeeeeee : " + enddate);
                        selectedCategory = leaveCategoryArrayList.get(position).getId();
                        lllayout.setVisibility(View.GONE);
                        llHalfDay.setVisibility(View.VISIBLE);
                        llFromDate.setVisibility(View.GONE);
                        llToDate.setVisibility(View.GONE);
                        openFromDatePicker(true);
                    }
                    Log.e("position", "=======" + selectedCategory);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        halfDaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isHalfDay = isChecked;
                if (isChecked) {
                    llHalfDay.setVisibility(View.VISIBLE);
                    llFromDate.setVisibility(View.GONE);
                    llToDate.setVisibility(View.GONE);
                    isHalfDay = true;
                    openFromDatePicker(true);
                } else {
                    llHalfDay.setVisibility(View.GONE);
                    llFromDate.setVisibility(View.VISIBLE);
                    isHalfDay = false;
                    llToDate.setVisibility(View.VISIBLE);
                }
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etReason.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        getLeaveDetails();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public int getselection() {
        for (int i = 0; i < leaveCategoryArrayList.size(); i++) {

            if (leaveCategoryArrayList.get(i).getName().equalsIgnoreCase(leavetype)) {
                oldleaveid = leaveCategoryArrayList.get(i).getId();
                return i;
            }
        }
        return 0;
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
            case R.id.btnproceed:
                applyLeave();
                overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
                finish();
                break;
            case R.id.btnCancel:
                overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
                finish();
                break;
            case R.id.tvFromDate:
                openFromDatePicker(false);
                break;
            case R.id.tvToDate:
                openToDatePicker();
                break;
            case R.id.tvHalfDate:
                openFromDatePicker(true);
                break;
        }
    }

    private void getLeaveDetails() {
        try {
            if (UIUtil.isInternetAvailable(getApplicationContext())) {

                UIUtil.startProgressDialog(getApplicationContext(), "Please wait Getting Details.");
                Map<String, String> params = new HashMap<String, String>();
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(getApplicationContext()).getApi().leaveApplication(params, new Callback<LeaveResponse>() {
                    @Override
                    public void success(LeaveResponse leaveResponse, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());

                            Log.e("Json ", "Hhd --- " + leaveResponse.toString());
                            Log.e("Json ", "Response --- " + response.getBody());

                            if (leaveResponse.getStatus() == Constants.SUCCESS) {
                                bindData(leaveResponse);
                            } else {

                                Toast.makeText(getApplicationContext(), "" + leaveResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            Log.e("Json ", "Hhd --- " + leaveResponse.toString());
                            Log.e("Json ", "Response --- " + response.getBody());
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
                Toast.makeText(getApplicationContext(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validatefields() {
        for (int i = 0; i < leaveHistoryArrayList.size(); i++) {
            String fromtextdate = tvFromDate.getText().toString();
            String totextdate = tvToDate.getText().toString();
            String haldtextdate = tvHalfDate.getText().toString();
            DateFormat format1 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date applyfromdate = null;
            try {
                applyfromdate = format1.parse(fromtextdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date applyhalfdate = null;
            try {
                applyhalfdate = format1.parse(haldtextdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date applytodate = null;
            try {
                applytodate = format1.parse(totextdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String frserverdate = leaveHistoryArrayList.get(i).getStartDate();
            String toserverdate = leaveHistoryArrayList.get(i).getEndDate();
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date date = null;
            try {
                date = format.parse(frserverdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date date1 = null;
            try {
                date1 = format.parse(toserverdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.e("qqqqqqqqqqqw:", String.valueOf(date) + "" + applyfromdate);
            if (isHalfDay) {
                if ((applyhalfdate.after(date) && applyhalfdate.before(date) && leaveHistoryArrayList.get(i).getStatus()) || (applyhalfdate.after(date1) && applyhalfdate.before(date1) && leaveHistoryArrayList.get(i).getStatus()) || (applyhalfdate.equals(date) || applyhalfdate.equals(date1) && leaveHistoryArrayList.get(i).getStatus())) {
                    Toast.makeText(getApplicationContext(), "Leave already applied for this date", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                if ((applyfromdate.after(date) && applytodate.before(date) && leaveHistoryArrayList.get(i).getStatus()) ||
                        (applyfromdate.after(date1) && applytodate.before(date1) && leaveHistoryArrayList.get(i).getStatus()) ||
                        ((applyfromdate.equals(date) || applyfromdate.equals(date1) || applytodate.equals(date) ||
                                applytodate.equals(date1)) && leaveHistoryArrayList.get(i).getStatus())) {
                    Toast.makeText(getApplicationContext(), "Leave already applied for this date", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;
    }


    private void bindData(LeaveResponse leaveResponse) {
        try {
            Leave leave = leaveResponse.getLeave();

            leaveHistoryArrayList.clear();
            leaveHistoryArrayList.addAll(leave.getLeaveListDetail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openToDatePicker() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String month, day;
                monthOfYear = monthOfYear + 1;
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

                Log.e("Date", " Text --" + day + "/" + month + "/" + String.valueOf(year));
                tvToDate.setText(day + "/" + month + "/" + String.valueOf(year));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(fromDate);
        datePickerDialog.show();
    }

    private void openFromDatePicker(final boolean isHalfDay) {
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

                try {
                    String str_date = day + "/" + month + "/" + String.valueOf(year);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = (Date) formatter.parse(str_date);
                    fromDate = date.getTime();
                    System.out.println("Today is " + date.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (isHalfDay) {
                    tvHalfDate.setText(day + "/" + month + "/" + String.valueOf(year));
                } else {
                    tvFromDate.setText(day + "/" + month + "/" + String.valueOf(year));
                }
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void applyLeave() {
        try {
            if (validatefields()) {
                if (UIUtil.isInternetAvailable(this)) {

                    UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                    JsonObject jsonObject = new JsonObject();
                    JsonObject jsonObject1 = new JsonObject();
                    jsonObject.addProperty("id", leaveid);
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                    jsonObject1.addProperty("employee_leave_type_id", selectedCategory);
                    jsonObject.addProperty("initial_employee_leave_type_id", oldleaveid);
                    if (isHalfDay) {
                        jsonObject1.addProperty("start_date", tvHalfDate.getText().toString() + " - " + tvHalfDate.getText().toString());
                        jsonObject1.addProperty("is_half_day", isHalfDay);
                    } else if (ispermissioncheck) {
                        jsonObject1.addProperty("start_date", tvHalfDate.getText().toString() + " - " + tvHalfDate.getText().toString());
                    } else {
                        jsonObject1.addProperty("start_date", tvFromDate.getText().toString() + " - " + tvToDate.getText().toString());
                    }
                    jsonObject1.addProperty("reason", etReason.getText().toString());
                    jsonObject1.addProperty("responded_by", "Employee");
                    jsonObject.add("leave_application", jsonObject1);

                    RetrofitAPI.getInstance(this).getApi().approveLeave(jsonObject, new Callback<JsonObject>() {
                        @Override
                        public void success(JsonObject jsonObj, Response response) {
                            UIUtil.stopProgressDialog(getApplicationContext());

                            Log.e("Json ", "Hhd --- " + jsonObj.toString());
                            Log.e("Json ", "Response --- " + response.getBody());
                            try {
                                if (Constants.SUCCESS == jsonObj.get("status").getAsInt()) {
                                    //getLeaveDetails();
                                    //clearFieldData();
                                } else {
                                    Toast.makeText(getApplicationContext(), jsonObj.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Toast.makeText(getApplicationContext(), "Something went wrong, try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
