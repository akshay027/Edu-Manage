package com.maxlore.edumanage.Activities.AdminActivities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Activities.AdminActivities.graph.BarUtility;
import com.maxlore.edumanage.Activities.TeacherActivities.LoginActivity;
import com.maxlore.edumanage.Activities.TeacherActivities.TeacherRollActivity;
import com.maxlore.edumanage.Adapters.TeacherAdapters.AnnouncementAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminDashBoard.AdminDashboard;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminDashBoard.AdminDashboardResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.CalenderDateResponse;
import com.maxlore.edumanage.Models.TeacherModels.Announcement;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.orm.SugarContext;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AdminLandingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tvTransfer, tvabsentstudent, tvResign, tvabsentteacher, tvTotalStudents, tvTotalEmployee, tvTotalParents, studentCount, teacherCount,
            nonteacherCount;
    private ListView lvAdminAnnouncement;
    private ArrayList<Announcement> announcementArrayList;
    private AnnouncementAdapter announcementAdapter;
    private TextView tvErrorAnn;
    private BarChart mChart;
    private Date d;
    private TextView tvUserName, tvEmailId, monthtv, branchname;
    private ImageView imageview;
    private LinearLayout absentstudentlv, lvtc, resignationlv, absentteacherlv;
    private CompactCalendarView compactCalendarView;
    private Long fromDate;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
    private ArrayList<String> dates;
    long milliseconds;
    private String month, year;
    private LinearLayout qmap_1, qmap_2, qmap_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        monthtv = (TextView) findViewById(R.id.monthtv);
        qmap_1 = (LinearLayout) findViewById(R.id.qmap_1);
        qmap_2 = (LinearLayout) findViewById(R.id.qmap_2);

        qmap_3 = (LinearLayout) findViewById(R.id.qmap_3);

        studentCount = (TextView) findViewById(R.id.studentCount);
        nonteacherCount = (TextView) findViewById(R.id.nonteacherCount);
        teacherCount = (TextView) findViewById(R.id.teacherCount);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SugarContext.init(this);
        Menu nav_Menu = navigationView.getMenu();
        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
            nav_Menu.findItem(R.id.changeBranch).setVisible(false);
            nav_Menu.findItem(R.id.changeRole).setVisible(true);
        } else {
            nav_Menu.findItem(R.id.changeBranch).setVisible(true);
            nav_Menu.findItem(R.id.changeRole).setVisible(false);
        }
        mChart = (BarChart) findViewById(R.id.barChatAttendance);
        BarUtility.initializBarChatAttendance(this, mChart);
        View header = navigationView.getHeaderView(0);

        tvUserName = (TextView) header.findViewById(R.id.username_tv);
        tvEmailId = (TextView) header.findViewById(R.id.emailid_tv);
        imageview = (ImageView) header.findViewById(R.id.imageView);
        branchname = (TextView) header.findViewById(R.id.branchname);
        branchname.setText(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_NAME));
        tvUserName.setText(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USERNAME));
        tvEmailId.setText(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_EMAIL));

        Log.e("branch name","==="+PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_NAME));
        bindAllListView();
        dates = new ArrayList();

        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM", Locale.getDefault());
        month = simpleDateFormat1.format(cal1.getTime());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy", Locale.getDefault());
        year = simpleDateFormat2.format(cal1.getTime());

        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        monthtv.setText(simpleDateFormat.format(cal.getTime()));
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        //  compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);

        getcalenderdate();
        compactCalendarView.showCalendarWithAnimation();
       /* qmap_1.postDelayed(new Runnable() {
            public void run() {
                qmap_1.setVisibility(View.GONE);
            }
        }, 10000);

        qmap_2.postDelayed(new Runnable() {
            public void run() {
                qmap_2.setVisibility(View.GONE);
            }
        }, 10000);

        qmap_3.postDelayed(new Runnable() {
            public void run() {
                qmap_3.setVisibility(View.GONE);
            }
        }, 10000);*/

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
// set a string to format your current date
                String curDate = sdf.format(dateClicked.getTime());
