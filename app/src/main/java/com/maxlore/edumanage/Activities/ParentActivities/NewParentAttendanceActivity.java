package com.maxlore.edumanage.Activities.ParentActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.ParentModels.Attnew.NewAttendanceResponseParent;
import com.maxlore.edumanage.Models.ParentModels.Attnew.NewStudentDayAttendanceDetail;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewParentAttendanceActivity extends AppCompatActivity {
    private CompactCalendarView compactCalendarView, compactCalendarViewpm;
    private TextView monthtv, monthtvpm;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
    private SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM", Locale.getDefault());
    private SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy", Locale.getDefault());
    private String monthsen, yearsen;
    private ArrayList<NewAttendanceResponseParent> newAttendanceResponseParentArrayList;
    private ArrayList<NewStudentDayAttendanceDetail> newStudentDayAttendanceDetailArrayList;
    private String date;
    private long milliseconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_parent_attendance);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        monthtv = (TextView) findViewById(R.id.monthtv);
        monthtvpm = (TextView) findViewById(R.id.monthtvpm);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarViewpm = (CompactCalendarView) findViewById(R.id.compactcalendar_viewpm);

        newStudentDayAttendanceDetailArrayList = new ArrayList();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);

        monthtv.setText(simpleDateFormat.format(cal.getTime()));
        monthtvpm.setText(simpleDateFormat.format(cal.getTime()));

        monthsen = simpleDateFormat1.format(cal.getTime());
        yearsen = simpleDateFormat2.format(cal.getTime());

        getAttendanceDetail();

        compactCalendarView.setUseThreeLetterAbbreviation(true);
         /*compactCalendarViewpm.setUseThreeLetterAbbreviation(true);*/
        getAttendanceDetail();

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {


            @Override
            public void onDayClick(Date dateClicked) {

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthtv.setText(simpleDateFormat.format(firstDayOfNewMonth.getTime()));
                monthsen = simpleDateFormat1.format(firstDayOfNewMonth.getTime());
                yearsen = simpleDateFormat2.format(firstDayOfNewMonth.getTime());
                Log.d("TAG", "Month was scrolled to: " + firstDayOfNewMonth);
                getAttendanceDetail();
            }
        });
        compactCalendarViewpm.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthtvpm.setText(simpleDateFormat.format(firstDayOfNewMonth.getTime()));
                monthsen = simpleDateFormat1.format(firstDayOfNewMonth.getTime());
                yearsen = simpleDateFormat2.format(firstDayOfNewMonth.getTime());
                Log.d("TAG", "Month was scrolled to: " + firstDayOfNewMonth);
                getAttendanceDetail();
            }
        });

    }

    private void getAttendanceDetail() {
       try{ if (UIUtil.isInternetAvailable(this)) {
            UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
            Map<String, String> params = new HashMap<String, String>();
            params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
            params.put("month", monthsen);
            params.put("year", yearsen);
            params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
            params.put("user", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE));
            RetrofitAPI.getInstance(this).getApi().getStudentAttendanceDetails(params, new Callback<NewAttendanceResponseParent>() {
                @Override
                public void success(NewAttendanceResponseParent newAttendanceResponseParent, Response response) {
try{
                    if (newAttendanceResponseParent.getStatus() == Constants.SUCCESS) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        if (newAttendanceResponseParent.getAttendanceType()) {
                            compactCalendarViewpm.setVisibility(View.GONE);
                        } else {
                            compactCalendarViewpm.setVisibility(View.VISIBLE);
                        }
                        newStudentDayAttendanceDetailArrayList.addAll(newAttendanceResponseParent.getStudentDayAttendanceDetail());
                        if (newAttendanceResponseParent.getAttendanceType()) {
                            markingattendanceforamonce();
                        } else {
                            markingattendanceforam();
                        }

                    } else {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Toast.makeText(getApplicationContext(), "" + newAttendanceResponseParent.getMessage(), Toast.LENGTH_SHORT).show();
                    }} catch (Exception e) {
    e.printStackTrace();
}
                }

                @Override
                public void failure(RetrofitError error) {
                    UIUtil.stopProgressDialog(getApplicationContext());
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Connect to internet.", Toast.LENGTH_LONG).show();
        }} catch (Exception e) {
           e.printStackTrace();
       }
    }

/*    private void binddata(NewAttendanceResponseParent newAttendanceResponseParent) {
        Log.e("sjhbhjfsbhjfdsbhjdsf   :", String.valueOf(newAttendanceResponseParent.getStudentDayAttendanceDetail()));
        newStudentDayAttendanceDetailArrayList.addAll(newAttendanceResponseParent.getStudentDayAttendanceDetail());
        if (newAttendanceResponseParent.getAttendanceType()) {
            markingattendanceforamonce();
        } else {
            markingattendanceforam();
        }
    }*/

    private void markingattendanceforam() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < newStudentDayAttendanceDetailArrayList.size(); i++) {
                    date = newStudentDayAttendanceDetailArrayList.get(i).getDate();
                    SimpleDateFormat f = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                    Date d = new Date();
                    try {
                        d = f.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    milliseconds = d.getTime();
                    if (newStudentDayAttendanceDetailArrayList.get(i).getAm().equals(Constants.PRESENT)) {
                        Event ev1 = new Event(Color.GREEN, milliseconds);
                        Log.d("TAG", "llllllllllllllllll    LLLLL: " + newStudentDayAttendanceDetailArrayList.get(i).getAm());
                        compactCalendarView.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getAm().equals(Constants.ABSENT)) {
                        Event ev1 = new Event(Color.RED, milliseconds);
                        compactCalendarView.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getAm().equals(Constants.HALFDAYATTEN)) {
                        Event ev1 = new Event(Color.YELLOW, milliseconds);
                        compactCalendarView.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getAm().equals(Constants.NONWORKINGDAY)) {
                        Event ev1 = new Event(Color.GRAY, milliseconds);
                        compactCalendarView.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getAm().equals(Constants.FUTUREDATE)) {
                        Event ev1 = new Event(Color.CYAN, milliseconds);
                        compactCalendarView.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getAm().equals(Constants.SCHOOLHOLIDAY)) {
                        Event ev1 = new Event(Color.MAGENTA, milliseconds);
                        compactCalendarView.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getAm().equals(Constants.LEAVEDAY)) {
                        Event ev1 = new Event(Color.BLUE, milliseconds);
                        compactCalendarView.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getAm().equals(Constants.STUDENTTIMETABLENOTASSIGN)) {
                        Event ev1 = new Event(Color.BLACK, milliseconds);
                        compactCalendarView.addEvent(ev1,true);
                    }
                }
            }
        }, 100);
        markingattenanceforpm();
    }

    private void markingattendanceforamonce() {
      try{  monthtvpm.setVisibility(View.GONE);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < newStudentDayAttendanceDetailArrayList.size(); i++) {
                    date = newStudentDayAttendanceDetailArrayList.get(i).getDate();
                    SimpleDateFormat f = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                    Date d = new Date();
                    try {
                        d = f.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    milliseconds = d.getTime();
                    if (newStudentDayAttendanceDetailArrayList.get(i).getAm().equals(Constants.PRESENT)) {
                        Event ev1 = new Event(Color.GREEN, milliseconds);
                        Log.d("TAG", "llllllllllllllllll    LLLLL: " + newStudentDayAttendanceDetailArrayList.get(i).getAm());
                        compactCalendarView.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getAm().equals(Constants.ABSENT)) {
                        Event ev1 = new Event(Color.RED, milliseconds);
                        compactCalendarView.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getAm().equals(Constants.HALFDAYATTEN)) {
                        Event ev1 = new Event(Color.YELLOW, milliseconds);
                        compactCalendarView.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getAm().equals(Constants.NONWORKINGDAY)) {
                        Event ev1 = new Event(Color.GRAY, milliseconds);
                        compactCalendarView.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getAm().equals(Constants.FUTUREDATE)) {
                        Event ev1 = new Event(Color.CYAN, milliseconds);
                        compactCalendarView.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getAm().equals(Constants.SCHOOLHOLIDAY)) {
                        Event ev1 = new Event(Color.MAGENTA, milliseconds);
                        compactCalendarView.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getAm().equals(Constants.LEAVEDAY)) {
                        Event ev1 = new Event(Color.BLUE, milliseconds);
                        compactCalendarView.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getAm().equals(Constants.STUDENTTIMETABLENOTASSIGN)) {
                        Event ev1 = new Event(Color.BLACK, milliseconds);
                        compactCalendarView.addEvent(ev1,true);
                    }
                }
            }
        }, 100);} catch (Exception e) {
          e.printStackTrace();
      }
    }

    private void markingattenanceforpm() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < newStudentDayAttendanceDetailArrayList.size(); i++) {
                    date = newStudentDayAttendanceDetailArrayList.get(i).getDate();
                    SimpleDateFormat f = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                    Date d = new Date();
                    try {
                        d = f.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    milliseconds = d.getTime();
                    if (newStudentDayAttendanceDetailArrayList.get(i).getPm().equals(Constants.PRESENT)) {
                        Event ev1 = new Event(Color.GREEN, milliseconds);
                        Log.d("TAG", "llllllllllllllllll    LLLLL: " + newStudentDayAttendanceDetailArrayList.get(i).getAm());
                        compactCalendarViewpm.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getPm().equals(Constants.ABSENT)) {
                        Event ev1 = new Event(Color.RED, milliseconds);
                        compactCalendarViewpm.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getPm().equals(Constants.HALFDAYATTEN)) {
                        Event ev1 = new Event(Color.YELLOW, milliseconds);
                        compactCalendarViewpm.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getPm().equals(Constants.NONWORKINGDAY)) {
                        Event ev1 = new Event(Color.GRAY, milliseconds);
                        compactCalendarViewpm.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getPm().equals(Constants.FUTUREDATE)) {
                        Event ev1 = new Event(Color.CYAN, milliseconds);
                        compactCalendarViewpm.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getPm().equals(Constants.SCHOOLHOLIDAY)) {
                        Event ev1 = new Event(Color.MAGENTA, milliseconds);
                        compactCalendarViewpm.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getPm().equals(Constants.LEAVEDAY)) {
                        Event ev1 = new Event(Color.BLUE, milliseconds);
                        compactCalendarViewpm.addEvent(ev1,true);
                    } else if (newStudentDayAttendanceDetailArrayList.get(i).getPm().equals(Constants.STUDENTTIMETABLENOTASSIGN)) {
                        Event ev1 = new Event(Color.BLACK, milliseconds);
                        compactCalendarViewpm.addEvent(ev1,true);
                    }
                }
            }
        }, 100);
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
            startActivity(new Intent(NewParentAttendanceActivity.this, ParentLandingActivity.class));
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
        startActivity(new Intent(NewParentAttendanceActivity.this, ParentLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }


}
