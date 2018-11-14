package com.maxlore.edumanage.Activities.TeacherActivities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.TeacherAdapters.LeaveHistoryAdapter;
import com.maxlore.edumanage.Adapters.TeacherAdapters.LeaveTypeAdapter;
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

public class LeaveActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView lvLeaveCart, lvLeaveHistory;
    //    private ArrayList<>
    private TextView tvName, tvFromDate, tvToDate, tvHalfDate, nodata, status, tvleaveAM, tvleavePM, btnSubmit, noleave;
    private EditText etReason;
    private Spinner spinnerLeaveCat;
    private ArrayList<LeaveCategory> leaveCategories;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> categorieslist;
    private LeaveTypeAdapter listAdapter;
    private ArrayList<LeaveHistory> leaveHistoryArrayList;
    private LeaveHistoryAdapter historyAdapter;
    private String selectedCategory = "";
    private int pos;
    private ArrayList<String> leavedatesArrayList;
    private Boolean leavetypecheck;
    private long fromDate;
    private Switch halfDaySwitch;
    private LinearLayout llHalfDay, llToDate, llFromDate, llAMPMleave, llhalfcheck, linearfullleave;
    private boolean isHalfDay, ispermissioncheck;
    private String meridiem = "";
    private ScrollView scrollfull;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_leave);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        etReason = (EditText) findViewById(R.id.etReason);
        scrollfull = (ScrollView) findViewById(R.id.scrollfull);
        linearfullleave = (LinearLayout) findViewById(R.id.linearfullleave);
        noleave = (TextView) findViewById(R.id.noleave);

        status = (TextView) findViewById(R.id.status);
        tvName = (TextView) findViewById(R.id.tvName);
        tvFromDate = (TextView) findViewById(R.id.tvFromDate);
        tvToDate = (TextView) findViewById(R.id.tvToDate);
        tvHalfDate = (TextView) findViewById(R.id.tvHalfDate);
        nodata = (TextView) findViewById(R.id.nodata);
        tvleaveAM = (TextView) findViewById(R.id.tvleaveAM);
        tvleavePM = (TextView) findViewById(R.id.tvleavePM);

        tvHalfDate.setOnClickListener(this);
        tvFromDate.setOnClickListener(this);
        tvToDate.setOnClickListener(this);
        tvleavePM.setOnClickListener(this);
        tvleaveAM.setOnClickListener(this);

        btnSubmit = (TextView) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        llhalfcheck = (LinearLayout) findViewById(R.id.llhalfcheck);
        llHalfDay = (LinearLayout) findViewById(R.id.llHalfDay);
        llToDate = (LinearLayout) findViewById(R.id.llToDate);
        llFromDate = (LinearLayout) findViewById(R.id.llFromDate);
        llAMPMleave = (LinearLayout) findViewById(R.id.llAMPMleave);

        spinnerLeaveCat = (Spinner) findViewById(R.id.spinnerLeaveCat);
        halfDaySwitch = (Switch) findViewById(R.id.halfDaySwitch);
//        checkableHalfDay = (CheckBox) findViewById(R.id.checkBox);

