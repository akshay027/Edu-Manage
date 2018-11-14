package com.maxlore.edumanage.Activities.ParentActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.ParentModels.ParentTransfercertificate.TransferCertificate;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ParentTransferCertificateActivity extends AppCompatActivity {
    private ArrayAdapter<String> spinneradapter;
    private String spiner;
    private int result;
    private ArrayList<String> categories;
    private TextView applybtn;
    private String StudentId, Appliedby, ParentName;
    private String AdmissionNo;
    private String StandardName, SectionName, StudentName;
    private List<TransferCertificate> transfercertificateArraylist;
    private EditText edSchoolname, edStudentName, edSectionName, edStandardName, edAdmissionNo, edName,
            edPlace, edDate, edParentname, edreason;
    private ArrayList respnseArraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_certificate);

        applybtn = (TextView) findViewById(R.id.btnApply);
        transfercertificateArraylist = new ArrayList<>();
        edSchoolname = (EditText) findViewById(R.id.edSchoolName);
        edStudentName = (EditText) findViewById(R.id.edstudentName);
        edName = (EditText) findViewById(R.id.edName);
        edSectionName = (EditText) findViewById(R.id.edSection);
        edStandardName = (EditText) findViewById(R.id.edstandard);
        edreason = (EditText) findViewById(R.id.edreason);
        edAdmissionNo = (EditText) findViewById(R.id.edAdmissionNo);
        edPlace = (EditText) findViewById(R.id.edPlace);

        edDate = (EditText) findViewById(R.id.edDate);
        edParentname = (EditText) findViewById(R.id.edParentName);

        edSchoolname.setInputType(InputType.TYPE_CLASS_TEXT);
        edStudentName.setInputType(InputType.TYPE_CLASS_TEXT);
        edName.setInputType(InputType.TYPE_CLASS_TEXT);
        edSectionName.setInputType(InputType.TYPE_CLASS_TEXT);
        edStandardName.setInputType(InputType.TYPE_CLASS_TEXT);
        edAdmissionNo.setInputType(InputType.TYPE_CLASS_TEXT);
        edPlace.setInputType(InputType.TYPE_CLASS_TEXT);
        edDate.setInputType(InputType.TYPE_CLASS_TEXT);
        edParentname.setInputType(InputType.TYPE_CLASS_TEXT);
        edreason.setInputType(InputType.TYPE_CLASS_TEXT);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        edSchoolname.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        edStudentName.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        edName.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        edSectionName.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        edStandardName.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        edreason.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        edAdmissionNo.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        edPlace.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        edDate.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        edParentname.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        respnseArraylist = new ArrayList();

        try {

            Intent intent = getIntent();
            //StudentId = getIntent().getStringExtra("id");
            //StudentName = intent.getStringExtra(Constants.NAME);
            StandardName = intent.getStringExtra(Constants.STANDARD);
            SectionName = intent.getStringExtra(Constants.SECTION);
            AdmissionNo = intent.getStringExtra("admission id");
            Appliedby = intent.getStringExtra("Applied By");
            ParentName = intent.getStringExtra(Constants.PARENT);

            ((EditText) findViewById(R.id.edstudentName)).setText(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_NAME));
            ((EditText) findViewById(R.id.edSection)).setText(SectionName);
            ((EditText) findViewById(R.id.edstandard)).setText(StandardName);
            ((EditText) findViewById(R.id.edAdmissionNo)).setText(AdmissionNo);
            ((EditText) findViewById(R.id.edName)).setText(ParentName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        applybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    createTc();
                    Intent intent = new Intent(getApplicationContext(), ParentTransferCertificateListItemActivity.class);
                    intent.putExtra("response", respnseArraylist);

                    overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
                    finish();

                }
            }
        });

    }

    private boolean validateFields() {
        String place = edPlace.getText().toString();
        String parentname = edParentname.getText().toString();
        String schoolname = edSchoolname.getText().toString();
        if (edreason.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Select Reason", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edSchoolname.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Select School Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edParentname.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Select Parent Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edPlace.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Select Place", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edDate.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Select Date", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            edPlace.setError(null);
        }

        return true;

    }

    private void clearFieldData() {
        edSchoolname.setText("");
        edParentname.setText("");
        edPlace.setText("");
        edDate.setText("");
        edreason.setText("");

    }

    private void createTc() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                if (validateFields()) {
                    JsonObject jsonObject = new JsonObject();
                    JsonObject jsonObject1 = new JsonObject();
                    jsonObject1.addProperty("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
                    jsonObject.addProperty("new_school_name", edSchoolname.getText().toString());
                    jsonObject.addProperty("applied_by", edParentname.getText().toString());
                    jsonObject1.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                    jsonObject.addProperty("place_name", edPlace.getText().toString());
                    jsonObject.addProperty("reason_for_leaving", edreason.getText().toString());
                    jsonObject1.add("transfer_certificate", jsonObject);
                    RetrofitAPI.getInstance(this).getApi().createTc(jsonObject1, new Callback<JsonObject>() {
                        @Override
                        public void success(JsonObject jsonObj, Response response) {
                            try {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Log.e("jsonObject", "jsonObject --- " + jsonObj.toString());
                                respnseArraylist.add(jsonObj.get("status").getAsInt());
                                clearFieldData();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            UIUtil.stopProgressDialog(getApplicationContext());
                        }
                    });
                }
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
