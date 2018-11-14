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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.ParentAdapters.ParentObservationAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.ParentModels.ParentObservation.Observation;
import com.maxlore.edumanage.Models.ParentModels.ParentObservation.ObservationResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentObservation.StudentObservation;
import com.maxlore.edumanage.R;
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

public class ParentObservation extends AppCompatActivity {
    private List<String> list;
    private ArrayAdapter<String> parentspinAdapter;
    private ArrayList<StudentObservation> studentObservationArrayList, finalarrayList;
    private TextView nodataavlble_tv;
    private String pos;
    private EditText edSearch;
    private ParentObservationAdapter parentObservationAdapter;
    private RecyclerView observationRecyclerView;
    private List<Observation> observationArrayList;
    private Handler handler;
    public int text;
    private int currentPosition;
    public static final int TIME_OUT = 1000;
    private TextView tv_nodata;
    private String messageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_observation);
        observationRecyclerView = (RecyclerView) findViewById(R.id.observationRecyclerView);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = new ArrayList<String>();
        studentObservationArrayList = new ArrayList<>();
        observationArrayList = new ArrayList<>();
        finalarrayList = new ArrayList<>();
        tv_nodata = (TextView) findViewById(R.id.tv_nodata);
        handler = new Handler();
        LinearLayoutManager llm = new LinearLayoutManager(this);

        observationRecyclerView.setLayoutManager(llm);
        handler = new Handler();
        parentObservationAdapter = new ParentObservationAdapter(this, studentObservationArrayList);
        observationRecyclerView.setAdapter(parentObservationAdapter);
        parentObservationAdapter.notifyDataSetChanged();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getStudentObservation();
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
            startActivity(new Intent(ParentObservation.this, ParentLandingActivity.class));
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
        startActivity(new Intent(ParentObservation.this, ParentLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.parentdashboardspinner, menu);

        MenuItem item = menu.findItem(R.id.parentdashboardspinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        parentspinAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        parentspinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parentspinAdapter.notifyDataSetChanged();
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
    }
*/

    /*private void updateSpinner() {
        list.clear();
        for (int i = 0; i < studentObservationArrayList.size(); i++) {
            list.add(studentObservationArrayList.get(i).getName());
        }
        parentspinAdapter.notifyDataSetChanged();
        Log.e("aaaaaaaaa", list + "");
    }*/

    public void getStudentObservation() {
       try{ if (UIUtil.isInternetAvailable(this)) {
            UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
            Map<String, String> params = new HashMap<String, String>();
            params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
            params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
            RetrofitAPI.getInstance(this).getApi().getStudentObservation(params, new Callback<ObservationResponse>() {
                @Override
                public void success(ObservationResponse observationResponse, retrofit.client.Response response) {
                   try{ if (observationResponse.getStatus() == Constants.SUCCESS) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        finalarrayList.clear();
                        finalarrayList.addAll(observationResponse.getStudentObservation());
                        studentObservationArrayList.addAll(observationResponse.getStudentObservation());
                        if (studentObservationArrayList.size() <= 0) {
                            observationRecyclerView.setVisibility(View.GONE);
                            tv_nodata.setVisibility(View.VISIBLE);
                        } else {
                            observationRecyclerView.setVisibility(View.VISIBLE);
                            tv_nodata.setVisibility(View.GONE);
                        }
                        parentObservationAdapter.notifyDataSetChanged();
                        bindData();
                        //updateSpinner();
                    } else {
                        Toast.makeText(getApplicationContext(), "" + observationResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }} catch (Exception e) {
                       e.printStackTrace();
                   }
                }

                @Override
                public void failure(RetrofitError error) {
                    UIUtil.stopProgressDialog(getApplicationContext());
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Connect to internet.", Toast.LENGTH_LONG).show();
        }} catch (Exception e) {
           e.printStackTrace();
       }
    }

    private void bindData() {


        parentObservationAdapter.SetOnItemClickListener(new ParentObservationAdapter.OnItemClickListener() {
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
        //spinData();
    }

    private void callConfirmation(final int position) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        currentPosition = position;

        final StudentObservation observation = studentObservationArrayList.get(position);
        Log.e("dddddddd", observation + "");
        builder.setTitle("Confirmation");
        String message = "Are you sure to Call " + observation.getTeacherName() + " ?";
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
        final StudentObservation observation = studentObservationArrayList.get(currentPosition);
        String number = observation.getPhoneNo();
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
        final StudentObservation observationmsg = studentObservationArrayList.get(position );
        alert.setMessage("Teacher Name : " + observationmsg.getTeacherName() + "\n");
        alert.setTitle("Message For Teacher:");


        edittext.setHint("Enter your message here");
        edittext.setText("");
        alert.setView(edittext);

        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (TextUtils.isEmpty(edittext.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Message should not be empty", Toast.LENGTH_SHORT).show();
                } else {
                    messageEditText = edittext.getText().toString();
                    submitMessage(observationmsg, position);
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

    private void submitMessage(final StudentObservation assignmessage, final int position) {
      try{  if (UIUtil.isInternetAvailable(this)) {

            UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
            final JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("mobile_no", assignmessage.getPhoneNo());
            jsonObject.addProperty("message", messageEditText);
            jsonObject.addProperty("user_type", "parent");
            jsonObject.addProperty("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
            jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
            RetrofitAPI.getInstance(this).getApi().sendassignmentmessage(jsonObject, new Callback<JsonObject>() {
                @Override
                public void success(JsonObject object, Response response) {
                    try{UIUtil.stopProgressDialog(getApplicationContext());

                    Log.e("Json ", "Hhd --- " + object.toString());
                    Log.e("Json ", "Response --- " + response.getBody());
                    if (object.get("status").getAsInt() == Constants.SUCCESS) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        studentObservationArrayList.set(position, assignmessage);
                        parentObservationAdapter.notifyDataSetChanged();
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
                    UIUtil.stopProgressDialog(getApplicationContext());
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Connect to internet.", Toast.LENGTH_LONG).show();
        }} catch (Exception e) {
          e.printStackTrace();
      }

    }


    private void spinData() {
        for (int i = 0; i < studentObservationArrayList.size(); i++) {

            updateData(studentObservationArrayList.get(i));
            break;

        }
    }

    private void updateData(StudentObservation studentObservation) {
        observationArrayList.clear();
        //observationArrayList.addAll(studentObservation.getObservation());

        parentObservationAdapter.notifyDataSetChanged();
    }
}





