package com.maxlore.edumanage.Activities.ParentActivities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.maxlore.edumanage.Adapters.ParentAdapters.ParentFeesHistoryAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.ParentModels.ParentFees.PayHistoryData;
import com.maxlore.edumanage.Models.ParentModels.ParentFees.PayHistoryResponse;

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

public class PaymentHistoryActivity extends AppCompatActivity {
    private RecyclerView payhistoryrecycler;
    private ArrayList<PayHistoryData> termArrayList, searchArrayList, datesarraylist;
    private Handler handler;
    private EditText etSearchpayhistory;
    private TextView tvFrompayDatefilter, tvtopayDatefilter, tv_nodata;
    private ParentFeesHistoryAdapter parentFeesHistoryAdapter;
    public int text, result;
    private long fromDate, comp1, comp2, comp3, comp4, todate;
    private String pos, frdate, tdate;
    public static final int TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        payhistoryrecycler = (RecyclerView) findViewById(R.id.payhistoryrecycler);

        etSearchpayhistory = (EditText) findViewById(R.id.etSearchpayhistory);
        tvFrompayDatefilter = (TextView) findViewById(R.id.tvFrompayDatefilter);
        tvtopayDatefilter = (TextView) findViewById(R.id.tvtopayDatefilter);
        tv_nodata = (TextView) findViewById(R.id.tv_nodata);

        termArrayList = new ArrayList<>();
        searchArrayList = new ArrayList<>();
        datesarraylist = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        payhistoryrecycler.setLayoutManager(llm);

        handler = new Handler();

        parentFeesHistoryAdapter = new ParentFeesHistoryAdapter(this, termArrayList);
        payhistoryrecycler.setAdapter(parentFeesHistoryAdapter);
        parentFeesHistoryAdapter.notifyDataSetChanged();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getPaymentdataforparent();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etSearchpayhistory.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        tvFrompayDatefilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendatepickerfrom();
            }
        });
        tvtopayDatefilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvFrompayDatefilter.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter From date First ", Toast.LENGTH_SHORT).show();
                } else {
                    opendatepickerTo();
                }
            }
        });
        etSearchpayhistory.addTextChangedListener(new TextWatcher() {
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
                        termArrayList.clear();
                        termArrayList.addAll(searchArrayList);
                        parentFeesHistoryAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void opendatepickerfrom() {
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

                tvFrompayDatefilter.setText(day + "/" + month + "/" + String.valueOf(year));
                frdate = day + "/" + month + "/" + String.valueOf(year);
                filterfromdate(frdate);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    private void filterfromdate(String frdate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = (Date) formatter.parse(frdate);
            comp1 = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        termArrayList.clear();
        for (int i = 0; i < searchArrayList.size(); i++) {
            PayHistoryData payHistoryData = searchArrayList.get(i);
            try {
                Date date1 = (Date) formatter.parse(payHistoryData.getRecieptDate());
                comp2 = date1.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (comp1 <= comp2) {
                datesarraylist.add(payHistoryData);
            }
        }
        opendatepickerTo();
    }

    private void opendatepickerTo() {
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
                    todate = date.getTime();
                    System.out.println("Today is " + date.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                tvtopayDatefilter.setText(day + "/" + month + "/" + String.valueOf(year));
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
        termArrayList.clear();
        for (int i = 0; i < datesarraylist.size(); i++) {
            PayHistoryData payHistoryData = datesarraylist.get(i);
            try {
                Date date1 = (Date) formatter.parse(payHistoryData.getRecieptDate());
                comp4 = date1.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (comp4 <= comp3) {
                termArrayList.add(payHistoryData);
            }
        }
        if (termArrayList.size() <= 0) {
            tv_nodata.setVisibility(View.VISIBLE);
            payhistoryrecycler.setVisibility(View.GONE);
        } else {
            tv_nodata.setVisibility(View.GONE);
            payhistoryrecycler.setVisibility(View.VISIBLE);
        }
        parentFeesHistoryAdapter.notifyDataSetChanged();
    }

    private void filterSearch(String search) {
        try {
            termArrayList.clear();
            for (int i = 0; i < searchArrayList.size(); i++) {
                PayHistoryData payHistoryData = searchArrayList.get(i);
                if (payHistoryData.getPaymentFor().toLowerCase().contains(search.toLowerCase()) || payHistoryData.getRecieptNo().toLowerCase().contains(search.toLowerCase())) {
                    termArrayList.add(payHistoryData);
                }
            }
            if (termArrayList.size() <= 0) {
                etSearchpayhistory.setError("No Record found");
            }
            parentFeesHistoryAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPaymentdataforparent() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));

                RetrofitAPI.getInstance(this).getApi().getPaymentdataforparent(params, new Callback<PayHistoryResponse>() {

                    @Override
                    public void success(PayHistoryResponse payHistoryResponse, Response response) {
                        try {
                            if (payHistoryResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                termArrayList.addAll(payHistoryResponse.getStudent());
                                searchArrayList.addAll(payHistoryResponse.getStudent());
                                parentFeesHistoryAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getApplicationContext(), "" + payHistoryResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                UIUtil.stopProgressDialog(getApplicationContext());
                            }
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
            startActivity(new Intent(PaymentHistoryActivity.this, ParentLandingActivity.class));
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
        startActivity(new Intent(PaymentHistoryActivity.this, ParentLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }

}
