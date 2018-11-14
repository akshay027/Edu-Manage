/*
package com.maxlore.edumanage.Activities.ParentActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.ParentAdapters.AcademicBusfeeAdapter;
import com.maxlore.edumanage.Adapters.ParentAdapters.BusFeesAdapter;
import com.maxlore.edumanage.Adapters.ParentAdapters.FeeFinesAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.ParentModels.ParentFees.AcademicBusData;
import com.maxlore.edumanage.Models.ParentModels.ParentFees.AcademicBusResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentFees.AcademicFeeResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentFees.BusFeeResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentFees.FineDataResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentFees.TermBusFees;
import com.maxlore.edumanage.Models.ParentModels.ParentFees.TermFeeData;
import com.maxlore.edumanage.Models.ParentModels.SchoolFeeTypeResponse;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FeesPaymentMOdule extends AppCompatActivity {

    private Button btnacademicfee, btnbusfee, btnfines;
    private RecyclerView payacademicfeerecycler, paybusfeerecycler, payacademicbusfeerecycler;
    private LinearLayout llbuttons, llbus_fines, llacademic_fine, llacademicbusfees;
    private ArrayList<SchoolFeeTypeResponse> finesArraylist, busfeeArraylist;
    private ArrayList<TermBusFees> busFeeResponseArrayList;
    private FeeFinesAdapter feeFinesAdapter;
    private AcademicBusfeeAdapter academicBusfeeAdapter;
    private BusFeesAdapter busFeesAdapter;
    private String total, paymenttype, feetypeid = "";
    private EditText etamountfeesedit;
    private double d, s, a, chc;
    private boolean split_pay;
    private ArrayList<TermFeeData> academicFeeDataArraylist, finalAcademicArraylist;
    private ArrayList<AcademicBusData> academicBusFeeDataArraylist;
    private TextView tv_totalamount, tv_paidamount, selectedbutton, tv_remainingamount, tv_totalbusacademic, btnpayfees, tv_totalacademic, tv_totalfineacademic, tv_concessionacademic, tv_totpaidacademic, tv_totremainingacademic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_payment_module);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        payacademicbusfeerecycler = (RecyclerView) findViewById(R.id.payacademicbusfeerecycler);
        paybusfeerecycler = (RecyclerView) findViewById(R.id.paybusfeerecycler);
        btnacademicfee = (Button) findViewById(R.id.btnacademicfee);
        etamountfeesedit = (EditText) findViewById(R.id.etamountfeesedit);
        btnbusfee = (Button) findViewById(R.id.btnbusfee);
        btnfines = (Button) findViewById(R.id.btnfines);
        btnpayfees = (TextView) findViewById(R.id.btnpayfees);
        tv_totalbusacademic = (TextView) findViewById(R.id.tv_totalbusacademic);
        selectedbutton = (TextView) findViewById(R.id.selectedbutton);

        academicFeeDataArraylist = new ArrayList<>();
        busFeeResponseArrayList = new ArrayList<>();
        academicBusFeeDataArraylist = new ArrayList<>();

        llbuttons = (LinearLayout) findViewById(R.id.llbuttons);
        llbus_fines = (LinearLayout) findViewById(R.id.llbus_fines);
        llacademicbusfees = (LinearLayout) findViewById(R.id.llacademicbusfees);

        tv_totalacademic = (TextView) findViewById(R.id.tv_totalacademic);
        tv_totalfineacademic = (TextView) findViewById(R.id.tv_totalfineacademic);
        tv_concessionacademic = (TextView) findViewById(R.id.tv_concessionacademic);
        tv_totpaidacademic = (TextView) findViewById(R.id.tv_totpaidacademic);
        tv_totremainingacademic = (TextView) findViewById(R.id.tv_totremainingacademic);
        llacademic_fine = (LinearLayout) findViewById(R.id.llacademic_fine);

        tv_totalamount = (TextView) findViewById(R.id.tv_totalamount);
        tv_paidamount = (TextView) findViewById(R.id.tv_paidamount);
        tv_remainingamount = (TextView) findViewById(R.id.tv_remainingamount);

        payacademicfeerecycler = (RecyclerView) findViewById(R.id.payacademicfeerecycler);
        feeFinesAdapter = new FeeFinesAdapter(this, academicFeeDataArraylist);
        payacademicfeerecycler.setAdapter(feeFinesAdapter);
        feeFinesAdapter.notifyDataSetChanged();

        LinearLayoutManager llm2 = new LinearLayoutManager(this);
        payacademicbusfeerecycler.setLayoutManager(llm2);

        LinearLayoutManager llm1 = new LinearLayoutManager(this);
        paybusfeerecycler.setLayoutManager(llm1);
        selectedbutton.setText("Academic Fees");
        busFeesAdapter = new BusFeesAdapter(this, busFeeResponseArrayList);
        paybusfeerecycler.setAdapter(busFeesAdapter);
        busFeesAdapter.notifyDataSetChanged();

        academicBusfeeAdapter = new AcademicBusfeeAdapter(this, academicBusFeeDataArraylist);
        payacademicbusfeerecycler.setAdapter(academicBusfeeAdapter);
        academicBusfeeAdapter.notifyDataSetChanged();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etamountfeesedit.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        llbus_fines.setVisibility(View.GONE);
        feeFinesAdapter.SetOnItemClickListener(new FeeFinesAdapter.OnItemClickListener() {
            @Override
            public void onPaymentFines(View view, int position, String amount) {
                //redirect from here USE INTENT and send payment amount too
                Intent abc = new Intent(FeesPaymentMOdule.this, PaymentActivity.class);
                if (academicFeeDataArraylist.get(position).getSplitPay()) {
                    a = Double.parseDouble(amount);
                    chc = Double.parseDouble(amount);
                    a = a * 100;
                } else if (!academicFeeDataArraylist.get(position).getSplitPay()) {
                    a = Double.parseDouble(academicFeeDataArraylist.get(position).getBalance().toString());
                    chc = Double.parseDouble(academicFeeDataArraylist.get(position).getBalance().toString());
                    a = a * 100;
                }
                if (chc > Double.parseDouble(academicFeeDataArraylist.get(position).getBalance().toString()) || chc < 0) {
                    Log.e("duyasfvgyhdsa   :::   ", String.valueOf(a));
                    Toast.makeText(getApplicationContext(), "Invalid amount!", Toast.LENGTH_SHORT).show();
                } else {
                    abc.putExtra("amountconvertedtorupee", a);
                    paymenttype = "Academic";
                    abc.putExtra("paymenttype", paymenttype);
                    feetypeid = academicFeeDataArraylist.get(position).getId().toString();
                    abc.putExtra("feeid", feetypeid);
                    startActivity(abc);
                }
            }
        });
        academicBusfeeAdapter.SetOnItemClickListener(new AcademicBusfeeAdapter.OnItemClickListener() {
            @Override
            public void onPaymentFines(View view, int position, String amount) {
                //redirect from here USE INTENT and send payment amount too


                Intent abc = new Intent(FeesPaymentMOdule.this, PaymentActivity.class);
                if (academicBusFeeDataArraylist.get(position).getSplitPay()) {
                    a = Double.parseDouble(amount);
                    chc = Double.parseDouble(amount);
                    Log.e("akjbvbsvvv   ", String.valueOf(Double.parseDouble(amount)));
                    Log.e("akjbvbsvvv   ", String.valueOf(a));
                    a = a * 100;
                    Log.e("akjbvbsvvv   ", String.valueOf(a));
                } else if (!academicBusFeeDataArraylist.get(position).getSplitPay()) {
                    a = Double.parseDouble(academicBusFeeDataArraylist.get(position).getBalance().toString());
                    chc = Double.parseDouble(academicBusFeeDataArraylist.get(position).getBalance().toString());
                    a = a * 100;
                }
                if (chc > Double.parseDouble(academicBusFeeDataArraylist.get(position).getBalance().toString()) || chc <= 0) {
                    Log.e("yuhdydjbd  :  ", a + "");
                    Toast.makeText(getApplicationContext(), "Invalid amount!", Toast.LENGTH_SHORT).show();
                } else {
                    abc.putExtra("amountconvertedtorupee", a);
                    paymenttype = "Academic&bus";
                    abc.putExtra("paymenttype", paymenttype);
                    feetypeid = academicBusFeeDataArraylist.get(position).getId().toString();
                    abc.putExtra("feeid", feetypeid);
                    startActivity(abc);
                }
            }
        });
        busFeesAdapter.SetOnItemClickListener(new BusFeesAdapter.OnItemClickListener() {
            @Override
            public void onPaymentFines(View view, int position, String amount) {
                Log.e("yuhdydjbd  :  ", d + "");
                Intent abc = new Intent(FeesPaymentMOdule.this, PaymentActivity.class);
                if (busFeeResponseArrayList.get(position).getSplitPay()) {
                    d = Double.parseDouble(amount);
                    chc = Double.parseDouble(amount);
                    d = d * 100;
                } else if (!busFeeResponseArrayList.get(position).getSplitPay()) {
                    d = Double.parseDouble(busFeeResponseArrayList.get(position).getFeeTypeFeeBalance().toString());
                    chc = Double.parseDouble(busFeeResponseArrayList.get(position).getFeeTypeFeeBalance().toString());
                    d = d * 100;
                }
                if (chc > Double.parseDouble(busFeeResponseArrayList.get(position).getFeeTypeFeeBalance().toString()) || chc <= 0) {
                    Toast.makeText(getApplicationContext(), "Invalid amount!", Toast.LENGTH_SHORT).show();
                } else {
                    abc.putExtra("amountconvertedtorupee", d);
                    paymenttype = "Bus";
                    abc.putExtra("paymenttype", paymenttype);
                    feetypeid = busFeeResponseArrayList.get(position).getId().toString();
                    abc.putExtra("feeid", feetypeid);
                    startActivity(abc);
                }
            }
        });
        btnpayfees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abc = new Intent(FeesPaymentMOdule.this, PaymentActivity.class);
                if (split_pay) {
                    d = Double.parseDouble(etamountfeesedit.getText().toString());
                    d = d * 100;
                    abc.putExtra("amountconvertedtorupee", d);
                    abc.putExtra("paymenttype", paymenttype);
                    abc.putExtra("feeid", feetypeid);
                    if (d > s || d <= 0) {
                        Toast.makeText(getApplicationContext(), "Invalid amount!", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(abc);
                        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
                    }
                } else {
                    abc.putExtra("amountconvertedtorupee", s);
                    abc.putExtra("paymenttype", paymenttype);
                    abc.putExtra("feeid", feetypeid);
                    startActivity(abc);
                    overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
                }
            }
        });
        btnfines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llacademic_fine.setVisibility(View.GONE);
                llbus_fines.setVisibility(View.VISIBLE);
                paybusfeerecycler.setVisibility(View.GONE);
                btnpayfees.setVisibility(View.VISIBLE);
                selectedbutton.setText("Fines");
                getFinesdetails();
            }
        });
        btnacademicfee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedbutton.setText("Academic Fees");
                if (total.equalsIgnoreCase("3")) {
                    llacademic_fine.setVisibility(View.VISIBLE);
                    llbus_fines.setVisibility(View.GONE);
                    btnpayfees.setVisibility(View.GONE);
                    llacademicbusfees.setVisibility(View.GONE);
                    payacademicbusfeerecycler.setVisibility(View.GONE);
                    paybusfeerecycler.setVisibility(View.GONE);
                    getAcademicfeedetail();
                } else {
                    llacademic_fine.setVisibility(View.VISIBLE);
                    llbus_fines.setVisibility(View.GONE);
                    payacademicfeerecycler.setVisibility(View.GONE);
                    payacademicbusfeerecycler.setVisibility(View.VISIBLE);
                    btnpayfees.setVisibility(View.GONE);
                    paybusfeerecycler.setVisibility(View.GONE);
                    getAcademicandbusfeedetail();
                }
            }
        });
        btnbusfee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedbutton.setText("Bus Fees");
                llacademic_fine.setVisibility(View.GONE);
                llbus_fines.setVisibility(View.VISIBLE);
                paybusfeerecycler.setVisibility(View.VISIBLE);
                btnpayfees.setVisibility(View.GONE);
                getBusfeedata();
            }
        });
        finesArraylist = new ArrayList<>();
        busfeeArraylist = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        payacademicfeerecycler.setLayoutManager(llm);

        getSchoolfeetype();
    }

    private void getSchoolfeetype() {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
            RetrofitAPI.getInstance(this).getApi().getschoolfeestype(params, new Callback<SchoolFeeTypeResponse>() {
                @Override
                public void success(SchoolFeeTypeResponse schoolFeeTypeResponse, Response response) {
                    try {
                        if (schoolFeeTypeResponse.getStatus() == Constants.SUCCESS) {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            if (schoolFeeTypeResponse.getCount() < 3) {
                                total = "2";
                                btnbusfee.setVisibility(View.GONE);
                                payacademicfeerecycler.setVisibility(View.GONE);
                                payacademicbusfeerecycler.setVisibility(View.VISIBLE);
                                llacademicbusfees.setVisibility(View.VISIBLE);
                                llbuttons.setWeightSum(2);
                                getAcademicandbusfeedetail();
                            } else {
                                total = "3";
                                btnbusfee.setVisibility(View.VISIBLE);
                                llbuttons.setWeightSum(3);
                                payacademicfeerecycler.setVisibility(View.VISIBLE);
                                payacademicbusfeerecycler.setVisibility(View.GONE);
                                llacademicbusfees.setVisibility(View.GONE);
                                getAcademicfeedetail();
                            }

                        } else {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Toast.makeText(getApplicationContext(), "Please Try After SomeTime", Toast.LENGTH_SHORT).show();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAcademicfeedetail() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));

                RetrofitAPI.getInstance(this).getApi().getacademicfeedata(params, new Callback<AcademicFeeResponse>() {

                    @Override
                    public void success(AcademicFeeResponse academicFeeResponse, Response response) {
                        try {
                            if (academicFeeResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                academicFeeDataArraylist.clear();
                                tv_totalacademic.setText(academicFeeResponse.getTotalFees().toString());
                                tv_concessionacademic.setText(academicFeeResponse.getTotalConsessions().toString());
                                tv_totalfineacademic.setText(academicFeeResponse.getTotalLateFeeFines().toString());
                                tv_totpaidacademic.setText(academicFeeResponse.getTotalFeesPaid().toString());
                                tv_totremainingacademic.setText(academicFeeResponse.getTotalFeesToBePaid().toString());
                                academicFeeDataArraylist.addAll(academicFeeResponse.getTermFees());
                                feeFinesAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getApplicationContext(), "" + academicFeeResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void getAcademicandbusfeedetail() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));

                RetrofitAPI.getInstance(this).getApi().getacademicandbusfeedata(params, new Callback<AcademicBusResponse>() {

                    @Override
                    public void success(AcademicBusResponse academicBusResponse, Response response) {
                        try {
                            if (academicBusResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                academicBusFeeDataArraylist.clear();
                                tv_totalacademic.setText(academicBusResponse.getTotalAcademicFees().toString());
                                tv_concessionacademic.setText(academicBusResponse.getTotalConsessions().toString());
                                tv_totalfineacademic.setText(academicBusResponse.getTotalLateFeeFines().toString());
                                tv_totalbusacademic.setText(academicBusResponse.getTotalBusFees().toString());
                                tv_totpaidacademic.setText(academicBusResponse.getTotalFeesPaid().toString());
                                tv_totremainingacademic.setText(academicBusResponse.getTotalFeesToBePaid().toString());
                                academicBusFeeDataArraylist.addAll(academicBusResponse.getTermFees());
                                academicBusfeeAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getApplicationContext(), "" + academicBusResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void getFinesdetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));

                RetrofitAPI.getInstance(this).getApi().getfinesdata(params, new Callback<FineDataResponse>() {

                    @Override
                    public void success(FineDataResponse fineDataResponse, Response response) {
                        try {
                            if (fineDataResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                tv_paidamount.setText(fineDataResponse.getPaid().toString());
                                tv_remainingamount.setText(fineDataResponse.getBalance().toString());
                                tv_totalamount.setText(fineDataResponse.getAmount().toString());
                                split_pay = fineDataResponse.getSplitPay();
                                s = Double.parseDouble(fineDataResponse.getBalance());
                                s = s * 100;
                                paymenttype = "Fine";
                                if (fineDataResponse.getBalance().equalsIgnoreCase("0") || fineDataResponse.getBalance().equalsIgnoreCase("0.0")) {
                                    btnpayfees.setVisibility(View.GONE);
                                    etamountfeesedit.setVisibility(View.GONE);
                                } else {
                                    btnpayfees.setVisibility(View.VISIBLE);
                                    etamountfeesedit.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "" + fineDataResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void getBusfeedata() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));

                RetrofitAPI.getInstance(this).getApi().getbusdata(params, new Callback<BusFeeResponse>() {

                    @Override
                    public void success(BusFeeResponse busFeeResponse, Response response) {
                        try {
                            if (busFeeResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                busFeeResponseArrayList.clear();
                                tv_paidamount.setText(busFeeResponse.getTotalBusFeesPaid().toString());
                                tv_remainingamount.setText(busFeeResponse.getTotalBusFeesToBePaid().toString());
                                tv_totalamount.setText(busFeeResponse.getTotalBusFees().toString());
                                busFeeResponseArrayList.addAll(busFeeResponse.getTermBusFees());
                                busFeesAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getApplicationContext(), "" + busFeeResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(FeesPaymentMOdule.this, ParentLandingActivity.class));
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
        }
        if (id == R.id.app_info) {
            alertboxpopup();
        }

        return super.onOptionsItemSelected(item);
    }

    private AlertDialog alertboxpopup() {
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
        startActivity(new Intent(FeesPaymentMOdule.this, ParentLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }


}
*/
