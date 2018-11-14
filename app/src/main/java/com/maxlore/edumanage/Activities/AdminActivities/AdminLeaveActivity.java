
package com.maxlore.edumanage.Activities.AdminActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.AdminLeaveApplicantAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminLeave.AdminLeaveApplicantDetails;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminLeave.EditData;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminLeave.EditLeaveResponse;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AdminLeaveActivity extends AppCompatActivity {
    private AdminLeaveApplicantAdapter adminLeaveApplicantAdapter;
    private ListView listView;
    private TextView tv_nodata;
    private ArrayList<AdminLeaveApplicantDetails> leaveeditDataArrayList, searcheditDataArrayList;
    private ArrayList<EditData> editDataArrayList;
    private int pos;
    private EditText searchStudent;
    private Handler handler;
    public static final int TIME_OUT = 1000;
    private String currentdate;
    private Date checkdate, enddate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_leave);

        listView = (ListView) findViewById(R.id.lv_leave);
        tv_nodata = (TextView) findViewById(R.id.tv_nodata);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editDataArrayList = new ArrayList<>();
        searcheditDataArrayList = new ArrayList();

        currentdate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        try {
            checkdate = new SimpleDateFormat("dd/MM/yyyy")
                    .parse(currentdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        leaveeditDataArrayList = new ArrayList<>();

        searchStudent = (EditText) findViewById(R.id.etSearch);

        handler = new Handler();
        adminLeaveApplicantAdapter = new AdminLeaveApplicantAdapter(this, leaveeditDataArrayList);
        listView.setAdapter(adminLeaveApplicantAdapter);
        //adminLeaveApplicantAdapter.notifyDataSetChanged();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        searchStudent.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // pos = position;
                if (leaveeditDataArrayList.get(position).getStatus() == null || leaveeditDataArrayList.get(position).getStatus() == true) {
                    try {
                        enddate = new SimpleDateFormat("dd/MM/yyyy")
                                .parse(leaveeditDataArrayList.get(position).getEndDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (enddate.before(checkdate)) {
                        Toast.makeText(getApplicationContext(), "Cannot edit as date exceeded...", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), AdminEditLeaveActivity.class);
                        intent.putExtra("lvid", leaveeditDataArrayList.get(position).getLeaveId());
                        intent.putExtra("id", leaveeditDataArrayList.get(position).getEmployeeId());
                        intent.putExtra(Constants.NAME, leaveeditDataArrayList.get(position).getEmployeeName());
                        intent.putExtra("status", leaveeditDataArrayList.get(position).getStatus());
                        intent.putExtra("leavetype", leaveeditDataArrayList.get(position).getEmployeeLeaveType());
                        intent.putExtra(Constants.FROMDATE, leaveeditDataArrayList.get(position).getStartDate());
                        intent.putExtra(Constants.ENDDATE, leaveeditDataArrayList.get(position).getEndDate());
                        intent.putExtra(Constants.REMARK, leaveeditDataArrayList.get(position).getRemark());
                        intent.putExtra(Constants.REASON, leaveeditDataArrayList.get(position).getReason());
                        intent.putExtra(Constants.HALFDAY, leaveeditDataArrayList.get(position).getHalfDay());
                        intent.putExtra("List", editDataArrayList);
                        intent.putExtra(Constants.LEAVETYPE, leaveeditDataArrayList.get(position).getEmployeeLeaveType());
                        startActivity(intent);

                    }
                } else {
                    listView.setClickable(false);
                }
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
                    if (searcheditDataArrayList.size() > 0) {
                        leaveeditDataArrayList.clear();
                        leaveeditDataArrayList.addAll(searcheditDataArrayList);
                        adminLeaveApplicantAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        getLeaveApplicant();
    }


    private void filterSearch(String search) {
        try {
            leaveeditDataArrayList.clear();
            for (int i = 0; i < searcheditDataArrayList.size(); i++) {
                AdminLeaveApplicantDetails details = searcheditDataArrayList.get(i);
                if (details.getEmployeeName().toLowerCase().contains(search.toLowerCase()) || details.getReason().toLowerCase().contains(search.toLowerCase()) ||
                        details.getEndDate().toLowerCase().contains(search.toLowerCase()) || details.getStartDate().toLowerCase().contains(search.toLowerCase())
                        || details.getEmployeeLeaveType().toLowerCase().contains(search.toLowerCase())) {
                    leaveeditDataArrayList.add(details);
                }
            }
            if (leaveeditDataArrayList.size() <= 0) {
                searchStudent.setError("No Record found");
            }
            adminLeaveApplicantAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLeaveApplicant() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().getLeaveApplicant(params, new Callback<EditLeaveResponse>() {

                    @Override
                    public void success(EditLeaveResponse adminLeaveResponse, Response response) {
                        try {
                            if (adminLeaveResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                leaveeditDataArrayList.clear();
                                searcheditDataArrayList.clear();
                                editDataArrayList.clear();
                                searcheditDataArrayList.addAll(adminLeaveResponse.getEmployeeList());
                                leaveeditDataArrayList.addAll(adminLeaveResponse.getEmployeeList());
                                editDataArrayList.addAll(adminLeaveResponse.getLeaveType());
                                Collections.sort(leaveeditDataArrayList, new AdminLeaveActivity.PersonComparator());

                       /* for (int i = 0; i < leaveeditDataArrayList.size(); i++) {
                            AdminLeaveApplicantDetails details = leaveeditDataArrayList.get(i);
                            details.setA(false);
                            details.setB(false);
                            leaveeditDataArrayList.set(i, details);
                        }*/
                                adminLeaveApplicantAdapter.notifyDataSetChanged();


                            } else {
                                Toast.makeText(AdminLeaveActivity.this, "" + adminLeaveResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

    public class PersonComparator implements Comparator<AdminLeaveApplicantDetails> {
        public int compare(AdminLeaveApplicantDetails o1, AdminLeaveApplicantDetails o2) {
            // Assume no nulls, and simple ordinal comparisons

            int value1 = o1.getEmployeeName().compareTo(o2.getEmployeeName());
            if (value1 == 0) {
                return o1.getEmployeeName().compareTo(o2.getEmployeeName());
            }
            return value1;
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
            startActivity(new Intent(AdminLeaveActivity.this, AdminLandingActivity.class));
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
        }
        if (id == R.id.app_info) {
            alertBox();
        }

        return super.onOptionsItemSelected(item);
    }

    private AlertDialog alertBox() {
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
        startActivity(new Intent(AdminLeaveActivity.this, AdminLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }


}
