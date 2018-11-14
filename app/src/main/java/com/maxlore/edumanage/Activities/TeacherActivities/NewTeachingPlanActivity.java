package com.maxlore.edumanage.Activities.TeacherActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.TeacherAdapters.NewTeachingPlanAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.Newteachingplan.NewTeachingPlanData;
import com.maxlore.edumanage.Models.TeacherModels.Newteachingplan.NewTeachingResponse;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewTeachingPlanActivity extends AppCompatActivity {
    private Spinner spinnerselectclass;
    private RecyclerView teachingplanRecycler;
    private ArrayList<NewTeachingPlanData> newTeachingPlanDataArrayList, searchArraylist;
    private ArrayList<String> spinnerclassArraylist;
    private NewTeachingPlanAdapter newTeachingPlanAdapter;
    private ArrayAdapter<String> spinnerclassadapter;
    private String classname = "";
    private EditText etSearchSubject;
    public static final int TIME_OUT = 1000;
    private Handler handler;
    private TextView refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_teaching_plan);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinnerselectclass = (Spinner) findViewById(R.id.spinnerselectclass);
        teachingplanRecycler = (RecyclerView) findViewById(R.id.teachingplanRecycler);
        refresh = (TextView) findViewById(R.id.refresh);
        etSearchSubject = (EditText) findViewById(R.id.etSearchSubject);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        teachingplanRecycler.setLayoutManager(llm);


        newTeachingPlanDataArrayList = new ArrayList<>();
        spinnerclassArraylist = new ArrayList<>();
        searchArraylist = new ArrayList<>();

        handler = new Handler();

        newTeachingPlanAdapter = new NewTeachingPlanAdapter(this, newTeachingPlanDataArrayList);
        teachingplanRecycler.setAdapter(newTeachingPlanAdapter);
        newTeachingPlanAdapter.notifyDataSetChanged();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etSearchSubject.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearchSubject.setText("");
                getNewTeachingPlanDetail();
            }
        });
        newTeachingPlanAdapter.SetOnItemClickListener(new NewTeachingPlanAdapter.OnItemClickListener() {
            @Override
            public void onyearlyselect(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), AcademicsYearDataActivity.class);
                intent.putExtra("secid", String.valueOf(newTeachingPlanDataArrayList.get(position).getSectionId()));
                intent.putExtra("subid", String.valueOf(newTeachingPlanDataArrayList.get(position).getSubjectId()));
                startActivity(intent);
            }

            @Override
            public void onmonthlyselect(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), AcademicsMonthDataActivity.class);
                intent.putExtra("secid", String.valueOf(newTeachingPlanDataArrayList.get(position).getSectionId()));
                intent.putExtra("subid", String.valueOf(newTeachingPlanDataArrayList.get(position).getSubjectId()));
                startActivity(intent);
            }

            @Override
            public void ondailyselect(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), AcademicsDailyDataActivity.class);
                intent.putExtra("secid", String.valueOf(newTeachingPlanDataArrayList.get(position).getSectionId()));
                intent.putExtra("subid", String.valueOf(newTeachingPlanDataArrayList.get(position).getSubjectId()));
                startActivity(intent);
            }
        });

        etSearchSubject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.length() > 2) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            filterSearch1(s.toString());
                        }
                    }, TIME_OUT);
                } else {
                    if (searchArraylist.size() > 0) {
                        newTeachingPlanDataArrayList.clear();
                        newTeachingPlanDataArrayList.addAll(searchArraylist);
                        newTeachingPlanAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        getNewTeachingPlanDetail();
    }

    private void filterSearch1(String search) {
        try {
            newTeachingPlanDataArrayList.clear();
            for (int i = 0; i < searchArraylist.size(); i++) {
                NewTeachingPlanData newTeachingPlanData = searchArraylist.get(i);
                if (!classname.isEmpty()) {
                    if (newTeachingPlanData.getSubject().toLowerCase().contains(search.toLowerCase()) && newTeachingPlanData.getClassName().toLowerCase().contains(classname.toLowerCase())) {
                        newTeachingPlanDataArrayList.add(newTeachingPlanData);
                    }
                } else if (newTeachingPlanData.getSubject().toLowerCase().contains(search.toLowerCase())) {
                    newTeachingPlanDataArrayList.add(newTeachingPlanData);
                }

            }
            if (newTeachingPlanDataArrayList.size() <= 0) {
                etSearchSubject.setError("No Record found");
            }
            newTeachingPlanAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateclassToSpinner() {
        try {
            spinnerclassArraylist.clear();
            spinnerclassArraylist.add("Select class");
            for (int i = 0; i < newTeachingPlanDataArrayList.size(); i++) {
                NewTeachingPlanData newTeachingPlanData = newTeachingPlanDataArrayList.get(i);
                spinnerclassArraylist.add(newTeachingPlanData.getClassName());
            }
          /*  for (int i = 0; i < spinnerclassArraylist.size(); i++) {
                for (int j = 1; j < spinnerclassArraylist.size(); j++) {
                    if (spinnerclassArraylist.get(i).equals(spinnerclassArraylist.get(j))) {
                        spinnerclassArraylist.remove(i);
                    }
                }
            }*/
            spinnerclassadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerclassArraylist);
            spinnerselectclass.setAdapter(spinnerclassadapter);
            spinnerclassadapter.notifyDataSetChanged();

            spinnerselectclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        classname = "";
                    } else {
                        classname = spinnerclassArraylist.get(position);
                        filterSearch(classname);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
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
            startActivity(new Intent(NewTeachingPlanActivity.this, TeacherLandingActivity.class));
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
        }
        if (id == R.id.app_info) {
            alertboxpopup();
        }

        return super.onOptionsItemSelected(item);
    }

    private AlertDialog alertboxpopup() {
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
        startActivity(new Intent(NewTeachingPlanActivity.this, TeacherLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }

    public void getNewTeachingPlanDetail() {
        try {
            if (UIUtil.isInternetAvailable(getApplicationContext())) {
                UIUtil.startProgressDialog(getApplicationContext(), "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(getApplicationContext()).getApi().getSession(params, new Callback<NewTeachingResponse>() {
                    @Override
                    public void success(NewTeachingResponse sessionResponse, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            if (sessionResponse.getStatus() == Constants.SUCCESS) {
                                newTeachingPlanDataArrayList.clear();
                                searchArraylist.clear();
                                newTeachingPlanDataArrayList.addAll(sessionResponse.getTeachingPlans());
                                searchArraylist.addAll(sessionResponse.getTeachingPlans());
                                newTeachingPlanAdapter.notifyDataSetChanged();
                                updateclassToSpinner();

                            } else {
                                Toast.makeText(getApplicationContext(), sessionResponse.getMessage(), Toast.LENGTH_LONG).show();
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

            } else

            {
                Toast.makeText(getApplicationContext(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterSearch(String search) {
        try {
            newTeachingPlanDataArrayList.clear();

            for (int i = 0; i < searchArraylist.size(); i++) {
                NewTeachingPlanData newTeachingPlanData = searchArraylist.get(i);
                if (newTeachingPlanData.getClassName().toLowerCase().contains(search.toLowerCase())) {
                    newTeachingPlanDataArrayList.add(newTeachingPlanData);
                }
            }
            if (newTeachingPlanDataArrayList.size() <= 0) {
                Toast.makeText(getApplicationContext(), "No record found !!", Toast.LENGTH_SHORT).show();
            }
            newTeachingPlanAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


