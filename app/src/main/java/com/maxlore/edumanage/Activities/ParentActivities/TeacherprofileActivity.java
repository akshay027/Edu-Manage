package com.maxlore.edumanage.Activities.ParentActivities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.maxlore.edumanage.Adapters.ParentAdapters.TeacherProfileAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.ParentModels.ParentTeacherProfile.ParentResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentTeacherProfile.Teacher;
import com.maxlore.edumanage.Models.ParentModels.ParentTeacherProfile.TeacherProfile;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TeacherprofileActivity extends AppCompatActivity {
    private List<String> list;
    private ArrayAdapter<String> parentspinAdapter;
    private ArrayList<TeacherProfile> teacherProfileArrayList, searchArrayList;
    private List<String> subj;
    private String pos;
    private TeacherProfileAdapter teacherProfileAdapter;
    private RecyclerView parentTeacherRecyclerView;
    private EditText searchTeacher;
    private List<Teacher> teacherArrayList;
    private Handler handler;
    public int text;
    private int currentPosition;
    public static final int TIME_OUT = 1000;
    private TextView tv_nodata;
    private String messageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherprofile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        parentTeacherRecyclerView = (RecyclerView) findViewById(R.id.teacherProfileRecyclerView);
        searchTeacher = (EditText) findViewById(R.id.etTeacherSearch);
        tv_nodata = (TextView) findViewById(R.id.tv_nodata);
        list = new ArrayList<String>();
        teacherProfileArrayList = new ArrayList<>();
        teacherArrayList = new ArrayList<>();
        searchArrayList = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(this);

        parentTeacherRecyclerView.setLayoutManager(llm);

        handler = new Handler();
        teacherProfileAdapter = new TeacherProfileAdapter(this, teacherProfileArrayList);
        parentTeacherRecyclerView.setAdapter(teacherProfileAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getTeacherProfile();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        searchTeacher.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        searchTeacher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /* Log.e("lllllllllllllllllllllllllllllllllllllllllllll",teacherArrayList+"");*/
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
                        teacherProfileArrayList.clear();
                        teacherProfileArrayList.addAll(searchArrayList);
                        /* Log.e("lllllllllllllllllllllllllllllllllllllllllllll",teacherArrayList+"");*/
                        teacherProfileAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            startActivity(new Intent(TeacherprofileActivity.this, ParentLandingActivity.class));
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(TeacherprofileActivity.this, ParentLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.parentdashboardspinner, menu);

        MenuItem item = menu.findItem(R.id.parentdashboardspinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        parentspinAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        parentspinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(parentspinAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = list.get(position);
                spinData(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return true;
    */


 /*   private void updateSpinner() {
        list.clear();
        for (int i = 0; i < teacherProfileArrayList.size(); i++) {
            list.add(teacherProfileArrayList.get(i).getStudentName());
        }
        parentspinAdapter.notifyDataSetChanged();
        Log.e("aaaaaaaaaaaaaa", list + "");
    }*/

    public void getTeacherProfile() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().getTeacherProfile(params, new Callback<ParentResponse>() {
                    @Override
                    public void success(ParentResponse parentResponse, retrofit.client.Response response) {
                        try {
                            if (parentResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                teacherProfileArrayList.clear();
                                searchArrayList.clear();
                                teacherProfileArrayList.addAll(parentResponse.getTeacherProfile());
                                searchArrayList.addAll(parentResponse.getTeacherProfile());
                                bindData();
                                Log.e("data-------", teacherProfileArrayList + "");
                                if (teacherProfileArrayList.size() <= 0) {
                                    parentTeacherRecyclerView.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                } else {
                                    parentTeacherRecyclerView.setVisibility(View.VISIBLE);
                                    tv_nodata.setVisibility(View.GONE);
                                }
                                teacherProfileAdapter.notifyDataSetChanged();
                                // updateSpinner();
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(TeacherprofileActivity.this, "" + parentResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void bindData() {
        try {
            teacherProfileAdapter.SetOnItemClickListener(new TeacherProfileAdapter.OnItemClickListener() {
                @Override
                public void onMessageTeacher(View view, int position) {
                    Log.e("onItemClick", "messaging ===== p-" + position);
                    messageConfirmation(position);
                }

                @Override
                public void onCallTeacher(View view, int position) {
                    Log.e("onItemClick", "Calling teacher of student=============== p-" + position);
                    callConfirmation(position);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        //spinData();
    }

    private void filterSearch(String search) {
        try {
            teacherProfileArrayList.clear();

            for (int i = 0; i < searchArrayList.size(); i++) {
                TeacherProfile teacher = searchArrayList.get(i);
                if (teacher.getName().toLowerCase().contains(search.toLowerCase())) {
                    teacherProfileArrayList.add(teacher);
                    /* Log.e("lllllllllllllllllllllllllllllllllllllllllllll",teacherArrayList+"");*/
                }
            }
            if (teacherProfileArrayList.size() <= 0) {
                searchTeacher.setError("No Record found");
            }
            teacherProfileAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callConfirmation(final int position) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        currentPosition = position;

        final TeacherProfile teacher = teacherProfileArrayList.get(position);
        Log.e("ddddddddddddddd", teacher + "");
        builder.setTitle("Confirmation");
        String message = "Are you sure to Call " + teacher.getName() + " ?";
        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /// TODO call Api here
                callParent();

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


    private void callParent() {
        final TeacherProfile teacher = teacherProfileArrayList.get(currentPosition);
        String number = teacher.getPhone();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    private void messageConfirmation(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        final TeacherProfile teachermsg = teacherProfileArrayList.get(position);
        alert.setMessage("Teacher Name : " + teachermsg.getName() + "\n");
        alert.setTitle("Message For Teacher:");


        edittext.setHint("Enter your message here");
        edittext.setText("");
        alert.setView(edittext);

        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (TextUtils.isEmpty(edittext.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Message Should not be Empty", Toast.LENGTH_SHORT).show();

                } else {
                    messageEditText = edittext.getText().toString();
                    submitMessage(teachermsg, position);
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

    private void submitMessage(final TeacherProfile assignmessage, final int position) {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                /* UIUtil.startProgressDialog(this, "Fetching Student Assignments..");*/
                final JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("mobile_no", assignmessage.getPhone());
                jsonObject.addProperty("message", messageEditText);
                jsonObject.addProperty("user_type", "parent");
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().sendassignmentmessage(jsonObject, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject object, Response response) {
                        /* UIUtil.stopProgressDialog(this);*/
                        try {
                            Log.e("Json ", "Hhd --- " + object.toString());
                            Log.e("Json ", "Response --- " + response.getBody());
                            if (object.get("status").getAsInt() == Constants.SUCCESS) {
                                //teacherArrayList.set(position, assignmessage);
                                teacherProfileAdapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void spinData() {
        for (int i = 0; i < teacherProfileArrayList.size(); i++) {
            updateData(teacherProfileArrayList.get(i));
            break;

        }
    }

    private void updateData(TeacherProfile teacher) {
        teacherProfileArrayList.clear();
        //teacherProfileArrayList.addAll(teacher.getTeacher());
        teacherProfileAdapter.notifyDataSetChanged();
    }
}



