package com.maxlore.edumanage.Activities.TeacherActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.TeacherAdapters.StudentObservationRecyclerAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.StudentObservation.Observation;
import com.maxlore.edumanage.Models.TeacherModels.StudentObservation.ObservationResponse;
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

public class StudentObservationActivity extends AppCompatActivity {
    private RecyclerView observationRecyclerView;
    private ArrayList<Observation> observationarrayList;
    private StudentObservationRecyclerAdapter studentObservationRecyclerAdapter;
    private String Name;
    private TextView createButton;
    private EditText reasonText;
    private Switch parentVisibility;
    private String id;
    private LinearLayout stuobservation;
    private TextView tvremarkhidden;
    private Boolean status = false;
    private TextView nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_observation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createButton = (TextView) findViewById(R.id.btnCreate);
        reasonText = (EditText) findViewById(R.id.etreason);
        parentVisibility = (Switch) findViewById(R.id.parentvisibility);
        tvremarkhidden = (TextView) findViewById(R.id.tvwremarkhidden);
        nodata = (TextView) findViewById(R.id.nodata);
        observationRecyclerView = (RecyclerView) findViewById(R.id.lvobservation);

        observationRecyclerView.setHasFixedSize(true);
        observationRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        observationarrayList = new ArrayList<>();
        studentObservationRecyclerAdapter = new StudentObservationRecyclerAdapter(this, observationarrayList);
        observationRecyclerView.setAdapter(studentObservationRecyclerAdapter);
        studentObservationRecyclerAdapter.notifyDataSetChanged();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        reasonText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    onCreateButton();
                }

            }
        });

        parentVisibility.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    status = true;

                } else if (!isChecked) {
                    status = false;
                }
            }
        })
        ;

        try {

            Intent intent = getIntent();
            id = getIntent().getStringExtra("id");
            Name = intent.getStringExtra(Constants.NAME);

            ((TextView) findViewById(R.id.tvwName)).setText(Name);


        } catch (Exception e) {
            e.printStackTrace();
        }
        getObservationDetail();

        observationRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                observationRecyclerView.isNestedScrollingEnabled();
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
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
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }

    private boolean validateFields() {

        if (reasonText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter Reason For Observation", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            reasonText.setError(null);
        }

        return true;

    }

    private void onCreateButton() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("student_id", id);
                jsonObject.addProperty("text", reasonText.getText().toString());
                jsonObject.addProperty("is_hidden_from_parent", status);
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().sentObservationDetails(jsonObject, new Callback<ObservationResponse>() {
                    @Override
                    public void success(ObservationResponse observation, Response response) {
                        try {
                            if (observation.getStatus() == Constants.SUCCESS) {
                                Log.e("jsonObject", "jsonObject --- " + observation.toString());
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "Observation created successfully'", Toast.LENGTH_SHORT).show();
                                getObservationDetail();
                                reasonText.setText("");
                                parentVisibility.setChecked(false);
                            } else {
                                Toast.makeText(getApplicationContext(), "" + observation.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void getObservationDetail() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");

                Map<String, String> params = new HashMap<String, String>();
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                params.put("student_id", id);

                RetrofitAPI.getInstance(this).getApi().getObservation(params, new Callback<ObservationResponse>() {
                    @Override
                    public void success(ObservationResponse observation, Response response) {
                        try {
                            if (observation.getStatus() == Constants.SUCCESS) {
                                Log.e("jsonObject", "jsonObject --- " + observation.toString());
                                UIUtil.stopProgressDialog(getApplicationContext());
                                observationarrayList.clear();
                                observationarrayList.addAll(observation.getObservationInfo());
                                studentObservationRecyclerAdapter.notifyDataSetChanged();

                                if (observationarrayList.size() <= 0) {
                                    nodata.setVisibility(View.VISIBLE);
                                    observationRecyclerView.setVisibility(View.GONE);
                                } else {
                                    nodata.setVisibility(View.GONE);
                                    observationRecyclerView.setVisibility(View.VISIBLE);
                                }

                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "" + observation.getMessage(), Toast.LENGTH_SHORT).show();
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
}
