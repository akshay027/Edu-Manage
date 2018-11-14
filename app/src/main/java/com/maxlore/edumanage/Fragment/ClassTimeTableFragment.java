package com.maxlore.edumanage.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;

import com.maxlore.edumanage.Activities.TeacherActivities.TimeTableDateAdapter;
import com.maxlore.edumanage.Adapters.TeacherAdapters.NavigationTimeTableAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.database.TimeTable;
import com.maxlore.edumanage.Models.TeacherModels.TimeTable.TimeTableResponse;
import com.maxlore.edumanage.Models.TeacherModels.database.TimeTableStructure;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ClassTimeTableFragment extends Fragment {

    private ListView lvdate;
    private ListView lvperiod;
    private TextView time_tv;
    private NavigationTimeTableAdapter timeTableAdapter;
    private ArrayList<TimeTable> arrayList;
    private ArrayList<TimeTable> timetableArrayList;
    private TextView noschedule_tv, periodTeacher;
    private ArrayAdapter noscheduleadapter;
    public int day;
    private String branch_id;
    private OnFragmentInteractionListener mListener;
    private String currentDay = "";
    public ArrayList<TimeTableStructure> timeTableSpinnerArrayList;
    private ArrayAdapter<String> structureAdapter;
    private ArrayAdapter<String> adapter;
    private String selectType;
    private Spinner spinnerView;
    private List<String> list;


    public ClassTimeTableFragment(String branch_id) {
        this.branch_id = branch_id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_time_table, container, false);


        lvdate = (ListView) view.findViewById(R.id.lvDate);
        time_tv = (TextView) view.findViewById(R.id.dateday_tv);
        lvperiod = (ListView) view.findViewById(R.id.lvPeriod);

        periodTeacher = (TextView) view.findViewById(R.id.ClassTeacher_tv);

        noschedule_tv = (TextView) view.findViewById(R.id.tv_noSchedule);
        noschedule_tv = (TextView) view.findViewById(R.id.tv_noSchedule);
        spinnerView = (Spinner) view.findViewById(R.id.spinnerView);
        lvdate = (ListView) view.findViewById(R.id.lvDate);
        time_tv = (TextView) view.findViewById(R.id.dateday_tv);
        lvperiod = (ListView) view.findViewById(R.id.lvPeriod);
        periodTeacher = (TextView) view.findViewById(R.id.ClassTeacher_tv);
       // finalArrayList = new ArrayList<>();
        arrayList = new ArrayList();
        list = new ArrayList<>();
        timeTableSpinnerArrayList=new ArrayList<>();
        timetableArrayList = new ArrayList();

        arrayList = new ArrayList<>();
        timeTableAdapter = new NavigationTimeTableAdapter(getActivity(), arrayList);
        lvperiod.setAdapter(timeTableAdapter);
        timeTableAdapter.notifyDataSetChanged();


        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        SimpleDateFormat format = new SimpleDateFormat("dd\n" + "EEE");
        String[] days = new String[7];
        int weekdays = -calendar.get(GregorianCalendar.DAY_OF_WEEK) + 1;
        calendar.add(Calendar.DAY_OF_MONTH, weekdays);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        currentDay = sdf.format(d);


        ArrayList<String> arrayListDate = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            arrayListDate.add(days[i]);
        }

        final TimeTableDateAdapter dateAdapter = new TimeTableDateAdapter(getActivity(), arrayListDate);
        dateAdapter.setPosition(day - 1);


        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver, new IntentFilter("custom-event-name"));

