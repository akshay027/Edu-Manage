/*
package com.maxlore.inmegh.Activities.ParentActivities;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.inmegh.API.RetrofitAPI;
import com.maxlore.inmegh.Activities.TeacherActivities.TimeTableDateAdapter;
import com.maxlore.inmegh.Adapters.ParentAdapters.ParentTimeTableAdapter;
import com.maxlore.inmegh.Database.PreferencesManger;
import com.maxlore.inmegh.Models.ParentModels.ParentTimeTable.ParentTimeTable;
import com.maxlore.inmegh.Models.ParentModels.ParentTimeTable.ParentTimeTableResponse;
import com.maxlore.inmegh.Models.ParentModels.ParentTimeTable.ParentTimeTableStructure;
import com.maxlore.inmegh.Models.TeacherModels.database.TimeTable;
import com.maxlore.inmegh.R;
import com.maxlore.inmegh.Utility.Constants;
import com.maxlore.inmegh.Utility.UIUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ParentTimeTableActivity extends AppCompatActivity {
    private List<String> list;
    public ArrayList<ParentTimeTable> parentTimeTableArrayList;
    private ArrayAdapter<String> structureAdapter;
    private int selectedStudent = 0;
    private String selectType;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ArrayAdapter<String> adapter;
    private PlaceholderFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_time_table);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = new ArrayList<String>();
        parentTimeTableArrayList = new ArrayList<>();
        fragment = PlaceholderFragment.newInstance(1);
        fragment.filterDayWise();
        sendMessage();
        getParentTimeTableData();
    }

    private void updateFragment() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        if (parentTimeTableArrayList.size() < 2) {
            tabLayout.setVisibility(View.GONE);
        } else {
            tabLayout.setVisibility(View.VISIBLE);
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedStudent = position;
                fragment.filterDayWise();
                Log.e("onPageSelected", "Position --:  " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void getParentTimeTableData() {
        if (UIUtil.isInternetAvailable(this)) {
            UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
            Map<String, String> params = new HashMap<String, String>();
            params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
            RetrofitAPI.getInstance(this).getApi().getParentTimeTableData(params,new Callback<ParentTimeTableResponse>() {
                @Override
                public void success(ParentTimeTableResponse parentTimeTableResponse, Response response) {

                    if (parentTimeTableResponse.getStatus() == Constants.SUCCESS) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        parentTimeTableArrayList.clear();
                        parentTimeTableArrayList.addAll(parentTimeTableResponse.getParentTimetable());
                       // updateSpinner();
                        updateFragment();
                    } else {
                        Toast.makeText(getApplicationContext(), "" + parentTimeTableResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

   *//*

*/
/* @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.timetablespinner, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectType = list.get(position);
                sendMessage();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return true;
    }*//*
*/
/*



   *//*

*/
/* private void updateSpinner() {
        list.clear();

        for (int i = 0; i < parentTimeTableArrayList.size(); i++) {
            List<ParentTimeTableStructure> tableStructures = parentTimeTableArrayList.get(i).getTimeTableStructure();
            for (int j = 0; j < tableStructures.size(); j++) {
                addIfNotExist(tableStructures.get(j).getName());
            }
        }
        adapter.notifyDataSetChanged();
        Log.e("bbbbbbbbbb", list + "");
    }
*//*


    private void addIfNotExist(String s) {
        if (!list.contains(s)) {
            list.add(s);
        }
    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private ListView lvdate;
        private ListView lvperiod;
        TextView time_tv;
        private ArrayAdapter dayadapter;
        TextView tv_periodtime, tv_period, noschedule_tv, periodTeacher;
        private String currentDay;
        private List<String> list;
        private ArrayAdapter<String> parenttimetablespinAdapter;
        ParentTimeTableAdapter parentTimeTableAdapter;
        ArrayList<TimeTable> arrayListtimetable;
        ArrayList<TimeTable> finalArrayList;
        private ArrayList arrayList;
        private ParentTimeTableAdapter timeTableAdapter;
        private int day;
        ArrayList<ParentTimeTable> parentTimeTablesList = new ArrayList<>();
        int selectedStudent = 0;
        String selectType;
        ParentTimeTableActivity activity;


        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {

            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_parent_time_table, container, false);
            lvdate = (ListView) view.findViewById(R.id.lvDate);
            time_tv = (TextView) view.findViewById(R.id.dateday_tv);
            lvperiod = (ListView) view.findViewById(R.id.lvPeriod);
            periodTeacher = (TextView) view.findViewById(R.id.ClassTeacher_tv);
            noschedule_tv = (TextView) view.findViewById(R.id.tv_noSchedule);
            finalArrayList = new ArrayList<>();
            arrayList = new ArrayList();
            timeTableAdapter = new ParentTimeTableAdapter(getActivity(), arrayList);
            lvperiod.setAdapter(timeTableAdapter);
            timeTableAdapter.notifyDataSetChanged();


            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);

            SimpleDateFormat format = new SimpleDateFormat("dd\n" + "EEEE");
            String[] days = new String[7];
            int weekdays = -calendar.get(GregorianCalendar.DAY_OF_WEEK) + 1;
            calendar.add(Calendar.DAY_OF_MONTH, weekdays);

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            Date d = new Date();
            currentDay = sdf.format(d);

            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("custom-event-name"));
            ArrayList<String> arrayListDate = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                days[i] = format.format(calendar.getTime());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                arrayListDate.add(days[i]);
            }
            final TimeTableDateAdapter dateAdapter = new TimeTableDateAdapter(getActivity(), arrayListDate);
            dateAdapter.setPosition(day - 1);

            lvdate.setAdapter(dateAdapter);
            dateAdapter.notifyDataSetChanged();


            lvdate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            currentDay = "Sunday";
                            dateAdapter.setPosition(0);
                            filterDayWise();
                            break;
                        case 1:
                            currentDay = "Monday";
                            dateAdapter.setPosition(1);
                            filterDayWise();
                            break;
                        case 2:
                            currentDay = "Tuesday";
                            dateAdapter.setPosition(2);
                            filterDayWise();
                            break;
                        case 3:
                            currentDay = "Wednesday";
                            dateAdapter.setPosition(3);
                            filterDayWise();
                            break;
                        case 4:
                            currentDay = "Thursday";
                            dateAdapter.setPosition(4);
                            filterDayWise();
                            break;
                        case 5:
                            currentDay = "Friday";
                            dateAdapter.setPosition(5);
                            filterDayWise();
                            break;
                        case 6:
                            currentDay = "Saturday";
                            dateAdapter.setPosition(6);
                            filterDayWise();
                            break;
                    }
                    dateAdapter.notifyDataSetChanged();
                }
            });

            filterDayWise();
            return view;
        }

        public void filterDayWise() {
            try {
                activity = (ParentTimeTableActivity) getActivity();

                selectType = activity.selectType;
                selectedStudent = activity.selectedStudent;
                parentTimeTablesList = activity.parentTimeTableArrayList;

                Log.e("checkkkkkk", "hnj");
                Log.e("ttttttttttttat", selectType);
                Log.e("pppp", selectedStudent + "positi");

                if (parentTimeTablesList.size() > 0) {
                    ParentTimeTable parentTimeTable = parentTimeTablesList.get(selectedStudent);
                    List<ParentTimeTableStructure> tableStructures = parentTimeTable.getTimeTableStructure();
                    for (int i = 0; i < tableStructures.size(); i++) {
                        if (tableStructures.get(i).getName().equalsIgnoreCase(selectType)) {
                            finalArrayList.clear();
                            finalArrayList.addAll(tableStructures.get(i).getTimeTable());
                            Log.e("acvghsa=====", finalArrayList + "");
                            break;
                        }
                    }
                    arrayList.clear();
                    for (int i = 0; i < finalArrayList.size(); i++) {
                        if (finalArrayList.get(i).getDay().equalsIgnoreCase(currentDay)) {
                            arrayList.add(finalArrayList.get(i));
                        }
                    }
                    timeTableAdapter.notifyDataSetChanged();
                }
                if (arrayList.size() <= 0) {
                    lvperiod.setVisibility(View.GONE);
                    noschedule_tv.setVisibility(View.VISIBLE);

                } else {
                    lvperiod.setVisibility(View.VISIBLE);
                    noschedule_tv.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get extra data included in the Intent
                String message = intent.getStringExtra("message");
                Log.d("receiver", "Got message: " + message);
                filterDayWise();
            }
        };

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            fragment = PlaceholderFragment.newInstance(position + 1);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return parentTimeTableArrayList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return parentTimeTableArrayList.get(position).getStudentName();
        }
    }


    private void sendMessage() {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("custom-event-name");
        intent.putExtra("message", "This is my message!");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }

}
*/
/*

*/
