/*
package com.maxlore.inmegh.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.inmegh.API.RetrofitAPI;
import com.maxlore.inmegh.Adapters.TeacherAdapters.MonthRecyclerAdapter;
import com.maxlore.inmegh.Models.TeacherModels.Academics.Session;
import com.maxlore.inmegh.Models.TeacherModels.Academics.SessionResponse;
import com.maxlore.inmegh.Models.TeacherModels.Academics.MonthSessionPlan;
import com.maxlore.inmegh.Models.TeacherModels.Academics.MonthSessionResponse;

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

public class MonthFragment extends Fragment {
    //ListView listViewweek;
    //ArrayList<WeekSessionPlan> monthArraylist;
    //monthAdapter monthAdapter;

    RecyclerView recyclerViewmonth;
    MonthRecyclerAdapter monthAdapter;
    ArrayList<MonthSessionPlan> monthArraylist;

    ArrayAdapter<String> sectionAdapter, standardAdapter, subjectAdapter;
    ArrayList<String> sectionArrayList, standardArrayList, subjectArrayList;
    Spinner sectionSpinner, standardSpinner, subjectSpinner;
    List<Session> sessionArrayList;
    ArrayList<String> finalsectionarrayList, finalstandardarrayList, finalsubjectarrayList;
    Button monthCreatebtn;
    private String standard, section, subject;
    AlertDialog levelDialog;
    View remark;
    int dialogposition;
    int currentPosition;
    private MonthSessionPlan plan;
    private TextView nodata_tv,nodataavail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month, container, false);

        recyclerViewmonth = (RecyclerView) view.findViewById(R.id.lv_month);
        List<String> cityName = new ArrayList<String>();
        monthCreatebtn = (Button) view.findViewById(R.id.Createmonth_btn);

        standardSpinner = (Spinner) view.findViewById(R.id.monthspinner1);
        sectionSpinner = (Spinner) view.findViewById(R.id.monthspinner2);
        subjectSpinner = (Spinner) view.findViewById(R.id.monthspinner3);

        nodata_tv=(TextView)view.findViewById(R.id.nodata_tv);
        nodataavail=(TextView)view.findViewById(R.id.nodataavail);
        remark = (View) view.findViewById(R.id.remarkview_month);

        sectionArrayList = new ArrayList();
        standardArrayList = new ArrayList();
        subjectArrayList = new ArrayList();

        finalsectionarrayList = new ArrayList();
        finalstandardarrayList = new ArrayList();
        finalsubjectarrayList = new ArrayList<>();

        monthArraylist = new ArrayList<>();
        monthAdapter = new MonthRecyclerAdapter(getActivity(), monthArraylist);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerViewmonth.setLayoutManager(llm);
        recyclerViewmonth.setAdapter(monthAdapter);
        monthAdapter.notifyDataSetChanged();


        sectionArrayList.add("SELECT SECTION");
        subjectArrayList.add("SELECT SUBJECT");

        sessionArrayList = new ArrayList<>();

        sectionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sectionArrayList);
        standardAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, standardArrayList);
        subjectAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, subjectArrayList);


        standardSpinner.setAdapter(standardAdapter);
        sectionSpinner.setAdapter(sectionAdapter);
        subjectSpinner.setAdapter(subjectAdapter);

        sectionArrayList = new ArrayList<>();

        monthAdapter.SetOnItemClickListener(new MonthRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onAckClick(View s, int position) {
                statusConfirmation(position);
            }
        });


        getSessionDetail();
        monthCreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnCreateButtonSelect();

            }
        });
        return view;
    }

    private void statusConfirmation(final int position) {

        currentPosition = position;
        plan = monthArraylist.get(position);
        int selected = -1;
        if (plan.getRemark() != 0)
            selected = plan.getRemark() - 1;
        final CharSequence[] items = {" Not Started ", " On Progress ", " Completed "};

        // Creating and Building the Dialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Change Status");
        builder.setSingleChoiceItems(items, selected, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setClickable(true);
                dialogposition = item;
                if (dialogposition != -1) {
                    markAcademics(plan.getSubTopicId(), (dialogposition + 1));
                }

                levelDialog.dismiss();
            }
        });
        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        dialogposition = -1;
        levelDialog = builder.create();
        levelDialog.show();

    }

    private void markAcademics(int planId, final int remarkId) {


        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("remark", remarkId);
        jsonObject.addProperty("plan_id", planId);
        jsonObject.addProperty("type", Constants.WEEKFRAGMENT);


        RetrofitAPI.getInstance(getActivity()).getApi().markremark(jsonObject, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject object, Response response) {
                try {
                    if (object == null) {
                        return;
                    }


                    if (object.get("status").getAsInt() == 1) {

                        plan.setRemark(remarkId);
                        monthArraylist.set(currentPosition, plan);
                        monthAdapter.notifyDataSetChanged();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }


    public void OnCreateButtonSelect() {

        recyclerViewmonth.setVisibility(View.VISIBLE);
        if (validSpinnerData()) {


            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", Constants.WEEKFRAGMENT);
            jsonObject.addProperty("standard_id", getStandardId());
            jsonObject.addProperty("section_id", getSectionId());
            jsonObject.addProperty("subject_id", getSubjectId());

            Log.e("Json", "JSON ----- " + jsonObject.toString());


            RetrofitAPI.getInstance(getActivity()).getApi().getWeekAcademicPlan(jsonObject, new Callback<MonthSessionResponse>() {
                @Override
                public void success(MonthSessionResponse weekSessionResponse, Response response) {


                    if (weekSessionResponse.getStatus() == Constants.SUCCESS) {
                        monthArraylist.clear();
                        monthArraylist.addAll(weekSessionResponse.getSessionPlan());
                        Log.e("Example", "--------------- --" + monthArraylist.toString());
                        monthAdapter.notifyDataSetChanged();
                        if(monthArraylist.size()<=0)
                        {
                            recyclerViewmonth.setVisibility(View.GONE);
                            nodata_tv.setVisibility(View.VISIBLE);
                            nodataavail.setVisibility(View.GONE);
                        }
                        else
                        {
                            recyclerViewmonth.setVisibility(View.VISIBLE);
                            nodata_tv.setVisibility(View.GONE);
                            nodataavail.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(getActivity(), "" + weekSessionResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
                    subjectArrayList.clear();
                    sectionSpinner.setClickable(false);
                    subjectSpinner.setClickable(false);
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
