package com.maxlore.edumanage.Activities.AdminActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.SubstitutepagetwoAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.TeacherSubstitutePagetwo.SubstituteSubject;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.TeacherSubstitutePagetwo.SubstituteSubjectResponse;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SubstituteTeacherPagetwo extends AppCompatActivity {
    private RecyclerView rvSubjects;
    private SubstitutepagetwoAdapter substitutepagetwoAdapter;

    private String empid, sectionid, classid, subjectid, timetabledefinationid, structureid, timetabledayid;
    private int pos;
    private String dayOfTheWeek;
    private ArrayList<SubstituteSubject> substituteSubjectArrayList, finalSubstituteArraylist;
    private String id, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_substitute_teacher_pagetwo);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rvSubjects = (RecyclerView) findViewById(R.id.rvSubjects);

        substituteSubjectArrayList = new ArrayList<>();
        finalSubstituteArraylist = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvSubjects.setLayoutManager(llm);

        substitutepagetwoAdapter = new SubstitutepagetwoAdapter(this, substituteSubjectArrayList);
        rvSubjects.setAdapter(substitutepagetwoAdapter);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        dayOfTheWeek = sdf.format(d);



        try {
            Intent intent = getIntent();
            id = intent.getStringExtra("id");
            name = intent.getStringExtra("name");
            Log.e("id","=="+id);
            Log.e("id","=="+name);

        } catch (Exception e) {
            e.printStackTrace();
        }
        empid = getIntent().getStringExtra("employee id");
        substituteTeacherMethod();
        substitutepagetwoAdapter.SetOnItemClickListener(new SubstitutepagetwoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < substituteSubjectArrayList.size(); i++) {
                    sectionid = substituteSubjectArrayList.get(position).getSectionId();
                    classid = substituteSubjectArrayList.get(position).getClassId();
                    subjectid = substituteSubjectArrayList.get(position).getSubjectId();
                    timetabledefinationid = substituteSubjectArrayList.get(position).getTimetableDefinationId();
                    structureid = substituteSubjectArrayList.get(position).getStructureId();
                    timetabledayid = substituteSubjectArrayList.get(position).getTimetableDayId();
                }
                Intent abc = new Intent(SubstituteTeacherPagetwo.this, SubstituteTeacherPageThree.class);
                if (id==null) {
                    abc.putExtra("section id", sectionid);
                    abc.putExtra("class id", classid);
                    abc.putExtra("subject id", subjectid);
                    abc.putExtra("timetabledefination id", timetabledefinationid);
                    abc.putExtra("structure id", structureid);
                    abc.putExtra("timetableday id", timetabledayid);
                    abc.putExtra("emp id", empid);
                    abc.putExtra("standardname", substituteSubjectArrayList.get(position).getStandard());
                    abc.putExtra("sectionname", substituteSubjectArrayList.get(position).getSection());
                    abc.putExtra("period", substituteSubjectArrayList.get(position).getPeriod());
                    abc.putExtra("start_time", substituteSubjectArrayList.get(position).getFromTime());
                    abc.putExtra("end_time", substituteSubjectArrayList.get(position).getToTime());

                } else {
                    abc.putExtra("section id", sectionid);
                    abc.putExtra("class id", classid);
                    abc.putExtra("subject id", subjectid);
                    abc.putExtra("timetabledefination id", timetabledefinationid);
                    abc.putExtra("structure id", structureid);
                    abc.putExtra("timetableday id", timetabledayid);
                    abc.putExtra("emp id", empid);
                    abc.putExtra("standardname", substituteSubjectArrayList.get(position).getStandard());
                    abc.putExtra("sectionname", substituteSubjectArrayList.get(position).getSection());
                    abc.putExtra("period", substituteSubjectArrayList.get(position).getPeriod());
                    abc.putExtra("start_time", substituteSubjectArrayList.get(position).getFromTime());
                    abc.putExtra("end_time", substituteSubjectArrayList.get(position).getToTime());

                    abc.putExtra("id", id);
                    abc.putExtra("name", name);
                }
                startActivity(abc);
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

    private void substituteTeacherMethod() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("employee_id", empid);
                jsonObject.addProperty("day", dayOfTheWeek);

                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().SubstituteTeacherClasses(jsonObject, new Callback<SubstituteSubjectResponse>() {
                    @Override
                    public void success(SubstituteSubjectResponse substituteSubjectResponse, Response response) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Log.e("jsonObject", "jsonObject --- " + substituteSubjectResponse.toString());

                        try {
                            if (substituteSubjectResponse == null) {
                                Toast.makeText(SubstituteTeacherPagetwo.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (substituteSubjectResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                substituteSubjectArrayList.addAll(substituteSubjectResponse.getTodayTimetableList());
                                substitutepagetwoAdapter.notifyDataSetChanged();
                                Toast.makeText(SubstituteTeacherPagetwo.this, substituteSubjectResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(SubstituteTeacherPagetwo.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
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