package com.maxlore.edumanage.Activities.AdminActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.DepartmentheadAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.DepartmentHead;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.DepartmentHeadResponse;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AdminDepartmentHeadfirstPage extends AppCompatActivity {
    private RecyclerView rv_departmenthead;
    private ArrayList<DepartmentHead> departmentHeadArrayList, finalarrayList;
    private DepartmentheadAdapter departmentheadAdapter;
    private int currentPosition;
    private String dele = "destroy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_department_headfirst_page);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv_departmenthead = (RecyclerView) findViewById(R.id.rv_departmenthead);
        departmentHeadArrayList = new ArrayList<>();
        finalarrayList = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv_departmenthead.setLayoutManager(llm);
    }

    private void bindDataforDepartment(List<DepartmentHead> departmentHeads) {
        finalarrayList.clear();
        departmentHeadArrayList.clear();

        departmentHeadArrayList.addAll(departmentHeads);
        finalarrayList.addAll(departmentHeads);
        departmentheadAdapter = new DepartmentheadAdapter(this, departmentHeadArrayList);
        rv_departmenthead.setAdapter(departmentheadAdapter);
        departmentheadAdapter.notifyDataSetChanged();
        departmentheadAdapter.SetOnItemClickListener(new DepartmentheadAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onEditDeptHead(View view, int position) {
                Intent abc = new Intent(AdminDepartmentHeadfirstPage.this, EditDepartHead.class);
                abc.putExtra("department_id", String.valueOf(departmentHeadArrayList.get(position).getDepartmentId()));
                abc.putExtra("old_departmenthead_id", String.valueOf(departmentHeadArrayList.get(position).getEmployeeId()));
                abc.putExtra("id", String.valueOf(departmentHeadArrayList.get(position).getDepartmentHeadId()));
                abc.putExtra("department_name", String.valueOf(departmentHeadArrayList.get(position).getDepartmentName()));
                abc.putExtra("dept_head_name", String.valueOf(departmentHeadArrayList.get(position).getDepartmentHeadName()));
                ArrayList deptnamelist = new ArrayList<>();
                ArrayList deptemplist = new ArrayList<Integer>();
                for (int i = 0; i < departmentHeadArrayList.size(); i++) {
                    if (departmentHeadArrayList.get(i).getDepartmentHeadStatus() == Constants.SUCCESS) {
                        deptnamelist.add(departmentHeadArrayList.get(i).getDepartmentName());
                        deptemplist.add(departmentHeadArrayList.get(i).getEmployeeId());
                    }
                }
                deptnamelist.removeAll(Collections.singleton(null));
                deptemplist.removeAll(Collections.singleton(null));
                abc.putExtra("dept_name_list", deptnamelist);
                abc.putExtra("dept_emp_list", deptemplist);
                startActivity(abc);
            }

            @Override
            public void onDeleteDeptHead(View view, int position) {
                deleteDeptHead(position);
            }

            @Override
            public void OnAssignDeptHead(View view, int position) {
                assignDept(position);
            }
        });
    }

    private void deleteDeptHead(final int position) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        currentPosition = position;

        builder.setTitle("Confirmation");
        String message = "Are you sure to Remove the Department head  " + " ?";
        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /// TODO call Api here
                editDeleteDeptHead();

            }
        });
        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void editDeleteDeptHead() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                JsonObject jsonObject = new JsonObject();
                JsonObject jsonObject1 = new JsonObject();
                jsonObject.addProperty("id", departmentHeadArrayList.get(currentPosition).getDepartmentHeadId());
                jsonObject.addProperty("perform", dele);
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
                            if (object == null || object.get("status").getAsInt() == 0) {
                                Toast.makeText(AdminDepartmentHeadfirstPage.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (object.get("status").getAsInt() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(AdminDepartmentHeadfirstPage.this, "Department head is deleted successfully...", Toast.LENGTH_SHORT).show();
                                getDepartmentHead();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AdminDepartmentHeadfirstPage.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
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

    private void editDeptHead(final int position) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(AdminDepartmentHeadfirstPage.this);
        currentPosition = position;

        builder.setTitle("Confirmation");
        String message = "Are you sure to change the Department head " + " ?";
        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /// TODO call Api here
                Intent abc = new Intent(AdminDepartmentHeadfirstPage.this, EditDepartHead.class);
                abc.putExtra("department_id", String.valueOf(departmentHeadArrayList.get(position).getDepartmentId()));
                abc.putExtra("old_departmenthead_id", String.valueOf(departmentHeadArrayList.get(position).getEmployeeId()));
                abc.putExtra("id", String.valueOf(departmentHeadArrayList.get(position).getDepartmentHeadId()));
                abc.putExtra("department_name", String.valueOf(departmentHeadArrayList.get(position).getDepartmentName()));
                abc.putExtra("dept_head_name", String.valueOf(departmentHeadArrayList.get(position).getDepartmentHeadName()));
                ArrayList deptnamelist = new ArrayList<>();
                ArrayList deptemplist = new ArrayList<Integer>();
                for (int i = 0; i < departmentHeadArrayList.size(); i++) {
                    if (departmentHeadArrayList.get(i).getDepartmentHeadStatus() == Constants.SUCCESS) {
                        deptnamelist.add(departmentHeadArrayList.get(i).getDepartmentName());
                        deptemplist.add(departmentHeadArrayList.get(i).getEmployeeId());
                    }
                }
                deptnamelist.removeAll(Collections.singleton(null));
                deptemplist.removeAll(Collections.singleton(null));
                abc.putExtra("dept_name_list", deptnamelist);
                abc.putExtra("dept_emp_list", deptemplist);
                startActivity(abc);

            }
        });
        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void assignDept(final int position) {
        try {
            currentPosition = position;
            // final ClassTeacher classTeacher = classTeacherArrayList.get(currentPosition);
            Intent abc = new Intent(AdminDepartmentHeadfirstPage.this, AssignDepartHead.class);
            abc.putExtra("department_id", String.valueOf(departmentHeadArrayList.get(currentPosition).getDepartmentId()));
            abc.putExtra("department_name", String.valueOf(departmentHeadArrayList.get(currentPosition).getDepartmentName()));
            abc.putExtra("dept_head_name", String.valueOf(departmentHeadArrayList.get(currentPosition).getDepartmentHeadName()));
            ArrayList deptnamelist = new ArrayList<>();
            ArrayList deptemplist = new ArrayList<Integer>();
            for (int i = 0; i < departmentHeadArrayList.size(); i++) {
                if (departmentHeadArrayList.get(i).getDepartmentHeadStatus() == Constants.SUCCESS) {
                    deptnamelist.add(departmentHeadArrayList.get(i).getDepartmentName());
                    deptemplist.add(departmentHeadArrayList.get(i).getEmployeeId());
                }
            }
            deptnamelist.removeAll(Collections.singleton(null));
            deptemplist.removeAll(Collections.singleton(null));
            abc.putExtra("dept_name_list", deptnamelist);
            abc.putExtra("dept_emp_list", deptemplist);
            startActivity(abc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        getDepartmentHead();
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
            startActivity(new Intent(AdminDepartmentHeadfirstPage.this, AdminLandingActivity.class));
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
        startActivity(new Intent(AdminDepartmentHeadfirstPage.this, AdminLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }


    private void getDepartmentHead() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().getdepthead(params, new Callback<DepartmentHeadResponse>() {

                    @Override
                    public void success(DepartmentHeadResponse departmentHeadResponse, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            if (departmentHeadResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                bindDataforDepartment(departmentHeadResponse.getDepartmentHead());

                            } else {
                                Toast.makeText(getApplicationContext(), "" + departmentHeadResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
