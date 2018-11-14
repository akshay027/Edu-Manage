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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.inmegh.API.RetrofitAPI;

import com.maxlore.inmegh.Adapters.TeacherAdapters.DailyRecyclerAdapter;
import com.maxlore.inmegh.Models.TeacherModels.Academics.DailySessionPlan;
import com.maxlore.inmegh.Models.TeacherModels.Academics.DailySessionResponse;
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

*
 * Created by maxlore on 8/9/2016.


public class DailyFragment extends Fragment {

    RecyclerView recyclerViewdaily;
    DailyRecyclerAdapter dailyRecyclerAdapter;
    ArrayList<DailySessionPlan> dailyarrayList;

    ArrayAdapter<String> sectionAdapter, standardAdapter, subjectAdapter;
    ArrayList<String> sectionArrayList, standardArrayList, subjectArrayList;
    ArrayList<String> finalsectionarrayList;
    ArrayList<String> finalstandardarrayList;
    ArrayList<String> finalsubjectarrayList;

    View remarkview;
    Spinner sectionSpinner, standardSpinner, subjectSpinner;
    List<Session> sessionArrayList;
    private Button dailyCreatebtn;
    private String standard, section, subject;
    AlertDialog levelDialog;
    int dialogposition;
    int currentPosition;
    private DailySessionPlan plan;

    TextView nodataavail,daily_tv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily, container, false);

        recyclerViewdaily = (RecyclerView) view.findViewById(R.id.lv_Daily);
        List<String> cityName = new ArrayList<String>();
        dailyCreatebtn = (Button) view.findViewById(R.id.CreateDaily_btn);

        standardSpinner = (Spinner) view.findViewById(R.id.Dailyspinner1);
        sectionSpinner = (Spinner) view.findViewById(R.id.Dailyspinner2);
        subjectSpinner = (Spinner) view.findViewById(R.id.Dailyspinner3);


        sectionArrayList = new ArrayList();
        standardArrayList = new ArrayList();
        subjectArrayList = new ArrayList();

        dailyarrayList = new ArrayList<>();
        dailyRecyclerAdapter = new DailyRecyclerAdapter(getActivity(), dailyarrayList);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerViewdaily.setAdapter(dailyRecyclerAdapter);
        recyclerViewdaily.setLayoutManager(llm);
        dailyRecyclerAdapter.notifyDataSetChanged();

        finalsectionarrayList = new ArrayList();
        finalstandardarrayList = new ArrayList();
        finalsubjectarrayList = new ArrayList<>();

        sectionArrayList.add("SELECT SECTION");
        subjectArrayList.add("SELECT SUBJECT");
        sessionArrayList = new ArrayList<>();

        daily_tv = (TextView) view.findViewById(R.id.dailynotavail_tv);
        nodataavail = (TextView) view.findViewById(R.id.nodataavail);

        sectionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sectionArrayList);
        standardAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, standardArrayList);
        subjectAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, subjectArrayList);

        sectionSpinner.setAdapter(sectionAdapter);
        standardSpinner.setAdapter(standardAdapter);
        subjectSpinner.setAdapter(subjectAdapter);


        getSessionDetail();
        sectionArrayList = new ArrayList<>();
        remarkview = (View) view.findViewById(R.id.remarkview_daily);

        sectionSpinner.setEnabled(true);
        subjectSpinner.setEnabled(true);

 listViewdaily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                listItem = (int) listViewdaily.getItemAtPosition(position);
                remarkview = (View) view.findViewById(R.id.remarkview_week);
               view.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Log.e("on click","-----------------"+listItem);
                   }
               });

            }
        });


        dailyRecyclerAdapter.SetOnItemClickListener(new DailyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onAckClick(View s, int position) {
                statusConfirmation(position);
            }
        });
        dailyCreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateButtonSelect();

            }
        });
        return view;
    }


    private void statusConfirmation(final int position) {
        currentPosition = position;

        plan = dailyarrayList.get(position);
        int selected = -1;

        if (plan.getRemark() != 0)
            selected = plan.getRemark() - 1;

        final CharSequence[] items = {" Not Started", " On Progress ", " Completed "};

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Change Status");
        builder.setSingleChoiceItems(items, selected, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                dialogposition = item;
                markAcademics(plan.getDailyPlanId(), (dialogposition + 1));
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
        jsonObject.addProperty("type", Constants.DAILYFRAGMENT);


        RetrofitAPI.getInstance(getActivity()).getApi().markremark(jsonObject, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject object, Response response) {
                try {
                    if (object == null) {
                        return;
                    }


                    if (object.get("status").getAsInt() == 1) {
                        plan.setRemark(remarkId);
                        dailyarrayList.set(currentPosition, plan);
                        dailyRecyclerAdapter.notifyDataSetChanged();

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

    public void onCreateButtonSelect() {
        recyclerViewdaily.setVisibility(View.VISIBLE);
        dailyarrayList.clear();

        if (validSpinnerData()) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", Constants.DAILYFRAGMENT);
            jsonObject.addProperty("standard_id", getStandardId());
            jsonObject.addProperty("section_id", getSectionId());
            jsonObject.addProperty("subject_id", getSubjectId());

            Log.e("Json", "JSON ----- " + jsonObject.toString());

            RetrofitAPI.getInstance(getActivity()).getApi().getDailyAcademicPlan(jsonObject, new Callback<DailySessionResponse>() {
                @Override
                public void success(DailySessionResponse dailySessionResponse, Response response) {

                    if (dailySessionResponse.getStatus() == Constants.SUCCESS) {
                        dailyarrayList.clear();
                        dailyarrayList.addAll(dailySessionResponse.getSessionPlan());
                        Log.e("Example", "--------------- --" + dailyarrayList.toString());
                        dailyRecyclerAdapter.notifyDataSetChanged();
                        if (dailyarrayList.size() <= 0) {
                            recyclerViewdaily.setVisibility(View.GONE);
                            daily_tv.setVisibility(View.VISIBLE);
                            nodataavail.setVisibility(View.GONE);
                        } else {
                            recyclerViewdaily.setVisibility(View.VISIBLE);
                            daily_tv.setVisibility(View.GONE);
                            nodataavail.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(getActivity(), "" + dailySessionResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
 sectionArrayList= new ArrayList<Section>();
                dailyarrayList.clear();
                finalArrayList.clear();
                finalArrayList.addAll(timeTableResponse);
                arrayList.addAll(students);
                Log.e("Example", " --" + students.toString());
                timeTableAdapter.notifyDataSetChanged();

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

 sectionArrayList= new ArrayList<Section>();
                dailyarrayList.clear();
                finalArrayList.clear();
                finalArrayList.addAll(timeTableResponse);
                dailyarrayList.addAll(students);
                Log.e("Example", " --" + students.toString());
                timeTableAdapter.notifyDataSetChanged();

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
                    sectionSpinner.setClickable(false);
                    subjectSpinner.setClickable(false);
                    standard = standardArrayList.get(position);
                } else {
                    subjectArrayList.clear();
                    sectionSpinner.setClickable(true);
                    subjectSpinner.setClickable(false);
                    standard = standardArrayList.get(position);
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
