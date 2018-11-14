package com.maxlore.edumanage.Activities.TeacherActivities;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.TeacherAdapters.MonthlySecondPageAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.Academics.DailyTopic;
import com.maxlore.edumanage.Models.TeacherModels.Academics.DailyTopicResponse;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MonthlySecondPageActivity extends AppCompatActivity {
    private RecyclerView subtopicrecycler;
    private Button btn_subtopic;
    private TextView btn_update;
    private String monthname, topicno, topicname, week, subtopicno, subtopicname, subtopicid, monthid, datestart, dateend;
    private int weekid;
    private ArrayList<DailyTopic> dailyTopicsarrayList;
    private MonthlySecondPageAdapter monthlySecondPageAdapter;
    private long mindate, maxdate;
    private TextView tv_monthname, tv_topicnodetail, tv_topicnamedetail, tv_weekname,
            tv_subtopicnodetail, tv_subtopicnamedetail, nodata2;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_second_page);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        subtopicrecycler = (RecyclerView) findViewById(R.id.subtopicrecycler);
        nodata2 = (TextView) findViewById(R.id.nodata2);
        tv_monthname = (TextView) findViewById(R.id.tv_monthname);
        /*tv_topicnodetail = (TextView) findViewById(R.id.tv_topicnodetail);*/
        tv_topicnamedetail = (TextView) findViewById(R.id.tv_topicnamedetail);
        tv_weekname = (TextView) findViewById(R.id.tv_weekname);
      /*  tv_subtopicnodetail = (TextView) findViewById(R.id.tv_subtopicnodetail);*/
        tv_subtopicnamedetail = (TextView) findViewById(R.id.tv_subtopicnamedetail);

        btn_subtopic = (Button) findViewById(R.id.btn_subtopic);
        btn_update = (TextView) findViewById(R.id.btn_update);
        dailyTopicsarrayList = new ArrayList<>();


        subtopicrecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        subtopicrecycler.setLayoutManager(llm);

        monthlySecondPageAdapter = new MonthlySecondPageAdapter(this, dailyTopicsarrayList);
        subtopicrecycler.setHasFixedSize(true);
        subtopicrecycler.setAdapter(monthlySecondPageAdapter);
        monthlySecondPageAdapter.notifyDataSetChanged();

        btn_subtopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyTopic dailyTopic = new DailyTopic();
                dailyTopicsarrayList.add(dailyTopic);
                monthlySecondPageAdapter.notifyDataSetChanged();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSecondpagedata();
            }
        });

        monthlySecondPageAdapter.SetOnItemClickListener(new MonthlySecondPageAdapter.OnItemClickListener() {
            @Override
            public void onCancel(View view, int position) {
                dailyTopicsarrayList.remove(position);
                monthlySecondPageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onAddSubTopic(String s, int position) {
                Log.e("hgvadhvdajhv  :", dailyTopicsarrayList.toString());
                dailyTopicsarrayList.get(position).setLessonName(s);

            }

            @Override
            public void onDateSelect(View view, int position) {
                selectDatepicker(position);
            }


        });
        try {
            Intent intent = getIntent();
            monthname = getIntent().getStringExtra("month");
            topicno = getIntent().getStringExtra("topicno");
            topicname = getIntent().getStringExtra("topicname");
            week = getIntent().getStringExtra("week");
            subtopicno = getIntent().getStringExtra("subtopicno");
            subtopicname = getIntent().getStringExtra("subtopicname");
            subtopicid = getIntent().getStringExtra("subtopicid");
            weekid = getIntent().getIntExtra("weekid", 0);
            monthid = getIntent().getStringExtra("monthid");

        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_monthname.setText(monthname);
        tv_topicnamedetail.setText(topicname);
        tv_weekname.setText(week);
        tv_subtopicnamedetail.setText(subtopicname);

        sendAcademics();
    }

    public void sendAcademics() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("sub_topic_id", subtopicid);
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                Log.e("Json", "JSON ----- " + jsonObject.toString());

                RetrofitAPI.getInstance(this).getApi().secondpagedata(jsonObject, new Callback<DailyTopicResponse>() {

                    @Override
                    public void success(DailyTopicResponse dailyTopicResponse, Response response) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        if (dailyTopicResponse.getStatus() == Constants.SUCCESS) {
                            dailyTopicsarrayList.addAll(dailyTopicResponse.getDailyTopics());
                            monthlySecondPageAdapter.notifyDataSetChanged();
                            datestart = dailyTopicResponse.getStartDate();
                            dateend = dailyTopicResponse.getEndDate();
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = sdf.parse(datestart);
                                Date date1 = sdf.parse(dateend);
                                mindate = date.getTime();
                                maxdate = date1.getTime();

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "" + dailyTopicResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void selectDatepicker(final int currentpos) {
        Calendar newCalendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(newCalendar.getTime());

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                String month, day;
                monthOfYear = monthOfYear + 1;
                if (monthOfYear < 10) {
                    month = "0" + String.valueOf(monthOfYear);
                } else {
                    month = String.valueOf(monthOfYear);
                }

                if (dayOfMonth < 10) {
                    day = "0" + String.valueOf(dayOfMonth);
                } else {
                    day = String.valueOf(dayOfMonth);
                }

                Log.e("Date", " Text --" + day + "/" + month + "/" + String.valueOf(year));
                dailyTopicsarrayList.get(currentpos).setDate(day + "-" + month + "-" + String.valueOf(year));
                monthlySecondPageAdapter.notifyDataSetChanged();
//                edDOB.setText(String.valueOf(year) + "-" + month + "-" + day);
//                tvDob.setText(newDate.toString());

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(maxdate);
        datePickerDialog.getDatePicker().setMinDate(mindate);
        datePickerDialog.show();
    }

    private JsonArray getSelectedSubjects() {

        JsonArray array = new JsonArray();
        for (int i = 0; i < dailyTopicsarrayList.size(); i++) {
            JsonObject jsonElements = new JsonObject();
            jsonElements.addProperty("daily_plan_id", dailyTopicsarrayList.get(i).getDailyPlanId());
            jsonElements.addProperty("date", dailyTopicsarrayList.get(i).getDate());
            jsonElements.addProperty("lesson_name", dailyTopicsarrayList.get(i).getLessonName());

            array.add(jsonElements);
        }
        return array;
    }

    public void onSecondpagedata() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("sub_topic_id", subtopicid);
                jsonObject.addProperty("month_id", subtopicid);
                jsonObject.addProperty("week_id", weekid);
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                jsonObject.add("daily_plan", getSelectedSubjects());
                Log.e("Json", "JSON ----- " + jsonObject.toString());

                RetrofitAPI.getInstance(this).getApi().createDailyplan(jsonObject, new Callback<DailyTopicResponse>() {


                    @Override
                    public void success(DailyTopicResponse dailyTopicResponse, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            if (dailyTopicResponse.getStatus() == Constants.SUCCESS) {
                                Toast.makeText(getApplicationContext(), "" + dailyTopicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(getApplicationContext(), "" + dailyTopicResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
            alertboxpopup();
        }

        return super.onOptionsItemSelected(item);
    }

    private AlertDialog alertboxpopup() {
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