// print the date in your log cat
                Log.d("CUR_DATE", curDate);
                for (int i = 0; i < dates.size(); i++) {

                    String date = dates.get(i);
                    SimpleDateFormat f = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                    d = new Date();
                    try {
                        d = f.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    milliseconds = d.getTime();
                    Log.e("data", "======" + d + "   ajhvshdgvhjsdav :  " + dateClicked);
                    if (dateClicked.equals(d)) {
                        Intent intent = new Intent(getApplicationContext(), AdminCalenderEventActivity.class);
                        intent.putExtra("date", curDate);
                        startActivity(intent);
                        List<com.github.sundeepk.compactcalendarview.domain.Event> events = compactCalendarView.getEvents(dateClicked);
                        Log.d("TAG", "Day was clicked: " + dateClicked + " with events " + events);
                    }
                    Log.e("data", "======" + milliseconds);
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthtv.setText(simpleDateFormat.format(firstDayOfNewMonth.getTime()));
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM", Locale.getDefault());
                month = simpleDateFormat1.format(firstDayOfNewMonth);
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy", Locale.getDefault());
                year = simpleDateFormat2.format(firstDayOfNewMonth);
                getcalenderdate();
                Log.d("TAG", "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });
        isConnectedToInternet();
    }

    public boolean isConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    private void bindAllListView() {

        tvTransfer = (TextView) findViewById(R.id.tvTransfer);
        tvabsentstudent = (TextView) findViewById(R.id.tvabsentstudent);
        tvResign = (TextView) findViewById(R.id.tvResign);

        tvabsentteacher = (TextView) findViewById(R.id.tvabsentteacher);

        tvErrorAnn = (TextView) findViewById(R.id.tvErrorAnn);
        lvAdminAnnouncement = (ListView) findViewById(R.id.lvAdminAnnouncement);

        absentstudentlv = (LinearLayout) findViewById(R.id.absentstudentlv);

        lvtc = (LinearLayout) findViewById(R.id.lvtc);
        absentteacherlv = (LinearLayout) findViewById(R.id.absentteacherlv);
        resignationlv = (LinearLayout) findViewById(R.id.resignationlv);

        absentstudentlv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(getApplicationContext(), AdminStudentAbsentListActivity.class);
                startActivity(intent);*/
            }
        });
        absentteacherlv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent = new Intent(getApplicationContext(), AdminAbsentTeacherActivity.class);
                startActivity(intent);*/
            }
        });
        resignationlv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(), AdminResignationActivity.class);
                startActivity(intent);*/
            }
        });
        lvtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(getApplicationContext(), AdminTCActivity.class);
                startActivity(intent);*/
            }
        });
        lvAdminAnnouncement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        announcementArrayList = new ArrayList<>();
        announcementAdapter = new AnnouncementAdapter(this, announcementArrayList);

        lvAdminAnnouncement.setAdapter(announcementAdapter);

        announcementAdapter.notifyDataSetChanged();
        getAdminDashboardDetails();

    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        getAdminDashboardDetails();
        getcalenderdate();
    }

    private void getAdminDashboardDetails() {

        if (UIUtil.isInternetAvailable(this)) {
            UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
            Map<String, String> params = new HashMap<String, String>();
            if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
            } else {
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
            }
            RetrofitAPI.getInstance(this).getApi().getAdminDashboardDetails(params, new Callback<AdminDashboardResponse>() {
                @Override
                public void success(AdminDashboardResponse dashRes, Response response) {

                    Log.e("API", "dashboardResponses" + dashRes.toString());
                    Log.e("API", "dashboardResponses" + response.getBody());

                    if (dashRes.getStatus() == Constants.SUCCESS) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        bindDataForDashBoard(dashRes.getDashboard());
                    } else {
                        Toast.makeText(getApplicationContext(), "" + dashRes.getMessage(), Toast.LENGTH_SHORT).show();
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
    }

    private void getcalenderdate() {

        if (UIUtil.isInternetAvailable(this)) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("month", month);
            params.put("year", year);
            if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
            } else {
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
            }
            RetrofitAPI.getInstance(this).getApi().getCalenderDate(params, new Callback<CalenderDateResponse>() {
                @Override
                public void success(CalenderDateResponse dashRes, Response response) {

                    if (dashRes.getStatus() == Constants.SUCCESS) {
                        // UIUtil.stopProgressDialog(getApplicationContext());

                        dates.clear();
                        dates.addAll((dashRes.getEvents()));
                        Log.e("dates", "-----" + dates);

                        for (int i = 0; i < dates.size(); i++) {

                            String date = dates.get(i);
                            SimpleDateFormat f = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                            Date d = new Date();
                            try {
                                d = f.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            milliseconds = d.getTime();
                            Event ev1 = new Event(R.drawable.circle1, milliseconds);
                            compactCalendarView.addEvent(ev1, true);

                            Log.e("data", "======" + milliseconds);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "" + dashRes.getMessage(), Toast.LENGTH_SHORT).show();
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
    }

    private void bindDataForDashBoard(AdminDashboard dashboard) {
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_IMAGE, dashboard.getImage());
        Log.e("kl", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_IMAGE));
        Picasso.with(this).load(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_IMAGE)).transform(new CircleTransform())
                .into(imageview);

        tvabsentteacher.setText("" + String.valueOf(dashboard.getAbsentStudentsCount()));
        tvabsentstudent.setText("" + String.valueOf(dashboard.getAbsentEmployeesCount()));
        tvResign.setText("" + String.valueOf(dashboard.getResignationCount()));
        tvTransfer.setText("" + String.valueOf(dashboard.getTransferCertificateCount()));

        studentCount.setText("" + String.valueOf(dashboard.getStudentCount()));
        nonteacherCount.setText("" + String.valueOf(dashboard.getNonTeachingCount()));
        teacherCount.setText("" + String.valueOf(dashboard.getTeachingCount()));

        announcementArrayList.clear();
        Log.e("getAssignments", "getAnnouncement -- " + dashboard.getAnnouncement().size());
        announcementArrayList.addAll(dashboard.getAnnouncement());
        announcementAdapter.notifyDataSetChanged();
        UIUtil.setListViewHeightBasedOnChildren(lvAdminAnnouncement);
        if (announcementArrayList.size() <= 0) {
            tvErrorAnn.setVisibility(View.VISIBLE);
        } else {
            tvErrorAnn.setVisibility(View.GONE);
        }
        setChartData(dashboard);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    /*    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.changeBranch) {
            startActivity(new Intent(this, AdminBranchActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } else if (id == R.id.changeRole) {
            startActivity(new Intent(this, TeacherRollActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } else if (id == R.id.notice_board) {
            startActivity(new Intent(this, AdminNoticeBoardActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        }/* else if (id == R.id.track_rides) {
            startActivity(new Intent(this, Admintrackridepageone.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } else if (id == R.id.assign_subject) {
            startActivity(new Intent(this, AdminAssignSubjectActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } else if (id == R.id.assign_class_teacher) {
            startActivity(new Intent(this, AssignClassteacher.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } */else if (id == R.id.holiday) {
            startActivity(new Intent(this, AdminHoliday.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } /*else if (id == R.id.department) {
            startActivity(new Intent(this, AdminDepartmentHeadfirstPage.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } else if (id == R.id.payment) {
            startActivity(new Intent(this, PaymentTabActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } else if (id == R.id.leave) {
            startActivity(new Intent(this, AdminLeaveActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        }*/ else if (id == R.id.EmployeeAttendance) {
            startActivity(new Intent(this, AdminAttendanceActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } else if (id == R.id.time_table) {
            startActivity(new Intent(this, AdminTimeTableTabActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } /*else if (id == R.id.Assignsubstitute) {
            startActivity(new Intent(this, AssignSubstituteTeacherActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } */else if (id == R.id.logout) {
            showLogoutConfirmation();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showLogoutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

//        AlertDialog.Builder builder =
//                new AlertDialog.Builder(this, R.style.AppTheme_AppBarOverlay);

        builder.setTitle("Confirmation");
        String message = "Do you want to logout?";
        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
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

    private void logout() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. logging out..");
                Map<String, String> params = new HashMap<String, String>();
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().logout(params, new Callback<JSONObject>() {
                    @Override
                    public void success(JSONObject object, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Log.e("API", "logout-" + object.toString());
                            Toast.makeText(getApplicationContext(), "Logout successfully..", Toast.LENGTH_SHORT).show();
                            PreferencesManger.clearPreferences(getApplicationContext());

                            startActivity(new Intent(AdminLandingActivity.this, LoginActivity.class));
                            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
                            finishAffinity();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        PreferencesManger.clearPreferences(getApplicationContext());
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Toast.makeText(getApplicationContext(), "Logout successfully..", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(AdminLandingActivity.this, LoginActivity.class));
                        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
                        finishAffinity();
                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setChartData(AdminDashboard dashboard) {

        float start = 1f;

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        BarEntry barEntry = new BarEntry(0, dashboard.getTeachingAttendancePercentage());

        yVals1.add(barEntry);
        yVals1.add(new BarEntry(1, dashboard.getNonTeachingAttendancePercentage()));
        yVals1.add(new BarEntry(2, dashboard.getStudentAttendancePercentage()));

        BarDataSet set1;
        mChart.animateX(2000);
        mChart.animateY(2000);
        mChart.animateXY(2000, 2000);
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(yVals1, "Current Month Attendance");
            set1.setColors(this.getResources().getColor(R.color.wallet_holo_blue_light),
                    this.getResources().getColor(R.color.wallet_dim_foreground_holo_dark),
                    this.getResources().getColor(R.color.grafcolor),
                    (this.getResources().getColor(R.color.red)));
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            mChart.setTouchEnabled(false);
//            data.setValueTypeface(mTfLight);
            data.setBarWidth(0.7f);
            mChart.setData(data);
        }
    }
}