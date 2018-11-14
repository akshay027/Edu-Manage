package com.maxlore.edumanage.Activities.TeacherActivities;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Activities.ParentActivities.ProfileImageviewActivity;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.Profile.Profile;
import com.maxlore.edumanage.Models.TeacherModels.Profile.ProfileResponse;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName, tvEmail, tvMobile, tvDOB, tvBloodGroup, tvGender, tvDeparment,
            tvEmpStatus, tvJoiningDate, tvLicense, tvPanCard, tvAadhaarCard, tvprimarymail, tvempcode;
    private ImageView iveditmobile, iv_editemail;
    private Toolbar toolbar;
    private String id, phone, emailid, name;
    private ImageView poster;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        tvName = (TextView) findViewById(R.id.tvName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvMobile = (TextView) findViewById(R.id.tvMobile);
        tvprimarymail = (TextView) findViewById(R.id.tvprimarymail);
        tvDOB = (TextView) findViewById(R.id.tvDOB);
        tvempcode = (TextView) findViewById(R.id.tvempcode);
        poster = (ImageView) findViewById(R.id.poster);
        //tvGender = (TextView) findViewById(R.id.tvGender);
        tvDeparment = (TextView) findViewById(R.id.tvDeparment);
        tvEmpStatus = (TextView) findViewById(R.id.tvEmpStatus);
        tvJoiningDate = (TextView) findViewById(R.id.tvJoiningDate);
        // tvPanCard = (TextView) findViewById(R.id.tvPanCard);
        //tvAadhaarCard = (TextView) findViewById(R.id.tvAadhaarCard);
        iv_editemail = (ImageView) findViewById(R.id.iv_editemail);
        iveditmobile = (ImageView) findViewById(R.id.iveditmobile);

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
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ProfileImageviewActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
        getProfileDetails();
    }

    private void getProfileDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Getting Profile Information...");
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_type", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE));
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().getProfile(params, new Callback<ProfileResponse>() {
                    @Override
                    public void success(ProfileResponse profileResponse, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            if (profileResponse == null)
                                return;
                            if (profileResponse.getStatus() == Constants.SUCCESS) {
                                bindData(profileResponse.getProfile());
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
        final Profile profile = new Profile();
        final EditText edittext = new EditText(this);
        alert.setTitle("E-Mail:");
        edittext.setText(emailid);
        if (!TextUtils.isEmpty(profile.getAcknowledgement()))
            edittext.setText("" + profile.getAcknowledgement());
        alert.setView(edittext);

        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String YouEditTextValue = edittext.getText().toString();
                if (TextUtils.isEmpty(edittext.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "email field cannot be empty", Toast.LENGTH_SHORT).show();

                } else {
                    if (YouEditTextValue.contains("@")) {
                        profile.setAcknowledgement(YouEditTextValue);
                        editEmail(profile);

                    } else {
                        //edittext.requestFocus();
                        //edittext.setError("@ is Missing");
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
        final Profile profile = new Profile();
        final EditText edittext = new EditText(this);
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setTitle("Mobile No:");
        edittext.setText(phone);
        if (!TextUtils.isEmpty(profile.getAcknowledgement()))
            edittext.setText("" + profile.getAcknowledgement());

        alert.setView(edittext);

        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String YouEditTextValue = edittext.getText().toString();
                if (TextUtils.isEmpty(edittext.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Number field cannot be empty", Toast.LENGTH_SHORT).show();

                } else {
                    if (YouEditTextValue.length() > 10) {
                        // edittext.requestFocus();
                        //edittext.setError("Number is Exceeded");
                        Toast.makeText(getApplicationContext(), "Try again Number is Exceeded", Toast.LENGTH_SHORT).show();
                    } else if (YouEditTextValue.length() < 10) {
                        //edittext.requestFocus();
                        //edittext.setError("Number is Less");
                        Toast.makeText(getApplicationContext(), "Try again Number is Less", Toast.LENGTH_SHORT).show();
                    } else if (YouEditTextValue.length() == 10) {
                        profile.setAcknowledgement(YouEditTextValue);
                        editMobileno(profile);
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

    private void editMobileno(final Profile profile) {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                JsonObject jsonObject = new JsonObject();
                JsonObject jsonObjectnew = new JsonObject();
                jsonObjectnew.addProperty("e_mobile", profile.getAcknowledgement());
                jsonObject.add("user", jsonObjectnew);
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().editemail(jsonObject, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject object, Response response) {
                        Log.e("jsonObject", "jsonObject --- " + object.toString());
                        UIUtil.stopProgressDialog(getApplicationContext());
                        try {
                            if (object == null) {
                                Toast.makeText(getApplicationContext(), "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (object.get("status").getAsInt() == Constants.SUCCESS) {
                                tvMobile.setText(profile.getAcknowledgement());
                                phone = profile.getAcknowledgement();
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

    private void editEmail(final Profile profile) {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                JsonObject jsonObject = new JsonObject();
                JsonObject jsonObjectnew = new JsonObject();
                jsonObjectnew.addProperty("email", profile.getAcknowledgement());
                jsonObject.add("user", jsonObjectnew);
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().editemail(jsonObject, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject object, Response response) {
                        Log.e("jsonObject", "jsonObject --- " + object.toString());
                        UIUtil.stopProgressDialog(getApplicationContext());

                        try {
                            if (object == null) {
                                Toast.makeText(getApplicationContext(), "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (object.get("status").getAsInt() == Constants.SUCCESS) {
                                tvprimarymail.setText(profile.getAcknowledgement());
                                emailid = profile.getAcknowledgement();
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

    private void bindData(Profile profile) {
        try {
            collapsingToolbarLayout.setTitle(profile.getName());
            if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_IMAGE).isEmpty()) {
                poster.setImageResource(R.drawable.photo);
            } else {
                Picasso.with(this).load(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_IMAGE)).fit().into(poster);

            }
            name = profile.getName();
            if (TextUtils.isEmpty(profile.getName())) {
                tvName.setText("N/A");
            } else {
                tvName.setText(profile.getName());
            }
            if (TextUtils.isEmpty(profile.getEmail())) {
                tvEmail.setText("N/A");
            } else {
                tvEmail.setText(profile.getEmail());
            }
            if (TextUtils.isEmpty(profile.getMobileNo())) {
                tvMobile.setText("N/A");
            } else {
                tvMobile.setText(profile.getMobileNo());
                phone = profile.getMobileNo();
            }
            if (TextUtils.isEmpty(profile.getDateOfBirth())) {
                tvDOB.setText("N/A");
            } else {
                tvDOB.setText(profile.getDateOfBirth());
            }
            if (TextUtils.isEmpty(profile.getEmpcode())) {
                tvempcode.setText("N/A");
            } else {
                tvempcode.setText(profile.getEmpcode());
            }
            if (TextUtils.isEmpty(profile.getPrimaryemail())) {
                tvprimarymail.setText("N/A");
            } else {
                tvprimarymail.setText(profile.getPrimaryemail());
                emailid = profile.getPrimaryemail();
            }

            if (TextUtils.isEmpty(profile.getDepartment())) {
                tvDeparment.setText("N/A");
            } else {
                tvDeparment.setText(profile.getDepartment());
            }

            if (TextUtils.isEmpty(profile.getStatus())) {
                tvEmpStatus.setText("N/A");
            } else {
                tvEmpStatus.setText(profile.getStatus());
            }

            if (TextUtils.isEmpty(profile.getJoiningDate())) {
                tvJoiningDate.setText("N/A");
            } else {
                tvJoiningDate.setText(profile.getJoiningDate());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            startActivity(new Intent(ProfileActivity.this, TeacherLandingActivity.class));
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(ProfileActivity.this, TeacherLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }
}
