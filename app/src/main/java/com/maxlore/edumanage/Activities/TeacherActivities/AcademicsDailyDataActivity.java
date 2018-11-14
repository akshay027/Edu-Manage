
package com.maxlore.edumanage.Activities.TeacherActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.TeacherAdapters.DailyDataAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.Academics.DailySessionPlan;
import com.maxlore.edumanage.Models.TeacherModels.Academics.DailySessionResponse;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AcademicsDailyDataActivity extends AppCompatActivity {

    private DailyDataAdapter dailyDataAdapter;
    private ListView lvDailydetail;
    private String secid, stdid, subid;
    private ArrayList<DailySessionPlan> dailySessionArrayList;
    private TextView nodata;
    private ArrayList<String> statusArraylist;
    private int status, plan_id;
    public static final int TIME_OUT = 1000;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academics_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        dailySessionArrayList = new ArrayList();
        statusArraylist = new ArrayList<>();
        
        nodata = (TextView) findViewById(R.id.nodata);
        lvDailydetail = (ListView) findViewById(R.id.lvDailydetail);
        
        dailyDataAdapter = new DailyDataAdapter(this, dailySessionArrayList);
        lvDailydetail.setAdapter(dailyDataAdapter);
        dailyDataAdapter.notifyDataSetChanged();
        
        handler = new Handler();
        
        statusArraylist.add("Not Started");
        statusArraylist.add("On Progress");
        statusArraylist.add("Completed");
        try {

            Intent intent = getIntent();
            secid = getIntent().getStringExtra("secid");
            stdid = getIntent().getStringExtra("stdid");
            subid = getIntent().getStringExtra("subid");

        } catch (Exception e) {
            e.printStackTrace();
        }
        lvDailydetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                plan_id = dailySessionArrayList.get(position).getDailyPlanId();
                if (dailySessionArrayList.get(position).getEditable()) {
                    statusAlertbox();
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot be updated", Toast.LENGTH_SHORT).show();

                }
            }
        });
        getDailyData();
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


    public void getDailyData() {
        try{if (UIUtil.isInternetAvailable(this)) {

            UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
            Map<String, String> params = new HashMap<String, String>();
            params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
            params.put("type", Constants.DAILYFRAGMENT);
            params.put("section_id", secid);
            params.put("subject_id", subid);

            RetrofitAPI.getInstance(this).getApi().getDailyAcademicPlan(params, new Callback<DailySessionResponse>() {
                @Override
                public void success(DailySessionResponse dailySessionResponse, Response response) {
try{
                    if (dailySessionResponse.getStatus() == Constants.SUCCESS) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        
                        dailySessionArrayList.clear();
                        dailySessionArrayList.addAll(dailySessionResponse.getSessionPlan());
                        lvDailydetail.setAdapter(dailyDataAdapter);
                        dailyDataAdapter.notifyDataSetChanged();
                        
                        if (dailySessionArrayList.size() <= 0) {
                            lvDailydetail.setVisibility(View.GONE);
                            nodata.setVisibility(View.VISIBLE);
                        } else {
                            lvDailydetail.setVisibility(View.VISIBLE);
                            nodata.setVisibility(View.GONE);
                        }
                        Log.e("Example", "--------------- --" + dailySessionArrayList.toString());

                    } else {
                        Toast.makeText(AcademicsDailyDataActivity.this, "" + dailySessionResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }} catch (Exception e) {
    e.printStackTrace();
}

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        } else {
            Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
        }} catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AlertDialog statusAlertbox() {
        
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View promptView = inflater.inflate(R.layout.list_item_statusalertbox, null);
        LinearLayout llnotstarted = (LinearLayout) promptView.findViewById(R.id.llnotstarted);
        LinearLayout llonprogress = (LinearLayout) promptView.findViewById(R.id.llonprogress);
        LinearLayout llcompleted = (LinearLayout) promptView.findViewById(R.id.llcompleted);

        final RadioButton radionotstarted = (RadioButton) promptView.findViewById(R.id.radionotstarted);
        final RadioButton radioonprogress = (RadioButton) promptView.findViewById(R.id.radioonprogress);
        final RadioButton radiocompleted = (RadioButton) promptView.findViewById(R.id.radiocompleted);

        llnotstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        status = 1;
                        radionotstarted.setChecked(true);
                        radioonprogress.setChecked(false);
                        radiocompleted.setChecked(false);
                        update_daily_plan_status();
                    }
                }, TIME_OUT);

            }
        });
        radioonprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        status = 2;
                        radionotstarted.setChecked(false);
                        radioonprogress.setChecked(true);
                        radiocompleted.setChecked(false);
                        update_daily_plan_status();
                    }
                }, TIME_OUT);
            }
        });
        radionotstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        status = 1;
                        radionotstarted.setChecked(true);
                        radioonprogress.setChecked(false);
                        radiocompleted.setChecked(false);
                        update_daily_plan_status();
                    }
                }, TIME_OUT);
            }
        });
        radiocompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        status = 3;
                        radionotstarted.setChecked(false);
                        radioonprogress.setChecked(false);
                        radiocompleted.setChecked(true);
                        update_daily_plan_status();
                    }
                }, TIME_OUT);
            }
        });

        llonprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        status = 2;
                        radionotstarted.setChecked(false);
                        radioonprogress.setChecked(true);
                        radiocompleted.setChecked(false);
                        update_daily_plan_status();
                    }
                }, TIME_OUT);

            }
        });
        llcompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        status = 3;
                        radionotstarted.setChecked(false);
                        radioonprogress.setChecked(false);
                        radiocompleted.setChecked(true);
                        update_daily_plan_status();
                    }
                }, TIME_OUT);

            }
        });
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

    private void update_daily_plan_status() {
       try{ if (UIUtil.isInternetAvailable(this)) {
            UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("plan_id", plan_id);
            jsonObject.addProperty("remark", status);
            jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
            RetrofitAPI.getInstance(this).getApi().update_daily_plan_status(jsonObject, new Callback<JsonObject>() {
                @Override
                public void success(JsonObject jsonObj, Response response) {
                    UIUtil.stopProgressDialog(getApplicationContext());

                    Log.e("Json ", "Hhd --- " + jsonObj.toString());
                    Log.e("Json ", "Response --- " + response.getBody());
                    try {

                        if (Constants.SUCCESS == jsonObj.get("status").getAsInt()) {
                            Toast.makeText(getApplicationContext(), jsonObj.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            getDailyData();
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

}
