package com.maxlore.edumanage.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.maxlore.edumanage.Activities.TeacherActivities.SplashScreenActivity;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.UIUtil;

public class InternetConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_connection);

        alertBox();
    }

    private void alertBox() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View promptView = inflater.inflate(R.layout.internetconnection_dialogbox, null);
        alertDialogBuilder.setView(promptView)
                .setCancelable(false)
                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            if (UIUtil.isInternetAvailable(getApplicationContext())) {
                                Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), InternetConnectionActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            }
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });
        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
