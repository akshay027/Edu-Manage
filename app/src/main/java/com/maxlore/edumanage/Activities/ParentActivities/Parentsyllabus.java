package com.maxlore.edumanage.Activities.ParentActivities;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.ParentAdapters.ParentSyllabusAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.ParentModels.ParentSyllabus.ParentSyllabus;
import com.maxlore.edumanage.Models.ParentModels.ParentSyllabus.ParentSyllabusResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentSyllabus.Syllabu;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;

public class Parentsyllabus extends AppCompatActivity {
    private List<String> list;
    private ArrayAdapter<String> parentspinAdapter;
    private ArrayList<ParentSyllabus> parentSyllabusArrayList, searchArrayList;
    private String pos;
    private ParentSyllabusAdapter parentSyllabusAdapter;
    private RecyclerView parentsyllabusRecyclerAdapter;
    private List<Syllabu> syllabuarrayList;
    private EditText searchSubject;
    public static final int TIME_OUT = 1000;
    private Handler handler;
    private DownloadManager downloadManager;
    private long reference;
    private BroadcastReceiver downloadComplete;
    private TextView tv_nodata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_syllabus);
        parentsyllabusRecyclerAdapter = (RecyclerView) findViewById(R.id.parentsyllabusRecyclerAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_nodata = (TextView) findViewById(R.id.tv_nodata);

        list = new ArrayList<String>();
        parentSyllabusArrayList = new ArrayList<>();
        syllabuarrayList = new ArrayList<>();
        searchArrayList = new ArrayList<>();

        searchSubject = (EditText) findViewById(R.id.etSearchSubject);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        handler = new Handler();

        parentsyllabusRecyclerAdapter.setLayoutManager(llm);
        parentSyllabusAdapter = new ParentSyllabusAdapter(this, parentSyllabusArrayList);
        parentsyllabusRecyclerAdapter.setAdapter(parentSyllabusAdapter);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        searchSubject.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });

        getParentSyllabus();

        parentSyllabusAdapter.SetOnItemClickListener(new ParentSyllabusAdapter.OnItemClickListener() {
            @Override
            public void onDownloadSyllabusTeacher(View view, int position) {
                Log.e("onItemClick", "messaging ===== p-" + position);
                ParentSyllabus syllabus = parentSyllabusArrayList.get(position);
                if ("Syllabus not created".equalsIgnoreCase(syllabus.getFilepath())) {
                    Toast.makeText(getApplicationContext(), " " + syllabus.getFilepath(), Toast.LENGTH_SHORT).show();
                } else {
                    startDownloadPdf(syllabus.getFilepath(), syllabus.getSubjectName());
                }
            }
        });
        searchSubject.addTextChangedListener(new TextWatcher() {
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
                        parentSyllabusArrayList.clear();
                        parentSyllabusArrayList.addAll(searchArrayList);
                        parentSyllabusAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
            startActivity(new Intent(Parentsyllabus.this, ParentLandingActivity.class));
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
        startActivity(new Intent(Parentsyllabus.this, ParentLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }


    private void filterSearch(String search) {
        try {
            parentSyllabusArrayList.clear();
            for (int i = 0; i < searchArrayList.size(); i++) {
                ParentSyllabus syllabu = searchArrayList.get(i);
                if (syllabu.getSubjectName().toLowerCase().contains(search.toLowerCase())) {
                    parentSyllabusArrayList.add(syllabu);
                }
            }
            if (parentSyllabusArrayList.size() <= 0) {
                searchSubject.setError("No Record found");
            }
            parentSyllabusAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getParentSyllabus() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
                params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));

                RetrofitAPI.getInstance(this).getApi().getParentSyllabus(params, new Callback<ParentSyllabusResponse>() {
                    @Override
                    public void success(ParentSyllabusResponse parentSyllabusResponse, retrofit.client.Response response) {
                        try {
                            if (parentSyllabusResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                searchArrayList.clear();
                                parentSyllabusArrayList.clear();
                                parentSyllabusArrayList.addAll(parentSyllabusResponse.getStudentSyllabus());
                                searchArrayList.addAll(parentSyllabusResponse.getStudentSyllabus());
                                parentSyllabusAdapter.notifyDataSetChanged();
                                if (parentSyllabusArrayList.size() <= 0) {
                                    parentsyllabusRecyclerAdapter.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                } else {
                                    parentsyllabusRecyclerAdapter.setVisibility(View.VISIBLE);
                                    tv_nodata.setVisibility(View.GONE);
                                }
                                Log.e("onItemClick", "messaging ===== p-" + parentSyllabusArrayList);
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "" + parentSyllabusResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

   /* private void bindData(List<Syllabu> syllabus) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchArrayList.clear();
        for (int i = 0; i < parentSyllabusArrayList.size(); i++) {
            syllabuarrayList.addAll(parentSyllabusArrayList.get(i).getSyllabus());
            searchArrayList.addAll(parentSyllabusArrayList.get(i).getSyllabus());
        }

        parentSyllabusAdapter.notifyDataSetChanged();

       // spinData();
    }*/

/*
    @Override
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
        for (int i = 0; i < parentSyllabusArrayList.size(); i++) {
            list.add(parentSyllabusArrayList.get(i).getName());
        }
        parentspinAdapter.notifyDataSetChanged();
        Log.e("aaaaaaaaaaaaaaaaaa", list + "");
    }

*/
 /*   private void spinData() {
        for (int i = 0; i < parentSyllabusArrayList.size(); i++) {
                updateData(parentSyllabusArrayList.get(i));
                break;

        }
    }

    private void updateData(com.maxlore.inmegh.Models.ParentModels.ParentSyllabus.ParentSyllabus parentSyllabus) {
        syllabuarrayList.clear();
        syllabuarrayList.addAll(parentSyllabus.getSyllabus());
        parentSyllabusAdapter.notifyDataSetChanged();
    }*/


    private void startDownloadPdf(String path, String subject) {
        if (downloadManager == null)
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(path);
        //http://www.axmag.com/download/pdfurl-guide.pdf
//        Uri uri = Uri.parse("http://www.axmag.com/download/pdfurl-guide.pdf");
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle("Syllabus Pdf");
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "syllabus_" + subject + ".pdf");
        request.setVisibleInDownloadsUi(true);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        reference = downloadManager.enqueue(request);
        Toast.makeText(getApplicationContext(), "Downloaded successfully...", Toast.LENGTH_SHORT).show();
    }
}
