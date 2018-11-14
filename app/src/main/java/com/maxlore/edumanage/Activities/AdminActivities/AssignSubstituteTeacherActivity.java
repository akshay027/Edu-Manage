package com.maxlore.edumanage.Activities.AdminActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.SubstituteTeacherPageone;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.SubstituteTeacher.AbsentResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.SubstituteTeacher.Absentlist;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AssignSubstituteTeacherActivity extends AppCompatActivity {
    private RecyclerView rv_teacherAbsent;
    private ArrayList<Absentlist> absentlistArrayList, finalArraylist;
    private SubstituteTeacherPageone substituteTeacherPageone;
    private ArrayAdapter<String> adapter;
    private String selectedCategory = "";
    private ArrayList responseArraylist;
    private int result;
    private TextView errorsub;
    private String empid;
    private Handler handler;
    private int currentPosition;
    public static final int TIME_OUT = 1000;
    private String id, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_substitute_teacher);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rv_teacherAbsent = (RecyclerView) findViewById(R.id.rv_teacherAbsent);
        errorsub = (TextView) findViewById(R.id.errorsub);
        absentlistArrayList = new ArrayList();
        finalArraylist = new ArrayList<>();
        responseArraylist = new ArrayList();

        try {
            Intent intent = getIntent();
            id = intent.getStringExtra("id");
            name = intent.getStringExtra("name");
            Log.e("id", "==" + id);
            Log.e("id", "==" + name);

        } catch (Exception e) {
            e.printStackTrace();
        }

        rv_teacherAbsent.setHasFixedSize(true);
        rv_teacherAbsent.setLayoutManager(new LinearLayoutManager(this));
        substituteTeacherPageone = new SubstituteTeacherPageone(this, absentlistArrayList);
        rv_teacherAbsent.setAdapter(substituteTeacherPageone);
        substituteTeacherPageone.notifyDataSetChanged();
        substituteTeacherPageone.SetOnItemClickListener(new SubstituteTeacherPageone.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                assignTeacher(position);
            }
        });
        getAbsentTeacherDetail();

    }

    private void assignTeacher(final int position) {
        try {
            currentPosition = position;
            for (int i = 0; i < absentlistArrayList.size(); i++) {
                empid = String.valueOf(absentlistArrayList.get(position).getEmployeeId());
            }
            // final ClassTeacher classTeacher = classTeacherArrayList.get(currentPosition);
            Intent abc = new Intent(AssignSubstituteTeacherActivity.this, SubstituteTeacherPagetwo.class);
            abc.putExtra("employee id", empid);
            if (id == null) {
                abc.putExtra("employee id", empid);
            } else {
                abc.putExtra("employee id", empid);
                abc.putExtra("id", id);
                abc.putExtra("name", name);
            }
            startActivity(abc);
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
            startActivity(new Intent(AssignSubstituteTeacherActivity.this, AdminLandingActivity.class));
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
        startActivity(new Intent(AssignSubstituteTeacherActivity.this, AdminLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }


    private void getAbsentTeacherDetail() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Fetch Details For Substitute Teacher");
                Map<String, String> params = new HashMap<String, String>();
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().getAbsentTeacherDetail(params, new Callback<AbsentResponse>() {
                    @Override
                    public void success(AbsentResponse absentResponse, Response response) {

                        UIUtil.stopProgressDialog(getApplicationContext());
                        Log.e("Json ", "Hhd --- " + absentResponse.toString());
                        Log.e("Json ", "Response --- " + response.getBody());
                        try {
                            if (absentResponse.getStatus() == Constants.SUCCESS) {
                                errorsub.setVisibility(View.GONE);
                                rv_teacherAbsent.setVisibility(View.VISIBLE);
                                UIUtil.stopProgressDialog(getApplicationContext());
                                absentlistArrayList.addAll(absentResponse.getEmployeeList());
                                substituteTeacherPageone.notifyDataSetChanged();

                            } else {
                                Toast.makeText(getApplicationContext(), "" + absentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                errorsub.setVisibility(View.VISIBLE);
                                rv_teacherAbsent.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.e("Json ", "Hhd --- " + absentResponse.toString());
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
/*    private void onEmployeeClick() {
        if (UIUtil.isInternetAvailable(this)) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("employee_id", employeeCategories.get(result).getEmployeeId());

            RetrofitAPI.getInstance(this).getApi().onEmployeeClick(jsonObject, new Callback<JsonObject>() {
                @Override
                public void success(JsonObject object, Response response) {
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

                }
            });
        } else {

            Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
        }
    }*/
}
