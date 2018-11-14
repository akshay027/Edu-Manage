package com.maxlore.edumanage.Activities.AdminActivities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.AdminResignationAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminResignation;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminResignationResponse;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AdminResignationActivity extends AppCompatActivity {
    private ListView resignationView;
    private EditText etSearch;
    private ArrayList<AdminResignation> resignationArrayList, searchArraylist;
    private AdminResignationAdapter adminResignationAdapter;
    private Handler handler;
    public static final int TIME_OUT = 1000;
    private TextView tvErrorAnn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_resignation);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        resignationView = (ListView) findViewById(R.id.resignationView);
        etSearch = (EditText) findViewById(R.id.etSearch);
        tvErrorAnn = (TextView) findViewById(R.id.tvErrorAnn);

        resignationArrayList = new ArrayList<>();
        searchArraylist = new ArrayList<>();

        handler = new Handler();

        adminResignationAdapter = new AdminResignationAdapter(this, resignationArrayList);
        resignationView.setAdapter(adminResignationAdapter);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
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
                    if (searchArraylist.size() > 0) {
                        resignationArrayList.clear();
                        resignationArrayList.addAll(searchArraylist);
                        adminResignationAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        getResignationlist();
    }

    private void filterSearch(String search) {
        try {
            resignationArrayList.clear();
            for (int i = 0; i < searchArraylist.size(); i++) {
                AdminResignation adminResignation = searchArraylist.get(i);
                if (adminResignation.getEmployeeName().toLowerCase().contains(search.toLowerCase()) ||
                        adminResignation.getEmpCode().toLowerCase().contains(search.toLowerCase()) ||
                        adminResignation.getAppliedOn().toLowerCase().contains(search.toLowerCase())) {
                    resignationArrayList.add(adminResignation);
                }
            }
            if (resignationArrayList.size() <= 0) {
                etSearch.setError("No Record found");
            }
            adminResignationAdapter.notifyDataSetChanged();
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
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }


    private void getResignationlist() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }

                RetrofitAPI.getInstance(this).getApi().getResignationlist(params, new Callback<AdminResignationResponse>() {

                    @Override
                    public void success(AdminResignationResponse adminTcResponse, Response response) {
                        try {
                            if (adminTcResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                resignationArrayList.clear();
                                searchArraylist.clear();
                                searchArraylist.addAll(adminTcResponse.getResignations());
                                resignationArrayList.addAll(adminTcResponse.getResignations());
                                if (resignationArrayList.size() <= 0) {
                                    tvErrorAnn.setVisibility(View.VISIBLE);
                                    resignationView.setVisibility(View.GONE);
                                } else {
                                    resignationView.setVisibility(View.VISIBLE);
                                    tvErrorAnn.setVisibility(View.GONE);

                                }
                                adminResignationAdapter.notifyDataSetChanged();
                                Log.e("kjndf", "====" + adminTcResponse.getResignations());


                            } else {
                                Toast.makeText(getApplicationContext(), "" + adminTcResponse.getMessage(), Toast.LENGTH_SHORT).show();
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