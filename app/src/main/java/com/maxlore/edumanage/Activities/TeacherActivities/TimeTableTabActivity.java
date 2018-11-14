package com.maxlore.edumanage.Activities.TeacherActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.maxlore.edumanage.Adapters.TeacherAdapters.TimeTablePageAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Fragment.ClassTimeTableFragment;
import com.maxlore.edumanage.Fragment.OwnTimeTableFragment;

import com.maxlore.edumanage.Models.TeacherModels.database.TimeTableStructure;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TimeTableTabActivity extends AppCompatActivity implements ClassTimeTableFragment.OnFragmentInteractionListener, OwnTimeTableFragment.OnFragmentInteractionListener {

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private TimeTablePageAdapter timeTablePageAdapter;
    private List<String> list;
    private List<String> idarr;
    private String shreyid, branchid;
    private Integer abc = 0;
    private ArrayAdapter<String> spinAdapter;
    private Iterator<TimeTableStructure> timeTableStructures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_tab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        branchid = PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID);
       /* SugarContext.terminate();
        SchemaGenerator schemaGenerator = new SchemaGenerator(getApplicationContext());
        schemaGenerator.deleteTables(new SugarDb(getApplicationContext()).getDB());
        SugarContext.init(getApplicationContext());
        schemaGenerator.createDatabase(new SugarDb(getApplicationContext()).getDB());*/

        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.addTab(tabLayout.newTab().setText("My TimeTable"));
                tabLayout.addTab(tabLayout.newTab().setText("Class TimeTable"));
                tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                if (PreferencesManger.getStringFields(getApplication(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase(Constants.CLASS_TEACHER)) {

                } else {
                    tabLayout.removeTabAt(1);
                }                /*SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                Date d = new Date();
                String dayOfTheWeek = sdf.format(d);*/
                timeTablePageAdapter = new TimeTablePageAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), branchid);
                mViewPager.setAdapter(timeTablePageAdapter);

                mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        mViewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }
        });
    }
/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timetablespinner, menu);
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_class_teacher_leave_tab, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);


        list = new ArrayList<String>();
        idarr = new ArrayList<>();

        timeTableStructures = TimeTableStructure.findAll(TimeTableStructure.class);
        while (timeTableStructures.hasNext()) {
            TimeTableStructure timeTableStructure = timeTableStructures.next();
            list.add(timeTableStructure.getName());
            idarr.add(String.valueOf(timeTableStructure.getStructureId()));
        }

        spinAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAdapter.notifyDataSetChanged();
        try {
            PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_CURRENT_TIME_TABLE_STRUCTURE_ID, idarr.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        spinner.setAdapter(spinAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int strid = getIdFromName(list.get(position));
                Log.e("strid", "strid ================== " + strid);
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_CURRENT_TIME_TABLE_STRUCTURE_ID, idarr.get(position));
                sendMessage();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            startActivity(new Intent(TimeTableTabActivity.this, TeacherLandingActivity.class));
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(TimeTableTabActivity.this, TeacherLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }

    @Override
    public void onFragmentInteraction() {
    /*    list.clear();
        idarr.clear();
        timeTableStructures = TimeTableStructure.findAll(TimeTableStructure.class);

        while (timeTableStructures.hasNext()) {

            TimeTableStructure timeTableStructure = timeTableStructures.next();
            if (!list.contains(timeTableStructure.getName())) {
                list.add(timeTableStructure.getName());
                idarr.add(String.valueOf(timeTableStructure.getStructureId()));
                Log.e("log log log log log  :", idarr + "");
            }
        }
        spinAdapter.notifyDataSetChanged();*/

    }

    private void sendMessage() {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("custom-event-name");
        // You can also include some extra data.
        intent.putExtra("message", "This is my message!");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private int getIdFromName(String name) {

        Log.e("name", "name -0-0-0-0- " + name);
        timeTableStructures = TimeTableStructure.findAll(TimeTableStructure.class);
        while (timeTableStructures.hasNext()) {
            TimeTableStructure timeTableStructure = timeTableStructures.next();
            Log.e("name", "TimeTableStructure -0-0-0-0- " + timeTableStructure.getName());
            if (timeTableStructure.getName().equalsIgnoreCase(name))
                shreyid = String.valueOf(timeTableStructure.getStructureId());
            return timeTableStructure.getStructureId();
        }
        return -1;
    }
}