//        checkableHalfDay.setOnClickListener(this);

        lvLeaveCart = (ListView) findViewById(R.id.lvLeaveCart);
        lvLeaveHistory = (ListView) findViewById(R.id.lvLeaveHistory);

        leaveCategories = new ArrayList<>();
        leaveHistoryArrayList = new ArrayList<>();

        categorieslist = new ArrayList<>();
        categorieslist.add("Select leave type");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorieslist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLeaveCat.setAdapter(adapter);
        leavedatesArrayList = new ArrayList<>();

        listAdapter = new LeaveTypeAdapter(this, leaveCategories);
        lvLeaveCart.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etReason.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        historyAdapter = new LeaveHistoryAdapter(this, leaveHistoryArrayList);
        lvLeaveHistory.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();

        setSupportActionBar(toolbar);

        getLeaveDetails();


        spinnerLeaveCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    selectedCategory = "";
                } else {
                    if (leaveCategories.get(position - 1).getLeavetypeper()) {
                        selectedCategory = categorieslist.get(position);
                        llhalfcheck.setVisibility(View.VISIBLE);
                        llHalfDay.setVisibility(View.GONE);
                        llFromDate.setVisibility(View.VISIBLE);
                        llToDate.setVisibility(View.VISIBLE);
                    } else {
                        ispermissioncheck = true;
                        isHalfDay = false;
                        selectedCategory = categorieslist.get(position);
                        llhalfcheck.setVisibility(View.GONE);
                        llHalfDay.setVisibility(View.VISIBLE);
                        llFromDate.setVisibility(View.GONE);
                        llToDate.setVisibility(View.GONE);
                        openFromDatePicker(true);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        halfDaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isHalfDay = isChecked;
                if (isChecked) {
                    llHalfDay.setVisibility(View.VISIBLE);
                    llFromDate.setVisibility(View.GONE);
                    llToDate.setVisibility(View.GONE);
                    meridiem = "AM";
                    llAMPMleave.setVisibility(View.VISIBLE);
                    openFromDatePicker(true);
                } else {
                    llHalfDay.setVisibility(View.GONE);
                    llFromDate.setVisibility(View.VISIBLE);
                    llToDate.setVisibility(View.VISIBLE);
                    llAMPMleave.setVisibility(View.GONE);
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                applyLeave();
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
            case R.id.tvleaveAM:
                selectAM();
                break;
            case R.id.tvleavePM:
                selectPM();
                break;
        }
    }

    private void selectAM() {

        meridiem = "AM";
        tvleaveAM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tvleaveAM.setTextColor(getResources().getColor(R.color.white));

        tvleavePM.setBackgroundColor(getResources().getColor(R.color.white));
        tvleavePM.setTextColor(getResources().getColor(R.color.textBlackDark));
    }

    private void selectPM() {
        meridiem = "PM";
        tvleavePM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tvleavePM.setTextColor(getResources().getColor(R.color.white));

        tvleaveAM.setBackgroundColor(getResources().getColor(R.color.white));
        tvleaveAM.setTextColor(getResources().getColor(R.color.textBlackDark));

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
            startActivity(new Intent(LeaveActivity.this, TeacherLandingActivity.class));
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
        startActivity(new Intent(LeaveActivity.this, TeacherLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }


    private void applyLeave() {
        try {
            if (validateFields()) {
                if (UIUtil.isInternetAvailable(this)) {
                    UIUtil.startProgressDialog(this, "Please Wait.. Applying Leave");
                    JsonObject jsonObject = new JsonObject();
                    JsonObject jsonObject1 = new JsonObject();
                    if (isHalfDay) {
                        jsonObject1.addProperty("start_date", tvHalfDate.getText().toString() + " - " + tvHalfDate.getText().toString());
                        jsonObject.addProperty("start_date", tvHalfDate.getText().toString());
                        jsonObject.addProperty("end_date", tvHalfDate.getText().toString());
                        jsonObject1.addProperty("is_half_day", isHalfDay);
                        jsonObject1.addProperty("meridiem", meridiem);
                    } /*else if (ispermissioncheck) {
                        jsonObject1.addProperty("start_date", tvHalfDate.getText().toString() + " - " + tvHalfDate.getText().toString());
                        jsonObject.addProperty("start_date", tvHalfDate.getText().toString());
                        jsonObject.addProperty("end_date", tvHalfDate.getText().toString());
                    }*/ else {
                        jsonObject1.addProperty("start_date", tvFromDate.getText().toString() + " - " + tvToDate.getText().toString());
                        jsonObject.addProperty("start_date", tvFromDate.getText().toString());
                        jsonObject.addProperty("end_date", tvToDate.getText().toString());
                    }
                    jsonObject1.addProperty("employee_leave_type_id", getLeaveCategory(selectedCategory));
                    jsonObject1.addProperty("reason", etReason.getText().toString());
                    jsonObject.add("leave_application", jsonObject1);
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                    RetrofitAPI.getInstance(this).getApi().applyLeave(jsonObject, new Callback<JsonObject>() {
                        @Override
                        public void success(JsonObject jsonObj, Response response) {
                            UIUtil.stopProgressDialog(getApplicationContext());

                            Log.e("Json ", "Hhd --- " + jsonObj.toString());
                            Log.e("Json ", "Response --- " + response.getBody());
                            try {

                                if (Constants.SUCCESS == jsonObj.get("status").getAsInt()) {
                                    getLeaveDetails();
                                    clearFieldData();
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

    private boolean validateFields() {
        for (int i = 0; i < leavedatesArrayList.size(); i++) {
            if (tvFromDate.getText().toString().equalsIgnoreCase(leavedatesArrayList.get(i)) || tvHalfDate.getText().toString().equalsIgnoreCase(leavedatesArrayList.get(i)) || tvToDate.getText().toString().equalsIgnoreCase(leavedatesArrayList.get(i))) {
                Toast.makeText(getApplicationContext(), "Leave Already Applied for this date", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (TextUtils.isEmpty(selectedCategory)) {
            Toast.makeText(this, "Enter all the mandatory fields", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Select Leave Type", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (isHalfDay || ispermissioncheck) {
            if (TextUtils.isEmpty(tvHalfDate.getText().toString())) {
                Toast.makeText(this, "Enter all the mandatory fields", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Select Date", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            if (TextUtils.isEmpty(tvFromDate.getText().toString())) {
                Toast.makeText(this, "Enter all the mandatory fields", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Select From Date", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (TextUtils.isEmpty(tvToDate.getText().toString())) {
                Toast.makeText(this, "Enter all the mandatory fields", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Select To Date", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (etReason.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter all the mandatory fields", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Enter Reason For Leave", Toast.LENGTH_SHORT).show();
            return false;
        } else {

            etReason.setError(null);
        }
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
            Log.e("qqqqqqqqqqqqqqw:      ", String.valueOf(date) + "" + applyfromdate);
            if (isHalfDay) {
                if ((applyhalfdate.after(date) && applyhalfdate.before(date) && leaveHistoryArrayList.get(i).getStatus()) || (applyhalfdate.after(date1) && applyhalfdate.before(date1) && leaveHistoryArrayList.get(i).getStatus()) || (applyhalfdate.equals(date) || applyhalfdate.equals(date1) && leaveHistoryArrayList.get(i).getStatus())) {
                    Toast.makeText(this, "Leave already applied for this date", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                if ((applyfromdate.after(date) && applytodate.before(date) && leaveHistoryArrayList.get(i).getStatus()) || (applyfromdate.after(date1) && applytodate.before(date1) && leaveHistoryArrayList.get(i).getStatus()) || ((applyfromdate.equals(date) || applyfromdate.equals(date1) || applytodate.equals(date) || applytodate.equals(date1)) && leaveHistoryArrayList.get(i).getStatus())) {
                    Toast.makeText(this, "Leave already applied for this date", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }

        return true;

    }

    private void getLeaveDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().leaveApplication(params, new Callback<LeaveResponse>() {
                    @Override
                    public void success(LeaveResponse leaveResponse, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());

                            Log.e("Json ", "Hhd --- " + leaveResponse.toString());
                            Log.e("Json ", "Response --- " + response.getBody());

                            if (leaveResponse.getStatus() == Constants.SUCCESS) {
                                bindData(leaveResponse);
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                linearfullleave.setVisibility(View.GONE);
                                noleave.setVisibility(View.VISIBLE);
                                scrollfull.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "" + leaveResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.e("Json ", "Hhd --- " + leaveResponse.toString());
                        Log.e("Json ", "Response --- " + response.getBody());

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

    private void bindData(LeaveResponse leaveResponse) {
        try {
            Leave leave = leaveResponse.getLeave();
            tvName.setText("" + leave.getName());

            updateSpinner();

            leaveCategories.clear();
            leaveCategories.addAll(leave.getLeaveCategory());
            listAdapter.notifyDataSetChanged();
            leavedatesArrayList.addAll(leave.getLeaveDates());
            UIUtil.setListViewHeightBasedOnChildren(lvLeaveCart);
            updateSpinner();

            leaveHistoryArrayList.clear();
            leaveHistoryArrayList.addAll(leave.getLeaveListDetail());
            historyAdapter.notifyDataSetChanged();
            for (int i = 0; i < leaveHistoryArrayList.size(); i++) {

                if (leaveHistoryArrayList.get(i).getStatus() == null) {
                    btnSubmit.setVisibility(View.GONE);
                    status.setVisibility(View.VISIBLE);
                    break;
                }

            }
            UIUtil.setListViewHeightBasedOnChildren(lvLeaveHistory);

            if (leaveHistoryArrayList.size() <= 0) {
                nodata.setVisibility(View.VISIBLE);
                lvLeaveHistory.setVisibility(View.GONE);
            } else {
                nodata.setVisibility(View.GONE);
                lvLeaveHistory.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateSpinner() {
        try {
            categorieslist.clear();
            categorieslist.add("Select leave Type");

            for (int i = 0; i < leaveCategories.size(); i++) {
                categorieslist.add(leaveCategories.get(i).getName());
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getLeaveCategory(String cat) {
        for (int i = 0; i < leaveCategories.size(); i++) {
            if (cat.equalsIgnoreCase(leaveCategories.get(i).getName())) {

                return leaveCategories.get(i).getId();
            }
        }
        return 0;
    }

    private void clearFieldData() {
        tvHalfDate.setText("");
        tvFromDate.setText("");
        tvToDate.setText("");
        etReason.setText("");
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
                for (int i = 0; i < leavedatesArrayList.size(); i++) {
                    if (tvToDate.getText().toString().equalsIgnoreCase(leavedatesArrayList.get(i))) {
                        Toast.makeText(getApplicationContext(), "Leave Already Applied for this date", Toast.LENGTH_SHORT).show();
                    }
                }
//                edDOB.setText(String.valueOf(year) + "-" + month + "-" + day);
//                tvDob.setText(newDate.toString());

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
                //Calendar myCalendar = Calendar.getInstance();
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
                    String str_date = day + "-" + month + "-" + String.valueOf(year);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
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
                    tvToDate.setEnabled(true);
                }
                for (int i = 0; i < leavedatesArrayList.size(); i++) {
                    if (tvFromDate.getText().toString().equalsIgnoreCase(leavedatesArrayList.get(i)) || tvHalfDate.getText().toString().equalsIgnoreCase(leavedatesArrayList.get(i))) {
                        Toast.makeText(getApplicationContext(), "Leave Already Applied for this date", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

}
