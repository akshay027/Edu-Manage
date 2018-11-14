
package com.maxlore.edumanage.Activities.AdminActivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.AdminTeacherAbsentAdapter;
import com.maxlore.edumanage.Adapters.AdminAdapters.AdminTeacherLeaveAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AbsentEmployees;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTeacherAbsent;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTeacherAbsentResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTeacherLeave;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AdminAbsentTeacherActivity extends AppCompatActivity {
    private AdminTeacherAbsentAdapter adminTeacherAbsentAdapter;
    private AdminTeacherLeaveAdapter adminTeacherLeaveAdapter;
    private EditText etabsentSearch, etleaveSearch;
    private ListView teacherlistView, teacherleavelistView;
    private Handler handler;
    private TextView tvErrorAnn1, tvErrorAnn2;
    private ArrayList<AdminTeacherAbsent> listSchoolabsentArrayList, searchArrayabsentlist;
    private ArrayList<AdminTeacherLeave> listSchoolleaveArrayList, searchArrayleavelist;
    public static final int TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_absent_teacher);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvErrorAnn1 = (TextView) findViewById(R.id.tvErrorAnn1);
        tvErrorAnn2 = (TextView) findViewById(R.id.tvErrorAnn2);

        teacherlistView = (ListView) findViewById(R.id.teacherlistView);

        teacherleavelistView = (ListView) findViewById(R.id.teacherleavelistView);
        etleaveSearch = (EditText) findViewById(R.id.etleaveSearch);
        etabsentSearch = (EditText) findViewById(R.id.etabsentSearch);

        handler = new Handler();

        searchArrayleavelist = new ArrayList();
        searchArrayabsentlist = new ArrayList();

        listSchoolabsentArrayList = new ArrayList<>();
        listSchoolleaveArrayList = new ArrayList<>();

        adminTeacherAbsentAdapter = new AdminTeacherAbsentAdapter(this, listSchoolabsentArrayList);
        teacherlistView.setAdapter(adminTeacherAbsentAdapter);

        adminTeacherLeaveAdapter = new AdminTeacherLeaveAdapter(this, listSchoolleaveArrayList);
        teacherleavelistView.setAdapter(adminTeacherLeaveAdapter);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etabsentSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        etleaveSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        etabsentSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.length() > 2) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            filterSearchabsent(s.toString());
                        }
                    }, TIME_OUT);
                } else {
                    if (searchArrayabsentlist.size() > 0) {
                        listSchoolabsentArrayList.clear();
                        listSchoolabsentArrayList.addAll(searchArrayabsentlist);
                        adminTeacherAbsentAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etleaveSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.length() > 2) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            filterSearchleave(s.toString());
                        }
                    }, TIME_OUT);
                } else {
                    if (searchArrayleavelist.size() > 0) {
                        listSchoolleaveArrayList.clear();
                        listSchoolleaveArrayList.addAll(searchArrayleavelist);
                        adminTeacherLeaveAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getTeacherAbsent();
    }

    private void filterSearchabsent(String search) {
        try {
            listSchoolabsentArrayList.clear();
            for (int i = 0; i < searchArrayabsentlist.size(); i++) {
                AdminTeacherAbsent adminTeacherAbsent = searchArrayabsentlist.get(i);
                if (adminTeacherAbsent.getEmpCode().toLowerCase().contains(search.toLowerCase()) ||
                        adminTeacherAbsent.getName().toLowerCase().contains(search.toLowerCase()) ||
                        adminTeacherAbsent.getStatus().toLowerCase().contains(search.toLowerCase())) {
                    listSchoolabsentArrayList.add(adminTeacherAbsent);
                }
            }
            if (listSchoolabsentArrayList.size() <= 0) {
                etabsentSearch.setError("No Record found");
            }
            adminTeacherAbsentAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterSearchleave(String search) {
        try {
            listSchoolleaveArrayList.clear();
            for (int i = 0; i < searchArrayleavelist.size(); i++) {
                AdminTeacherLeave adminTeacherLeave = searchArrayleavelist.get(i);
                if (adminTeacherLeave.getEmpCode().toLowerCase().contains(search.toLowerCase()) ||
                        adminTeacherLeave.getName().toLowerCase().contains(search.toLowerCase()) ||
                        adminTeacherLeave.getStatus().toLowerCase().contains(search.toLowerCase())) {
                    listSchoolleaveArrayList.add(adminTeacherLeave);
                }
            }
            if (listSchoolleaveArrayList.size() <= 0) {
                etleaveSearch.setError("No Record found");
            }
            adminTeacherLeaveAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getTeacherAbsent() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");

                Map<String, String> params = new HashMap<String, String>();
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().getTeacherAbsentlist(params, new Callback<AdminTeacherAbsentResponse>() {

                    @Override
                    public void success(AdminTeacherAbsentResponse teachaerlist, Response response) {
                        try {
                            if (teachaerlist.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());

                                bindData(teachaerlist.getAbsentEmployees());

                                Log.e("kjndf", "====" + teachaerlist.getAbsentEmployees());

                            } else {
                                Toast.makeText(getApplicationContext(), "" + teachaerlist.getMessage(), Toast.LENGTH_SHORT).show();
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


    private void bindData(AbsentEmployees adminAbsentteacher) {
        Log.e("data1", "====" + adminAbsentteacher.getLeave());
        Log.e("data2", "====" + adminAbsentteacher.getAbsent());

        bindDataforLeave(adminAbsentteacher.getLeave());
        bindDataforAbsent(adminAbsentteacher.getAbsent());

    }

    private void bindDataforLeave(List<AdminTeacherLeave> adminAbsentsleave) {
        try {
            listSchoolleaveArrayList.clear();
            searchArrayleavelist.clear();
            searchArrayleavelist.addAll(adminAbsentsleave);
            listSchoolleaveArrayList.addAll(adminAbsentsleave);
            if (listSchoolleaveArrayList.size() <= 0) {
                tvErrorAnn2.setVisibility(View.VISIBLE);
                teacherleavelistView.setVisibility(View.GONE);
            } else {
                teacherleavelistView.setVisibility(View.VISIBLE);
                tvErrorAnn2.setVisibility(View.GONE);
            }
            adminTeacherLeaveAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindDataforAbsent(List<AdminTeacherAbsent> adminAbsents) {
        try {
            listSchoolabsentArrayList.clear();
            searchArrayabsentlist.clear();
            searchArrayabsentlist.addAll(adminAbsents);
            listSchoolabsentArrayList.addAll(adminAbsents);
            if (listSchoolabsentArrayList.size() <= 0) {
                tvErrorAnn1.setVisibility(View.VISIBLE);
                teacherlistView.setVisibility(View.GONE);
            } else {
                teacherlistView.setVisibility(View.VISIBLE);
                tvErrorAnn1.setVisibility(View.GONE);

            }
            adminTeacherAbsentAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}