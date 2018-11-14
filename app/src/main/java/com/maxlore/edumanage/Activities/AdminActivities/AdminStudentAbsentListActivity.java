package com.maxlore.edumanage.Activities.AdminActivities;

import android.content.DialogInterface;
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

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.AdminStudentAbsentAdapter;
import com.maxlore.edumanage.Adapters.AdminAdapters.AdminStudentLeaveAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAbsent;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAbsentLeave;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAbsentStudent;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAbsentStudentResponse;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AdminStudentAbsentListActivity extends AppCompatActivity {
    private AdminStudentAbsentAdapter adminStudentAbsentAdapter;
    private AdminStudentLeaveAdapter adminStudentLeaveAdapter;
    private EditText etabsentSearch, etleaveSearch;
    private ListView studentlistView, studentleavelistView;
    private TextView tvErrorAnn1, tvErrorAnn2;
    private ArrayList finalArrylist;
    private ArrayList<AdminAbsent> listSchoolabsentArrayList, searchArrayabsentlist;
    private ArrayList<AdminAbsentLeave> listSchoolleaveArrayList, searchArryleavelist;
    private Handler handler;
    public static final int TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student_absent_list);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        studentlistView = (ListView) findViewById(R.id.studentlistView);

        studentleavelistView = (ListView) findViewById(R.id.studentleavelistView);
        tvErrorAnn1 = (TextView) findViewById(R.id.tvErrorAnn1);
        tvErrorAnn2 = (TextView) findViewById(R.id.tvErrorAnn2);
        etleaveSearch = (EditText) findViewById(R.id.etleaveSearch);
        etabsentSearch = (EditText) findViewById(R.id.etabsentSearch);
        handler = new Handler();

        searchArryleavelist = new ArrayList();
        searchArrayabsentlist = new ArrayList();

        listSchoolabsentArrayList = new ArrayList<>();
        listSchoolleaveArrayList = new ArrayList<>();

        adminStudentAbsentAdapter = new AdminStudentAbsentAdapter(this, listSchoolabsentArrayList);
        studentlistView.setAdapter(adminStudentAbsentAdapter);

        adminStudentLeaveAdapter = new AdminStudentLeaveAdapter(this, listSchoolleaveArrayList);
        studentleavelistView.setAdapter(adminStudentLeaveAdapter);

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
                        adminStudentAbsentAdapter.notifyDataSetChanged();
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
                    if (searchArryleavelist.size() > 0) {
                        listSchoolleaveArrayList.clear();
                        listSchoolleaveArrayList.addAll(searchArryleavelist);
                        adminStudentLeaveAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        getStudentAbsent();
    }

    private void filterSearchabsent(String search) {
        try {
            listSchoolabsentArrayList.clear();
            for (int i = 0; i < searchArrayabsentlist.size(); i++) {
                AdminAbsent adminstudentAbsent = searchArrayabsentlist.get(i);
                if (adminstudentAbsent.getAdmissionNumber().toLowerCase().contains(search.toLowerCase()) ||
                        adminstudentAbsent.getName().toLowerCase().contains(search.toLowerCase()) ||
                        adminstudentAbsent.getStatus().toLowerCase().contains(search.toLowerCase())) {
                    listSchoolabsentArrayList.add(adminstudentAbsent);
                }
            }
            if (listSchoolabsentArrayList.size() <= 0) {
                etabsentSearch.setError("No Record found");
            }
            adminStudentAbsentAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterSearchleave(String search) {
        try {
            listSchoolleaveArrayList.clear();
            for (int i = 0; i < searchArryleavelist.size(); i++) {
                AdminAbsentLeave adminTeacherLeave = searchArryleavelist.get(i);
                if (adminTeacherLeave.getAdmissionNumber().toLowerCase().contains(search.toLowerCase()) ||
                        adminTeacherLeave.getName().toLowerCase().contains(search.toLowerCase()) ||
                        adminTeacherLeave.getStatus().toLowerCase().contains(search.toLowerCase())) {
                    listSchoolleaveArrayList.add(adminTeacherLeave);
                }
            }
            if (listSchoolleaveArrayList.size() <= 0) {
                etleaveSearch.setError("No Record found");
            }
            adminStudentLeaveAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getStudentAbsent() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }

                RetrofitAPI.getInstance(this).getApi().getStudentAbsentlist(params, new Callback<AdminAbsentStudentResponse>() {

                    @Override
                    public void success(AdminAbsentStudentResponse teachaerlist, Response response) {
                        try {
                            if (teachaerlist.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());

                                bindDataforAbsent(teachaerlist.getAbsentStudents());

                                Log.e("kjndf", "====" + teachaerlist.getAbsentStudents());

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


    private void bindDataforAbsent(AdminAbsentStudent adminAbsentstudent) {
        Log.e("data1", "====" + adminAbsentstudent.getAdminAbsentLeave());
        Log.e("data2", "====" + adminAbsentstudent.getAbsent());

        bindDataforAbsent1(adminAbsentstudent.getAdminAbsentLeave());
        bindDataforAbsent2(adminAbsentstudent.getAbsent());


    }

    private void bindDataforAbsent1(List<AdminAbsentLeave> adminAbsentsleave) {
        try {
            listSchoolleaveArrayList.clear();
            searchArryleavelist.clear();
            searchArryleavelist.addAll(adminAbsentsleave);
            listSchoolleaveArrayList.addAll(adminAbsentsleave);
            if (listSchoolabsentArrayList.size() <= 0) {
                tvErrorAnn2.setVisibility(View.VISIBLE);
                studentleavelistView.setVisibility(View.GONE);
            } else {
                studentleavelistView.setVisibility(View.VISIBLE);
                tvErrorAnn2.setVisibility(View.GONE);

            }
            adminStudentLeaveAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindDataforAbsent2(List<AdminAbsent> adminAbsents) {
        try {
            listSchoolabsentArrayList.clear();
            searchArrayabsentlist.clear();
            searchArrayabsentlist.addAll(adminAbsents);
            listSchoolabsentArrayList.addAll(adminAbsents);
            if (listSchoolabsentArrayList.size() <= 0) {
                tvErrorAnn1.setVisibility(View.VISIBLE);
                studentlistView.setVisibility(View.GONE);
            } else {
                studentlistView.setVisibility(View.VISIBLE);
                tvErrorAnn1.setVisibility(View.GONE);

            }
            adminStudentAbsentAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