//        if (!PreferencesManger.getBooleanFields(getActivity(), Constants.Pref.KEY_TIME_TABLE_DOWNLOAD_CLASS))

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser)
            getTimeTableDetail();
    }

    public void getTimeTableDetail() {
        try {
            UIUtil.startProgressDialog(getActivity(), "Please Wait.. Getting Details");
            Map<String, String> params = new HashMap<String, String>();
            UIUtil.startProgressDialog(getActivity(), "Please Wait.. Getting Details");
            params.put("branch_id", branch_id);
            params.put("class_timetable", String.valueOf(true));

            RetrofitAPI.getInstance(getActivity()).getApi().getTimeTableData(params, new Callback<TimeTableResponse>() {
                @Override
                public void success(TimeTableResponse timeTableResponse, Response response) {
                    try {
                        UIUtil.stopProgressDialog(getActivity());
                        if (timeTableResponse.getStatus() == Constants.SUCCESS) {
                            timeTableSpinnerArrayList.clear();
                            timeTableSpinnerArrayList.addAll(timeTableResponse.getTimeTableStructure());
                            Log.e("arrylist", "--------" + timeTableSpinnerArrayList);
                            updateSpinner();
                            //saveTimeTableData(timeTableResponse.getTimeTableStructure());
                            Log.e("classtimeTableResponse", " -------------" + timeTableResponse.toString() + "");
                        } else {
                            Toast.makeText(getContext(), timeTableResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                /* sectionArrayList= new ArrayList<Section>();
                arrayList.clear();
                timeTableArrayList.clear();
                timeTableArrayList.addAll(timeTableResponse);
                arrayList.addAll(students);
                Log.e("Example", " --" + students.toString());
                timeTableAdapter.notifyDataSetChanged();*/
                }

                @Override
                public void failure(RetrofitError error) {
                    UIUtil.stopProgressDialog(getActivity());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateSpinner() {
        list.clear();

        // timeTableSpinnerArrayList.add("Select Subject");
        for (int i = 0; i < timeTableSpinnerArrayList.size(); i++) {
            list.add(timeTableSpinnerArrayList.get(i).getName());

        }
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_new);
        // adapter.notifyDataSetChanged();
        spinnerView.setAdapter(adapter);
      /*  adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_color, subjectArraylist);
        subjectAdapter.setDropDownViewResource(R.layout.spinnertext);
        spinnerSubject.setAdapter(subjectAdapter);*/
        adapter.notifyDataSetChanged();

        spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                    selectType = list.get(position);
                    sendMessage();
                    filterDayWise();
                    timeTableAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void sendMessage() {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("custom-event-name");
        intent.putExtra("message", "This is my message!");
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }
    private void saveTimeTableData(List<TimeTableStructure> timeTableStructures) {
        try {
            TimeTableStructure.deleteAll(TimeTable.class);
            TimeTable.deleteAll(TimeTable.class, "OWN_CLASS = ? ", new String[]{"1"});
            TimeTableStructure.saveInTx(timeTableStructures);
            for (int i = 0; i < timeTableStructures.size(); i++) {
                TimeTableStructure timeTableStructure = timeTableStructures.get(i);
                List<TimeTable> timeTableList = timeTableStructure.getTimeTable();
                TimeTable.saveInTx(timeTableList);
            }
            filterDayWise();
            PreferencesManger.addBooleanFields(getActivity(), Constants.Pref.KEY_TIME_TABLE_DOWNLOAD_CLASS, true);
            if (mListener != null) {
                mListener.onFragmentInteraction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void filterDayWise() {
        try {

            Log.e("checkkkkkk", "hnj");
            Log.e("ttttttttttttat", selectType);
            //  Log.e("pppp", selectedStudent + "positi");

            if (timeTableSpinnerArrayList.size() > 0) {
             /*   ParentTimeTable parentTimeTable = parentTimeTableArrayList.get(selectedStudent);
                List<ParentTimeTableStructure> tableStructures = parentTimeTable.getTimeTableStructure();*/
                for (int i = 0; i < timeTableSpinnerArrayList.size(); i++) {
                    if (timeTableSpinnerArrayList.get(i).getName().equalsIgnoreCase(selectType)) {
                        timetableArrayList.clear();
                        timetableArrayList.addAll(timeTableSpinnerArrayList.get(i).getTimeTable());
                        Log.e("acvghsa=====", timetableArrayList + "");
                        break;
                    }
                }
                arrayList.clear();
                for (int i = 0; i < timetableArrayList.size(); i++) {
                    if (timetableArrayList.get(i).getDay().equalsIgnoreCase(currentDay)) {
                        arrayList.add(timetableArrayList.get(i));
                    }
                }
                timeTableAdapter.notifyDataSetChanged();
            }
            if (arrayList.size() <= 0) {
                lvperiod.setVisibility(GONE);
                noschedule_tv.setVisibility(VISIBLE);

            } else {
                lvperiod.setVisibility(VISIBLE);
                noschedule_tv.setVisibility(GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }/*
    private void filterDayWise() {
        try {
            arrayList.clear();
            final String structure = PreferencesManger.getStringFields(getContext(), Constants.Pref.KEY_CURRENT_TIME_TABLE_STRUCTURE_ID);
            Log.e("timetable", "structure -------------" + structure + "");

            arrayList.addAll(TimeTable.find(TimeTable.class, "STRUCTURE_ID = ? AND DAY = ? AND OWN_CLASS = ?  ", new String[]{structure, currentDay, "1"}));
            timeTableArrayList.addAll(TimeTable.find(TimeTable.class, "STRUCTURE_ID = ? AND DAY = ? AND OWN_CLASS = ?  ", new String[]{structure, currentDay, "1"}));
            Log.e("data", "arrayList -- " + arrayList.toString());
            Log.e("dadadad", "hjsvsh  " + timeTableArrayList.toString());
            timeTableAdapter.notifyDataSetChanged();

*//*       for (int i = 0; i < timeTableArrayList.size(); i++) {
           if (timeTableArrayList.get(i).getDay().equalsIgnoreCase(day)) {
               arrayList.add(timeTableArrayList.get(i));
                           }
        }
        timeTableAdapter.notifyDataSetChanged();*//*
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
    }*/

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
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


