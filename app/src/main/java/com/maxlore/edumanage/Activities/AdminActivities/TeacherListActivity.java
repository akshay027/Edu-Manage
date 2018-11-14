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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.TeachersListAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.ClassteacherModel.ListTeacher;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.ClassteacherModel.Teacherlist;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TeacherListActivity extends AppCompatActivity {
    private RecyclerView teacherlistRecyclerView;
    private ArrayList<ListTeacher> listTeacherArrayList, searchArrayList;
    private TeachersListAdapter teachersListAdapter;
    private int currentPosition;
    private ArrayList<Integer> arrayList;
    private ArrayList<String> seclist, stdlist;
    private Handler handler;
    private EditText etTeacherSearch;
    private String pos, Standard, Section, sectionid, currentcls;
    private int spinpos;
    private LinearLayout llcurrent;
    private TextView tv_section, tv_standard, tv_classteacher;
    public static final int TIME_OUT = 1000;
    private String message = "Are you Sure  ?";
    private Boolean checkshrey = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_class_teacher);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        teacherlistRecyclerView = (RecyclerView) findViewById(R.id.teacherlistRecyclerView);
        tv_section = (TextView) findViewById(R.id.tv_section);
        tv_standard = (TextView) findViewById(R.id.tv_standard);

        llcurrent = (LinearLayout) findViewById(R.id.llcurrent);

        tv_classteacher = (TextView) findViewById(R.id.tv_classteacher);

        etTeacherSearch = (EditText) findViewById(R.id.etTeacherSearch);

        handler = new Handler();

        listTeacherArrayList = new ArrayList<>();
        searchArrayList = new ArrayList<>();
        arrayList = new ArrayList<Integer>();
        stdlist = new ArrayList<String>();
        seclist = new ArrayList<String>();

        teachersListAdapter = new TeachersListAdapter(this, listTeacherArrayList);
        teacherlistRecyclerView.setAdapter(teachersListAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        teacherlistRecyclerView.setLayoutManager(llm);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etTeacherSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });

        etTeacherSearch.addTextChangedListener(new TextWatcher() {
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
                    if (searchArrayList.size() > 0) {
                        listTeacherArrayList.clear();
                        listTeacherArrayList.addAll(searchArrayList);
                        teachersListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        try {
            Intent intent = getIntent();
            pos = getIntent().getStringExtra("pos");
            Standard = intent.getStringExtra(Constants.STANDARD);
            Section = intent.getStringExtra(Constants.SECTION);
            arrayList = (ArrayList<Integer>) getIntent().getSerializableExtra("mylist");
            seclist = (ArrayList<String>) getIntent().getSerializableExtra("seclist");
            stdlist = (ArrayList<String>) getIntent().getSerializableExtra("stdlist");
            sectionid = getIntent().getStringExtra("section_id");
            currentcls = getIntent().getStringExtra("curr_classteacher");
            Log.e("abccclllll", currentcls + "");
            if (currentcls == null) {
                llcurrent.setVisibility(View.GONE);
            } else {
                llcurrent.setVisibility(View.VISIBLE);
                tv_classteacher.setText(currentcls);
            }

            tv_section.setText(Section);
            tv_standard.setText(Standard);

        } catch (Exception e) {
            e.printStackTrace();
        }

        teachersListAdapter.SetOnItemClickListener(new TeachersListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                spinpos = position;
                clearSelection(spinpos);
                assignTeacher(position);
                teachersListAdapter.notifyDataSetChanged();

            }

        });

        getTeacherlist();

    }

    private void clearSelection(int position) {
        for (int i = 0; i < listTeacherArrayList.size(); i++) {
            if (position == i) {
                listTeacherArrayList.get(i).setSelected(true);

            } else {
                listTeacherArrayList.get(i).setSelected(false);
                // Cancelmethod();
            }
        }
    }

    private void filterSearch(String search) {
        try {
            listTeacherArrayList.clear();
            for (int i = 0; i < searchArrayList.size(); i++) {
                ListTeacher listTeacher = searchArrayList.get(i);
                if (listTeacher.getEmployeeName().toLowerCase().contains(search.toLowerCase())) {
                    listTeacherArrayList.add(listTeacher);
                }
            }
            if (listTeacherArrayList.size() <= 0) {
                etTeacherSearch.setError("No Record found");
            }
            teachersListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cancelmethod() {
        finish();
    }

    private void assignTeacher(int currentPosition) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        for (int i = 0; i < arrayList.size(); i++) {

            if (arrayList.get(i) == listTeacherArrayList.get(currentPosition).getEmployeeId()) {
                message = listTeacherArrayList.get(currentPosition).getEmployeeName() + " is already class teacher of " + stdlist.get(i) + "-" + seclist.get(i) + ". Do you want to reassign this teacher ?";
            }
        }

        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /// TODO call Api here
                assignClassTeacherMethod();
            }
        });
        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listTeacherArrayList.clear();
                getTeacherlist();
                dialog.dismiss();
            }
        });
        builder.show();


    }

    private void assignClassTeacherMethod() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                JsonObject jsonObject = new JsonObject();
                JsonObject jsonObject1 = new JsonObject();
                jsonObject1.addProperty("employee_id", listTeacherArrayList.get(spinpos).getEmployeeId());
                jsonObject.addProperty("section_id", sectionid);
                jsonObject.add("class_teacher", jsonObject1);
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }

                RetrofitAPI.getInstance(this).getApi().assignclassTeacher(jsonObject, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject object, Response response) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Log.e("jsonObject", "jsonObject --- " + object.toString());
                        try {
                            if (object == null) {
                                Toast.makeText(TeacherListActivity.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (object.get("status").getAsInt() == Constants.SUCCESS) {
                                Toast.makeText(TeacherListActivity.this, object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(TeacherListActivity.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                        }
                        finish();
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

    private void bindData(List<ListTeacher> listTeachers) {
        searchArrayList.clear();
        listTeacherArrayList.addAll(listTeachers);
        searchArrayList.addAll(listTeachers);
        teachersListAdapter.notifyDataSetChanged();
    }

    private void getTeacherlist() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
               // params.put("department_id")
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().getTeacherlist(params, new Callback<Teacherlist>() {

                    @Override
                    public void success(Teacherlist teacherlist, Response response) {
                        try {
                            if (teacherlist.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                bindData(teacherlist.getListTeacher());

                            } else {
                                Toast.makeText(getApplicationContext(), "" + teacherlist.getMessage(), Toast.LENGTH_SHORT).show();
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

}
