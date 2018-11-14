package com.maxlore.edumanage.Activities.TeacherActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Adapters.TeacherAdapters.AnnouncementAdapter;
import com.maxlore.edumanage.Adapters.TeacherAdapters.DashBoardAssignmentAdapter;
import com.maxlore.edumanage.Adapters.TeacherAdapters.EventAdapter;
import com.maxlore.edumanage.Adapters.TeacherAdapters.HouseGroupAdapter;
import com.maxlore.edumanage.Adapters.TeacherAdapters.TimeTableAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.CalenderDateResponse;
import com.maxlore.edumanage.Models.TeacherModels.Announcement;
import com.maxlore.edumanage.Models.TeacherModels.Assignment;
import com.maxlore.edumanage.Models.TeacherModels.Dashboard;
import com.maxlore.edumanage.Models.TeacherModels.DashboardResponse;
import com.maxlore.edumanage.Models.TeacherModels.DashboardTimeTable;
import com.maxlore.edumanage.Models.TeacherModels.Event;
import com.maxlore.edumanage.Models.TeacherModels.HouseGroup;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;
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

import static com.maxlore.edumanage.Utility.Constants.CLASS_TEACHER;
import static com.maxlore.edumanage.Utility.Constants.Pref.KEY_USER_TYPE;

