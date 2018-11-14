package com.maxlore.edumanage.Activities.AdminActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.AdminAssignSubjectAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAssignSubject.Assign;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAssignSubject.AssignResponse;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.maxlore.edumanage.R;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AdminAssignSubjectActivity extends AppCompatActivity {

    private ArrayList<Assign> employeeCategorieslist, searchArraylist;
    private AdminAssignSubjectAdapter adminAssignSubjectAdapter;
    private ArrayAdapter<String> adapter;
    private String selectedCategory = "";
    private ListView lvemployee;
    private ArrayList responseArraylist;
    private int result;
    private Handler handler;
    public static final int TIME_OUT = 1000;
    private EditText etSearch;
    private TextView tv_nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asign_subject);

        tv_nodata = (TextView) findViewById(R.id.nodata);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        handler = new Handler();
        lvemployee = (ListView) findViewById(R.id.lvemployee);
        etSearch = (EditText) findViewById(R.id.etSearch);

        employeeCategorieslist = new ArrayList();
        searchArraylist = new ArrayList<>();
        responseArraylist = new ArrayList();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
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
                        employeeCategorieslist.clear();
                        employeeCategorieslist.addAll(searchArraylist);
                        adminAssignSubjectAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adminAssignSubjectAdapter = new AdminAssignSubjectAdapter(this, employeeCategorieslist);
        lvemployee.setAdapter(adminAssignSubjectAdapter);
        adminAssignSubjectAdapter.notifyDataSetChanged();

        getAssignSubject();

        lvemployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                result = position;
                onEmployeeClick();
                Intent intent = new Intent(getApplicationContext(), AdminAssignSubjectDetailsActivity.class);
                intent.putExtra("id", String.valueOf(employeeCategorieslist.get(position).getEmployeeId()));
                //intent.putExtra("response", String.valueOf(responseArraylist));
                startActivity(intent);
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
            startActivity(new Intent(AdminAssignSubjectActivity.this, AdminLandingActivity.class));
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
        startActivity(new Intent(AdminAssignSubjectActivity.this, AdminLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }

    private void filterSearch(String search) {
        try {
            employeeCategorieslist.clear();
            for (int i = 0; i < searchArraylist.size(); i++) {
                Assign assign = searchArraylist.get(i);
                if (assign.getEmployeeName().toLowerCase().contains(search.toLowerCase())) {
                    employeeCategorieslist.add(assign);
                }
            }
            if (employeeCategorieslist.size() <= 0) {
                etSearch.setError("No Record found");
            }
            adminAssignSubjectAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAssignSubject() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().getAssignSubject(params, new Callback<AssignResponse>() {
                    @Override
                    public void success(AssignResponse assignResponse, Response response) {


                        Log.e("Json ", "Hhd --- " + assignResponse.toString());
                        Log.e("Json ", "Response --- " + response.getBody());
                        try {
                            if (assignResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                searchArraylist.clear();
                                employeeCategorieslist.clear();
                                employeeCategorieslist.addAll(assignResponse.getEmployee());
                                searchArraylist.addAll(assignResponse.getEmployee());
                                if (employeeCategorieslist.size() <= 0) {
                                    lvemployee.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                } else {
                                    lvemployee.setVisibility(View.VISIBLE);
                                    tv_nodata.setVisibility(View.GONE);
                                }
                                adminAssignSubjectAdapter.notifyDataSetChanged();

                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "" + assignResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.e("Json ", "Hhd --- " + assignResponse.toString());
                        Log.e("Json ", "Response --- " + response.getBody());
                        //Log.e("spinner1132", "----" + responseArraylist.toString());
                        //Log.e("spinner23re4", "----" + categories.toString());
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

    private void onEmployeeClick() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("employee_id", employeeCategorieslist.get(result).getEmployeeId());
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().onEmployeeClick(jsonObject, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject object, Response response) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Log.e("jsonObject", "jsonObject --- " + object.toString());

                        //responseArraylist.add(object.getAvailableSubjectList());
                        try {
                            if (object == null) {
                                Toast.makeText(AdminAssignSubjectActivity.this, "Something went worng, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (object.get("status").getAsInt() == Constants.SUCCESS) {

                                Toast.makeText(AdminAssignSubjectActivity.this, object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AdminAssignSubjectActivity.this, "Something went worng, try after sometime...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                    }
                });
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}