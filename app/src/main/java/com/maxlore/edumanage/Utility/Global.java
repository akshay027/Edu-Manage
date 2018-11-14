//package com.maxlore.inmegh.Utility;
//
//import android.app.Activity;
//import android.app.Application;
//import android.content.Context;
//
//import com.maxlore.inmegh.API.RetrofitAPI;
//
///**
// * Created by Raj on 11/10/15.
// */
//public class Global extends Application {
//
//    private static Context instance;
//
//    public static Context getContext() {
//        return instance;
//    }
//
//    @Override
//    public void onCreate() {
//        instance = getApplicationContext();
//        super.onCreate();
//        RetrofitAPI.getInstance();
//    }
//
//    // When any activity pauses with the new activity not being ours.
//    public void onActivityPause(Activity activity) {
//
//    }
//
//    // When any activity resumes with a previous activity not being ours.
//    public void onActivityResume(Activity activity) {
//
//    }
//
//
//}
