package com.maxlore.edumanage.Activities.AdminActivities;

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
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminBranchesResponse.AdminBranches;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminBranchesResponse.BranchesData;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by maxlore on 13-Sep-17.
 */

public class AdminBranchActivity extends AppCompatActivity {

    private ArrayList<BranchesData> roleArrayList;
    private int dialogposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_roll);
        this.setTheme(R.style.AlertDialogCustom);
        roleArrayList = new ArrayList<>();

        getAdminBranches();
    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }

    private void getAdminBranches() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                //UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                RetrofitAPI.getInstance(this).getApi().getadminbranches(params, new Callback<AdminBranches>() {
                    @Override
                    public void success(AdminBranches adminBranches, Response response) {
                        try {
                            if (adminBranches.getStatus() == Constants.SUCCESS) {
                                // UIUtil.stopProgressDialog(getApplicationContext());
                                roleArrayList.clear();
                                roleArrayList.addAll(adminBranches.getBranchesDataList());
                                Log.e("data", "======" + roleArrayList);
                                getAlert();

                            } else {
                                Toast.makeText(getApplicationContext(), "" + adminBranches.getMessage(), Toast.LENGTH_SHORT).show();
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
            if (roleArrayList.size() <= 1) {
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_NAME, String.valueOf(roleArrayList.get(0).getName()));
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID, String.valueOf(roleArrayList.get(dialogposition).getId()));
                Intent intent;
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    intent = new Intent(getApplicationContext(), AdminLandingActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
                Toast.makeText(getApplicationContext(), "Only One Branch Allotted", Toast.LENGTH_SHORT).show();

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle("Select Branch :");
                builder.setSingleChoiceItems(items, selected, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        dialogposition = item;
                        Log.e("itemposition", "------" + roleArrayList.get(dialogposition).getId());
                        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE, "Admin");
                        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_NAME, String.valueOf(roleArrayList.get(dialogposition).getName()));
                        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID, String.valueOf(roleArrayList.get(dialogposition).getId()));
                        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("admin")) {
                            Intent intent = new Intent(getApplicationContext(), AdminLandingActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                    }
                });

             /*   builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Employee") || PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Class Teacher")) {
                            Intent intent = new Intent(getApplicationContext(), TeacherLandingActivity.class);
                            startActivity(intent);
                        } else if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("branch admin")) {
                            Intent intent = new Intent(getApplicationContext(), AdminLandingActivity.class);
                            startActivity(intent);
                        } else if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Admin")) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


