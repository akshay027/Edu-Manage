package com.maxlore.edumanage.Activities.AdminActivities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.AdminHolidayAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminHolidays.Holiday;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminHolidays.HolidayResponse;
import com.maxlore.edumanage.Models.StatusResponseClass;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AdminHoliday extends AppCompatActivity implements View.OnClickListener {
    private AdminHolidayAdapter adminHolidayAdapter;
    private TextView tvFromDate, tvToDate;
    private EditText etType, etReason;
    private TextView tvFromDate1, tvToDate1;
    private EditText etType1, etReason1;
    private TextView btnCreate;
    private RecyclerView rvLeaveHistory;
    private ArrayList<Holiday> holidayArrayList;
    private long fromDate, fromdate1;
    private int result, pos;
    private final Context context = this;
    private TextView tv_nodata;
    private String currentdate;
    private Date chckdate, enddate;
    private Toast mToastToShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_holiday);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvFromDate = (TextView) findViewById(R.id.tvFromDate);
        tvToDate = (TextView) findViewById(R.id.tvToDate);

        etType = (EditText) findViewById(R.id.etType);
        etReason = (EditText) findViewById(R.id.etReason);
        tv_nodata = (TextView) findViewById(R.id.nodata);

        holidayArrayList = new ArrayList();
        currentdate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        try {
            chckdate = new SimpleDateFormat("dd/MM/yyyy")
                    .parse(currentdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rvLeaveHistory = (RecyclerView) findViewById(R.id.rvLeaveHistory);
        rvLeaveHistory.setHasFixedSize(true);
        rvLeaveHistory.setLayoutManager(new LinearLayoutManager(this));
        adminHolidayAdapter = new AdminHolidayAdapter(this, holidayArrayList);
        rvLeaveHistory.setAdapter(adminHolidayAdapter);

        btnCreate = (TextView) findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(this);
        tvFromDate.setOnClickListener(this);
        tvToDate.setOnClickListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etReason.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });


        etType.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        adminHolidayAdapter.SetOnItemClickListener(new AdminHolidayAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(View view, int position) {
                pos = position;
                try {
                    enddate = new SimpleDateFormat("dd/MM/yyyy")
                            .parse(holidayArrayList.get(position).getHolidayEndDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (enddate.before(chckdate)) {
                    Toast.makeText(getApplicationContext(), "Date Exceeded...", Toast.LENGTH_SHORT).show();
                } else {
                    editHoliday(pos);
                }
            }

            @Override
            public void onDeleteClick(View view, int position) {
                result = position;
                try {
                    enddate = new SimpleDateFormat("dd/MM/yyyy")
                            .parse(holidayArrayList.get(position).getHolidayEndDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (enddate.before(chckdate)) {
                    Log.e("check", String.valueOf(chckdate));
                    Log.e("end", String.valueOf(enddate));
                    Log.e("end", holidayArrayList.get(position).getHolidayEndDate());

                    Toast.makeText(getApplicationContext(), "Date Exceeded...", Toast.LENGTH_SHORT).show();
                } else {
                    setMessage();
                }
            }

            @Override
            public void onItemClick(View view, int position) {

            }
        });

        getHolidayDetails();
    }

    private boolean validateFieldsedit() {
        if (etType1.getText().toString().trim().isEmpty()) {
            //etType1.setError("Enter Type");
            Toast.makeText(this, "Please Enter All the Fields", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etType1.setError(null);
        }
        if (etReason1.getText().toString().trim().isEmpty()) {
            // etReason1.setError("Enter Message");
            Toast.makeText(this, "Please Enter All the Fields", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etReason1.setError(null);
        }
       /* for (int i = 0; i < holidayArrayList.size(); i++) {
            if (tvFromDate1.getText().toString().equalsIgnoreCase(holidayArrayList.get(i).getHolidayDate()) ||
                    tvFromDate1.getText().toString().equalsIgnoreCase(holidayArrayList.get(i).getHolidayEndDate())) {
                showToast();
                // btnCreate.setError("Holiday already created for this date");
                //Toast.makeText(getApplicationContext(), "Holiday already created for this date", Toast.LENGTH_SHORT).show();
                return false;
            }
        }*/
        return true;
    }

    private AlertDialog editHoliday(final int pos) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        View promptView = inflater.inflate(R.layout.admin_holiday_dialogbox, null);
        final Holiday holiday1 = holidayArrayList.get(pos);

        tvFromDate1 = (TextView) promptView.findViewById(R.id.tvFromDate1);
        // tvToDate1 = (TextView) promptView.findViewById(R.id.tvToDate1);

        etType1 = (EditText) promptView.findViewById(R.id.etType1);
        etReason1 = (EditText) promptView.findViewById(R.id.etReason1);

        etType1.setInputType(InputType.TYPE_CLASS_TEXT);
        etReason1.setInputType(InputType.TYPE_CLASS_TEXT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etReason1.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });


        etType1.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        etReason1.setText("" + holiday1.getReason());
        etType1.setText("" + holiday1.getHolidayType());

        tvFromDate1.setText("" + holiday1.getHolidayDate());

        builder.setView(promptView)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if ((validateFieldsedit())) {
                            editholidayfinal();
                        } else {
                            editHoliday(pos);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return builder.create();

    }

    private void setMessage() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        final EditText edittext = new EditText(context);
        // set title
        alertDialogBuilder.setTitle("Confirmation");
        alertDialogBuilder
                .setMessage("Do you want to delete the holiday!")
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteMessage();
                    }
                })
                .setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreate:
                createHoliday();
                break;
            case R.id.tvFromDate:
                openFromDatePicker();
                break;
            case R.id.tvToDate:
                openToDatePicker();
                break;
            case R.id.tvFromDate1:
                openFromDatePicker1();
                break;
            case R.id.tvToDate1:
                openToDatePicker1();
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
            startActivity(new Intent(AdminHoliday.this, AdminLandingActivity.class));
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
        startActivity(new Intent(AdminHoliday.this, AdminLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }


    private void editholidayfinal() {
        try {

            if (UIUtil.isInternetAvailable(this)) {

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("holiday_date", tvFromDate1.getText().toString());
                jsonObject.addProperty("holiday_type", etType1.getText().toString());
                jsonObject.addProperty("reason", etReason1.getText().toString());
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                jsonObject.addProperty("holiday_id", holidayArrayList.get(pos).getHolidayId());
                jsonObject.addProperty("manage", "update");

                RetrofitAPI.getInstance(this).getApi().UpdateData(jsonObject, new Callback<StatusResponseClass>() {
                    @Override
                    public void success(StatusResponseClass object, Response response) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Log.e("jsonObject", "jsonObject --- " + object.toString());

                       try {
                            if (object.getStatus() == Constants.SUCCESS) {
                                getHolidayDetails();
                                adminHolidayAdapter.notifyDataSetChanged();
                                Toast.makeText(AdminHoliday.this, object.getMessage(), Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(AdminHoliday.this, object.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("msg ","==="+"msg ni mil rha h");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AdminHoliday.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
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

    private void createHoliday() {
        try {
            if (validateFields()) {

                if (UIUtil.isInternetAvailable(this)) {

                    UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                    JsonObject jsonObject = new JsonObject();
                    JsonObject jsonObject1 = new JsonObject();
                    jsonObject.addProperty("holiday_date", tvFromDate.getText().toString() + "-" + tvToDate.getText().toString());
                    jsonObject.addProperty("holiday_type", etType.getText().toString());
                    jsonObject.addProperty("reason", etReason.getText().toString());
                    jsonObject1.add("holiday", jsonObject);
                    if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                        jsonObject1.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                    } else {
                        jsonObject1.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                    }
                    RetrofitAPI.getInstance(this).getApi().createHoliday(jsonObject1, new Callback<StatusResponseClass>() {
                        @Override
                        public void success(StatusResponseClass jsonObj, Response response) {
                            UIUtil.stopProgressDialog(getApplicationContext());

                            Log.e("Json ", "Hhd --- " + jsonObj.toString());
                            Log.e("Json ", "Response --- " + response.getBody());
                            try {

                                if (Constants.SUCCESS == jsonObj.getStatus()) {
                                    UIUtil.stopProgressDialog(getApplicationContext());
                                    Toast.makeText(getApplicationContext(), "holiday Created successfully", Toast.LENGTH_SHORT).show();
                                    getHolidayDetails();
                                    clearFieldData();
                                } else {
                                    Toast.makeText(getApplicationContext(), jsonObj.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void deleteMessage() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("holiday_id", holidayArrayList.get(result).getHolidayId());
                jsonObject.addProperty("manage", "delete");
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().UpdateData(jsonObject, new Callback<StatusResponseClass>() {
                    @Override
                    public void success(StatusResponseClass object, Response response) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Log.e("jsonObject", "jsonObject --- " + object.toString());

                        try {
                            if (object.getStatus() == null) {
                                Toast.makeText(AdminHoliday.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (object.getStatus() == Constants.SUCCESS) {
                                getHolidayDetails();
                                adminHolidayAdapter.notifyDataSetChanged();
                                Toast.makeText(AdminHoliday.this, "holiday Deleted successfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(AdminHoliday.this, object.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AdminHoliday.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
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

    private void clearFieldData() {
        tvFromDate.setText("");
        tvToDate.setText("");
        etType.setText("");
        etReason.setText("");
    }

    /*
        private boolean validateFields1() {
            for (int i = 0; i < holidayArrayList.size(); i++) {
                if (tvFromDate1.getText().toString().equalsIgnoreCase(holidayArrayList.get(i).getHolidayDate())
                        || tvFromDate1.getText().toString().equalsIgnoreCase(holidayArrayList.get(i).getHolidayEndDate())) {
                    Toast.makeText(getApplicationContext(), "This Date is Already Applied", Toast.LENGTH_LONG).show();
                    return false;
                }

            }return true;
        }
    */


    public void showToast() {
        // Set the toast and duration
        int toastDurationInMilliSeconds = 100;
        mToastToShow = Toast.makeText(this, "Holiday already created for this date", Toast.LENGTH_SHORT);

        // Set the countdown to display the toast
        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 100 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                mToastToShow.show();
            }

            public void onFinish() {
                mToastToShow.cancel();
            }
        };

        // Show the toast and starts the countdown
        mToastToShow.show();
        toastCountDown.start();
    }

    private boolean validateFields() {

        if (tvFromDate.getText().toString().trim().isEmpty()) {
            tvFromDate.setError("Enter From Date");
            // Toast.makeText(this, "Select From Date", Toast.LENGTH_SHORT).show();
            return false;
        } else {

            tvFromDate.setError(null);
        }
        if (tvToDate.getText().toString().trim().isEmpty()) {
            tvToDate.setError("Enter To Date");
            //Toast.makeText(this, "Select To Date", Toast.LENGTH_SHORT).show();
            return false;
        } else {

            tvToDate.setError(null);
        }
        String type = etType.getText().toString();
        if (etType.getText().toString().trim().isEmpty()) {
            etType.setError("Enter Type");
            // Toast.makeText(this, "Enter Type", Toast.LENGTH_SHORT).show();
            return false;
        } else {

            etType.setError(null);
        }
        if (!type.matches("[a-zA-Z ]+")) {
            etType.requestFocus();
            etType.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        } else {

            etType.setError(null);
        }
        if (etReason.getText().toString().trim().isEmpty()) {
            etReason.setError("Enter Reason For Holiday");
            // Toast.makeText(this, "Enter Reason For Holiday", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etReason.setError(null);
        }
        for (int i = 0; i < holidayArrayList.size(); i++) {
            if (tvFromDate.getText().toString().equalsIgnoreCase(holidayArrayList.get(i).getHolidayDate()) ||
                    tvToDate.getText().toString().equalsIgnoreCase(holidayArrayList.get(i).getHolidayEndDate())
                    || tvFromDate.getText().toString().equalsIgnoreCase(holidayArrayList.get(i).getHolidayEndDate()) ||
                    tvToDate.getText().toString().equalsIgnoreCase(holidayArrayList.get(i).getHolidayDate())) {
                showToast();
               // btnCreate.setError("Holiday already created for this date");
                //Toast.makeText(getApplicationContext(), "Holiday already created for this date", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void getHolidayDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().getHolidayDetails(params, new Callback<HolidayResponse>() {
                    @Override
                    public void success(HolidayResponse holidayResponse, Response response) {
                        try {

                            Log.e("Json ", "Hhd --- " + holidayResponse.toString());
                            Log.e("Json ", "Response --- " + response.getBody());

                            if (holidayResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                bindDataforHoliday(holidayResponse);
                            } else {
                                Toast.makeText(getApplicationContext(), "" + holidayResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Log.e("Json ", "Hhd --- " + holidayResponse.toString());
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

    private void bindDataforHoliday(HolidayResponse holidayResponse) {
        try {
            holidayArrayList.clear();
            holidayArrayList.addAll(holidayResponse.getHoliday());
            if (holidayArrayList.size() <= 0) {
                rvLeaveHistory.setVisibility(View.GONE);
                tv_nodata.setVisibility(View.VISIBLE);
            } else {
                rvLeaveHistory.setVisibility(View.VISIBLE);
                tv_nodata.setVisibility(View.GONE);
            }
            adminHolidayAdapter.notifyDataSetChanged();
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
//        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.getDatePicker().setMinDate(fromDate);
        datePickerDialog.show();
    }

    private void openFromDatePicker() {
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
                tvFromDate.setText(day + "/" + month + "/" + String.valueOf(year));
                tvToDate.setEnabled(true);
                try {
                    String str_date = day + "/" + month + "/" + String.valueOf(year);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = (Date) formatter.parse(str_date);
                    fromDate = date.getTime();
                    System.out.println("Today is " + date.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void openToDatePicker1() {
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
                tvToDate1.setText(day + "/" + month + "/" + String.valueOf(year));

//                edDOB.setText(String.valueOf(year) + "-" + month + "-" + day);
//                tvDob.setText(newDate.toString());
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.getDatePicker().setMinDate(fromdate1);
        datePickerDialog.show();
    }

    private void openFromDatePicker1() {
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
                tvFromDate1.setText(day + "/" + month + "/" + String.valueOf(year));
                try {
                    String str_date = day + "/" + month + "/" + String.valueOf(year);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = (Date) formatter.parse(str_date);
                    fromdate1 = date.getTime();
                    System.out.println("Today is " + date.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

}
