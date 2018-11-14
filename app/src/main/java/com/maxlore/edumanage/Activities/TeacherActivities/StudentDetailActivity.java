package com.maxlore.edumanage.Activities.TeacherActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.TeacherAdapters.StudentRecyclerAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.StudentDetails.StudentDetail;
import com.maxlore.edumanage.Models.TeacherModels.StudentDetails.StudentResponse;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class StudentDetailActivity extends AppCompatActivity {

    private RecyclerView studentdetailsRecyclerView;
    private ArrayList<StudentDetail> StudentDetaisArraylist;
    private StudentRecyclerAdapter studentRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        studentdetailsRecyclerView = (RecyclerView) findViewById(R.id.recyclerStudentdetail);
        StudentDetaisArraylist = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        studentRecyclerAdapter = new StudentRecyclerAdapter(this, StudentDetaisArraylist);

        studentdetailsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
       // RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), 2);
        studentdetailsRecyclerView.setLayoutManager(layoutManager);
        studentdetailsRecyclerView.setAdapter(studentRecyclerAdapter);

        studentRecyclerAdapter.notifyDataSetChanged();

        getStudentDetail();

        studentRecyclerAdapter.SetOnItemClickListener(new StudentRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), StudentInformationActivity.class);
                Log.e("Position Id", "----------" + StudentDetaisArraylist.get(position).getSectionid());
                intent.putExtra(Constants.ONWARD, StudentDetaisArraylist.get(position).getSectionid());
                intent.putExtra(Constants.SECTION, StudentDetaisArraylist.get(position).getSectionName());
                intent.putExtra(Constants.STANDARD, StudentDetaisArraylist.get(position).getStandardName());
                //intent.putExtra(Constants.RETURN, arrayListOnward.get(position).getRoutingPlaceTimingId());
                //intent.putExtra(Constants.CITY, arrayListOnward.get(position).getStopName());
                startActivity(intent);
            }
        });


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
            startActivity(new Intent(StudentDetailActivity.this, TeacherLandingActivity.class));
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
        }
        if (id == R.id.app_info) {
            alertBox();
        }

        return super.onOptionsItemSelected(item);
    }

    private AlertDialog alertBox() {
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
        startActivity(new Intent(StudentDetailActivity.this, TeacherLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }


    public void getStudentDetail() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");

                studentdetailsRecyclerView.setVisibility(View.VISIBLE);
                Map<String, String> params = new HashMap<String, String>();
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));

                RetrofitAPI.getInstance(this).getApi().getstudentdetail(params, new Callback<StudentResponse>() {
                    @Override
                    public void success(StudentResponse studentResponse, Response response) {
                        try {
                            if (studentResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                StudentDetaisArraylist.clear();
                                StudentDetaisArraylist.addAll(studentResponse.getClassSection());

                                studentRecyclerAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getApplicationContext(), "" + studentResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
}
