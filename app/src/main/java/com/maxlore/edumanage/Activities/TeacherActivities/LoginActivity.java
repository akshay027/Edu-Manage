package com.maxlore.edumanage.Activities.TeacherActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Activities.AdminActivities.AdminBranchActivity;
import com.maxlore.edumanage.Activities.ParentActivities.ParentRollActivity;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.SignInResponse;
import com.maxlore.edumanage.Models.TeacherModels.StudentsRole;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;
import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUserName, etPassword;
    private Button btnSubmit;
    //private CheckBox cbShowpassword;
    private List<String> rolelist;
    private List<StudentsRole> parentrolelist;
    private Boolean exit = false;
    private ImageView passvisible, passinvisible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);

        passvisible = (ImageView) findViewById(R.id.passvisible);
        passinvisible = (ImageView) findViewById(R.id.passinvisible);

        SugarContext.init(this);
        rolelist = new ArrayList<String>();
        parentrolelist = new ArrayList<>();
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        /*cbShowpassword = (CheckBox) findViewById(R.id.cbShowpassword);*/
        btnSubmit.setOnClickListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        etUserName.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) ||
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (validateCredentials()) {
                            login();
                            Log.e("User Type00001111", "-=-=-=-=-=----=-=-=-=-=-=" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE));
                        }
                        // Toast.makeText(HelloFormStuff.this, edittext.getText(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                return false;
            }
        });
        etPassword.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) ||
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (validateCredentials()) {
                            login();
                        }
                        // Toast.makeText(HelloFormStuff.this, edittext.getText(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                return false;
            }

        });
        passinvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int start, end;
                passvisible.setVisibility(View.VISIBLE);
                passinvisible.setVisibility(View.GONE);
                start = etPassword.getSelectionStart();
                end = etPassword.getSelectionEnd();
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                etPassword.setSelection(start, end);

            }
        });
        passvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int start, end;
                passvisible.setVisibility(View.GONE);
                passinvisible.setVisibility(View.VISIBLE);
                start = etPassword.getSelectionStart();
                end = etPassword.getSelectionEnd();
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                etPassword.setSelection(start, end);
            }
        });
       /* cbShowpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             *//*   if(isChecked) {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    etPassword.setInputType(129);
                }*//*
                if (!isChecked) {
                    // show password
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
                v.startAnimation(myAnim);
                if (validateCredentials())
                    login();
                break;
        }
    }

    private boolean validateCredentials() {

        if (TextUtils.isEmpty(etUserName.getText().toString())) {
            etUserName.setError("Please enter username");
            return false;
        }

        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Please enter Password");
            return false;
        }

        return true;

    }

    private void login() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Signing .....");

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("user_email", etUserName.getText().toString());
                jsonObject.addProperty("password", etPassword.getText().toString());
                // jsonObject.addProperty("devise_id", ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
                // jsonObject.addProperty("registration_id", FirebaseInstanceId.getInstance().getToken());
                jsonObject.addProperty("mobile_os", "Android");

                Log.e("jsonObject", "jsonObject : " + jsonObject.toString());

                RetrofitAPI.getInstance(this).getApi().signIn(jsonObject, new Callback<SignInResponse>() {

                    @Override
                    public void success(SignInResponse jsonObject, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());

                            Log.e("Json ", "Hhd---" + jsonObject.toString());
                            Log.e("Json ", "response---" + response.getBody());
                            Log.e("uhgajhdgjhf  ", jsonObject.getStatus() + "jjhsd");
                            if (jsonObject.getStatus() == Constants.SUCCESS) {
                                final Toast tst = Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT);
                                tst.show();
                                Log.e("Token", "-=-=-=-=-=----=-=-=-=-=-= jsonObject " + jsonObject.toString());
                                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_USERNAME, jsonObject.getUserName());
                                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_EMAIL, jsonObject.getEmail());
                                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN, jsonObject.getToken());
                                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE, jsonObject.getUserType());
                                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID, String.valueOf((jsonObject.getBranchId())));
                                //  PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_USER_SUB_TYPE, jsonObject.getUserSubType());
                                Log.e("User Type1", "-=-=-=-=-=----=-=-=-=-=-= jsonObject " + jsonObject.getUserType());
                                Log.e("User Type2", "-=-=-=-=-=----=-=-=-=-=-=" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE));
                                Log.e("User Name", "-=-=-=-=-=----=-=-=-=-=-=" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USERNAME));
                                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Parent")) {
                                    startActivity(new Intent(getApplicationContext(), ParentRollActivity.class));
                                } else if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Admin")) {
                                    startActivity(new Intent(getApplicationContext(), AdminBranchActivity.class));
                                } else {
                                    startActivity(new Intent(getApplicationContext(), TeacherRollActivity.class));
                                }
                       /* if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Parent")) {

                            Intent intent = new Intent(getApplicationContext(), ParentRollActivity.class);
                            startActivity(intent);

                        } else {
                            getRoleDetails(jsonObject.getRoles());
                        }*/
                                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finish();
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}