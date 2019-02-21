package com.maxlore.edumanage.Activities.ParentActivities;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Activities.AdminActivities.CircleTransform;
import com.maxlore.edumanage.Activities.TeacherActivities.CalenderEventActivity;
import com.maxlore.edumanage.Activities.TeacherActivities.LoginActivity;
import com.maxlore.edumanage.Adapters.ParentAdapters.ParentDashboardAssignment;
import com.maxlore.edumanage.Adapters.TeacherAdapters.AnnouncementAdapter;
import com.maxlore.edumanage.Adapters.TeacherAdapters.EventAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.CalenderDateResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentAssignment;
import com.maxlore.edumanage.Models.ParentModels.ParentDashboard;
import com.maxlore.edumanage.Models.ParentModels.ParentDashboardResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentStudent;
import com.maxlore.edumanage.Models.TeacherModels.Announcement;
import com.maxlore.edumanage.Models.TeacherModels.Event;
import com.maxlore.edumanage.Models.TeacherModels.TrackTrip.TripCheckResponse;
import com.maxlore.edumanage.Models.TeacherModels.TrackTrip.TripStudent;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
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

import static android.widget.Toast.LENGTH_SHORT;

public class ParentLandingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private List<String> list;
    private ArrayAdapter<String> parentspinAdapter;
    private ArrayList<ParentStudent> parentStudentArrayList;
    private int s;
    private ListView lvAnnouncement, lvEvent, lvAssignment;
    private TextView tvAttendance, tvLeave, tvNewMails;
    private TextView tvErrorAnn, tvErrorEvent, tvErrorAss;

    private List<String> rolelist;
    private ArrayAdapter<String> spinAdapter;
    private Spinner rolespin;

    private TextView tvUserName, tvEmailId, studentname;
    private ImageView imageview;
    private String pos;
    private EventAdapter eventAdapter;
    private ParentDashboardAssignment assignmentAdapter;
    private AnnouncementAdapter announcementAdapter;
    private Date d;
    private ArrayList<Event> eventArrayList;
    private ArrayList<ParentAssignment> assignmentArrayList;
    private ArrayList<Announcement> announcementArrayList;
    private ArrayList<TripStudent> tripStudentArrayList;

    public ParentDashboardResponse dashboardResponse;
    private LinearLayout llTripRunning;
    private TextView tvTripRunning;
    private TextView tvClassToday, monthtv;
    private LinearLayout lvAttendance, lvleave;
    private ArrayList<String> dates;
    long milliseconds;
    private CompactCalendarView compactCalendarView;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
    private String month, year, trip_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SugarContext.init(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        monthtv = (TextView) findViewById(R.id.monthtv);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
/*
        rolelist = new ArrayList<String>();
        rolelist.add("class teacher");
        rolelist.add("Branch Admin");
        rolelist.add("Parent");*/

        llTripRunning = (LinearLayout) findViewById(R.id.llTripRunning);
        tvTripRunning = (TextView) findViewById(R.id.tvTripRunning);
        llTripRunning.setOnClickListener(this);
        tvTripRunning.setOnClickListener(this);
        tvTripRunning.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));
        llTripRunning.setVisibility(View.GONE);

        tripStudentArrayList = new ArrayList<>();
        Log.e("Constanid", "================" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
        list = new ArrayList<String>();
        parentStudentArrayList = new ArrayList<>();
        View header = navigationView.getHeaderView(0);

        tvUserName = (TextView) header.findViewById(R.id.username_tv);
        studentname = (TextView) header.findViewById(R.id.studentname);
        tvEmailId = (TextView) header.findViewById(R.id.emailid_tv);
        imageview = (ImageView) header.findViewById(R.id.imageView);

        studentname.setText(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_NAME));
        tvUserName.setText(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USERNAME));
        tvEmailId.setText(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_EMAIL));

        Log.e("Token", "onCreate -=-=-=-=-=----=-=-=-=-=-=" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN));
        Log.e("Token", "onCreate KEY_USER_TYPE -=-=-=-=-=----=-=-=-=-=-=" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE));

        Log.e("Token", "onCreate KEY_USER_SUB_TYPE-=-=-=-=-=----=-=-=-=-=-=" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_SUB_TYPE));

        Log.e("Token", "Username=============================================" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USERNAME));
        Log.e("Token", "email============================================" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_EMAIL));

        bindAllListView();

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
     /*   dates.add("Mon Jan 03 00:00:00 GMT+05:30 2018");
        dates.add("Mon Feb 06 00:00:00 GMT+05:30 2018");
        dates.add("Mon Mar 08 00:00:00 GMT+05:30 2018");
        dates.add("Tue Aug 09 00:00:00 GMT+05:30 2017");*/
        // ArrayList<String> da = new ArrayList();
        //da.addAll(dates);
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM", Locale.getDefault());
        month = simpleDateFormat1.format(cal1.getTime());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy", Locale.getDefault());
        year = simpleDateFormat2.format(cal1.getTime());
        getcalenderdate();
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


                //Intent calIntent = new Intent(Intent.ACTION_INSERT);
                // calIntent.setData(CalendarContract.Events.CONTENT_URI);
                //startActivity(calIntent);
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
        startActivity(new Intent(this, ParentProfileActivity.class));
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {


            case R.id.llTripRunning:
            case R.id.tvTripRunning:
               /* intent = new Intent(this, MapsActivity.class);
                intent.putExtra(Constants.TRIP_STUDENT, trip_id);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);*/
                break;
        }

    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        getParentDashboardDetails();
        getcalenderdate();
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.parentdashboardspinner, menu);

        MenuItem item = menu.findItem(R.id.parentdashboardspinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        parentspinAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        parentspinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parentspinAdapter.notifyDataSetChanged();
        spinner.setAdapter(parentspinAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = list.get(position);
                assignmentData(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return true;
    }
*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        if (id == R.id.choosestudent) {
            startActivity(new Intent(this, ParentRollActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        }
       /* if (id == R.id.applyleave) {
            startActivity(new Intent(this, ParentLeaveActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } else if (id == R.id.applyfortransfer) {
            startActivity(new Intent(this, ParentTransferCertificateListItemActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } else if (id == R.id.fees) {
            startActivity(new Intent(this, FeesPaymentMOdule.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } else if (id == R.id.assignment) {
            startActivity(new Intent(this, ParentAssignmentActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        }*/ else if (id == R.id.parenttimetable) {
            startActivity(new Intent(this, ParentTimeTableClassActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } /*else if (id == R.id.trackride) {
            startActivity(new Intent(this, MapsActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } else if (id == R.id.teacherprofile) {
            startActivity(new Intent(this, TeacherprofileActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } else if (id == R.id.parentobservation) {
            startActivity(new Intent(this, ParentObservation.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } else if (id == R.id.paymenthistory) {
            startActivity(new Intent(this, PaymentHistoryActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } else if (id == R.id.syllabus) {
            startActivity(new Intent(this, Parentsyllabus.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } */else if (id == R.id.attendance) {
            startActivity(new Intent(this, NewParentAttendanceActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } else if (id == R.id.profile) {
            startActivity(new Intent(this, ParentProfileActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } else if (id == R.id.schoolInfo) {
            startActivity(new Intent(this, ParentSchoolInfoActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } /*else if (id == R.id.chatparent) {
            startActivity(new Intent(this, ChatListActivity.class));
        }*/ else if (id == R.id.parentlogout) {
            showLogoutConfirmation();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getParentDashboardDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));

                RetrofitAPI.getInstance(this).getApi().getParentDashboardDetails(params, new Callback<ParentDashboardResponse>() {
                    @Override
                    public void success(ParentDashboardResponse dashRes, Response response) {
                        try {

                            Log.e("API", "dashboardResponses" + dashRes.toString());
                            Log.e("API", "dashboardResponses" + response.getBody());

                            checkForTripStatus();

                            if (dashRes.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                bindData(dashRes.getDashboard());
                                // updateSpinner();

                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "" + dashRes.getMessage(), LENGTH_SHORT).show();
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

                Toast.makeText(this, "Please Connect to Internet", LENGTH_SHORT).show();
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
                                    d = new Date();
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
                                Toast.makeText(getApplicationContext(), "" + dashRes.getMessage(), LENGTH_SHORT).show();
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
                Toast.makeText(this, "Please Connect to Internet", LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkForTripStatus() {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
            params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
            RetrofitAPI.getInstance(this).getApi().checkTripRunningStatus(params, new Callback<TripCheckResponse>() {
                @Override
                public void success(TripCheckResponse tripCheckResponse, Response response) {
                    try {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        if (tripCheckResponse.getStatus() == Constants.ON_TRIP) {
                            tripStudentArrayList.clear();
                            //tripStudentArrayList.addAll(tripCheckResponse.getTrip());
                            PreferencesManger.addStringFields(getApplicationContext(), Constants.TRIP_STUDENT, tripCheckResponse.getTripId());
                            llTripRunning.setVisibility(View.VISIBLE);
                        } else {
                            llTripRunning.setVisibility(View.GONE);
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

    private void bindAllListView() {

        tvAttendance = (TextView) findViewById(R.id.tvmyAttendance);
        tvLeave = (TextView) findViewById(R.id.tvLeave);
        // tvNewMails = (TextView) findViewById(R.id.tvNewMails);

        tvErrorAnn = (TextView) findViewById(R.id.tvErrorAnn);
        // tvErrorEvent = (TextView) findViewById(R.id.tvErrorEvent);
        tvErrorAss = (TextView) findViewById(R.id.tvErrorAss);

        lvAnnouncement = (ListView) findViewById(R.id.lvAnnouncement);
        // lvEvent = (ListView) findViewById(R.id.lvEvent);
        lvAssignment = (ListView) findViewById(R.id.lvAssignments);

        lvAttendance = (LinearLayout) findViewById(R.id.lvAttendance);
        lvleave = (LinearLayout) findViewById(R.id.lvleave);
        lvAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(), NewParentAttendanceActivity.class);
                startActivity(intent);*/
            }
        });
        lvleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(getApplicationContext(), ParentLeaveActivity.class);
                startActivity(intent);*/
            }
        });
        lvAnnouncement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
   /*     lvEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/
        lvAssignment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        /*lvAssignment.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });*/
        //eventArrayList = new ArrayList<>();
        assignmentArrayList = new ArrayList<>();
        announcementArrayList = new ArrayList<>();

        // eventAdapter = new EventAdapter(this, eventArrayList);
        assignmentAdapter = new ParentDashboardAssignment(this, assignmentArrayList);
        announcementAdapter = new AnnouncementAdapter(this, announcementArrayList);


        //lvEvent.setAdapter(eventAdapter);
        lvAssignment.setAdapter(assignmentAdapter);
        lvAnnouncement.setAdapter(announcementAdapter);


        // eventAdapter.notifyDataSetChanged();
        assignmentAdapter.notifyDataSetChanged();
        announcementAdapter.notifyDataSetChanged();

    }

    private void bindData(ParentDashboard dashboard) {
        try {
            PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_IMAGE, dashboard.getImage());
            Picasso.with(this).load(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_IMAGE)).resize(50, 50)
                    .transform(new CircleTransform()).into(imageview);

            parentStudentArrayList.clear();
            parentStudentArrayList.add(dashboard.getStudent());
            Log.e("Error", "=================" + parentStudentArrayList);
            //tvNewMails.setText("" + String.valueOf(dashboard.getMailCount()));

            // Bind event data to list
       /* eventArrayList.clear();
        eventAdapter.notifyDataSetChanged();
        UIUtil.setListViewHeightBasedOnChildren(lvEvent);*/

      /*  if (eventArrayList.size() == 0) {
            tvErrorEvent.setVisibility(View.VISIBLE);
        } else {
            tvErrorEvent.setVisibility(View.GONE);
        }*/
            announcementArrayList.clear();
            Log.e("getAssignments", "getAnnouncement -- " + dashboard.getAnnouncement().size());
            announcementArrayList.addAll(dashboard.getAnnouncement());
            announcementAdapter.notifyDataSetChanged();
            UIUtil.setListViewHeightBasedOnChildren(lvAnnouncement);

            if (announcementArrayList.size() == 0) {
                tvErrorAnn.setVisibility(View.VISIBLE);
            } else {
                tvErrorAnn.setVisibility(View.GONE);
            }

            tvAttendance.requestFocus();
            tvLeave.requestFocus();

            assignmentData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 /*   private void updateSpinner() {
        list.clear();

        for (int i = 0; i < parentStudentArrayList.size(); i++) {
            list.add(parentStudentArrayList.get(i).getStudentName());
        }
        parentspinAdapter.notifyDataSetChanged();
        Log.e("aaaaaaaaaaaaaaaaa", list + "");
    }*/

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
                            Toast.makeText(getApplicationContext(), "Logout successfully..", LENGTH_SHORT).show();
                            PreferencesManger.clearPreferences(getApplicationContext());

                            startActivity(new Intent(ParentLandingActivity.this, LoginActivity.class));
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
                        Toast.makeText(getApplicationContext(), "Logout successfully..", LENGTH_SHORT).show();

                        startActivity(new Intent(ParentLandingActivity.this, LoginActivity.class));
                        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
                        finishAffinity();
                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void assignmentData() {
        for (int i = 0; i < parentStudentArrayList.size(); i++) {
            updateData(parentStudentArrayList.get(i));
            break;
        }
    }

    private void updateData(ParentStudent parentStudent) {
        try {
            assignmentArrayList.clear();
            assignmentArrayList.addAll(parentStudent.getAssignment());
            assignmentAdapter.notifyDataSetChanged();
            if (parentStudent.getAttendancePercent() != null) {
                tvAttendance.setText(parentStudent.getAttendancePercent() + "%");
            } else {
                tvAttendance.setText("0%");
            }
            if (assignmentArrayList.size() == 0) {
                lvAssignment.setVisibility(View.GONE);
                tvErrorAss.setVisibility(View.VISIBLE);
            } else {
                lvAssignment.setVisibility(View.VISIBLE);
                tvErrorAss.setVisibility(View.GONE);
            }
            tvLeave.setText("" + String.valueOf(parentStudent.getLeaveApply()));
            UIUtil.setListViewHeightBasedOnChildren(lvAssignment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
