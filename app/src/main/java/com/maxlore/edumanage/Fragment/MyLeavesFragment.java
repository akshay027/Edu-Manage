package com.maxlore.edumanage.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Activities.TeacherActivities.TeacherEditLeaveActivity;
import com.maxlore.edumanage.Adapters.TeacherAdapters.LeaveHistoryAdapter;
import com.maxlore.edumanage.Adapters.TeacherAdapters.LeaveTypeAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.Leaves.Leave;
import com.maxlore.edumanage.Models.TeacherModels.Leaves.LeaveCategory;
import com.maxlore.edumanage.Models.TeacherModels.Leaves.LeaveHistory;
import com.maxlore.edumanage.Models.TeacherModels.Leaves.LeaveResponse;
import com.maxlore.edumanage.R;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MyLeavesFragment extends Fragment implements View.OnClickListener {

    private ListView lvLeaveCart, lvLeaveHistory;
    //    private ArrayList<>
    private TextView tvName, tvFromDate, tvToDate, tvHalfDate, nodata, status, tvleaveAM, tvleavePM, btnSubmit, noleave;
    private EditText etReason;
    private Spinner spinnerLeaveCat;
    private ArrayList<LeaveCategory> leavecategorieslistlist;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> categorieslist;
    private LeaveTypeAdapter listAdapter;
    private ArrayList<String> leavedatesArrayList;
    private ArrayList<LeaveHistory> leaveHistoryArrayList;
    private LeaveHistoryAdapter historyAdapter;
    private String selectedCategory = "";
    private long fromDate;
    private List<Date> dates;
    private Switch halfDaySwitch;
    private LinearLayout llHalfDay, llToDate, llFromDate, llAMPMleave, llhalfcheck, linearfullleave;
    private boolean isHalfDay, ispermissioncheck;
    private String meridiem = "";


    public MyLeavesFragment() {
        // Required empty public constructor
    }

    public static MyLeavesFragment newInstance() {
        MyLeavesFragment fragment = new MyLeavesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_leaves, container, false);

        status = (TextView) view.findViewById(R.id.status);

        etReason = (EditText) view.findViewById(R.id.etReason);

        tvName = (TextView) view.findViewById(R.id.tvName);
        tvFromDate = (TextView) view.findViewById(R.id.tvFromDate);
        tvToDate = (TextView) view.findViewById(R.id.tvToDate);
        tvHalfDate = (TextView) view.findViewById(R.id.tvHalfDate);
        tvleaveAM = (TextView) view.findViewById(R.id.tvleaveAM);
        tvleavePM = (TextView) view.findViewById(R.id.tvleavePM);
        linearfullleave = (LinearLayout) view.findViewById(R.id.linearfullleave);
        noleave = (TextView) view.findViewById(R.id.noleave);
        dates = new ArrayList<Date>();
        tvHalfDate.setOnClickListener(this);
        tvFromDate.setOnClickListener(this);
        tvToDate.setOnClickListener(this);
        tvleavePM.setOnClickListener(this);
        tvleaveAM.setOnClickListener(this);

        btnSubmit = (TextView) view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        llHalfDay = (LinearLayout) view.findViewById(R.id.llHalfDay);
        llToDate = (LinearLayout) view.findViewById(R.id.llToDate);
        llFromDate = (LinearLayout) view.findViewById(R.id.llFromDate);
        llhalfcheck = (LinearLayout) view.findViewById(R.id.llhalfcheck);
        llAMPMleave = (LinearLayout) view.findViewById(R.id.llAMPMleave);


        spinnerLeaveCat = (Spinner) view.findViewById(R.id.spinnerLeaveCat);
        halfDaySwitch = (Switch) view.findViewById(R.id.halfDaySwitch);
//        checkableHalfDay = (CheckBox) view.findViewById(R.id.checkBox);

//        checkableHalfDay.setOnClickListener(this);

        lvLeaveCart = (ListView) view.findViewById(R.id.lvLeaveCart);
        lvLeaveHistory = (ListView) view.findViewById(R.id.lvLeaveHistory);

        leavecategorieslistlist = new ArrayList<>();
        leaveHistoryArrayList = new ArrayList<>();
        leavedatesArrayList = new ArrayList<>();

        categorieslist = new ArrayList<>();
        categorieslist.add("Select Leave type");

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categorieslist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLeaveCat.setAdapter(adapter);


        listAdapter = new LeaveTypeAdapter(getActivity(), leavecategorieslistlist);
        lvLeaveCart.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        historyAdapter = new LeaveHistoryAdapter(getActivity(), leaveHistoryArrayList);
        lvLeaveHistory.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();

        getLeaveDetails();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etReason.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        lvLeaveHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("daigvuhysadgvbuhsadv ", "Hdajhvsdauhvhasdvsadhd --- ");
                if (leaveHistoryArrayList.get(position).getStatus() == null) {
                    Intent intent = new Intent(getContext(), TeacherEditLeaveActivity.class);
                    intent.putExtra("lvid", leaveHistoryArrayList.get(position).getLeavapplicationid());
                    intent.putExtra("leavetype", leaveHistoryArrayList.get(position).getEmployeeLeaveType());
                    intent.putExtra("fromdate", leaveHistoryArrayList.get(position).getStartDate());
                    intent.putExtra("todate", leaveHistoryArrayList.get(position).getEndDate());
                    intent.putExtra(Constants.REASON, leaveHistoryArrayList.get(position).getReason());
                    intent.putExtra(Constants.HALFDAY, leaveHistoryArrayList.get(position).getHalfDay());
                    intent.putExtra("types", leavecategorieslistlist);
                    startActivity(intent);
                } else

                {
                    lvLeaveHistory.setClickable(false);
                }
            }
        });
        spinnerLeaveCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    selectedCategory = "";
                } else {
                    if (leavecategorieslistlist.get(position - 1).getLeavetypeper()) {
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
                    meridiem = "AM";
                    llAMPMleave.setVisibility(View.VISIBLE);
                    llToDate.setVisibility(View.GONE);
                    openFromDatePicker(true);
                } else {
                    llHalfDay.setVisibility(View.GONE);
                    llFromDate.setVisibility(View.VISIBLE);
                    llToDate.setVisibility(View.VISIBLE);
                    llAMPMleave.setVisibility(View.GONE);
                }
            }
        });


        return view;
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
        tvleaveAM.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        tvleaveAM.setTextColor(getResources().getColor(R.color.white));

        tvleavePM.setBackgroundColor(getResources().getColor(R.color.white));
        tvleavePM.setTextColor(getResources().getColor(R.color.textBlackDark));
    }

    private void selectPM() {
        meridiem = "PM";
        tvleavePM.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        tvleavePM.setTextColor(getResources().getColor(R.color.white));

        tvleaveAM.setBackgroundColor(getResources().getColor(R.color.white));
        tvleaveAM.setTextColor(getResources().getColor(R.color.textBlackDark));

    }


    private void applyLeave() {
        try {
            if (validateFields()) {

                if (UIUtil.isInternetAvailable(getActivity())) {

                    UIUtil.startProgressDialog(getActivity(), "Please wait Geting Details.");
                    JsonObject jsonObject = new JsonObject();
                    JsonObject jsonObject1 = new JsonObject();
                    if (isHalfDay) {
                        jsonObject1.addProperty("start_date", tvHalfDate.getText().toString() + " - " + tvHalfDate.getText().toString());
                        jsonObject.addProperty("start_date", tvHalfDate.getText().toString());
                        jsonObject.addProperty("end_date", tvHalfDate.getText().toString());
                        jsonObject1.addProperty("is_half_day", isHalfDay);
                        jsonObject1.addProperty("meridiem", meridiem);
                    } else if (ispermissioncheck) {
                        jsonObject1.addProperty("start_date", tvHalfDate.getText().toString() + " - " + tvHalfDate.getText().toString());
                        jsonObject.addProperty("start_date", tvHalfDate.getText().toString());
                        jsonObject.addProperty("end_date", tvHalfDate.getText().toString());
                    } else {
                        jsonObject1.addProperty("start_date", tvFromDate.getText().toString() + " - " + tvToDate.getText().toString());
                        jsonObject.addProperty("start_date", tvFromDate.getText().toString());
                        jsonObject.addProperty("end_date", tvToDate.getText().toString());
                    }
                    jsonObject1.addProperty("employee_leave_type_id", getLeaveCategory(selectedCategory));
                    jsonObject1.addProperty("reason", etReason.getText().toString());
                    jsonObject.add("leave_application", jsonObject1);
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_BRANCH_ID));

                    RetrofitAPI.getInstance(getActivity()).getApi().applyLeave(jsonObject, new Callback<JsonObject>() {
                        @Override
                        public void success(JsonObject jsonObj, Response response) {
                            UIUtil.stopProgressDialog(getActivity());

                            Log.e("Json ", "Hhd --- " + jsonObj.toString());
                            Log.e("Json ", "Response --- " + response.getBody());
                            try {

                                if (Constants.SUCCESS == jsonObj.get("status").getAsInt()) {
                                    Toast.makeText(getActivity(), jsonObj.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    getLeaveDetails();
                                    clearFieldData();
                                } else {
                                    Toast.makeText(getActivity(), jsonObj.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            UIUtil.stopProgressDialog(getActivity());
                            Toast.makeText(getActivity(), "Something went wrong, try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateFields() {
        for (int i = 0; i < leavedatesArrayList.size(); i++) {
            if (tvFromDate.getText().toString().equalsIgnoreCase(leavedatesArrayList.get(i)) || tvHalfDate.getText().toString().equalsIgnoreCase(leavedatesArrayList.get(i)) || tvToDate.getText().toString().equalsIgnoreCase(leavedatesArrayList.get(i))) {
                Toast.makeText(getActivity(), "Leave Already Applied for this date", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (TextUtils.isEmpty(selectedCategory)) {
            Toast.makeText(getActivity(), "Enter all the mandatory fields", Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "Select Leave Type", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (isHalfDay || ispermissioncheck) {
            if (TextUtils.isEmpty(tvHalfDate.getText().toString())) {
                Toast.makeText(getActivity(), "Enter all the mandatory fields", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Select Date", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            if (TextUtils.isEmpty(tvFromDate.getText().toString())) {
                Toast.makeText(getActivity(), "Enter all the mandatory fields", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Select From Date", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (TextUtils.isEmpty(tvToDate.getText().toString())) {
                Toast.makeText(getActivity(), "Enter all the mandatory fields", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Select To Date", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (TextUtils.isEmpty(etReason.getText().toString())) {
            Toast.makeText(getActivity(), "Enter all the mandatory fields", Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "Enter Reason For Leave", Toast.LENGTH_SHORT).show();
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
            Log.e("qqqqqqqqqqqw:      ", String.valueOf(date) + "" + applyfromdate);
            if (isHalfDay) {
                if ((applyhalfdate.after(date) && applyhalfdate.before(date) && leaveHistoryArrayList.get(i).getStatus()) || (applyhalfdate.after(date1) && applyhalfdate.before(date1) && leaveHistoryArrayList.get(i).getStatus()) || (applyhalfdate.equals(date) || applyhalfdate.equals(date1) && leaveHistoryArrayList.get(i).getStatus())) {
                    Toast.makeText(getActivity(), "Leave already applied for this date", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                if ((applyfromdate.after(date) && applytodate.before(date) && leaveHistoryArrayList.get(i).getStatus()) || (applyfromdate.after(date1) && applytodate.before(date1) && leaveHistoryArrayList.get(i).getStatus()) || ((applyfromdate.equals(date) || applyfromdate.equals(date1) || applytodate.equals(date) || applytodate.equals(date1)) && leaveHistoryArrayList.get(i).getStatus())) {
                    Toast.makeText(getActivity(), "Leave already applied for this date", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }

        return true;

    }

    private void getLeaveDetails() {
        try {
            if (UIUtil.isInternetAvailable(getActivity())) {

                UIUtil.startProgressDialog(getActivity(), "Please wait Getting Details.");
                Map<String, String> params = new HashMap<String, String>();
                params.put("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(getActivity()).getApi().leaveApplication(params, new Callback<LeaveResponse>() {
                    @Override
                    public void success(LeaveResponse leaveResponse, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getActivity());

                            Log.e("Json ", "Hhd --- " + leaveResponse.toString());
                            Log.e("Json ", "Response --- " + response.getBody());

                            if (leaveResponse.getStatus() == Constants.SUCCESS) {
                                noleave.setVisibility(View.GONE);
                                linearfullleave.setVisibility(View.VISIBLE);
                                bindDataForLeave(leaveResponse);
                            } else {
                                linearfullleave.setVisibility(View.GONE);
                                noleave.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "" + leaveResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            Log.e("Json ", "Hhd --- " + leaveResponse.toString());
                            Log.e("Json ", "Response --- " + response.getBody());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        UIUtil.stopProgressDialog(getActivity());
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindDataForLeave(LeaveResponse leaveResponse) {
        try {
            Leave leave = leaveResponse.getLeave();
            tvName.setText("" + leave.getName());

            updateSpinner();

            leavecategorieslistlist.clear();
            leavecategorieslistlist.addAll(leave.getLeaveCategory());
            listAdapter.notifyDataSetChanged();
            UIUtil.setListViewHeightBasedOnChildren(lvLeaveCart);
            updateSpinner();
            leavedatesArrayList.addAll(leave.getLeaveDates());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateSpinner() {
        try {
            categorieslist.clear();
            categorieslist.add("Select Leave Type");

            for (int i = 0; i < leavecategorieslistlist.size(); i++) {
                categorieslist.add(leavecategorieslistlist.get(i).getName());
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getLeaveCategory(String cat) {
        for (int i = 0; i < leavecategorieslistlist.size(); i++) {
            if (cat.equalsIgnoreCase(leavecategorieslistlist.get(i).getName())) {
                return leavecategorieslistlist.get(i).getId();
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
                for (int i = 0; i < leavedatesArrayList.size(); i++) {
                    if (tvToDate.getText().toString().equalsIgnoreCase(leavedatesArrayList.get(i))) {
                        Toast.makeText(getActivity(), "Leave Already Applied for this date", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.getDatePicker().setMinDate(fromDate);

        datePickerDialog.show();
    }


    private void openFromDatePicker(final boolean isHalfDay) {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
                }
                for (int i = 0; i < leavedatesArrayList.size(); i++) {
                    if (tvFromDate.getText().toString().equalsIgnoreCase(leavedatesArrayList.get(i)) || tvHalfDate.getText().toString().equalsIgnoreCase(leavedatesArrayList.get(i))) {
                        Toast.makeText(getActivity(), "Leave Already Applied for this date", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }


    public static boolean setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() * (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + 50;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }


    public static boolean setListViewHeightForHistory(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() * (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + 40 * numberOfItems;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }
}
