package com.maxlore.edumanage.Activities.AdminActivities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.DaysAdapter;
import com.maxlore.edumanage.Adapters.AdminAdapters.DaysAdapterPaymentAdmin;
import com.maxlore.edumanage.Adapters.AdminAdapters.FilterOptionAdapter;
import com.maxlore.edumanage.Adapters.AdminAdapters.PaymentStatusAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels.FiltersModel;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels.FiltersResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels.PaymentForModel;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels.PaymentStatusModel;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FilterOptionsActivity extends AppCompatActivity {

    private TextView btnproceed;
    private RecyclerView rvfilerpaymentforoptions, rvfilerpaymentstatusoptions, rvfilerpaymentdaysoptions;
    private ListView lvfiltertype;
    private ArrayList<String> types, daystype, paymentstatusselected, paymentforselected, daysselected;
    private ArrayList<PaymentForModel> paymentforArraylist;
    private ArrayList<PaymentStatusModel> paymentStatusModelsArraylist;
    private FilterOptionAdapter filterOptionAdapter;
    private PaymentStatusAdapter paymentStatusAdapter;
    private DaysAdapter daysAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_options);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnproceed = (TextView) findViewById(R.id.btnproceed);
        lvfiltertype = (ListView) findViewById(R.id.lvfiltertype);
        rvfilerpaymentforoptions = (RecyclerView) findViewById(R.id.rvfilerpaymentforoptions);
        rvfilerpaymentstatusoptions = (RecyclerView) findViewById(R.id.rvfilerpaymentstatusoptions);
        rvfilerpaymentdaysoptions = (RecyclerView) findViewById(R.id.rvfilerpaymentdaysoptions);

        rvfilerpaymentforoptions.setHasFixedSize(true);
        rvfilerpaymentforoptions.setLayoutManager(new LinearLayoutManager(this));

        rvfilerpaymentstatusoptions.setHasFixedSize(true);
        rvfilerpaymentstatusoptions.setLayoutManager(new LinearLayoutManager(this));

        rvfilerpaymentdaysoptions.setHasFixedSize(true);
        rvfilerpaymentdaysoptions.setLayoutManager(new LinearLayoutManager(this));

        daysselected = new ArrayList<>();
        types = new ArrayList<String>();
        paymentforselected = new ArrayList<String>();
        daystype = new ArrayList<String>();
        paymentstatusselected = new ArrayList<String>();
        paymentforArraylist = new ArrayList<>();
        paymentStatusModelsArraylist = new ArrayList<>();

        final DaysAdapterPaymentAdmin typeadapter = new DaysAdapterPaymentAdmin(this, types);
        lvfiltertype.setAdapter(typeadapter);
        typeadapter.notifyDataSetChanged();
        types.add("Days");
        types.add("Payment For");
        types.add("Status");

        daysAdapter = new DaysAdapter(this, daystype);
        rvfilerpaymentdaysoptions.setAdapter(daysAdapter);
        daysAdapter.notifyDataSetChanged();
        daystype.add("Today");
        daystype.add("One Week");
        daystype.add("One Month");
        daystype.add("Three Month");
        daystype.add("Six Month");

        filterOptionAdapter = new FilterOptionAdapter(this, paymentforArraylist);
        rvfilerpaymentforoptions.setAdapter(filterOptionAdapter);
        filterOptionAdapter.notifyDataSetChanged();

        paymentStatusAdapter = new PaymentStatusAdapter(this, paymentStatusModelsArraylist);
        rvfilerpaymentstatusoptions.setAdapter(paymentStatusAdapter);
        paymentStatusAdapter.notifyDataSetChanged();

        lvfiltertype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    rvfilerpaymentdaysoptions.setVisibility(View.VISIBLE);
                    rvfilerpaymentforoptions.setVisibility(View.GONE);
                    rvfilerpaymentstatusoptions.setVisibility(View.GONE);
                } else if (position == 1) {
                    rvfilerpaymentdaysoptions.setVisibility(View.GONE);
                    rvfilerpaymentforoptions.setVisibility(View.VISIBLE);
                    rvfilerpaymentstatusoptions.setVisibility(View.GONE);
                } else if (position == 2) {
                    rvfilerpaymentdaysoptions.setVisibility(View.GONE);
                    rvfilerpaymentforoptions.setVisibility(View.GONE);
                    rvfilerpaymentstatusoptions.setVisibility(View.VISIBLE);
                }
            }
        });
        daysAdapter.SetOnItemClickListener(new DaysAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                daysselected.add(paymentStatusModelsArraylist.get(position).getName());
            }
        });
        paymentStatusAdapter.SetOnItemClickListener(new PaymentStatusAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (paymentStatusModelsArraylist.get(position).getCheck_box() == 0) {
                    paymentStatusModelsArraylist.get(position).setCheck_box(1);
                    paymentstatusselected.add(paymentStatusModelsArraylist.get(position).getName());
                } else {
                    paymentStatusModelsArraylist.get(position).setCheck_box(0);
                    if (paymentstatusselected.contains(paymentStatusModelsArraylist.get(position).getName())) {
                        paymentstatusselected.remove(paymentStatusModelsArraylist.get(position).getName());
                    }

                }
                paymentStatusAdapter.notifyDataSetChanged();
            }
        });
        filterOptionAdapter.SetOnItemClickListener(new FilterOptionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (paymentforArraylist.get(position).getCheck_box() == 0) {
                    paymentforArraylist.get(position).setCheck_box(1);
                    paymentforselected.add(paymentforArraylist.get(position).getName());
                } else {
                    paymentforArraylist.get(position).setCheck_box(0);
                    if (paymentstatusselected.contains(paymentforArraylist.get(position).getName())) {
                        paymentforselected.remove(paymentforArraylist.get(position).getName());
                    }

                }
                filterOptionAdapter.notifyDataSetChanged();
            }
        });
        btnproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), paymentforselected + "      " + paymentstatusselected, Toast.LENGTH_SHORT).show();
            }
        });
        getFiltersDada();
    }

    private void getFiltersDada() {
      try{  Map<String, String> params = new HashMap<String, String>();
        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
            params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
        } else {
            params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
        }
        RetrofitAPI.getInstance(this).getApi().getfeesfilterdata(params, new Callback<FiltersResponse>() {
            @Override
            public void success(FiltersResponse filtersResponse, Response response) {

try{
                if (filtersResponse.getStatus() == Constants.SUCCESS) {
                    UIUtil.stopProgressDialog(getApplicationContext());
                    updatedata(filtersResponse);

                } else {
                    UIUtil.stopProgressDialog(getApplicationContext());
                    UIUtil.stopProgressDialog(getApplicationContext());
                    Toast.makeText(getApplicationContext(), "Try After SomeTime", Toast.LENGTH_SHORT).show();
                }
} catch (Exception e) {
    e.printStackTrace();
}
            }

            @Override
            public void failure(RetrofitError error) {
                UIUtil.stopProgressDialog(getApplicationContext());
            }
        });} catch (Exception e) {
              e.printStackTrace();
          }
    }

    private void updatedata(FiltersResponse filtersResponse) {
        adddatattolistview(filtersResponse.getFilters());
    }

    private void adddatattolistview(FiltersModel filters) {
        paymentStatusModelsArraylist.addAll(filters.getPaymentStatus());
        paymentforArraylist.addAll(filters.getPaymentFor());
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


}
