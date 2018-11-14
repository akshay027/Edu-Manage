package com.maxlore.edumanage.Activities.ParentActivities;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.ParentAdapters.ParentLeaveHistoryAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.ParentModels.ParentLeaves.ParentLeave;
import com.maxlore.edumanage.Models.ParentModels.ParentLeaves.ParentLeaveHistory;
import com.maxlore.edumanage.Models.ParentModels.ParentLeaves.ParentLeaveResponse;

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

public class ParentLeaveActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView lvLeaveCart, lvLeaveHistory;
    private TextView tvName, tvleaveAM, tvleavePM, tvTeacherName, tvFromDate, tvToDate, tvHalfDate, tvHalfDate1, nodata, tvleaveAM1, tvleavePM1;
    private EditText etReason;
    private ArrayAdapter<String> spinneradapter;
    private TextView btnSubmit;
    private ParentLeave parentLeave;
    private Handler handler;
    public static final int TIME_OUT = 1000;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> categories;
    private ArrayAdapter categoriesAdapter;
    private ArrayList<ParentLeaveHistory> leaveHistoryArrayList;
    private ParentLeaveHistoryAdapter parenthistoryAdapter;
    private String selectedCategory = "";
    private ArrayAdapter listAdapter;
    private long fromDate, fromdate1;
    private Spinner spinner;
    private Switch halfDaySwitch, halfDaySwitch1, halfDaySwitchParent;
    private LinearLayout llHalfDay, llToDate, llFromDate, llHalfDay1, llFromDate1, llToDate1, llAMPMleave;
    private boolean isHalfDay, isHalfDay1, ccc;
    private ArrayList<ParentLeaveHistory> parentLeaveArrayList;
    private String spiner;
    private int result;
    private TextView tvFromDate1, tvToDate1;
    private EditText etType1, etReason1;
    private String meridiem1 = "";
    private int editpos, deletepos;
    private String meridiem = "";
    private LinearLayout llAMPMleave1;
    private String teachername;
    private String curDate;
    private View fromview1, tvampmview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_leave);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvHalfDate = (TextView) findViewById(R.id.tvHalfDate);
        tvName = (TextView) findViewById(R.id.tvPName);
        tvFromDate = (TextView) findViewById(R.id.tvFromDate);
        tvToDate = (TextView) findViewById(R.id.tvToDate);
        btnSubmit = (TextView) findViewById(R.id.btnSubmit);
        tvTeacherName = (TextView) findViewById(R.id.tvPTeacherName);
        nodata = (TextView) findViewById(R.id.nodata);
        etReason = (EditText) findViewById(R.id.etReason);
        halfDaySwitchParent = (Switch) findViewById(R.id.halfDaySwitchParent);
        llHalfDay = (LinearLayout) findViewById(R.id.llHalfDay);
        tvFromDate.setOnClickListener(this);
        tvToDate.setOnClickListener(this);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        curDate = sdf.format(date.getTime());
