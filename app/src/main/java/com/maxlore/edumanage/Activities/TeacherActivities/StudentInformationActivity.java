package com.maxlore.edumanage.Activities.TeacherActivities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.TeacherAdapters.StudentInfoRecyclerAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.StudentDetails.StudentInfo;
import com.maxlore.edumanage.Models.TeacherModels.StudentDetails.StudentInfoResponse;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class StudentInformationActivity extends AppCompatActivity {
    private RecyclerView infoRecyclerView;
    private ArrayList<StudentInfo> infoarrayList, searchArrayList;
    private ImageView infoimageView, titleimageview;
    private TextView infotextView;
    private int placeId;
    private String s = "";
    private StudentInfoRecyclerAdapter infoadapter;
    private final Context context = this;
    private String StandardName, SectionName;
    private EditText searchStudent;
    private Handler handler;
    public static final int TIME_OUT = 1000;
    private int msgpos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        searchStudent = (EditText) findViewById(R.id.etSearch);

        titleimageview = (ImageView) findViewById(R.id.ivMessage);
        infoimageView = (ImageView) findViewById(R.id.ivmessage);
        infotextView = (TextView) findViewById(R.id.tvInfoTitle);
        infoRecyclerView = (RecyclerView) findViewById(R.id.recyclerStudentinfo);
        infoRecyclerView.setHasFixedSize(true);
        infoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        handler = new Handler();
        infoarrayList = new ArrayList<>();
        searchArrayList = new ArrayList<>();
        infoadapter = new StudentInfoRecyclerAdapter(this, infoarrayList);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        infoRecyclerView.setAdapter(infoadapter);
        infoadapter.notifyDataSetChanged();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        searchStudent.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        searchStudent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(s) && s.length() > 2) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            filterSearch(s.toString());
                        }
                    }, TIME_OUT);
                } else {
                    if (searchArrayList.size() > 0) {
                        infoarrayList.clear();
                        infoarrayList.addAll(searchArrayList);
                        infoadapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
       /* infoarrayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StudentInformationActivity.this, StudentObservationActivity.class);
                intent.putExtra("id", String.valueOf(infoarrayList.get(position).getId()));
                startActivity(intent);

            }
        });*/
        titleimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                final EditText edittext = new EditText(context);
                // set title
                alertDialogBuilder.setTitle("Send Message");
                edittext.setHint("Enter your message here");
                alertDialogBuilder.setView(edittext);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (TextUtils.isEmpty(edittext.getText().toString())) {
                                    Toast.makeText(getApplicationContext(), "Message Should not be empty", Toast.LENGTH_SHORT).show();
                                } else {
                                    StudentInformationActivity.this.finish();
                                    String YouEditTextValue = edittext.getText().toString();
                                    tittleMessage(YouEditTextValue);
                                }
                            }
                        })
                        .setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });


                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });


        infoadapter.SetOnItemClickListener(new StudentInfoRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(StudentInformationActivity.this, StudentObservationActivity.class);
                intent.putExtra("id", String.valueOf(infoarrayList.get(position).getId()));
                intent.putExtra(Constants.NAME, infoarrayList.get(position).getName());
                startActivity(intent);
            }

            @Override
            public void onCallClick(View view, int position) {
                confirmationForMakeCall(position);
            }

            @Override
            public void onMessageClick(View view, int position) {
                msgpos = position;
                sentMessageConfirmation(position);
            }


        });

        try {

            Intent intent = getIntent();
            placeId = intent.getIntExtra(Constants.ONWARD, -1);
            StandardName = intent.getStringExtra(Constants.STANDARD);
            SectionName = intent.getStringExtra(Constants.SECTION);
            ((TextView) findViewById(R.id.tvInfosec)).setText(SectionName);
            ((TextView) findViewById(R.id.tvInfostd)).setText(StandardName);

        } catch (Exception e) {
            e.printStackTrace();
        }

        getStudentInfoDetails();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_attendance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
        }
        if (id == R.id.app_info) {
            alertboxpopup();
        }

        return super.onOptionsItemSelected(item);
    }

    private AlertDialog alertboxpopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View promptView = inflater.inflate(R.layout.list_item_pop_app_info, null);


        builder.setView(promptView)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return builder.create();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }


    private String stringpass() {
        String numbers = "";
        for (int i = 0; i < infoarrayList.size(); i++) {
            numbers = numbers + infoarrayList.get(i).getContactNo() + ",";
        }
        Log.e("Strimnf", "=========" + numbers);
        return numbers;

    }

    private void filterSearch(String search) {
        try {
            infoarrayList.clear();
            for (int i = 0; i < searchArrayList.size(); i++) {
                StudentInfo student = searchArrayList.get(i);
                if (student.getName().toLowerCase().contains(search.toLowerCase())) {
                    infoarrayList.add(student);
                }
            }
            if (infoarrayList.size() <= 0) {
                searchStudent.setError("No Record found");
            }
            infoadapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeCall(String number) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        startActivity(intent);
    }

    private void sentMessage(String number, String message) {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("student_id", infoarrayList.get(msgpos).getId());
                jsonObject.addProperty("message", message);
                jsonObject.addProperty("mobile_no", number);
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().setMessage(jsonObject, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject object, Response response) {
                        Log.e("jsonObject", "jsonObject --- " + object.toString());
                        UIUtil.stopProgressDialog(getApplicationContext());

                        try {
                            if (object == null) {
                                Toast.makeText(StudentInformationActivity.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (object.get("status").getAsInt() == Constants.SUCCESS) {
                                Toast.makeText(StudentInformationActivity.this, object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(StudentInformationActivity.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
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

    private void tittleMessage(String message) {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("message", message);
                jsonObject.addProperty("mobile_no", stringpass());
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().setMessage(jsonObject, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject object, Response response) {
                        Log.e("jsonObject", "jsonObject --- " + object.toString());
                        UIUtil.stopProgressDialog(getApplicationContext());

                        try {
                            if (object == null) {
                                Toast.makeText(StudentInformationActivity.this, "Something went worng, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (object.get("status").getAsInt() == Constants.SUCCESS) {
                                Toast.makeText(StudentInformationActivity.this, object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(StudentInformationActivity.this, "Something went worng, try after sometime...", Toast.LENGTH_SHORT).show();
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

    private void getStudentInfoDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                Map<String, String> params = new HashMap<String, String>();
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                params.put("section_id", String.valueOf(placeId));
                RetrofitAPI.getInstance(this).getApi().getStudentInfo(params, new Callback<StudentInfoResponse>() {
                    @Override
                    public void success(StudentInfoResponse studentInfoResponse, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            if (studentInfoResponse.getStatus() == Constants.SUCCESS) {
                                infoarrayList.clear();
                                searchArrayList.clear();

                                Log.e("Studentdata", "--------------" + infoarrayList);
                                infoarrayList.addAll(studentInfoResponse.getStudent());
                                searchArrayList.addAll(studentInfoResponse.getStudent());
                                infoadapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getApplicationContext(), "" + studentInfoResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void confirmationForMakeCall(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

//        AlertDialog.Builder builder =
//                new AlertDialog.Builder(this, R.style.AppTheme_AppBarOverlay);
        final StudentInfo student = infoarrayList.get(position);
        builder.setTitle("Confirmation");
        String message = "Do you want to Call " + student.getName() + " on this number " + student.getContactNo() + " ?";
        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                makeCall(student.getContactNo());

            }
        });
        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void sentMessageConfirmation(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);

        final StudentInfo student = infoarrayList.get(position);

        alert.setMessage("Student Name : " + student.getName() + "\n");
        alert.setTitle("Confirmation");

        edittext.setHint("Enter your message here");
//        if (!TextUtils.isEmpty(studentLeave.getAcknowledgement()))
//            edittext.setText("" + studentLeave.getAcknowledgement());
        alert.setView(edittext);

        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (TextUtils.isEmpty(edittext.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Message Should not be empty", Toast.LENGTH_SHORT).show();
                } else {
                    String YouEditTextValue = edittext.getText().toString();
                    sentMessage(student.getContactNo(), YouEditTextValue);
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
}