public class TeacherLandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView lvAnnouncement, lvEvent, lvAssignment, lvTimeTable, lvHouseGroup;
    private TextView tvAttendance, tvLeave;
    private TextView tvErrorAnn, tvErrorEvent, tvErrorAss, tvErrorTT, tvErrorHG;

    private LinearLayout lvattandance, lvleave;

    private TextView tvUserName, tvEmailId, monthtv;
    private ImageView imageview;
    private EventAdapter eventAdapter;
    private DashBoardAssignmentAdapter assignmentAdapter;
    private AnnouncementAdapter announcementAdapter;
    private HouseGroupAdapter houseGroupAdapter;
    private TimeTableAdapter timeTableAdapter;
    private Date d;
    private ArrayList<Event> eventArrayList;
    private ArrayList<Assignment> assignmentArrayList;
    private ArrayList<Announcement> announcementArrayList;
    private ArrayList<HouseGroup> houseGroupArrayList;
    private ArrayList<DashboardTimeTable> dashboardTimeTableArrayList;
    private ArrayList<String> arrayList;

    public DashboardResponse dashboardResponse;
    private CompactCalendarView compactCalendarView;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
    private ArrayList<String> dates;
    private long milliseconds;
    private String month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        arrayList = new ArrayList<>();
        monthtv = (TextView) findViewById(R.id.monthtv);
        tvUserName = (TextView) header.findViewById(R.id.username_tv);
        tvEmailId = (TextView) header.findViewById(R.id.emailid_tv);
        imageview = (ImageView) header.findViewById(R.id.imageView);

        tvUserName.setText(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USERNAME));
        tvEmailId.setText(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_EMAIL));


        Log.e("Token", "onCreate -=-=-=-=-=----=-=-=-=-=-=" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN));
        Log.e("Token", "onCreate KEY_USER_TYPE -=-=-=-=-=----=-=-=-=-=-=" + PreferencesManger.getStringFields(getApplicationContext(), KEY_USER_TYPE));
        Log.e("Token", "onCreate KEY_USER_SUB_TYPE-=-=-=-=-=----=-=-=-=-=-=" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_SUB_TYPE));
        Log.e("Token", "Username=============================================" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USERNAME));
        Log.e("Token", "email============================================" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_EMAIL));
        bindAllListView();
        if (PreferencesManger.getStringFields(getApplicationContext(), KEY_USER_TYPE).equalsIgnoreCase("Class Teacher")) {
            toolbar.setSubtitle(Html.fromHtml("<font color='#ffffff'>Class Teacher</font>"));
            getDashboardDetails();
        } else if (PreferencesManger.getStringFields(getApplicationContext(), KEY_USER_TYPE).equalsIgnoreCase("Teaching")) {
            toolbar.setSubtitle(Html.fromHtml("<font color='#ffffff'>Teacher</font>"));
            getTeacherDashboardDetails();
        }
        tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenProfile();
            }
        });
        tvEmailId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenProfile();
            }
        });
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenProfile();
            }
        });

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
        // compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        getcalenderdate();
        compactCalendarView.showCalendarWithAnimation();

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                //Calendar date = Calendar.getInstance();
// for your date format use

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
                        Intent intent = new Intent(getApplicationContext(), CalenderEventActivity.class);
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

                monthtv.setText(simpleDateFormat.format(firstDayOfNewMonth));
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM", Locale.getDefault());
                month = simpleDateFormat1.format(firstDayOfNewMonth);
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy", Locale.getDefault());
                year = simpleDateFormat2.format(firstDayOfNewMonth);
                getcalenderdate();
                Log.d("TAG", "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });
    }

    public void OpenProfile() {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    private void bindAllListView() {
        try {
            tvAttendance = (TextView) findViewById(R.id.tvAttendance);
            tvLeave = (TextView) findViewById(R.id.tvLeave);
            tvErrorAnn = (TextView) findViewById(R.id.tvErrorAnn);
            // tvErrorEvent = (TextView) findViewById(R.id.tvErrorEvent);
            tvErrorAss = (TextView) findViewById(R.id.tvErrorAss);
            tvErrorTT = (TextView) findViewById(R.id.tvErrorTT);
            tvErrorHG = (TextView) findViewById(R.id.tvErrorHG);

            lvattandance = (LinearLayout) findViewById(R.id.lvattandance);
            lvleave = (LinearLayout) findViewById(R.id.lvleave);

            lvattandance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CLASS_TEACHER.equalsIgnoreCase(PreferencesManger.getStringFields(getApplicationContext(), KEY_USER_TYPE))) {
                       /* startActivity(new Intent(getApplicationContext(), OwnAttendanceActivity.class));
                        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);*/
                    } else {
                        AlertForTeacher();
                    }
                }
            });
            lvleave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent = new Intent(getApplicationContext(), LeaveActivity.class);
                    startActivity(intent);*/
                }
            });
            lvAnnouncement = (ListView) findViewById(R.id.lvAnnouncement);
            //lvEvent = (ListView) findViewById(R.id.lvEvent);
            lvAssignment = (ListView) findViewById(R.id.lvAssignment);
            lvTimeTable = (ListView) findViewById(R.id.lvTimeTable);
            lvHouseGroup = (ListView) findViewById(R.id.lvHouseGroup);
            lvHouseGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            lvTimeTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            lvAssignment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });

            lvAnnouncement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });

            assignmentArrayList = new ArrayList<>();
            announcementArrayList = new ArrayList<>();
            houseGroupArrayList = new ArrayList<>();
            dashboardTimeTableArrayList = new ArrayList<>();

            // eventAdapter = new EventAdapter(this, eventArrayList);
            assignmentAdapter = new DashBoardAssignmentAdapter(this, assignmentArrayList);
            announcementAdapter = new AnnouncementAdapter(this, announcementArrayList);
            houseGroupAdapter = new HouseGroupAdapter(this, houseGroupArrayList);
            timeTableAdapter = new TimeTableAdapter(this, dashboardTimeTableArrayList);

            //lvEvent.setAdapter(eventAdapter);
            lvAssignment.setAdapter(assignmentAdapter);
            lvAnnouncement.setAdapter(announcementAdapter);
            lvHouseGroup.setAdapter(houseGroupAdapter);
            lvTimeTable.setAdapter(timeTableAdapter);

            //eventAdapter.notifyDataSetChanged();
            assignmentAdapter.notifyDataSetChanged();
            announcementAdapter.notifyDataSetChanged();
            houseGroupAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        if (PreferencesManger.getStringFields(getApplicationContext(), KEY_USER_TYPE).equalsIgnoreCase("Class Teacher")) {
            getDashboardDetails();
            getcalenderdate();
        } else if (PreferencesManger.getStringFields(getApplicationContext(), KEY_USER_TYPE).equalsIgnoreCase("Employee")) {
            getTeacherDashboardDetails();
            getcalenderdate();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.attendance) {
            if (Constants.CLASS_TEACHER.equalsIgnoreCase(PreferencesManger.getStringFields(this, Constants.Pref.KEY_USER_TYPE))) {
                startActivity(new Intent(this, AttendanceActivity.class));
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
                Log.i("Class Teacher Value1---", Constants.CLASS_TEACHER );
                Log.i("Key User Type Value1---", Constants.Pref.KEY_USER_TYPE );

            } else {
                Log.i("Class Teacher Value2---", Constants.CLASS_TEACHER );
                Log.i("Key User Type Value2---", Constants.Pref.KEY_USER_TYPE );
                AlertForTeacher();
            }
        } else if (id == R.id.changeRole) {
            startActivity(new Intent(this, TeacherRollActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } /*else if (id == R.id.leave) {
            if (PreferencesManger.getStringFields(this, Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase(Constants.CLASS_TEACHER)) {
                startActivity(new Intent(this, ClassTeacherLeaveTabActivity.class));
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            } else {
                startActivity(new Intent(this, LeaveActivity.class));
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            }
        }*/ else if (id == R.id.timeTable) {
            if (PreferencesManger.getStringFields(this, KEY_USER_TYPE).equalsIgnoreCase(CLASS_TEACHER)) {
                startActivity(new Intent(this, TimeTableTabActivity.class));
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            } else {
                startActivity(new Intent(this, TimeTableActivity.class));
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            }
        } /*else if (id == R.id.academics) {
            startActivity(new Intent(this, NewTeachingPlanActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);

        }*/ else if (id == R.id.ownat) {
            startActivity(new Intent(this, OwnAttendanceActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);

        } else if (id == R.id.profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);

        } else if (id == R.id.schoolInfo) {
            startActivity(new Intent(this, SchoolinfoActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);

        } /*else if (id == R.id.studentdetails) {
            if (PreferencesManger.getStringFields(this, Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase(Constants.CLASS_TEACHER)) {
                startActivity(new Intent(this, StudentTabDetailsActivity.class));
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            } else {
                startActivity(new Intent(this, StudentDetailActivity.class));
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            }
        } else if (id == R.id.assignment) {
            startActivity(new Intent(this, AssignmentActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);

        }*/ else if (id == R.id.logout) {
            showLogoutConfirmation();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void getTeacherDashboardDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().getTeacherDashboardDetails(params, new Callback<DashboardResponse>() {
                    @Override
                    public void success(DashboardResponse dashRes, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Log.e("API", "dashboardResponses" + dashRes.toString());
                            Log.e("API", "dashboardResponses" + response.getBody());
                            if (dashRes.getStatus() == Constants.SUCCESS) {
                                bindData(dashRes.getDashboard());

                            } else {
                                Toast.makeText(getApplicationContext(), "" + dashRes.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void getcalenderdate() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("month", month);
                params.put("year", year);
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().getCalenderDate(params, new Callback<CalenderDateResponse>() {
                    @Override
                    public void success(CalenderDateResponse dashRes, Response response) {
                        try {
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
                                    com.github.sundeepk.compactcalendarview.domain.Event ev1 = new com.github.sundeepk.compactcalendarview.domain.Event(R.drawable.circle1, milliseconds);
                                    compactCalendarView.addEvent(ev1, true);
                                    Log.e("data", "======" + milliseconds);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "" + dashRes.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void getDashboardDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().getDashboardDetails(params, new Callback<DashboardResponse>() {
                    @Override
                    public void success(DashboardResponse dashRes, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Log.e("API", "dashboardResponses" + dashRes.toString());
                            Log.e("API", "dashboardResponses" + response.getBody());
                            if (dashRes.getStatus() == Constants.SUCCESS) {
                                bindData(dashRes.getDashboard());
                            } else {
                                Toast.makeText(getApplicationContext(), "" + dashRes.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void bindData(Dashboard dashboard) {
        try {
            PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_IMAGE, dashboard.getImage());

            Picasso.with(this).load(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_IMAGE)).resize(50, 50)
                    .transform(new CircleTransform()).into(imageview);
            if (dashboard.getAttendenceCount() != null) {
                tvAttendance.setText(dashboard.getAttendenceCount() + "%");
            } else {
                tvAttendance.setText("0%");
            }
            tvLeave.setText("" + String.valueOf(dashboard.getLeaveBalance()));

            assignmentArrayList.clear();
            Log.e("getAssignments", "getAssignments -- " + dashboard.getAssignments().size());
            assignmentArrayList.addAll(dashboard.getAssignments());
            assignmentAdapter.notifyDataSetChanged();
            setListViewHeightForAss(lvAssignment);

            if (assignmentArrayList.size() == 0) {
                tvErrorAss.setVisibility(View.VISIBLE);
            } else {
                tvErrorAss.setVisibility(View.GONE);
            }

            houseGroupArrayList.clear();
            houseGroupArrayList.addAll(dashboard.getHouseGroup());
            Log.e("dashboard", "dashboard --" + dashboard.getHouseGroup().toString());
            houseGroupAdapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(lvHouseGroup);

            if (houseGroupArrayList.size() == 0) {
                tvErrorHG.setVisibility(View.VISIBLE);
            } else {
                tvErrorHG.setVisibility(View.GONE);
            }

            dashboardTimeTableArrayList.clear();
            dashboardTimeTableArrayList.addAll(dashboard.getTimetables());
            Log.e("dashboard", "dashboard --" + dashboard.getTimetables().size());
            timeTableAdapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(lvTimeTable);

            if (dashboardTimeTableArrayList.size() == 0) {
                tvErrorTT.setVisibility(View.VISIBLE);
            } else {
                tvErrorTT.setVisibility(View.GONE);
            }

            announcementArrayList.clear();
            Log.e("getAssignments", "getAnnouncement -- " + dashboard.getAnnouncement().size());
            announcementArrayList.addAll(dashboard.getAnnouncement());
            announcementAdapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(lvAnnouncement);

            if (announcementArrayList.size() == 0) {
                tvErrorAnn.setVisibility(View.VISIBLE);
            } else {
                tvErrorAnn.setVisibility(View.GONE);
            }

            tvAttendance.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        try {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setListViewHeightForAss(ListView listView) {
        try {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null)
                return;

            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            int totalHeight = 0;
            View view = null;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                view = listAdapter.getView(i, view, listView);
                if (i == 0)
                    view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

                view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += view.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showLogoutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

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

    private void AlertForTeacher() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Alert");
        String message = "You do not have the permission to mark attendance!";
        builder.setMessage(message);
        builder.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    private void logout() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. logging out..");
                Map<String, String> params = new HashMap<String, String>();
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().logout(params, new Callback<JSONObject>() {
                    @Override
                    public void success(JSONObject object, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Log.e("API", "logout-" + object.toString());
                            Toast.makeText(getApplicationContext(), "Logged Out successfully", Toast.LENGTH_SHORT).show();
                            PreferencesManger.clearPreferences(getApplicationContext());
                            SugarContext.terminate();
                            SchemaGenerator schemaGenerator = new SchemaGenerator(getApplicationContext());
                            schemaGenerator.deleteTables(new SugarDb(getApplicationContext()).getDB());
                            SugarContext.init(getApplicationContext());
                            schemaGenerator.createDatabase(new SugarDb(getApplicationContext()).getDB());

                            startActivity(new Intent(TeacherLandingActivity.this, LoginActivity.class));
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

                        startActivity(new Intent(TeacherLandingActivity.this, LoginActivity.class));
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
}
