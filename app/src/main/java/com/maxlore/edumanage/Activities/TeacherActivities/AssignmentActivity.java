package com.maxlore.edumanage.Activities.TeacherActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.TeacherAdapters.AssigmentsectionAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.AssignmentNavigationbar.Assignment;
import com.maxlore.edumanage.Models.TeacherModels.AssignmentNavigationbar.AssignmentStructure;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AssignmentActivity extends AppCompatActivity {
    private ListView assignmentlist;
    private ArrayList<Assignment> assignmentArraylist;
    private AssigmentsectionAdapter assignmentAdapter;
    private Integer getid;
    private boolean session;
    private int currentposition;
    private TextView tv_nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_nodata = (TextView) findViewById(R.id.tv_nodata);
        assignmentlist = (ListView) findViewById(R.id.lv_assignment);
        assignmentArraylist = new ArrayList<>();

        session = true;
        assignmentAdapter = new AssigmentsectionAdapter(this, assignmentArraylist, session);

        Log.e("", assignmentlist.toString());

        getAssignmentData();
//        assignmentlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(AssignmentActivity.this, AssignmentDetail.class);
//                intent.putExtra("id", String.valueOf(assignmentArraylist.get(position).getId()));
//                startActivity(intent);
//
//            }
//        });
    }

  /*  @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        getAssignmentData();
    }*/

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
            startActivity(new Intent(AssignmentActivity.this, TeacherLandingActivity.class));
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
        startActivity(new Intent(AssignmentActivity.this, TeacherLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }

    private void getAssignmentData() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().getAssignmentData(params, new Callback<AssignmentStructure>() {

                    @Override
                    public void success(AssignmentStructure assignmentStructures, Response response) {
                        try {
                            if (assignmentStructures.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                assignmentArraylist.clear();
                                session = assignmentStructures.getSession();
                                Log.e("sessionnnnnn :   ", String.valueOf(session));
                                assignmentArraylist.addAll(assignmentStructures.getAssignment());
                                assignmentlist.setAdapter(assignmentAdapter);
                                Collections.sort(assignmentArraylist, new PersonComparator());
                                assignmentAdapter.notifyDataSetChanged();
                                if (assignmentArraylist.size() <= 0) {
                                    assignmentlist.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                } else {
                                    UIUtil.stopProgressDialog(getApplicationContext());
                                    assignmentlist.setVisibility(View.VISIBLE);
                                    tv_nodata.setVisibility(View.GONE);
                                }
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "" + assignmentStructures.getMessage(), Toast.LENGTH_SHORT).show();
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


    public class PersonComparator implements Comparator<Assignment> {
        public int compare(Assignment o1, Assignment o2) {
            // Assume no nulls, and simple ordinal comparisons

            int value1 = o1.getStandard().compareTo(o2.getStandard());
            if (value1 == 0) {
                return o1.getSection().compareTo(o2.getSection());
            }
            return value1;
        }
    }

}