// print the date in your log cat
        Log.d("CUR_DATE", curDate);

        tvHalfDate.setOnClickListener(this);
        parentLeaveArrayList = new ArrayList();
        categories = new ArrayList<>();
        btnSubmit.setOnClickListener(this);

        llToDate = (LinearLayout) findViewById(R.id.llToDate);
        llFromDate = (LinearLayout) findViewById(R.id.llFromDate);

        llAMPMleave = (LinearLayout) findViewById(R.id.llAMPMleave);
        tvleaveAM = (TextView) findViewById(R.id.tvleaveAM);
        tvleavePM = (TextView) findViewById(R.id.tvleavePM);

        handler = new Handler();
        MenuItem item = (MenuItem) findViewById(R.id.spinner);

        lvLeaveHistory = (RecyclerView) findViewById(R.id.lvLeaveHistory);
        leaveHistoryArrayList = new ArrayList<>();
        LocalBroadcastManager.getInstance(this).registerReceiver(pushBroadcastReceiver, new IntentFilter("chat"));

        lvLeaveHistory.setHasFixedSize(true);
        lvLeaveHistory.setLayoutManager(new LinearLayoutManager(this));
        parenthistoryAdapter = new ParentLeaveHistoryAdapter(this, leaveHistoryArrayList);
        lvLeaveHistory.setAdapter(parenthistoryAdapter);
        tvleavePM.setOnClickListener(this);
        tvleaveAM.setOnClickListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etReason.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        getLeaveDetails();

        // tvName.setText(parentLeaveArrayList.get(0).getName());
        // tvTeacherName.setText(parentLeaveArrayList.get(0).getClassTeacherName());

        //Log.e("name","0000"+parentLeaveArrayList.get(0).getName());
        //Log.e("tvTeacherName","0000"+parentLeaveArrayList.get(0).getClassTeacherName());
        parenthistoryAdapter.SetOnItemClickListener(new ParentLeaveHistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void ondeleteClick(View view, int position) {
               /* if (leaveHistoryArrayList.get(position).getFromDate().compareTo(curDate) < 0 || leaveHistoryArrayList.get(position).getToDate().compareTo(curDate) < 0) {
                    Toast.makeText(getApplicationContext(), "Date Already Exceeded", Toast.LENGTH_SHORT).show();
                } else {*/
                deletepos = position;
                delete();

            }

            @Override
            public void oneditClick(View view, int position) {
                editpos = position;
                editleave(position);
            }
        });
        halfDaySwitchParent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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


    }

    private void delete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        // set title
        alertDialogBuilder.setTitle("Confirmation");
        alertDialogBuilder
                .setMessage("Do you want to Delete it ?")
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        deleteMessage();

                    }
                })
                .setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private AlertDialog editleave(final int pos) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View promptView = inflater.inflate(R.layout.admin_leave_edit_dialogbox, null);
        final ParentLeaveHistory parentLeaveHistory = leaveHistoryArrayList.get(pos);

        tvFromDate1 = (TextView) promptView.findViewById(R.id.tvFromDate1);
        tvToDate1 = (TextView) promptView.findViewById(R.id.tvToDate1);

        llHalfDay1 = (LinearLayout) promptView.findViewById(R.id.llHalfDay1);
        etReason1 = (EditText) promptView.findViewById(R.id.etReason1);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etReason1.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        tvleavePM1 = (TextView) promptView.findViewById(R.id.tvleavePM1);
        tvleaveAM1 = (TextView) promptView.findViewById(R.id.tvleaveAM1);
        fromview1 = (View) promptView.findViewById(R.id.fromview1);
        tvampmview = (View) promptView.findViewById(R.id.tvampmview);

        tvleavePM1.setOnClickListener(this);
        tvleaveAM1.setOnClickListener(this);
        tvHalfDate1 = (TextView) promptView.findViewById(R.id.tvHalfDate1);
        tvHalfDate1.setOnClickListener(this);
        llToDate1 = (LinearLayout) promptView.findViewById(R.id.llToDate1);
        llFromDate1 = (LinearLayout) promptView.findViewById(R.id.llFromDate1);
        etReason1.setInputType(InputType.TYPE_CLASS_TEXT);
        halfDaySwitch1 = (Switch) promptView.findViewById(R.id.halfDaySwitch1);
        llAMPMleave1 = (LinearLayout) promptView.findViewById(R.id.llAMPMleave1);
        halfDaySwitch1.setChecked(parentLeaveHistory.getIsHalfDay());
        halfDaySwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isHalfDay1 = isChecked;

                if (isChecked) {
                    llHalfDay1.setVisibility(View.VISIBLE);
                    llFromDate1.setVisibility(View.GONE);
                    llToDate1.setVisibility(View.GONE);
                    meridiem1 = "AM";
                    llAMPMleave1.setVisibility(View.VISIBLE);
                    openFromDatePicker1(true);
                    fromview1.setVisibility(View.GONE);
                    tvampmview.setVisibility(View.VISIBLE);
                } else {
                    llHalfDay1.setVisibility(View.GONE);
                    llFromDate1.setVisibility(View.VISIBLE);
                    llToDate1.setVisibility(View.VISIBLE);
                    llAMPMleave1.setVisibility(View.GONE);
                    fromview1.setVisibility(View.VISIBLE);
                    tvampmview.setVisibility(View.GONE);

                }
            }
        });

        etReason1.setText("" + parentLeaveHistory.getReason());
        tvFromDate1.setText("" + parentLeaveHistory.getFromDate());
        tvToDate1.setText("" + parentLeaveHistory.getToDate());


        builder.setView(promptView)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        editMessage();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                applyLeave();
                break;
            case R.id.tvFromDate:
                openFromDatePicker(false);
                break;
            case R.id.tvHalfDate:
                openFromDatePicker(true);
                break;
            case R.id.tvToDate:
                openToDatePicker();
                break;
            case R.id.tvFromDate1:
                openFromDatePicker1(false);
                break;
            case R.id.tvToDate1:
                openToDatePicker1();
                break;
          /*  case R.id.tvHalfDate1:
                openFromDatePicker1(true);
                break;*/
            case R.id.tvleaveAM:
                selectAM();
                break;
            case R.id.tvleavePM:
                selectPM();
                break;
            case R.id.tvleaveAM1:
                selectAM1();
                break;
            case R.id.tvleavePM1:
                selectPM1();
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

    private void selectAM1() {

        meridiem1 = "AM";
        tvleaveAM1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tvleaveAM1.setTextColor(getResources().getColor(R.color.white));

        tvleavePM1.setBackgroundColor(getResources().getColor(R.color.white));
        tvleavePM1.setTextColor(getResources().getColor(R.color.textBlackDark));
    }

    private void selectPM1() {
        meridiem1 = "PM";
        tvleavePM1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tvleavePM1.setTextColor(getResources().getColor(R.color.white));

        tvleaveAM1.setBackgroundColor(getResources().getColor(R.color.white));
        tvleaveAM1.setTextColor(getResources().getColor(R.color.textBlackDark));

    }

    private void applyLeave() {
        try {
            if (validateFields()) {

                if (UIUtil.isInternetAvailable(this)) {

                    UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                    JsonObject jsonObject = new JsonObject();
                    JsonObject jsonObject1 = new JsonObject();
             /*   if (isHalfDay) {
                    jsonObject.addProperty("start_date", tvHalfDate.getText().toString());
                    jsonObject.addProperty("end_date", tvHalfDate.getText().toString());
                    jsonObject.addProperty("meridiem", meridiem);
                } else {
                    jsonObject.addProperty("start_date", tvFromDate.getText().toString());
                    jsonObject.addProperty("end_date", tvToDate.getText().toString());
                    jsonObject.addProperty("meridiem", meridiem);
                }
                jsonObject.addProperty("is_half_day", isHalfDay);
                jsonObject.addProperty("reason", etReason.getText().toString());
                jsonObject1.add("student_leave_apply", jsonObject);*/

                    jsonObject.addProperty("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));

                    if (isHalfDay) {
                        jsonObject1.addProperty("start_date", tvHalfDate.getText().toString() + " -" + tvHalfDate.getText().toString());
                        jsonObject.addProperty("start_date", tvHalfDate.getText().toString());
                        jsonObject.addProperty("end_date", tvHalfDate.getText().toString());
                        jsonObject1.addProperty("is_half_day", isHalfDay);
                        jsonObject1.addProperty("meridiem", meridiem);
                    } else {
                        jsonObject1.addProperty("start_date", tvFromDate.getText().toString() + " - " + tvToDate.getText().toString());
                        jsonObject.addProperty("start_date", tvFromDate.getText().toString());
                        jsonObject.addProperty("end_date", tvToDate.getText().toString());

                    }
                    jsonObject1.addProperty("is_half_day", isHalfDay);
                    jsonObject1.addProperty("reason", etReason.getText().toString());
                    jsonObject.add("student_leave_apply", jsonObject1);
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                    RetrofitAPI.getInstance(this).getApi().applyParentLeave(jsonObject, new Callback<JsonObject>() {
                        @Override
                        public void success(JsonObject jsonObj, Response response) {


                            Log.e("Json ", "Hhd --- " + jsonObj.toString());
                            Log.e("Json ", "Response --- " + response.getBody());
                            try {

                                if (Constants.SUCCESS == jsonObj.get("status").getAsInt()) {
                                    UIUtil.stopProgressDialog(getApplicationContext());
                                    getLeaveDetails();
                                    Toast.makeText(getApplicationContext(), "Leave Applied Sucessfully", Toast.LENGTH_SHORT).show();

                                    clearFieldData();
                                    parenthistoryAdapter.notifyDataSetChanged();
                                } else {
                                    UIUtil.stopProgressDialog(getApplicationContext());
                                    Toast.makeText(getApplicationContext(), jsonObj.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                UIUtil.stopProgressDialog(getApplicationContext());
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

    private void editMessage() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                JsonObject jsonObject = new JsonObject();
                JsonObject jsonObject1 = new JsonObject();
                jsonObject.addProperty("id", leaveHistoryArrayList.get(editpos).getId());

                if (isHalfDay1) {
                    jsonObject1.addProperty("start_date", tvHalfDate1.getText().toString() + " -" + tvHalfDate1.getText().toString());
                    jsonObject.addProperty("start_date", tvHalfDate1.getText().toString());
                    jsonObject.addProperty("end_date", tvHalfDate1.getText().toString());
                    jsonObject1.addProperty("is_half_day", isHalfDay1);
                    jsonObject1.addProperty("meridiem", meridiem1);
                } else {
                    jsonObject1.addProperty("start_date", tvFromDate1.getText().toString() + " - " + tvToDate1.getText().toString());
                    jsonObject.addProperty("start_date", tvFromDate1.getText().toString());
                    jsonObject.addProperty("end_date", tvToDate1.getText().toString());

                }
                jsonObject1.addProperty("is_half_day", isHalfDay1);
                jsonObject1.addProperty("reason", etReason1.getText().toString());
                jsonObject.addProperty("manage", "update");
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                jsonObject.add("student_leave_apply", jsonObject1);

                RetrofitAPI.getInstance(this).getApi().Updateleave(jsonObject, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject object, Response response) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Log.e("jsonObject", "jsonObject --- " + object.toString());

                        try {
                            if (object == null) {
                                Toast.makeText(ParentLeaveActivity.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (object.get("status").getAsInt() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                parenthistoryAdapter.notifyDataSetChanged();
                                Toast.makeText(ParentLeaveActivity.this, object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                getLeaveDetails();
                            } else {
                                Toast.makeText(ParentLeaveActivity.this, object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Toast.makeText(ParentLeaveActivity.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
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

    private void deleteMessage() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details.");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id", leaveHistoryArrayList.get(deletepos).getId());
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                jsonObject.addProperty("manage", "delete");
                RetrofitAPI.getInstance(this).getApi().Updateleave(jsonObject, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject object, Response response) {
                        Log.e("jsonObject", "jsonObject --- " + object.toString());
                        UIUtil.stopProgressDialog(getApplicationContext());
                        try {
                            if (object == null) {
                                Toast.makeText(getApplicationContext(), "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (object.get("status").getAsInt() == Constants.SUCCESS) {
                                getLeaveDetails();
                                parenthistoryAdapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ParentLeaveActivity.this, object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Something went wrong, try again.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateFields() {

        if (isHalfDay) {
            if (TextUtils.isEmpty(tvHalfDate.getText().toString())) {
                Toast.makeText(this, "Select Date", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            if (TextUtils.isEmpty(tvFromDate.getText().toString())) {
                Toast.makeText(this, "Select From Date", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (TextUtils.isEmpty(tvToDate.getText().toString())) {
                Toast.makeText(this, "Select To Date", Toast.LENGTH_SHORT).show();
                return false;
            }
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
            String frserverdate = leaveHistoryArrayList.get(i).getFromDate();
            String toserverdate = leaveHistoryArrayList.get(i).getToDate();
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
            Log.e("qqqqqqqqqqw:      ", String.valueOf(date) + "" + applyfromdate);
            if (isHalfDay) {
                if ((applyhalfdate.after(date) && applyhalfdate.before(date)) || applyhalfdate.after(date1) && applyhalfdate.before(date1) || applyhalfdate.equals(date) || applyhalfdate.equals(date1)) {
                    Toast.makeText(this, "Leave already applied for this date", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                if ((applyfromdate.after(date) && applytodate.before(date)) || applyfromdate.after(date1) && applytodate.before(date1) || applyfromdate.equals(date) || applyfromdate.equals(date1) || applytodate.equals(date) || applytodate.equals(date1)) {
                    Toast.makeText(this, "Leave already applied for this date", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        if (etReason.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter Reason For Leave", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etReason.setError(null);
        }
        return true;

    }


    /* @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.parentleavespinner, menu);

         MenuItem item = menu.findItem(R.id.spinner);
         final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

         spinneradapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
         spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinner.setAdapter(spinneradapter);

         spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 spiner = categories.get(position);
                 setStudentData(spiner);

                 tvName.setText(parentLeaveArrayList.get(position).getName());
                 tvTeacherName.setText(parentLeaveArrayList.get(position).getClassTeacherName());
                 result = parentLeaveArrayList.get(position).getId();
                 Log.e("id", "=====" + result);

             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });
         return true;
     }

 */
    private void getLeaveDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Getting Details..Please Wait");
                Map<String, String> params = new HashMap<String, String>();
                params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));

                RetrofitAPI.getInstance(this).getApi().parentApplyLeave(params, new Callback<ParentLeaveResponse>() {

                    @Override
                    public void success(ParentLeaveResponse leaveResponse, Response response) {
                        try {
                            if (leaveResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                leaveHistoryArrayList.clear();
                                // parentLeaveArrayList.clear();

                                leaveHistoryArrayList.addAll(leaveResponse.getLeaves());
                                Log.e("data", "0000000000000" + parentLeaveArrayList);
                                tvTeacherName.setText(leaveResponse.getClassTeacher());
                                if (leaveResponse.getClassTeacher() == null) {
                                    Toast.makeText(getApplicationContext(), "Class teacher not assigned. Try again Later", Toast.LENGTH_SHORT).show();
                                    Intent abc = new Intent(ParentLeaveActivity.this, ParentLandingActivity.class);
                                    startActivity(abc);
                                } else {
                                    tvName.setText(leaveResponse.getName());
                                    if (leaveHistoryArrayList.size() <= 0) {
                                        nodata.setVisibility(View.VISIBLE);
                                        lvLeaveHistory.setVisibility(View.GONE);
                                    } else {
                                        nodata.setVisibility(View.GONE);
                                        lvLeaveHistory.setVisibility(View.VISIBLE);
                                    }
                                    parenthistoryAdapter.notifyDataSetChanged();
                                }
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "" + leaveResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        error.getCause();
                        Log.e("data", "0000000000000" + error.getCause());
                        Toast.makeText(getApplicationContext(), "Server is not responding, Try after some time.", Toast.LENGTH_SHORT).show();

                    }
                });
            } else {

                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    public void getLeaveDetails() {
        if (UIUtil.isInternetAvailable(this)) {

            // UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
            Map<String, String> params = new HashMap<String, String>();
            params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(),Constants.Pref.KEY_Id));

            RetrofitAPI.getInstance(this).getApi().parentApplyLeave(params, new Callback<ParentLeaveResponse>() {
                @Override
                public void success(ParentLeaveResponse leaveResponse, Response response) {

                    Log.e("Json ", "Hhd --- " + leaveResponse.toString());
                    Log.e("Json ", "Response --- " + response.getBody());

                        UIUtil.stopProgressDialog(getApplicationContext());
                        leaveHistoryArrayList.clear();
                        parentLeaveArrayList.clear();
                        parentLeaveArrayList.addAll(leaveResponse.getList());
                        Log.e("data", "0000000000000" + parentLeaveArrayList);
                        for (int i = 0; i < parentLeaveArrayList.size(); i++) {
                            ParentLeave parentLeave = parentLeaveArrayList.get(i);
                            leaveHistoryArrayList.addAll(parentLeave.getLeaves());
                            parenthistoryAdapter.notifyDataSetChanged();


                    }
                    Log.e("Json ", "Hhd --- " + leaveResponse.toString());
                    Log.e("Json ", "Response --- " + response.getBody());
                    Log.e("spinner1132", "----" + parentLeaveArrayList.toString());
                    Log.e("spinner23re4", "----" + categories.toString());

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

    private void clearFieldData() {
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

//                edDOB.setText(String.valueOf(year) + "-" + month + "-" + day);
//                tvDob.setText(newDate.toString());

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(fromDate);

        datePickerDialog.show();
    }

    private void openFromDatePicker(final boolean isHalfDay) {
        final Calendar newCalendar = Calendar.getInstance();
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
                    tvToDate.setText(day + "/" + month + "/" + String.valueOf(year));
                    tvToDate.setEnabled(true);
                }
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    /* private void updateSpinner() {
         categories.clear();
         for (int i = 0; i < parentLeaveArrayList.size(); i++) {
             ParentLeave parentLeave = parentLeaveArrayList.get(i);
             categories.add(parentLeave.getName());
         }
         spinneradapter.notifyDataSetChanged();
     }
 */


    private void update(ParentLeave parentLeave) {
        try {
            leaveHistoryArrayList.clear();
            leaveHistoryArrayList.addAll(parentLeave.getLeaves());
            parenthistoryAdapter.notifyDataSetChanged();
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
        datePickerDialog.getDatePicker().setMinDate(fromDate);
        datePickerDialog.show();
    }

    private void openFromDatePicker1(final boolean isHalfDay1) {
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
                // tvFromDate1.setText(day + "/" + month + "/" + String.valueOf(year));
                try {
                    String str_date = day + "/" + month + "/" + String.valueOf(year);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = (Date) formatter.parse(str_date);
                    fromDate = date.getTime();
                    System.out.println("Today is " + date.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (isHalfDay1) {
                    tvHalfDate1.setText(day + "/" + month + "/" + String.valueOf(year));
                } else {
                    tvFromDate1.setText(day + "/" + month + "/" + String.valueOf(year));
                    tvToDate1.setText(day + "/" + month + "/" + String.valueOf(year));
                }
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private BroadcastReceiver pushBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

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
            startActivity(new Intent(ParentLeaveActivity.this, ParentLandingActivity.class));
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
        startActivity(new Intent(ParentLeaveActivity.this, ParentLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }


}
