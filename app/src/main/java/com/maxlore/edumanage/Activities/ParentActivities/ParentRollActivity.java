package com.maxlore.edumanage.Activities.ParentActivities;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.StudentRoleResponse;
import com.maxlore.edumanage.Models.TeacherModels.StudentsRole;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ParentRollActivity extends AppCompatActivity {

    private ArrayList<StudentsRole> roleArrayList;
    private int dialogposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_roll);
        this.setTheme(R.style.AlertDialogCustom);
        roleArrayList = new ArrayList<>();

        getParentRole();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }

    private void getParentRole() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                //UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                RetrofitAPI.getInstance(this).getApi().getparentroll(params, new Callback<StudentRoleResponse>() {
                    @Override
                    public void success(StudentRoleResponse studentRoleResponse, Response response) {
                        try {
                            if (studentRoleResponse.getStatus() == Constants.SUCCESS) {
                                // UIUtil.stopProgressDialog(getApplicationContext());
                                roleArrayList.clear();
                                roleArrayList.addAll(studentRoleResponse.getStudents());
                                Log.e("data", "======" + roleArrayList);
                                getAlert();

                            } else {
                                Toast.makeText(getApplicationContext(), "" + studentRoleResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void getAlert() {
        try {
            int selected = -1;

            Log.e("list", "======" + roleArrayList);

            final CharSequence[] items = new String[roleArrayList.size()];
            String[] list = new String[roleArrayList.size()];
            for (int i = 0; i < roleArrayList.size(); i++) {
                items[i] = roleArrayList.get(i).getName();
            }
            if (roleArrayList.size() == 1) {
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_NAME, String.valueOf(roleArrayList.get(0).getName()));
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_Id, String.valueOf(roleArrayList.get(0).getId()));
                Intent intent = new Intent(getApplicationContext(), ParentLandingActivity.class);
                Toast.makeText(getApplicationContext(), "Only one child", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finishAffinity();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle("Select Child :");
                builder.setSingleChoiceItems(items, selected, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        dialogposition = item;
                        Log.e("itemposition", "------" + roleArrayList.get(dialogposition).getId());
                        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_NAME, String.valueOf(roleArrayList.get(dialogposition).getName()));

                        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_Id, String.valueOf(roleArrayList.get(dialogposition).getId()));
                        Intent intent = new Intent(getApplicationContext(), ParentLandingActivity.class);
                        //intent.putExtra("id", dialogposition);
                        startActivity(intent);
                        finishAffinity();

                    }
                });

               /* builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(getApplicationContext(), ParentLandingActivity.class);

                        startActivity(intent);
                    }
                });*/
                dialogposition = -1;
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                //alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                // Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                //nbutton.setTextColor(R.color.black);

            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}


