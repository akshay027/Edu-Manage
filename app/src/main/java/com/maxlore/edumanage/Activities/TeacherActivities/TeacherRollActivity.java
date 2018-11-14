package com.maxlore.edumanage.Activities.TeacherActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Activities.AdminActivities.AdminLandingActivity;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.TeacherRoleResponse;
import com.maxlore.edumanage.Models.TeacherRole;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by maxlore on 30-Aug-17.
 */

public class TeacherRollActivity extends AppCompatActivity {

    private ArrayList<TeacherRole> roleArrayList;
    private int dialogposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_roll);
        this.setTheme(R.style.AlertDialogCustom);
        roleArrayList = new ArrayList<>();

        getTeacherRole();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }

    private void getTeacherRole() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                //UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                RetrofitAPI.getInstance(this).getApi().getteacherrole(params, new Callback<TeacherRoleResponse>() {
                    @Override
                    public void success(TeacherRoleResponse teacherRoleResponse, Response response) {
                        try {
                            if (teacherRoleResponse.getStatus() == Constants.SUCCESS) {
                                // UIUtil.stopProgressDialog(getApplicationContext());
                                roleArrayList.clear();
                                roleArrayList.addAll(teacherRoleResponse.getRoles());
                                Log.e("data", "======" + roleArrayList);
                                getAlert();

                            } else {
                                Toast.makeText(getApplicationContext(), "" + teacherRoleResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
            for (int i = 0; i < roleArrayList.size(); i++) {
                items[i] = roleArrayList.get(i).getName();
            }
            if (roleArrayList.size() < 1) {
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_TEACHER_ROLE, String.valueOf(roleArrayList.get(0).getName()));
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_Id, String.valueOf(roleArrayList.get(dialogposition).getId()));
                Intent intent;
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Employee") || PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Class Teacher")) {
                    intent = new Intent(getApplicationContext(), TeacherLandingActivity.class);
                    startActivity(intent);
                    finishAffinity();
                } else if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    intent = new Intent(getApplicationContext(), AdminLandingActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
                Toast.makeText(getApplicationContext(), "Only One Role Allotted", Toast.LENGTH_SHORT).show();

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle("Select Role :");
                builder.setSingleChoiceItems(items, selected, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        dialogposition = item;
                        Log.e("itemposition", "------" + roleArrayList.get(dialogposition).getId());
                        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE, String.valueOf(roleArrayList.get(dialogposition).getName()));
                        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_Id, String.valueOf(roleArrayList.get(dialogposition).getId()));
                        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Employee") ||
                                PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Class Teacher")) {
                            Intent intent = new Intent(getApplicationContext(), TeacherLandingActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }else if ( PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Principal"))
                        {
                            Toast.makeText(getApplicationContext(), "Principal login not yet created, Please choose some other fields", Toast.LENGTH_SHORT).show();
                        }else if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                            Intent intent = new Intent(getApplicationContext(), AdminLandingActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }

                        //intent.putExtra("id", dialogposition);


                    }
                });

        /*        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Employee") || PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Class Teacher")) {
                            Intent intent = new Intent(getApplicationContext(), TeacherLandingActivity.class);
                            startActivity(intent);
                        } else if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("branch admin")) {
                            Intent intent = new Intent(getApplicationContext(), AdminLandingActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                });*/
                dialogposition = -1;
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                //alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                // Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                //nbutton.setTextColor(R.color.black);

            }


        } catch (
                Exception e)

        {
            e.printStackTrace();

        }
    }

}


