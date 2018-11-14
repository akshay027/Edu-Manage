/*
package com.maxlore.inmegh.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.inmegh.API.RetrofitAPI;


import com.maxlore.inmegh.Adapters.TeacherAdapters.YearAdapter;
import com.maxlore.inmegh.Models.TeacherModels.Academics.YearSessionPlan;
import com.maxlore.inmegh.Models.TeacherModels.Academics.YearSessionResponse;
import com.maxlore.inmegh.Models.TeacherModels.Academics.Session;
import com.maxlore.inmegh.Models.TeacherModels.Academics.SessionResponse;
import com.maxlore.inmegh.R;
import com.maxlore.inmegh.Utility.Constants;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

*/
/**
 * Created by maxlore on 8/9/2016.
 *//*

public class YearFragment extends Fragment {
    ListView listViewYear;
    ArrayAdapter<String> sectionAdapter, standardAdapter, subjectAdapter;
    ArrayList<String> sectionArrayList, standardArrayList, subjectArrayList;
    ArrayList<String> finalsectionarrayList, finalstandardarrayList, finalsubjectarrayList;
    Spinner sectionSpinner, standardSpinner, subjectSpinner;
    List<Session> sessionArrayList;
    Button YearCreatebtn;
    private String standard, section, subject;
    ArrayList<YearSessionPlan> YearArraylist;
    YearAdapter YearAdapter;
    private TextView nodata_tv, nodataavail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_year, container, false);

        listViewYear = (ListView) view.findViewById(R.id.lv_Year);
        List<String> cityName = new ArrayList<String>();
        YearCreatebtn = (Button) view.findViewById(R.id.CreateYear_btn);

        standardSpinner = (Spinner) view.findViewById(R.id.Yearspinner1);
        sectionSpinner = (Spinner) view.findViewById(R.id.Yearspinner2);
        subjectSpinner = (Spinner) view.findViewById(R.id.Yearspinner3);

        nodata_tv=(TextView)view.findViewById(R.id.nodata_tv);
        nodataavail=(TextView)view.findViewById(R.id.nodataavail);
        sectionArrayList = new ArrayList();
        standardArrayList = new ArrayList();
        subjectArrayList = new ArrayList();

        finalsectionarrayList = new ArrayList();
        finalstandardarrayList = new ArrayList();
        finalsubjectarrayList = new ArrayList<>();

        sectionArrayList.add("SELECT SECTION");
        subjectArrayList.add("SELECT SUBJECT");

        sectionSpinner.setEnabled(true);
        subjectSpinner.setEnabled(true);

        YearArraylist = new ArrayList<>();
        YearAdapter = new YearAdapter(getActivity(), YearArraylist);
        listViewYear.setAdapter(YearAdapter);

        sessionArrayList = new ArrayList<>();

        sectionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sectionArrayList);
        standardAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, standardArrayList);
        subjectAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, subjectArrayList);

        sectionSpinner.setAdapter(sectionAdapter);
        standardSpinner.setAdapter(standardAdapter);
        subjectSpinner.setAdapter(subjectAdapter);

        sectionArrayList = new ArrayList<>();
        getSessionDetail();

        YearCreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateButtonSelect();
            }
        });


        return view;
    }


    private void onCreateButtonSelect() {
        listViewYear.setVisibility(View.VISIBLE);

        if (validSpinnerData()) {


            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", Constants.MONTHFRAGMENT);
            jsonObject.addProperty("standard_id", getStandardId());
            jsonObject.addProperty("section_id", getSectionId());
            jsonObject.addProperty("subject_id", getSubjectId());

            Log.e("Json", "JSON ----- " + jsonObject.toString());

            RetrofitAPI.getInstance(getActivity()).getApi().getMonthlyAcademicPlan(jsonObject, new Callback<YearSessionResponse>() {
                @Override
                public void success(YearSessionResponse monthSessionResponse, Response response) {

                    if (monthSessionResponse.getStatus() == Constants.SUCCESS) {
                        YearArraylist.clear();
                        YearArraylist.addAll(monthSessionResponse.getSessionPlan());
                        Log.e("Example", "--------------- --" + YearArraylist.toString());
                        YearAdapter.notifyDataSetChanged();
                        if(YearArraylist.size()<=0)
                        {
                            listViewYear.setVisibility(View.GONE);
                            nodata_tv.setVisibility(View.VISIBLE);
                            nodataavail.setVisibility(View.GONE);
                        }
                        else
                        {
                            listViewYear.setVisibility(View.VISIBLE);
                            nodata_tv.setVisibility(View.GONE);
                            nodataavail.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(getActivity(), "" + monthSessionResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                */
