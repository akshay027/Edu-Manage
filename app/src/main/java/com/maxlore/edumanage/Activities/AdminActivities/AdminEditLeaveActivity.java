package com.maxlore.edumanage.Activities.AdminActivities;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminLeave.EditData;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AdminEditLeaveActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvName, tvFromDate, tvToDate, tvHalfDate, etReason;
    private EditText etRemark;
    private Spinner spinnerLeaveCat;
    private TextView btnSubmit, btnCancel;
    private Switch halfDaySwitch, lopSwitch, holidaySwitch, weekdaysSwitch;
    private long fromDate;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> categorieslist;
    private Boolean isHalfDay, lop, holiday, weekday, radiobuttonStatus, halfday, status, ispermissioncheck;
    private String name, remark, leavetype, fromdate, enddate, fromdate1, enddate1, reason, date1, date2;
    private LinearLayout llHalfDay, llToDate, llFromDate, lllayout;
    private int id, leaveid, oldleaveid;
    private RadioButton rbYes, rbNo;
    private ArrayList<EditData> arrayList;
    private RadioGroup radiogroup;
    private ArrayList<String> leavetypearraylist;
    private ArrayList<EditData> nameArraylist;
    private Integer selectedCategory;
    private View fromview, toview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_leave);

        etReason = (TextView) findViewById(R.id.etReason);
        etRemark = (EditText) findViewById(R.id.etRemark);

        fromview = (View) findViewById(R.id.fromview);
        toview = (View) findViewById(R.id.toview);
        tvName = (TextView) findViewById(R.id.tvName);
        tvFromDate = (TextView) findViewById(R.id.tvFromDate);
        tvToDate = (TextView) findViewById(R.id.tvToDate);
        tvHalfDate = (TextView) findViewById(R.id.tvHalfDate);

        rbYes = (RadioButton) findViewById(R.id.rbYes);
        arrayList = new ArrayList<>();

        leavetypearraylist = new ArrayList<>();
        nameArraylist = new ArrayList<>();

        rbNo = (RadioButton) findViewById(R.id.rbNo);

        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        tvHalfDate.setOnClickListener(this);
        tvFromDate.setOnClickListener(this);
        tvToDate.setOnClickListener(this);

        btnSubmit = (TextView) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        btnCancel = (TextView) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

        llHalfDay = (LinearLayout) findViewById(R.id.llHalfDay);
        llToDate = (LinearLayout) findViewById(R.id.llToDate);
        llFromDate = (LinearLayout) findViewById(R.id.llFromDate);
        spinnerLeaveCat = (Spinner) findViewById(R.id.spinnerLeaveCat);
        lllayout = (LinearLayout) findViewById(R.id.lllayout);

        halfDaySwitch = (Switch) findViewById(R.id.halfDaySwitch);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etRemark.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        try {

            Intent intent = getIntent();
            leaveid = getIntent().getIntExtra("lvid", 0);
            status = getIntent().getBooleanExtra("status", false);
            id = getIntent().getIntExtra("id", 0);
            name = intent.getStringExtra(Constants.NAME);
            remark = intent.getStringExtra(Constants.REMARK);
            reason = intent.getStringExtra(Constants.REASON);
            leavetype = intent.getStringExtra("leavetype");
            fromdate = intent.getStringExtra(Constants.FROMDATE);
            enddate = intent.getStringExtra(Constants.ENDDATE);
            halfday = getIntent().getBooleanExtra(Constants.HALFDAY, false);
            leavetypearraylist = intent.getStringArrayListExtra("leavetype");
            nameArraylist = (ArrayList<EditData>) getIntent().getSerializableExtra("List");

            Log.e("=======", "Today in dd-MM-yyyy format : " + fromdate);
            Log.e("=======", "Today in dd-MM-yyyy format : " + enddate);

            if (halfday == Constants.TRUE) {
                halfDaySwitch.setChecked(true);
                isHalfDay = true;
                llHalfDay.setVisibility(View.VISIBLE);
                llFromDate.setVisibility(View.GONE);
                llToDate.setVisibility(View.GONE);
                fromview.setVisibility(View.GONE);
                toview.setVisibility(View.GONE);

            } else if (halfday == Constants.FALSE) {
                halfDaySwitch.setChecked(false);
                llHalfDay.setVisibility(View.GONE);
                llFromDate.setVisibility(View.VISIBLE);
                isHalfDay = false;
                fromview.setVisibility(View.VISIBLE);
                llToDate.setVisibility(View.VISIBLE);
                toview.setVisibility(View.VISIBLE);

            }
            categorieslist = new ArrayList<>();
            for (int i = 0; i < nameArraylist.size(); i++) {
                categorieslist.add(nameArraylist.get(i).getName());
            }
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorieslist);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerLeaveCat.setAdapter(adapter);
            spinnerLeaveCat.setSelection(
                    getSelection());

            spinnerLeaveCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    if (nameArraylist.get(position).getPermission()) {
                        ispermissioncheck = false;
                        selectedCategory = nameArraylist.get(position).getLeaveTypeId();
                        lllayout.setVisibility(View.VISIBLE);
                        llHalfDay.setVisibility(View.GONE);
                        llFromDate.setVisibility(View.VISIBLE);
                        llToDate.setVisibility(View.VISIBLE);
                        toview.setVisibility(View.VISIBLE);
                        fromview.setVisibility(View.VISIBLE);
                    } else {
                        ispermissioncheck = true;
                        isHalfDay = false;
                        selectedCategory = nameArraylist.get(position).getLeaveTypeId();
                        lllayout.setVisibility(View.GONE);
                        llHalfDay.setVisibility(View.VISIBLE);
                        llFromDate.setVisibility(View.GONE);
                        llToDate.setVisibility(View.GONE);
                        toview.setVisibility(View.GONE);
                        fromview.setVisibility(View.GONE);
                        openFromDatePicker(true);
                    }
                    Log.e("position", "=======" + selectedCategory);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            Log.e("status", "-----" + status);
            Log.e("halfday", "-----" + halfday);

            Log.e("leaveid", "-----" + leaveid);
            if (status) {
                radiogroup.check(rbYes.getId());
                radiobuttonStatus = true;
            } else {
                radiogroup.check(rbNo.getId());
                radiobuttonStatus = false;
            }
            ((TextView) findViewById(R.id.tvName)).setText(name);
            ((TextView) findViewById(R.id.etReason)).setText(reason);
            ((TextView) findViewById(R.id.tvFromDate)).setText(fromdate);
            ((TextView) findViewById(R.id.tvToDate)).setText(enddate);
            ((TextView) findViewById(R.id.tvHalfDate)).setText(fromdate);
            ((EditText) findViewById(R.id.etRemark)).setText(remark);
            ((Spinner) findViewById(R.id.spinnerLeaveCat)).setTag(leavetype);
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


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public int getSelection() {
        for (int i = 0; i < nameArraylist.size(); i++) {

            if (nameArraylist.get(i).getName().equalsIgnoreCase(leavetype)) {
                oldleaveid = nameArraylist.get(i).getLeaveTypeId();
                return i;
            }
        }
        return 0;
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rbYes:
                if (checked)
                    radiobuttonStatus = true;
                break;
            case R.id.rbNo:
                if (checked)
                    radiobuttonStatus = false;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
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


    private void applyLeave() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                JsonObject jsonObject = new JsonObject();
                JsonObject jsonObject1 = new JsonObject();
                jsonObject.addProperty("id", leaveid);
                jsonObject1.addProperty("employee_id", id);
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
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
                jsonObject1.addProperty("approved", radiobuttonStatus);
                jsonObject1.addProperty("remarks", etRemark.getText().toString());
                jsonObject1.addProperty("responded_by", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE));
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

//                edDOB.setText(String.valueOf(year) + "-" + month + "-" + day);
//                tvDob.setText(newDate.toString());

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
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
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
}