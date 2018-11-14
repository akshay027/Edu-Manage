package com.maxlore.edumanage.Activities.TeacherActivities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Activities.AdminActivities.AdminLandingActivity;
import com.maxlore.edumanage.Activities.InternetConnectionActivity;
import com.maxlore.edumanage.Activities.ParentActivities.ParentLandingActivity;
import com.maxlore.edumanage.Activities.SecondActivity;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.VersionChecker;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.orm.SugarContext;

import java.util.concurrent.ExecutionException;


public class SplashScreenActivity extends Activity {

    public static final int TIME_OUT = 2000;
    public Handler handler;
    private String playstoreVersion, currentAppVersionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        Mint.initAndStartSession(MyActivity.this, "43ad11a7");
        SugarContext.init(this);
        VersionChecker versionChecker = new VersionChecker();
        try {
            playstoreVersion = versionChecker.execute().get();
            Log.e("play store versionCode", "==========" + playstoreVersion);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            currentAppVersionCode = String.valueOf(pInfo.versionName);
            Log.e("App versionCode", "==========" + currentAppVersionCode);
            //  Log.e("versionCode", "==========" + playStoreVersionCode);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        /*float playStoreVersionCode = Float.parseFloat((FirebaseRemoteConfig.getInstance().getString(
                "android_latest_version_code")));*/
     /*   try {
            newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + "com.maxlore.inmegh" + "&hl=en")
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get()
                    .select("div[itemprop=softwareVersion]")
                    .first()
                    .ownText();
            Log.e("new Version", newVersion);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String currentAppVersionCode = String.valueOf(pInfo.versionCode);
            Log.e("versionCode", "==========" + currentAppVersionCode);
            //  Log.e("versionCode", "==========" + playStoreVersionCode);
            if (newVersion.compareTo(currentAppVersionCode) > 0) {
//Show update popup or whatever best for you
                Toast.makeText(getApplicationContext(), "update is required", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Continue", Toast.LENGTH_SHORT).show();

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (UIUtil.isInternetAvailable(getApplicationContext())) {
                        if (playstoreVersion == null) {
                            Intent intent = new Intent(getApplicationContext(), InternetConnectionActivity.class);

                            startActivity(intent);
                            SplashScreenActivity.this.finish();

                        } else {
                            if (playstoreVersion.compareTo(currentAppVersionCode) > 0) {
//Show update popup or whatever best for you
                                startActivity(new Intent(getApplicationContext(), SecondActivity.class));
                                SplashScreenActivity.this.finish();

                            } else {
//                startActivity(new Intent(SplashScreenActivity.this, ClassTeacherLeaveTabActivity.class));
                                if (TextUtils.isEmpty(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN))) {
                                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                                    SplashScreenActivity.this.finish();
                                } else {
                                    if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Parent")) {
                                        startActivity(new Intent(getApplicationContext(), ParentLandingActivity.class));
                                        SplashScreenActivity.this.finish();
                                    } else if
                                            (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Admin")) {
                                        startActivity(new Intent(getApplicationContext(), AdminLandingActivity.class));
                                        SplashScreenActivity.this.finish();
                                    } else {
                                        startActivity(new Intent(getApplicationContext(), TeacherLandingActivity.class));
                                        SplashScreenActivity.this.finish();
                                    }

                                }
                            } //optional
                     /* else {
                        Intent intent = new Intent(getApplicationContext(), InternetConnectionActivity.class);
                        startActivity(intent);
                        SplashScreenActivity.this.finish();
                    }*/
                            //optional
                        }}
                     else {
                        Intent intent = new Intent(getApplicationContext(), InternetConnectionActivity.class);
                        startActivity(intent);
                        SplashScreenActivity.this.finish();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
                finish();
            }
        }, TIME_OUT);
    }
}