/* sectionArrayList= new ArrayList<Section>();
                arrayList.clear();
                finalArrayList.clear();
                finalArrayList.addAll(timeTableResponse);
                arrayList.addAll(students);
                Log.e("Example", " --" + students.toString());
                timeTableAdapter.notifyDataSetChanged();*//*

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }

    private boolean validSpinnerData() {
        if (TextUtils.isEmpty(standard) || standard.equalsIgnoreCase("SELECT STANDARD")) {

            Toast.makeText(getActivity(), "Select Standard", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(section) || section.equalsIgnoreCase("SELECT SECTION")) {

            Toast.makeText(getActivity(), "Select section", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(subject) || subject.equalsIgnoreCase("SELECT SUBJECT")) {

            Toast.makeText(getActivity(), "Select subject", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void getSessionDetail() {


        RetrofitAPI.getInstance(getActivity()).getApi().getSession(new Callback<SessionResponse>() {
            @Override
            public void success(SessionResponse sessionResponse, Response response) {
                if (sessionResponse.getStatus() == Constants.SUCCESS) {
                    standardArrayList.clear();
                    sectionArrayList.clear();
                    subjectArrayList.clear();
                    sessionArrayList.addAll(sessionResponse.getSession());

                    Log.e("List ", sessionArrayList.toString());
                    updateStandardToSpinner();
                } else {
                    Toast.makeText(getContext(), sessionResponse.getMessage(), Toast.LENGTH_LONG).show();
                }

                */
/* sectionArrayList= new ArrayList<Section>();
                arrayList.clear();
                finalArrayList.clear();
                finalArrayList.addAll(timeTableResponse);
                arrayList.addAll(students);
                Log.e("Example", " --" + students.toString());
                timeTableAdapter.notifyDataSetChanged();*//*

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    private void updateStandardToSpinner() {
        standardArrayList.clear();
        standardArrayList.add("SELECT STANDARD");
        for (int i = 0; i < sessionArrayList.size(); i++) {
            Session session = sessionArrayList.get(i);
            if (!standardArrayList.contains(session.getStandardName()))
                standardArrayList.add(session.getStandardName());
        }

        Log.e("updateStandardToSpinner", standardArrayList.toString());
        standardAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, standardArrayList);
        standardSpinner.setAdapter(standardAdapter);
        standardAdapter.notifyDataSetChanged();

        standardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0) {
                    sectionArrayList.clear();
                    standard = standardArrayList.get(position);
                    sectionSpinner.setClickable(false);
                    subjectSpinner.setClickable(false);
                    subjectArrayList.clear();

                } else {
                    standard = standardArrayList.get(position);
                    sectionSpinner.setClickable(true);
                    subjectSpinner.setClickable(false);
                    updateSectionToSpinner();
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void updateSectionToSpinner() {

        sectionArrayList.clear();
        sectionArrayList.add("SELECT SECTION");
        for (int i = 0; i < sessionArrayList.size(); i++) {
            Session session = sessionArrayList.get(i);
            if (!sectionArrayList.contains(session.getSectionName()) && session.getStandardName().equalsIgnoreCase(standard))
                sectionArrayList.add(session.getSectionName());
        }


        Log.e("updateSectionToSpinner ", sectionArrayList.toString());
        sectionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sectionArrayList);
        sectionSpinner.setAdapter(sectionAdapter);
        sectionAdapter.notifyDataSetChanged();
        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    subjectArrayList.clear();
                    subjectSpinner.setClickable(false);
                    section = sectionArrayList.get(position);
                } else {
                    section = sectionArrayList.get(position);
                    subjectSpinner.setClickable(true);

                    updateSubjectToSpinner();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void updateSubjectToSpinner() {

        subjectArrayList.clear();
        subjectArrayList.add("SELECT SUBJECT");
        for (int i = 0; i < sessionArrayList.size(); i++) {
            Session session = sessionArrayList.get(i);
            if (!subjectArrayList.contains(session.getSubjectName())
                    && session.getStandardName().equalsIgnoreCase(standard))
                subjectArrayList.add(session.getSubjectName());
        }


        Log.e("updateSubjectToSpinner ", subjectArrayList.toString());
        subjectAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, subjectArrayList);
        subjectSpinner.setAdapter(subjectAdapter);
        subjectAdapter.notifyDataSetChanged();

        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject = subjectArrayList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private int getStandardId() {
        for (int i = 0; i < sessionArrayList.size(); i++) {
            if (sessionArrayList.get(i).getStandardName().equalsIgnoreCase(standard)) {
                return sessionArrayList.get(i).getStandardId();
            }
        }
        return -1;
    }


    private int getSectionId() {
        for (int i = 0; i < sessionArrayList.size(); i++) {
            if (sessionArrayList.get(i).getStandardName().equalsIgnoreCase(standard)
                    && sessionArrayList.get(i).getSectionName().equalsIgnoreCase(section)) {
                return sessionArrayList.get(i).getSectionId();
            }
        }
        return -1;
    }

    private int getSubjectId() {
        for (int i = 0; i < sessionArrayList.size(); i++) {
            if (sessionArrayList.get(i).getStandardName().equalsIgnoreCase(standard)
                    && sessionArrayList.get(i).getSectionName().equalsIgnoreCase(section)
                    && sessionArrayList.get(i).getSubjectName().equalsIgnoreCase(subject)) {
                return sessionArrayList.get(i).getSubjectId();
            }
        }
        return -1;
    }
}

*/
