package com.maxlore.edumanage.Activities.TeacherActivities;

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

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.OwnAtt.EmployeeDayAttendanceDetail;
import com.maxlore.edumanage.Models.TeacherModels.OwnAtt.OwnAttResponse;

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
import com.maxlore.edumanage.R;
public class OwnAttendanceActivity extends AppCompatActivity {
    private CompactCalendarView compactCalendarView;
    private TextView monthtv;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
    private SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM", Locale.getDefault());
    private SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy", Locale.getDefault());
    private String monthsen, yearsen;
    private ArrayList<EmployeeDayAttendanceDetail> employeeDayAttendanceDetailsArrayList;
    private String date;
    private long milliseconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_attendance);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        monthtv = (TextView) findViewById(R.id.monthtvown);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_viewown);

        employeeDayAttendanceDetailsArrayList = new ArrayList();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);

        monthtv.setText(simpleDateFormat.format(cal.getTime()));

        monthsen = simpleDateFormat1.format(cal.getTime());
        yearsen = simpleDateFormat2.format(cal.getTime());
        compactCalendarView.setUseThreeLetterAbbreviation(true);
         /*compactCalendarViewpm.setUseThreeLetterAbbreviation(true);*/
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
        getAttendanceDetail();
    }

    private void getAttendanceDetail() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("month", monthsen);
                params.put("year", yearsen);
                params.put("user", "Employee");
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().getemployeeAttendanceDetails(params, new Callback<OwnAttResponse>() {
                    @Override
                    public void success(OwnAttResponse newAttendanceResponseTeacher, Response response) {
                        try {
                            if (newAttendanceResponseTeacher.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                bindData(newAttendanceResponseTeacher);

                            } else {
                                Toast.makeText(getApplicationContext(), "" + newAttendanceResponseTeacher.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "Connect to internet.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindData(OwnAttResponse newAttendanceResponseTeacher) {
        employeeDayAttendanceDetailsArrayList.addAll(newAttendanceResponseTeacher.getEmployeeDayAttendanceDetail());
        Log.e("hibshbsjhbsjhbs", employeeDayAttendanceDetailsArrayList + "");
        markingAttendanceforam();

    }

    private void markingAttendanceforam() {
        try {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < employeeDayAttendanceDetailsArrayList.size(); i++) {
                        date = employeeDayAttendanceDetailsArrayList.get(i).getDate();
                        SimpleDateFormat f = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                        Date d = new Date();
                        try {
                            d = f.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        milliseconds = d.getTime();
                        if (employeeDayAttendanceDetailsArrayList.get(i).getAttendanceStatus().equals(Constants.PRESENT)) {
                            Event ev1 = new Event(Color.GREEN, milliseconds);
                            Log.d("TAG", "llllllllllllllllll    LLLLL: " + employeeDayAttendanceDetailsArrayList.get(i).getAttendanceStatus());
                            compactCalendarView.addEvent(ev1, true);
                        } else if (employeeDayAttendanceDetailsArrayList.get(i).getAttendanceStatus().equals(Constants.ABSENT)) {
                            Event ev1 = new Event(Color.RED, milliseconds);
                            compactCalendarView.addEvent(ev1, true);
                        } else if (employeeDayAttendanceDetailsArrayList.get(i).getAttendanceStatus().equals(Constants.HALFDAYATTEN)) {
                            Event ev1 = new Event(Color.YELLOW, milliseconds);
                            compactCalendarView.addEvent(ev1, true);
                        } else if (employeeDayAttendanceDetailsArrayList.get(i).getAttendanceStatus().equals(Constants.NONWORKINGDAY)) {
                            Event ev1 = new Event(Color.GRAY, milliseconds);
                            compactCalendarView.addEvent(ev1, true);
                        } else if (employeeDayAttendanceDetailsArrayList.get(i).getAttendanceStatus().equals(Constants.FUTUREDATE)) {
                            Event ev1 = new Event(Color.CYAN, milliseconds);
                            compactCalendarView.addEvent(ev1, true);
                        } else if (employeeDayAttendanceDetailsArrayList.get(i).getAttendanceStatus().equals(Constants.SCHOOLHOLIDAY)) {
                            Event ev1 = new Event(Color.MAGENTA, milliseconds);
                            compactCalendarView.addEvent(ev1, true);
                        } else if (employeeDayAttendanceDetailsArrayList.get(i).getAttendanceStatus().equals(Constants.LEAVEDAY)) {
                            Event ev1 = new Event(Color.BLUE, milliseconds);
                            compactCalendarView.addEvent(ev1, true);
                        } else if (employeeDayAttendanceDetailsArrayList.get(i).getAttendanceStatus().equals(Constants.STUDENTTIMETABLENOTASSIGN)) {
                            Event ev1 = new Event(Color.BLACK, milliseconds);
                            compactCalendarView.addEvent(ev1, true);
                        }
                    }
                }
            }, 100);
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
            startActivity(new Intent(OwnAttendanceActivity.this, TeacherLandingActivity.class));
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
        startActivity(new Intent(OwnAttendanceActivity.this, TeacherLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }

}
