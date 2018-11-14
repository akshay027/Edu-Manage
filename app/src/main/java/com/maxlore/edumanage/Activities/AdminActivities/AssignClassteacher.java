package com.maxlore.edumanage.Activities.AdminActivities;

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

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.AssignClassTeacherAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.ClassteacherModel.ClassTeacher;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.ClassteacherModel.ClassTeacherResponse;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AssignClassteacher extends AppCompatActivity {

    private RecyclerView lv_classteacher;
    private ArrayList<ClassTeacher> classTeacherArrayList, finalarrayList;
    private AssignClassTeacherAdapter assignClassTeacherAdapter;
    private int currentPosition;
    private String dele = "destroy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_classteacher);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_classteacher = (RecyclerView) findViewById(R.id.lv_classteacher);
        classTeacherArrayList = new ArrayList<>();
        finalarrayList = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        lv_classteacher.setLayoutManager(llm);
    }

    private void bindDataforClassTearcher(List<ClassTeacher> classTeachers) {
        finalarrayList.clear();
        classTeacherArrayList.clear();

        classTeacherArrayList.addAll(classTeachers);
        finalarrayList.addAll(classTeachers);
        assignClassTeacherAdapter = new AssignClassTeacherAdapter(this, classTeacherArrayList);
        lv_classteacher.setAdapter(assignClassTeacherAdapter);
        assignClassTeacherAdapter.notifyDataSetChanged();
        assignClassTeacherAdapter.SetOnItemClickListener(new AssignClassTeacherAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e("onItemClick", "onItemClick ===== p-" + position);
            }

            @Override
            public void onEditClassTeacher(View view, int position) {
                editClassTeacher(position);
            }

            @Override
            public void onDeleteClassTeacher(View view, int position) {
                deleteTeacher(position);
            }

            @Override
            public void OnAssignClassTeacher(View view, int position) {
                assignTeacher(position);
            }

        });
    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        getClassTeacher();
    }

    private void editClassTeacher(final int position) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(AssignClassteacher.this);
        currentPosition = position;

        final ClassTeacher classTeacher = classTeacherArrayList.get(position);
        builder.setTitle("Confirmation");
        String message = "Are you sure to change the Class Teacher " + " ?";
        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /// TODO call Api here
                currentPosition = position;
                // final ClassTeacher classTeacher = classTeacherArrayList.get(currentPosition);
                Intent abc = new Intent(AssignClassteacher.this, EditClassTeacher.class);
                abc.putExtra("old_employee_id", String.valueOf(classTeacherArrayList.get(currentPosition).getEmployeeId()));
                abc.putExtra("old_class_teacher_id", String.valueOf(classTeacherArrayList.get(currentPosition).getClassTeacherId()));
                abc.putExtra(Constants.SECTION, classTeacherArrayList.get(currentPosition).getSectionName());
                abc.putExtra("section_id", String.valueOf(classTeacherArrayList.get(currentPosition).getSectionId()));
                abc.putExtra(Constants.STANDARD, classTeacherArrayList.get(currentPosition).getStandardName());
                ArrayList myList = new ArrayList<Integer>();
                ArrayList myclassstandardList = new ArrayList<>();
                ArrayList myclasssectionList = new ArrayList<>();
                for (int i = 0; i < classTeacherArrayList.size(); i++) {
                    if (classTeacherArrayList.get(i).getClassTeacherStatus() == Constants.SUCCESS) {
                        myList.add(classTeacherArrayList.get(i).getEmployeeId());
                        myclassstandardList.add(classTeacherArrayList.get(i).getStandardName());
                        myclasssectionList.add(classTeacherArrayList.get(i).getSectionName());
                    }
                }
                myclasssectionList.removeAll(Collections.singleton(null));
                myclassstandardList.removeAll(Collections.singleton(null));
                myList.removeAll(Collections.singleton(null));
                abc.putExtra("stdlist", myclassstandardList);
                abc.putExtra("seclist", myclasssectionList);
                abc.putExtra("mylist", myList);
                startActivity(abc);

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
            startActivity(new Intent(AssignClassteacher.this, AdminLandingActivity.class));
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
        startActivity(new Intent(AssignClassteacher.this, AdminLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
}


    private void deleteTeacher(final int position) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        currentPosition = position;

        final ClassTeacher classTeacher = classTeacherArrayList.get(position);
        builder.setTitle("Confirmation");
        String message = "Are you sure to Remove the Class Teacher  " + " ?";
        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /// TODO call Api here
                editdeleteTeacher();


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

    private void editdeleteTeacher() {
        if (UIUtil.isInternetAvailable(this)) {
            UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", classTeacherArrayList.get(currentPosition).getClassTeacherId());
            jsonObject.addProperty("perform", dele);
            if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
            } else {
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
            }
            RetrofitAPI.getInstance(this).getApi().editdeleteTeacher(jsonObject, new Callback<JsonObject>() {
                @Override
                public void success(JsonObject object, Response response) {
                    UIUtil.stopProgressDialog(getApplicationContext());


                    Log.e("jsonObject", "jsonObject --- " + object.toString());
                    try {
                        if (object == null || object.get("status").getAsInt() == 0) {
                            Toast.makeText(AssignClassteacher.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (object.get("status").getAsInt() == Constants.SUCCESS) {
                            /*classTeacherArrayList.get(currentPosition).setClassTeacherStatus(0);
                            classTeacherArrayList.get(currentPosition).setClassTeacherId(null);
                            assignClassTeacherAdapter.notifyDataSetChanged();*/
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Toast.makeText(AssignClassteacher.this, "Class teacher removed successfully...", Toast.LENGTH_SHORT).show();
                            getClassTeacher();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(AssignClassteacher.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
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


    private void assignTeacher(final int position) {
        currentPosition = position;
        // final ClassTeacher classTeacher = classTeacherArrayList.get(currentPosition);
        Intent abc = new Intent(AssignClassteacher.this, TeacherListActivity.class);
        abc.putExtra("pos", String.valueOf(classTeacherArrayList.get(currentPosition).getEmployeeId()));
        abc.putExtra(Constants.SECTION, classTeacherArrayList.get(currentPosition).getSectionName());
        abc.putExtra("section_id", String.valueOf(classTeacherArrayList.get(currentPosition).getSectionId()));
        abc.putExtra(Constants.STANDARD, classTeacherArrayList.get(currentPosition).getStandardName());
        ArrayList myList = new ArrayList<>();
        ArrayList myclassstandardList = new ArrayList<>();
        ArrayList myclasssectionList = new ArrayList<>();
        for (int i = 0; i < classTeacherArrayList.size(); i++) {
            if (classTeacherArrayList.get(i).getClassTeacherStatus() == Constants.SUCCESS) {
                myList.add(classTeacherArrayList.get(i).getEmployeeId());
                myclassstandardList.add(classTeacherArrayList.get(i).getStandardName());
                myclasssectionList.add(classTeacherArrayList.get(i).getSectionName());
            }
        }
        myclasssectionList.removeAll(Collections.singleton(null));
        myclassstandardList.removeAll(Collections.singleton(null));
        myList.removeAll(Collections.singleton(null));
        abc.putExtra("stdlist", myclassstandardList);
        abc.putExtra("seclist", myclasssectionList);
        abc.putExtra("mylist", myList);
        startActivity(abc);
    }

    private void getClassTeacher() {
        if (UIUtil.isInternetAvailable(this)) {
            UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
            Map<String, String> params = new HashMap<String, String>();
            if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
            } else {
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
            }
            RetrofitAPI.getInstance(this).getApi().getClassteacher(params, new Callback<ClassTeacherResponse>() {

                @Override
                public void success(ClassTeacherResponse classTeacherResponse, Response response) {
                    UIUtil.stopProgressDialog(getApplicationContext());
                    if (classTeacherResponse.getStatus() == Constants.SUCCESS) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        bindDataforClassTearcher(classTeacherResponse.getClassTeacher());

                    } else {
                        Toast.makeText(getApplicationContext(), "" + classTeacherResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
}
