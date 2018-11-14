package com.maxlore.edumanage.Activities.ParentActivities;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.ParentAdapters.ParentAssignmentDetailAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.ParentModels.ParentAssignmentDetails.ParentAssignment;
import com.maxlore.edumanage.Models.ParentModels.ParentAssignmentDetails.ParentAssignmentResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentAssignmentDetails.ParentAssignmentStructure;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ParentAssignmentActivity extends AppCompatActivity {
    private RecyclerView parentrecyclerView;
    private ParentAssignmentDetailAdapter assignmentDetailAdapter;
    private List<ParentAssignmentStructure> parentAssignmentResponseslist;
    private List<ParentAssignmentStructure> parentAssignmentStructureslist;
    private List<ParentAssignment> parentAssignmentslist;
    private ArrayAdapter<String> spinneradapter;
    private ArrayList<String> categories;
    private int currentPosition;
    private TextView tv_nodata, questionbox;
    private String spiner;
    private DownloadManager downloadManager;
    private String YouEditTextValue;
    private long reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_assignment);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        parentrecyclerView = (RecyclerView) findViewById(R.id.lvAssignment);
        tv_nodata = (TextView) findViewById(R.id.tv_nodata);

        parentAssignmentResponseslist = new ArrayList<>();
        parentAssignmentStructureslist = new ArrayList<>();
        parentAssignmentslist = new ArrayList<>();

        parentrecyclerView.setHasFixedSize(true);
        parentrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        assignmentDetailAdapter = new ParentAssignmentDetailAdapter(this, parentAssignmentslist);
        parentrecyclerView.setAdapter(assignmentDetailAdapter);
        //assignmentDetailAdapter.notifyDataSetChanged();

        categories = new ArrayList<>();

        markAssignment();

        assignmentDetailAdapter.SetOnItemClickListener(new ParentAssignmentDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                alertquestionBox(position);
            }

            @Override
            public void onDownload(View view, int position) {
                Log.e("onItemClick", "messaging ===== p-" + position);
                ParentAssignment parentAssignment = parentAssignmentslist.get(position);
                startDownloadPdf(parentAssignment.getFilePath(), parentAssignment.getFileName());

            }

        });
    }

    private void startDownloadPdf(String path, String filename) {
        if (downloadManager == null)
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse( path);
        //http://www.axmag.com/download/pdfurl-guide.pdf
//        Uri uri = Uri.parse("http://www.axmag.com/download/pdfurl-guide.pdf");
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(filename + " Pdf");
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "Assignment_" + filename + ".pdf");
        request.setVisibleInDownloadsUi(true);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        reference = downloadManager.enqueue(request);
        Toast.makeText(getApplicationContext(), "Downloaded successfully...", Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(ParentAssignmentActivity.this, ParentLandingActivity.class));
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
        }
        if (id == R.id.app_info) {
            alertboxpopup();
        }

        return super.onOptionsItemSelected(item);
    }

    private AlertDialog alertquestionBox(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View promptView = inflater.inflate(R.layout.list_item_pop_question, null);
        questionbox = (TextView) promptView.findViewById(R.id.questionbox);
        builder.setTitle(" Assignment: ");

        questionbox.setText(parentAssignmentslist.get(position).getQuestion());
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
        startActivity(new Intent(ParentAssignmentActivity.this, ParentLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }


    private void markAssignment() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));

                RetrofitAPI.getInstance(this).getApi().getParentAssignment(params, new Callback<ParentAssignmentResponse>() {
                    @Override
                    public void success(ParentAssignmentResponse parentAssignmentResponse, Response response) {
                        try {
                            if (parentAssignmentResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                parentAssignmentResponseslist.add(parentAssignmentResponse.getStudentAssignment());
                                setStudentData();
                                //updateSpinner();
                                Log.e("assignment", "-------" + parentAssignmentResponseslist);
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "" + parentAssignmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.getCause();
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
/*
    private void callConfirmation(int position) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        currentPosition = position;

        final ParentAssignment student = parentAssignmentslist.get(position);
        builder.setTitle("Confirmation");
        String message = "Are you sure to Call " + student.getTeacherName() + " ?";
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

    }*/

 /*   private void callParent() {
        final ParentAssignment parentAssignment = parentAssignmentslist.get(currentPosition);
        String number = parentAssignment.getPhone();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        startActivity(intent);
    }*/

  /*  private void messageConfirmation(final int position) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        final ParentAssignment parentasg = parentAssignmentslist.get(position);
        alert.setMessage("Teacher Name : " + parentasg.getTeacherName() + "\n");
        alert.setTitle("Assignment:");


        edittext.setHint("Enter your message here");
        edittext.setText("");

        alert.setView(edittext, 40, 2, 40, 2);

        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                YouEditTextValue = edittext.getText().toString();

                submitMessage(parentasg, position);

            }
        });

        alert.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }*/

/*    private void submitMessage(final ParentAssignment assignmessage, final int position) {
        if (UIUtil.isInternetAvailable(this)) {

            UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
            jsonObject.addProperty("mobile_no", assignmessage.getPhone());
            jsonObject.addProperty("message", YouEditTextValue);
            jsonObject.addProperty("user_type", "parent");
            RetrofitAPI.getInstance(this).getApi().sendassignmentmessage(jsonObject, new Callback<JsonObject>() {
                @Override
                public void success(JsonObject object, Response response) {
                    UIUtil.stopProgressDialog(getApplicationContext());
                }

                @Override
                public void failure(RetrofitError error) {
                    UIUtil.stopProgressDialog(getApplicationContext());
                }
            });
        }
    }*/

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.parentleavespinner, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        spinneradapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinneradapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spiner = categories.get(position);
                setStudentData(spiner);
                Log.e("selectposition", "====" + spiner + parentAssignmentslist);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return true;
    }*/

    private void setStudentData() {
        for (int i = 0; i < parentAssignmentResponseslist.size(); i++) {

            updateAssignmentDetails(parentAssignmentResponseslist.get(i));
            break;


        }
    }

    private void updateAssignmentDetails(ParentAssignmentStructure parentasignmentstruct) {
        try {
            parentAssignmentslist.clear();
            parentAssignmentslist.addAll(parentasignmentstruct.getAssignment());
            Log.e("assignment", "-------" + parentAssignmentslist);
            if (parentAssignmentslist.size() <= 0) {
                parentrecyclerView.setVisibility(View.GONE);
                tv_nodata.setVisibility(View.VISIBLE);
            } else {
                parentrecyclerView.setVisibility(View.VISIBLE);
                tv_nodata.setVisibility(View.GONE);
            }
            assignmentDetailAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*    private void updateSpinner() {
        categories.clear();
        for (int i = 0; i < parentAssignmentResponseslist.size(); i++) {
            ParentAssignmentStructure parentasignmentstruct = parentAssignmentResponseslist.get(i);
            categories.add(parentasignmentstruct.getName());
        }
        spinneradapter.notifyDataSetChanged();
    }*/

}
