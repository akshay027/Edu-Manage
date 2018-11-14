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
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.SubstituteTeacherListAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.TeacherSubstitutePagetwo.SubstituteTeacher;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.TeacherSubstitutePagetwo.SubstituteTeacherResponse;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SubstituteTeacherPageThree extends AppCompatActivity {
    private RecyclerView rvteachersavailable;
    private SubstituteTeacherListAdapter substituteTeacherListAdapter;
    private ArrayList<SubstituteTeacher> FinalSubstituteTeacherArraylist, optionalTeacherArrayList, substituteTeacherArrayList;
    private String empid, sectionid, classid, subjectid, timetabledefinationid, structureid, timetabledayid, standardname, sectionname, period, starttime, endtime;
    private String dayOfTheWeek;
    private int a, b;
    private String date, id, name;
    private int assignpos;
    private TextView Noteacher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_substitute_teacher_page_three);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvteachersavailable = (RecyclerView) findViewById(R.id.rvteachersavailable);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvteachersavailable.setLayoutManager(llm);

        Noteacher = (TextView) findViewById(R.id.Noteacher);
        substituteTeacherArrayList = new ArrayList<>();
        optionalTeacherArrayList = new ArrayList<>();

        FinalSubstituteTeacherArraylist = new ArrayList<>();
        sectionid = getIntent().getStringExtra("section id");
        classid = getIntent().getStringExtra("class id");
        subjectid = getIntent().getStringExtra("subject id");
        timetabledefinationid = getIntent().getStringExtra("timetabledefination id");
        structureid = getIntent().getStringExtra("structure id");
        timetabledayid = getIntent().getStringExtra("timetableday id");
        empid = getIntent().getStringExtra("emp id");
        standardname = getIntent().getStringExtra("standardname");
        sectionname = getIntent().getStringExtra("sectionname");
        period = getIntent().getStringExtra("period");
        starttime = getIntent().getStringExtra("start_time");
        endtime = getIntent().getStringExtra("end_time");

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");

        a = Integer.parseInt(sectionid);
        b = Integer.parseInt(subjectid);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        dayOfTheWeek = sdf.format(d);
        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        substituteTeacherListAdapter = new SubstituteTeacherListAdapter(this, substituteTeacherArrayList, a, b);
        rvteachersavailable.setAdapter(substituteTeacherListAdapter);
        substituteTeacherListAdapter.SetOnItemClickListener(new SubstituteTeacherListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                assignpos = position;

                clearSelection(position);
                assignTeacher();
                substituteTeacherListAdapter.notifyDataSetChanged();
            }


        });
        TeacherList();
    }

    private void clearSelection(int position) {
        for (int i = 0; i < substituteTeacherArrayList.size(); i++) {
            if (position == i) {
                substituteTeacherArrayList.get(i).setSelected(true);
            } else {
                substituteTeacherArrayList.get(i).setSelected(false);
            }
        }
    }

    private void assignTeacher() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setTitle("Confirmation");
        String message = "Are you sure " + " ?";
        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /// TODO call Api here
                SubTeacher();
            }
        });
        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                substituteTeacherListAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void SubTeacher() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                JsonObject jsonObject = new JsonObject();
                JsonObject jsonObject1 = new JsonObject();
                jsonObject1.addProperty("employee_id", empid);
                jsonObject1.addProperty("class_id", classid);
                jsonObject1.addProperty("section_id", sectionid);
                jsonObject1.addProperty("period_id", timetabledefinationid);
                jsonObject1.addProperty("subject_id", subjectid);
                jsonObject1.addProperty("subs_date", date);
                jsonObject1.addProperty("substitute_employee_id", substituteTeacherArrayList.get(assignpos).getEmployeeId());
                jsonObject.add("substitute_teacher", jsonObject1);
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
          /*  for (int i = 0; i < substituteTeacherArrayList.size(); i++) {
                if (substituteTeacherArrayList.get(i).getTeachersubstitutionid() != null) {
                    jsonObject.addProperty("teacher_substitution_id", substituteTeacherArrayList.get(i).getTeachersubstitutionid());
                }

            }*/
                RetrofitAPI.getInstance(this).getApi().SubTeacher(jsonObject, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject object, Response response) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Log.e("jsonObject", "jsonObject --- " + object.toString());

                        try {
                            if (object == null) {
                                Toast.makeText(SubstituteTeacherPageThree.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (object.get("status").getAsInt() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(SubstituteTeacherPageThree.this, "Teacher assigned successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SubstituteTeacherPageThree.this, AssignSubstituteTeacherActivity.class);
                                intent.putExtra("name", substituteTeacherArrayList.get(assignpos).getEmployeeFirstName().toString());
                                intent.putExtra("id", substituteTeacherArrayList.get(assignpos).getEmployeeId().toString());
                                startActivity(intent);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(SubstituteTeacherPageThree.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Toast.makeText(SubstituteTeacherPageThree.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
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


    private void TeacherList() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("section_id", sectionid);
                jsonObject.addProperty("employee_id", empid);
                jsonObject.addProperty("subject_id", subjectid);

                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }

                RetrofitAPI.getInstance(this).getApi().TeacherList(jsonObject, new Callback<SubstituteTeacherResponse>() {
                    @Override
                    public void success(SubstituteTeacherResponse substituteTeacherResponse, Response response) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Log.e("jsonObject", "jsonObject --- " + substituteTeacherResponse.toString());
                        try {
                            if (substituteTeacherResponse == null) {
                                Toast.makeText(SubstituteTeacherPageThree.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (substituteTeacherResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                substituteTeacherArrayList.clear();
                                substituteTeacherArrayList.addAll(substituteTeacherResponse.getSubtituteTeacherList());
                                optionalTeacherArrayList.addAll(substituteTeacherResponse.getSubtituteTeacherList());
                                if(id==null){
                               }
                                else {
                                    for (int i = 0; i < optionalTeacherArrayList.size(); i++) {
                                        if (id.equalsIgnoreCase(optionalTeacherArrayList.get(i).getEmployeeId())) {
                                            substituteTeacherArrayList.remove(substituteTeacherArrayList.get(i));
                                            Log.e("data", "====" + id.equals(optionalTeacherArrayList.get(i).getEmployeeId()));
                                        }
                                    }
                                }
                                Log.e("data1", "====" +optionalTeacherArrayList.get(0).getEmployeeId());
                                Log.e("45gfh", "====" + id);
                               // Log.e("data", "====" + id.equals(optionalTeacherArrayList.get(0).getEmployeeId()));

                                if (substituteTeacherArrayList.size() <= 0) {
                                    Noteacher.setVisibility(View.VISIBLE);
                                    rvteachersavailable.setVisibility(View.GONE);
                                } else {
                                    Noteacher.setVisibility(View.GONE);
                                    rvteachersavailable.setVisibility(View.VISIBLE);
                                }
                                substituteTeacherListAdapter.notifyDataSetChanged();
                                Toast.makeText(SubstituteTeacherPageThree.this, substituteTeacherResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(SubstituteTeacherPageThree.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
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
