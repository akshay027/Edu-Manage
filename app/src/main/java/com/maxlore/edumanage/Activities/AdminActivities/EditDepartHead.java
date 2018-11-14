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

/**
 * Created by maxlore on 28-Sep-17.
 */

public class EditDepartHead extends AppCompatActivity {

    private RecyclerView teacherlistRecyclerView;
    private ArrayList<ListTeacher> listTeacherArrayList, optionalTeacherArrayList, searchArrayList;
    private TeachersListAdapter teachersListAdapter;
    private int currentPosition;
    private ArrayList<String> dept_name_list;
    private ArrayList<Integer> dept_emp_list;
    private Handler handler;
    private EditText etTeacherSearch;
    private String dept_name, dept_id, currentdeparthead, old_departmenthead_id, id;
    private int spinpos;
    private int a = 0;
    private LinearLayout llcurrent;
    private TextView tv_departmentname, tv_dept_headname;
    public static final int TIME_OUT = 1000;
    private String message = "Are you Sure  ?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_depart_head);
        teacherlistRecyclerView = (RecyclerView) findViewById(R.id.teacherlistRecyclerView);
        llcurrent = (LinearLayout) findViewById(R.id.llcurrent);
        tv_departmentname = (TextView) findViewById(R.id.tv_departmentname);
        tv_dept_headname = (TextView) findViewById(R.id.tv_dept_headname);
        etTeacherSearch = (EditText) findViewById(R.id.etTeacherSearch);

        handler = new Handler();

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listTeacherArrayList = new ArrayList<>();
        optionalTeacherArrayList = new ArrayList<>();
        searchArrayList = new ArrayList<>();
        dept_emp_list = new ArrayList<Integer>();
        dept_name_list = new ArrayList<String>();

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
            dept_name = getIntent().getStringExtra("department_name");
            old_departmenthead_id = getIntent().getStringExtra("old_departmenthead_id");
            dept_name_list = (ArrayList<String>) getIntent().getSerializableExtra("dept_name_list");
            dept_emp_list = (ArrayList<Integer>) getIntent().getSerializableExtra("dept_emp_list");
            dept_id = getIntent().getStringExtra("department_id");
            id = getIntent().getStringExtra("id");
            Log.e("aaaaaaaaaaaa    :", String.valueOf(dept_emp_list));
            currentdeparthead = getIntent().getStringExtra("getDepartmentHeadName");
            Log.e("abccclllll", currentdeparthead + "");
            Log.e("old_departmenthead_id", old_departmenthead_id + "");

            if (currentdeparthead == null) {
                llcurrent.setVisibility(View.GONE);
            } else {
                llcurrent.setVisibility(View.VISIBLE);
                tv_dept_headname.setText(currentdeparthead);
            }

            tv_departmentname.setText(dept_name);

        } catch (Exception e) {
            e.printStackTrace();
        }

        teachersListAdapter.SetOnItemClickListener(new TeachersListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                spinpos = position;
                Log.e("posssssssssssssss :", String.valueOf(position));
                clearSelection(spinpos);
                assignTeacher(spinpos);
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

    private void Cancelmethod() {
        finish();
    }

    private void assignTeacher(int currentPosition) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        message = "Are you Sure  ?";
        for (int i = 0; i < dept_emp_list.size(); i++) {
            if (dept_emp_list.get(i) == (listTeacherArrayList.get(spinpos).getEmployeeId())) {
                Log.e("abccccccc......... :", String.valueOf(dept_emp_list.get(i)));
                Log.e("cdeeeeeee......... :", String.valueOf((listTeacherArrayList.get(spinpos).getEmployeeId())));
                message = listTeacherArrayList.get(currentPosition).getEmployeeName() + " is already department head of " + dept_name_list.get(i) + ". Do you want to reassign this teacher ?";
            }
        }

        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /// TODO call Api here
                editDeptHead();
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

    private void editDeptHead() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                JsonObject jsonObject = new JsonObject();
                JsonObject jsonObject1 = new JsonObject();
                jsonObject.addProperty("old_department_head", old_departmenthead_id);
                jsonObject.addProperty("id", id);
                jsonObject.add("department_head", jsonObject1);
                jsonObject1.addProperty("employee_id", listTeacherArrayList.get(spinpos).getEmployeeId());
                jsonObject.addProperty("perform", "update");
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().managedepartmenthead(jsonObject, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject object, Response response) {

                        UIUtil.stopProgressDialog(getApplicationContext());
                        Log.e("jsonObject", "jsonObject --- " + object.toString());
                        try {
                            if (object == null) {
                                Toast.makeText(EditDepartHead.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (object.get("status").getAsInt() == Constants.SUCCESS) {
                                Toast.makeText(EditDepartHead.this, object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(EditDepartHead.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }


                    @Override
                    public void failure(RetrofitError error) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                    }
                });
            } else {
                UIUtil.stopProgressDialog(getApplicationContext());
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void bindDataforTeacher(List<ListTeacher> listTeachers) {
        searchArrayList.clear();
        listTeacherArrayList.addAll(listTeachers);
        optionalTeacherArrayList.addAll(listTeachers);
        for (int i = 0; i < optionalTeacherArrayList.size(); i++) {
            if (old_departmenthead_id.equalsIgnoreCase(optionalTeacherArrayList.get(i).getEmployeeId().toString())) {
                listTeacherArrayList.remove(listTeacherArrayList.get(i));
                Log.e("data","===="+old_departmenthead_id.equals(optionalTeacherArrayList.get(i).getEmployeeId()));
            }
        }
        //Log.e("iuiuiu8","===="+optionalTeacherArrayList.get(5).getEmployeeId().toString());
        Log.e("3452345","===="+optionalTeacherArrayList);
        Log.e("list2","===="+listTeacherArrayList);
        searchArrayList.addAll(listTeachers);
        teachersListAdapter.notifyDataSetChanged();
    }

    private void getTeacherlist() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("department_id", dept_id);
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
                                bindDataforTeacher(teacherlist.getListTeacher());

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
