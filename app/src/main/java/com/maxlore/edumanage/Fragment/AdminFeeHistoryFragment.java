package com.maxlore.edumanage.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.FeeHistoryAdminAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels.AdminPaymentHistoryModel;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels.FiltersModel;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels.PaymentHistorymodel;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

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

/**
 * Created by Shrey on 21-Jul-17.
 */

public class AdminFeeHistoryFragment extends Fragment {
    private FeeHistoryAdminAdapter feeHistoryAdminAdapter;
    private ListView listview;
    private ArrayAdapter<String> filterstatusAdapter, filterpaymentAdapter, filterdaysAdapter;
    private ArrayList<String> statusArraylist;
    private ArrayList<String> paymentArraylist;
    private ArrayList<String> daysArraylist;
    private ArrayList<FiltersModel> modelPaymentArraylist;
    private TextView btnFilter, tvFromDatefilter, tvtoDatefilter;
    private EditText etSearchfeehistory;
    private long fromDate, comp1, comp2, comp3, comp4, todate;
    private ArrayList<String> selectedArraylist;
    private String status = "", payment = "", days = "";
    private Handler handler;
    private String pos, frdate, tdate;
    public static final int TIME_OUT = 1000;
    private Spinner spinnerstatusfilter, spinnerpaymentfilter, spinnerdaysfilter;
    private ArrayList<PaymentHistorymodel> paymentHistorymodels, searchArrayList, dateArraylist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_paymenthistory_fragment, container, false);

        listview = (ListView) view.findViewById(R.id.listview);
        /*btnFilter = (TextView) view.findViewById(R.id.btnFilter);*/
        tvFromDatefilter = (TextView) view.findViewById(R.id.tvFromDatefilter);
        tvtoDatefilter = (TextView) view.findViewById(R.id.tvtoDatefilter);
        etSearchfeehistory = (EditText) view.findViewById(R.id.etSearchfeehistory);

        modelPaymentArraylist = new ArrayList<>();
        paymentHistorymodels = new ArrayList<>();
        searchArrayList = new ArrayList<>();
        statusArraylist = new ArrayList<>();
        paymentArraylist = new ArrayList<>();
        daysArraylist = new ArrayList<>();
        dateArraylist = new ArrayList<>();
        selectedArraylist = new ArrayList<>();

        feeHistoryAdminAdapter = new FeeHistoryAdminAdapter(getActivity(), paymentHistorymodels);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        handler = new Handler();

        listview.setAdapter(feeHistoryAdminAdapter);
        feeHistoryAdminAdapter.notifyDataSetChanged();

        /*btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abc = new Intent(getActivity(), FilterOptionsActivity.class);
                startActivity(abc);
            }
        });*/
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etSearchfeehistory.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        tvFromDatefilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatepickerForFrom();
            }
        });
        tvtoDatefilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatepickerForTo();
            }
        });
        etSearchfeehistory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.length() > 0) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            filterSearch(s.toString());
                        }
                    }, TIME_OUT);
                } else {
                    if (searchArrayList.size() > 0) {
                        paymentHistorymodels.clear();
                        paymentHistorymodels.addAll(searchArrayList);
                        feeHistoryAdminAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        getPaymentData();
        return view;
    }

    private void filterSearch(String search) {
        paymentHistorymodels.clear();
        for (int i = 0; i < searchArrayList.size(); i++) {
            PaymentHistorymodel paymentHistorymodel = searchArrayList.get(i);
            if (paymentHistorymodel.getStudentName().toLowerCase().contains(search.toLowerCase())) {
                paymentHistorymodels.add(paymentHistorymodel);
            }
        }
        if (paymentHistorymodels.size() <= 0) {
            etSearchfeehistory.setError("No Record found");
        }
        feeHistoryAdminAdapter.notifyDataSetChanged();
    }

    private void openDatepickerForFrom() {
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

                tvFromDatefilter.setText(day + "/" + month + "/" + String.valueOf(year));
                frdate = day + "/" + month + "/" + String.valueOf(year);
                filterFromDate(frdate);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    private void filterFromDate(String frdate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = (Date) formatter.parse(frdate);
            comp1 = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        paymentHistorymodels.clear();
        for (int i = 0; i < searchArrayList.size(); i++) {
            PaymentHistorymodel paymentHistorymodel = searchArrayList.get(i);
            try {
                Date date1 = (Date) formatter.parse(paymentHistorymodel.getRecieptDate());
                comp2 = date1.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (comp1 <= comp2) {
                dateArraylist.add(paymentHistorymodel);
            }
        }
        openDatepickerForTo();
    }

    private void openDatepickerForTo() {
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
                    todate = date.getTime();
                    System.out.println("Today is " + date.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                tvtoDatefilter.setText(day + "/" + month + "/" + String.valueOf(year));
                tdate = day + "/" + month + "/" + String.valueOf(year);
                filterToDate(tdate);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(fromDate);
        datePickerDialog.show();

    }

    private void filterToDate(String tdate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = (Date) formatter.parse(tdate);
            comp3 = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        paymentHistorymodels.clear();
        for (int i = 0; i < dateArraylist.size(); i++) {
            PaymentHistorymodel paymentHistorymodel = dateArraylist.get(i);
            try {
                Date date1 = (Date) formatter.parse(paymentHistorymodel.getRecieptDate());
                comp4 = date1.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (comp4 <= comp3) {
                paymentHistorymodels.add(paymentHistorymodel);
            }
        }
        feeHistoryAdminAdapter.notifyDataSetChanged();
    }

    private void getPaymentData() {
        try {
            if (UIUtil.isInternetAvailable(getActivity())) {
                UIUtil.startProgressDialog(getActivity(), "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                if (PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    params.put("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    params.put("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(getActivity()).getApi().getpaymentdata(params, new Callback<AdminPaymentHistoryModel>() {
                    @Override
                    public void success(AdminPaymentHistoryModel adminPaymentHistoryModel, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getActivity());
                            Log.e("jsonObject", "jsonObject --- " + adminPaymentHistoryModel.toString());
                            searchArrayList.clear();
                            paymentHistorymodels.clear();
                            searchArrayList.addAll(adminPaymentHistoryModel.getPaymentHistories());
                            paymentHistorymodels.addAll(adminPaymentHistoryModel.getPaymentHistories());
                            feeHistoryAdminAdapter.notifyDataSetChanged();
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
}
