package com.maxlore.edumanage.Activities.TeacherActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Activities.AdminActivities.AdminLandingActivity;
import com.maxlore.edumanage.Activities.ParentActivities.ParentRollActivity;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.database.Role;

import com.maxlore.edumanage.Utility.Constants;

import java.util.ArrayList;

public class RoleActivity extends AppCompatActivity {
    private ArrayList<Role> roleArrayList;

    private int dialogposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);
        this.setTheme(R.style.AlertDialogCustom);
        int selected = -1;

        roleArrayList = new ArrayList<>();

        //  roleArrayList.clear();
        roleArrayList.addAll(Role.listAll(Role.class));
        String[] list = new String[roleArrayList.size()];
        for (int i = 0; i < roleArrayList.size(); i++) {
            list[i] = roleArrayList.get(i).getRoleName();
        }
        try {
            if (roleArrayList.size() == 1) {

                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE, roleArrayList.get(dialogposition).getRoleName());
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Parent")) {
                    Toast.makeText(getApplicationContext(), "Only One Role Allotted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ParentRollActivity.class));
                    finishAffinity();
                } else if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Admin")) {
                    Toast.makeText(getApplicationContext(), "Only One Role Allotted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), AdminLandingActivity.class));
                    finishAffinity();
                } else {
                    Toast.makeText(getApplicationContext(), "Only One Role Allotted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), TeacherLandingActivity.class));
                    finishAffinity();
                }
            } else {
                // Creating and Building the Dialog
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.mipmap.ic_role);
                builder.setTitle("Select Login Type :");
                builder.setSingleChoiceItems(list, selected,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                dialogposition = item;
                                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE, roleArrayList.get(dialogposition).getRoleName());
                                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Parent")) {
                                    startActivity(new Intent(getApplicationContext(), ParentRollActivity.class));
                                    finishAffinity();
                                } else if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Admin")) {
                                    startActivity(new Intent(getApplicationContext(), AdminLandingActivity.class));
                                    finishAffinity();
                                } else {
                                    startActivity(new Intent(getApplicationContext(), TeacherLandingActivity.class));
                                    finishAffinity();
                                }
                            }

                        });
                builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Parent")) {
                            startActivity(new Intent(getApplicationContext(), ParentRollActivity.class));
                            finishAffinity();
                        } else if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("")) {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finishAffinity();
                        } else if
                                (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Admin")) {
                            startActivity(new Intent(getApplicationContext(), AdminLandingActivity.class));
                            finishAffinity();
                        } else {
                            startActivity(new Intent(getApplicationContext(), TeacherLandingActivity.class));
                            finishAffinity();
                        }
                    }
                });

                dialogposition = -1;
                AlertDialog alert = builder.create();
                alert.show();
                alert.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setTextColor(R.color.black);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

