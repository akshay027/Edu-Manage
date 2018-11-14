package com.maxlore.edumanage.Activities.TeacherActivities;

import android.Manifest;
import android.content.Context;
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

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.TeacherAdapters.AssignmentDetailAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.AssignmentDetail.Student;
import com.maxlore.edumanage.Models.TeacherModels.AssignmentDetail.StudentStructure;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by maxlore on 10-Aug-16.
 */
public class AssignmentDetail extends AppCompatActivity {

    private RecyclerView assignmentRecyclerView;
    private EditText searchStudent;
    private ArrayList<Student> studentArrayList, searchArrayList;
    private Handler handler;
    private ImageView titleimageview;
    private AssignmentDetailAdapter assignmentDetailAdapter;
    public int text;
    private final Context context = this;
    private TextView tvInfosec, tvInfostd;
    private int currentPosition;
    public static final int TIME_OUT = 1000;
    private String messageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_detail);

        assignmentRecyclerView = (RecyclerView) findViewById(R.id.assignmentRecyclerView);
        searchStudent = (EditText) findViewById(R.id.etStudentSearch);

        tvInfosec = (TextView) findViewById(R.id.tvInfosec);
        tvInfostd = (TextView) findViewById(R.id.tvInfostd);
        titleimageview = (ImageView) findViewById(R.id.ivMessage);
        studentArrayList = new ArrayList<>();

        searchArrayList = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        assignmentRecyclerView.setLayoutManager(llm);

        handler = new Handler();

        assignmentDetailAdapter = new AssignmentDetailAdapter(this, studentArrayList,context);
        assignmentRecyclerView.setAdapter(assignmentDetailAdapter);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        searchStudent.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getAssignmentData();

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
                        studentArrayList.clear();
                        studentArrayList.addAll(searchArrayList);
                        assignmentDetailAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        titleimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                final EditText edittext = new EditText(context);
                // set title
                alertDialogBuilder.setTitle("Assignment");
                edittext.setHint("Enter your message here");
                alertDialogBuilder.setView(edittext);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (TextUtils.isEmpty(edittext.getText().toString())) {
                                    Toast.makeText(getApplicationContext(), "Message Should Not Be Empty", Toast.LENGTH_SHORT).show();
                                } else {
                                    AssignmentDetail.this.finish();
                                    String messageEditText = edittext.getText().toString();
                                    tittleMessage(messageEditText);
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

    }

    private String stringpass() {
        String numbers = "";
        for (int i = 0; i < studentArrayList.size(); i++) {
            numbers = numbers + studentArrayList.get(i).getContactNo() + ",";
        }
        Log.e("Strimnf", "=========" + numbers);
        return numbers;

    }

    private void tittleMessage(String message) {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("message", message);
                jsonObject.addProperty("mobile_no", stringpass());
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().setMessage(jsonObject, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject object, Response response) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Log.e("jsonObject", "jsonObject --- " + object.toString());


                        try {
                            if (object == null) {
                                Toast.makeText(AssignmentDetail.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (object.get("status").getAsInt() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(AssignmentDetail.this, "Message Sent Sucessfully", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AssignmentDetail.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(AssignmentDetail.this, AssignmentActivity.class));
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
        startActivity(new Intent(AssignmentDetail.this, AssignmentActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }


    // startActivity(new Intent(this, AssignmentActivity.class));
    private void filterSearch(String search) {
        try {
            studentArrayList.clear();
            for (int i = 0; i < searchArrayList.size(); i++) {
                Student student = searchArrayList.get(i);
                if (student.getName().toLowerCase().contains(search.toLowerCase())) {
                    studentArrayList.add(student);
                }
            }
            if (studentArrayList.size() <= 0) {
                searchStudent.setError("No Record found");
            }
            assignmentDetailAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindData(List<Student> students) {
        try {
            searchArrayList.clear();
            studentArrayList.addAll(students);
            searchArrayList.addAll(students);
            assignmentDetailAdapter.notifyDataSetChanged();
            assignmentDetailAdapter.SetOnItemClickListener(new AssignmentDetailAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Log.e("onItemClick", "onItemClick ===== p-" + position);
                }

                @Override
                public void onAssignmentcompleted(View view, int position) {
                    Log.e("onItemClick", "AssignmentCompletion ===== p-" + position);
                    assignmentConfirmation(position);
                }

                @Override
                public void onMessageParent(View view, int position) {
                    Log.e("onItemClick", "messaging ===== p-" + position);
                    messageConfirmation(position);
                }

                @Override
                public void onCallParent(View view, int position) {
                    Log.e("onItemClick", "Calling parent of student=============== p-" + position);
                    callConfirmation(position);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callConfirmation(final int position) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        currentPosition = position;

        final Student student = studentArrayList.get(position);
        builder.setTitle("Confirmation");
        String message = "Are you sure to Call " + student.getName() + " ?";
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

    private void assignmentConfirmation(final int position) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        currentPosition = position;

        final Student student = studentArrayList.get(position);
        builder.setTitle("Confirmation");
        String assign = student.getStatus() == Constants.STATUSCOMPLETED ? "ASSIGNMENT INCOMPLETE" : "ASSIGNMENT COMPLETED";
        String message = "Do you want to mark " + assign + " For " + student.getName() + "?";
        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /// TODO call Api here
                markAssignment();

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


    private void getAssignmentData() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                JsonObject jsonObject = new JsonObject();
                String id = getIntent().getStringExtra("id");
                String stan = getIntent().getStringExtra("standard");
                String section_tv = getIntent().getStringExtra("section");
                jsonObject.addProperty("assignment_id", id);
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                tvInfosec.setText(section_tv);
                tvInfostd.setText(stan);

                RetrofitAPI.getInstance(this).getApi().getAssignmentDetail(jsonObject, new Callback<StudentStructure>() {

                    @Override
                    public void success(StudentStructure studentStructure, Response response) {
                        try {
                            if (studentStructure.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                bindData(studentStructure.getStudent());

                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "" + studentStructure.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void callParent() {
        final Student student = studentArrayList.get(currentPosition);
        String number = student.getContactNo();
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


    private void markAssignment() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");

                final Student student = studentArrayList.get(currentPosition);
                JsonObject jsonObject = new JsonObject();
                String id = getIntent().getStringExtra("id");
                jsonObject.addProperty("student_id", student.getId());
                jsonObject.addProperty("assignment_id", id);
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                jsonObject.addProperty("status", student.getStatus() == Constants.STATUSCOMPLETED ? false : true);

                RetrofitAPI.getInstance(this).getApi().markAssignment(jsonObject, new Callback<JSONObject>() {
                    @Override
                    public void success(JSONObject object, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Log.e("API", "Assignment completion" + object.toString());
                            student.setStatus(student.getStatus() == Constants.STATUSCOMPLETED ? Constants.STATUSNOTCOMPLETED : Constants.STATUSCOMPLETED);
                            studentArrayList.set(currentPosition, student);
                            assignmentDetailAdapter.notifyDataSetChanged();
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

    private void messageConfirmation(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        final Student studentmsg = studentArrayList.get(position);
        alert.setMessage("To : " + studentmsg.getName() + "\n");
        alert.setTitle("Assignment:");


        edittext.setHint("Enter your message here");

        edittext.setText("");
        alert.setView(edittext);

        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if (TextUtils.isEmpty(edittext.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Message Should Not Be Empty", Toast.LENGTH_SHORT).show();
                } else {
                    messageEditText = edittext.getText().toString();
                    submitMessage(studentmsg, position);
                }
               // messageEditText = edittext.getText().toString();
                //submitMessage(studentmsg, position);

            }
        });

        alert.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }

    private void submitMessage(final Student assignmessage, final int position) {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                final JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("mobile_no", assignmessage.getContactNo());
                jsonObject.addProperty("message", messageEditText);
                jsonObject.addProperty("user_type", "Employee");
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().sendassignmentmessage(jsonObject, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject object, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());

                            Log.e("Json ", "Hhd --- " + object.toString());
                            Log.e("Json ", "Response --- " + response.getBody());
                            if (object.get("status").getAsInt() == Constants.SUCCESS) {
                                studentArrayList.set(position, assignmessage);
                                assignmentDetailAdapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "Message Sent Sucessfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
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
}
