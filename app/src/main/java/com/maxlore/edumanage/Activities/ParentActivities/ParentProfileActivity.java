package com.maxlore.edumanage.Activities.ParentActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.ParentModels.ParentProfile.ParentProfile;
import com.maxlore.edumanage.Models.ParentModels.ParentProfile.ParentProfileResponse;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ParentProfileActivity extends AppCompatActivity {
    private ArrayAdapter<String> spinneradapter;
    private String spiner;
    private ArrayList<String> categories;
    private Toolbar toolbar;
    private String id, phone, emailid;
    private ArrayList<ParentProfile> profileResponseArrayList;
    private TextView tvName, tvDOB, tvAdmissionDate, tvAdmissionNo, tvTeacherName,
            tvReligion, tvSchoolEmail, tvEmail, tvPhone, tvMotherName, tvFatherName;
    private int result;
    private ImageView iveditmobile, iv_editemail;
    private ArrayList<ParentProfile> arrayList;
    private ImageView poster;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        poster = (ImageView) findViewById(R.id.poster);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        categories = new ArrayList<>();
        profileResponseArrayList = new ArrayList<>();

        tvMotherName = (TextView) findViewById(R.id.tvMotherName);
        tvFatherName = (TextView) findViewById(R.id.tvFatherName);
        tvName = (TextView) findViewById(R.id.tvName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvDOB = (TextView) findViewById(R.id.tvDOB);
        tvReligion = (TextView) findViewById(R.id.tvReligion);
        tvSchoolEmail = (TextView) findViewById(R.id.tvSchoolEmail);
        tvAdmissionDate = (TextView) findViewById(R.id.tvAdmissionDate);
        tvAdmissionNo = (TextView) findViewById(R.id.tvAdmissionNo);
        tvTeacherName = (TextView) findViewById(R.id.tvClassTeacher);

        iv_editemail = (ImageView) findViewById(R.id.iv_editemail);
        iveditmobile = (ImageView) findViewById(R.id.iveditmobile);
        getProfileDetails();

        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentProfileActivity.this, ProfileImageviewActivity.class);

                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
        iv_editemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageConfirmation();
            }
        });
        iveditmobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editConfirmation();
            }
        });

    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        getProfileDetails();
    }

    private void getProfileDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Getting Profile Information...");
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_type", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE));
                params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().getParentProfile(params, new Callback<ParentProfileResponse>() {
                    @Override
                    public void success(ParentProfileResponse profileResponse, Response response) {
                        try {
                            if (profileResponse == null)
                                return;
                            if (profileResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());

                                bindData(profileResponse.getParentProfile());
                                //updateSpinner();
                                Log.e("profiledata", "==========" + profileResponse.toString());

                            } else {
                                Toast.makeText(getApplicationContext(), "" + profileResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        UIUtil.stopProgressDialog(getApplicationContext());

                        Toast.makeText(getApplicationContext(), "Server is not responding, Try after some time.", Toast.LENGTH_SHORT).show();

                    }
                });
            } else {

                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void messageConfirmation() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final ParentProfile parentProfile = new ParentProfile();
        final EditText edittext = new EditText(this);
        if (emailid == null) {
            alert.setTitle("Email :" + " ");
        } else {
            alert.setTitle("Email :" + emailid);
        }

        edittext.setHint("Enter Email Id");
        if (!TextUtils.isEmpty(parentProfile.getAcknowledgement()))
            edittext.setText("" + parentProfile.getAcknowledgement());
        alert.setView(edittext);

        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (TextUtils.isEmpty(edittext.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Mail Should not be empty", Toast.LENGTH_SHORT).show();
                } else {
                    String YouEditTextValue = edittext.getText().toString();
                    if (YouEditTextValue.contains("@")) {
                        parentProfile.setAcknowledgement(YouEditTextValue);
                        editEmail(parentProfile);

                    } else {
                        Toast.makeText(getApplicationContext(), "Try again @ is Missing", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        alert.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }

    private void editConfirmation() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final ParentProfile parentProfile = new ParentProfile();
        final EditText edittext = new EditText(this);
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setTitle("Mobile No :" + phone);
        edittext.setHint("Enter Mobile no.");
        if (!TextUtils.isEmpty(parentProfile.getAcknowledgement()))
            edittext.setText("" + parentProfile.getAcknowledgement());

        alert.setView(edittext);

        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (TextUtils.isEmpty(edittext.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Mobile No. Should not be empty", Toast.LENGTH_SHORT).show();
                } else {
                    String YouEditTextValue = edittext.getText().toString();
                    if (YouEditTextValue.length() > 10) {

                        Toast.makeText(getApplicationContext(), "Try again Number is Exceeded", Toast.LENGTH_SHORT).show();
                    } else if (YouEditTextValue.length() < 10) {

                        Toast.makeText(getApplicationContext(), "Try again Number is Less", Toast.LENGTH_SHORT).show();
                    } else if (YouEditTextValue.length() == 10) {
                        parentProfile.setAcknowledgement(YouEditTextValue);
                        editMobileno(parentProfile);
                    }
                }
            }
        });

        alert.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }

    private void editMobileno(final ParentProfile parentProfile) {
        try {
            JsonObject jsonObject = new JsonObject();
            JsonObject jsonObjectnew = new JsonObject();
            jsonObject.addProperty("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
            jsonObjectnew.addProperty("phone", parentProfile.getAcknowledgement());
            jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
            jsonObject.add("user", jsonObjectnew);
            RetrofitAPI.getInstance(this).getApi().editemail(jsonObject, new Callback<JsonObject>() {
                @Override
                public void success(JsonObject object, Response response) {
                    Log.e("jsonObject", "jsonObject --- " + object.toString());

                    try {
                        if (object == null) {
                            Toast.makeText(getApplicationContext(), "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (object.get("status").getAsInt() == Constants.SUCCESS) {
                            tvPhone.setText(parentProfile.getAcknowledgement());
                            phone = parentProfile.getAcknowledgement();
                            Toast.makeText(getApplicationContext(), object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editEmail(final ParentProfile parentProfile) {
        try {
            JsonObject jsonObject = new JsonObject();
            JsonObject jsonObjectnew = new JsonObject();
            jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
            jsonObjectnew.addProperty("email", parentProfile.getAcknowledgement());
            jsonObject.add("user", jsonObjectnew);
            jsonObject.addProperty("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
            RetrofitAPI.getInstance(this).getApi().editemail(jsonObject, new Callback<JsonObject>() {
                @Override
                public void success(JsonObject object, Response response) {
                    Log.e("jsonObject", "jsonObject --- " + object.toString());

                    try {
                        if (object == null) {
                            Toast.makeText(getApplicationContext(), "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (object.get("status").getAsInt() == Constants.SUCCESS) {
                            tvEmail.setText(parentProfile.getAcknowledgement());
                            emailid = parentProfile.getAcknowledgement();
                            Toast.makeText(getApplicationContext(), object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Something went worng, try after sometime...", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindData(ParentProfile profile) {
        try {
            collapsingToolbarLayout.setTitle(profile.getName());
            if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_IMAGE).isEmpty() || PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_IMAGE) == null) {
                poster.setImageResource(R.drawable.photo);
            } else {
                Picasso.with(this).load(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_IMAGE)).fit().into(poster);
            }
            name = profile.getName();
            if (TextUtils.isEmpty(profile.getName())) {
                tvName.setText("NA");
            } else {
                tvName.setText(profile.getName());
            }
            if (TextUtils.isEmpty(profile.getFatherName())) {
                tvFatherName.setText("NA");
            } else {
                tvFatherName.setText(profile.getFatherName());
            }
            if (TextUtils.isEmpty(profile.getMotherName())) {
                tvMotherName.setText("NA");
            } else {
                tvMotherName.setText(profile.getMotherName());
            }
            if (TextUtils.isEmpty(profile.getEmail())) {
                tvEmail.setText("NA");
            } else {
                tvEmail.setText(profile.getEmail());
                emailid = profile.getEmail();
            }
            if (TextUtils.isEmpty(profile.getPhone())) {
                tvPhone.setText("NA");
            } else {
                tvPhone.setText(profile.getPhone());
                phone = profile.getPhone();
            }
            if (TextUtils.isEmpty(profile.getDob())) {
                tvDOB.setText("NA");
            } else {
                tvDOB.setText(profile.getDob());
            }
            if (TextUtils.isEmpty(profile.getReligion())) {
                tvReligion.setText("NA");
            } else {
                tvReligion.setText(profile.getReligion());
            }
            if (TextUtils.isEmpty(profile.getSchoolEmail())) {
                tvSchoolEmail.setText("NA");
            } else {
                tvSchoolEmail.setText(profile.getSchoolEmail());
            }
            if (TextUtils.isEmpty(profile.getClassTeacher())) {
                tvTeacherName.setText("NA");
            } else {
                tvTeacherName.setText(profile.getClassTeacher());
            }
            if (TextUtils.isEmpty(profile.getAdmissionDate())) {
                tvAdmissionDate.setText("NA");
            } else {
                tvAdmissionDate.setText(profile.getAdmissionDate());
            }
            if (TextUtils.isEmpty(profile.getAdmissionNo())) {
                tvAdmissionNo.setText("NA");
            } else {
                tvAdmissionNo.setText(profile.getAdmissionNo());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }

}
