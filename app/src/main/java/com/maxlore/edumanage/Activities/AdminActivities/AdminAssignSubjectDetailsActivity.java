package com.maxlore.edumanage.Activities.AdminActivities;

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
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.AdminAssignSubjectDetailsAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAssignSubject.AssignSubject;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAssignSubject.AssignSubjectResponse;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AdminAssignSubjectDetailsActivity extends AppCompatActivity {

    private AdminAssignSubjectDetailsAdapter adminAssignSubjectDetailsAdapter;
    private RecyclerView rvsubjects;
    private TextView btnSend;
    private ArrayList<AssignSubject> subjectArrayList, searchArraylist;
    private int response;
    private String id;
    private int result;
    private int array[];
    private String arr[];
    private SparseBooleanArray mCheckStates;
    private String items;
    private Handler handler;
    private String pos;
    public static final int TIME_OUT = 1000;
    private EditText etSearch;
    private TextView tv_nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_assign_subject_details);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvsubjects = (RecyclerView) findViewById(R.id.rvsubjects);
        etSearch = (EditText) findViewById(R.id.etSearch);
        tv_nodata = (TextView) findViewById(R.id.nodata);
        rvsubjects.setHasFixedSize(true);
        rvsubjects.setLayoutManager(new LinearLayoutManager(this));

        handler = new Handler();
        btnSend = (TextView) findViewById(R.id.btnSend);
        subjectArrayList = new ArrayList<>();

        mCheckStates = new SparseBooleanArray();
        searchArraylist = new ArrayList<>();

        adminAssignSubjectDetailsAdapter = new AdminAssignSubjectDetailsAdapter(this, subjectArrayList);
        rvsubjects.setAdapter(adminAssignSubjectDetailsAdapter);
        adminAssignSubjectDetailsAdapter.notifyDataSetChanged();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        adminAssignSubjectDetailsAdapter.SetOnItemClickListener(new AdminAssignSubjectDetailsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                result = position;
              /*  selectedItems += (subjectArrayList.get(result).getSubjectName().toString() + ",");*/

                if (subjectArrayList.get(position).getFlag() == 1) {
                    subjectArrayList.get(position).setFlag(0);
                } else {
                    subjectArrayList.get(position).setFlag(1);
                }
                adminAssignSubjectDetailsAdapter.notifyDataSetChanged();

            }

        });

        try {

            Intent intent = getIntent();
            id = getIntent().getStringExtra("id");

            getSubjectDetails();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("responsd  ", "   dscc" + response);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(s) && s.length() > 2) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            filterSearch(s.toString());
                        }
                    }, TIME_OUT);
                } else {
                    if (searchArraylist.size() > 0) {
                        subjectArrayList.clear();
                        subjectArrayList.addAll(searchArraylist);
                        adminAssignSubjectDetailsAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignData();
            }
        });

    }

    private void filterSearch(String search) {
        try {
            subjectArrayList.clear();
            for (int i = 0; i < searchArraylist.size(); i++) {
                AssignSubject assignSubject = searchArraylist.get(i);
                if (assignSubject.getSubjectName().toLowerCase().contains(search.toLowerCase())) {
                    subjectArrayList.add(assignSubject);
                }
            }
            if (subjectArrayList.size() <= 0) {
                etSearch.setError("No Record found");
            }
            adminAssignSubjectDetailsAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSubjectDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("employee_id", id);
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().onSubjectDetails(jsonObject, new Callback<AssignSubjectResponse>() {
                    @Override
                    public void success(AssignSubjectResponse assignSubjectResponse, Response response) {
                        try {
                            if (assignSubjectResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                searchArraylist.clear();
                                searchArraylist.addAll(assignSubjectResponse.getAvailableSubjectList());
                                subjectArrayList.addAll(assignSubjectResponse.getAvailableSubjectList());
                                if (subjectArrayList.size() <= 0) {
                                    rvsubjects.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                } else {
                                    rvsubjects.setVisibility(View.VISIBLE);
                                    tv_nodata.setVisibility(View.GONE);
                                }
                                adminAssignSubjectDetailsAdapter.notifyDataSetChanged();
                                Log.e("jsonObject", "jsonObject --- " + assignSubjectResponse.toString());
                                Log.e("jsonObject", "my list --- " + subjectArrayList.toString());
                            } else {
                                Toast.makeText(getApplicationContext(), "" + assignSubjectResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

    private JsonArray getSelectedSubjects() {
        JsonArray selectessubject = new JsonArray();
        for (int i = 0; i < subjectArrayList.size(); i++) {
            if (subjectArrayList.get(i).getFlag() == 1) {
                selectessubject.add(new JsonPrimitive(subjectArrayList.get(i).getSubjectName()));
            }
        }
        return selectessubject;

    }

    private boolean validateFields() {
        if (getSelectedSubjects().size() < 1) {
            Toast.makeText(this, "Please Select atleast one subject", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private void assignData() {
        try {
            if (validateFields()) {
                if (UIUtil.isInternetAvailable(this)) {
                    UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("employee_id", id);
                    jsonObject.add("subject_name", getSelectedSubjects());
                    if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                        jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                    } else {
                        jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                    }
                    Log.e("jsonObject", "jsonObject --- " + jsonObject.toString());

                    RetrofitAPI.getInstance(this).getApi().assignData(jsonObject, new Callback<JsonObject>() {
                        @Override
                        public void success(JsonObject object, Response response) {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Log.e("jsonObject", "jsonObject --- " + object.toString());
                            Log.e("item ", "djfhksdh   " + subjectArrayList.get(result).getSubjectName());


                            try {
                                if (object == null) {
                                    Toast.makeText(AdminAssignSubjectDetailsActivity.this, "Something went worng, try after sometime...", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (object.get("status").getAsInt() == Constants.SUCCESS) {
                                    Toast.makeText(AdminAssignSubjectDetailsActivity.this, "Subjects assigned successfully", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(AdminAssignSubjectDetailsActivity.this, "Something went worng, try after sometime...", Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            finish();
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
