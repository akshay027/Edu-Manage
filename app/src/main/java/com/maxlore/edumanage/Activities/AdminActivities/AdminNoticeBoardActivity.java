package com.maxlore.edumanage.Activities.AdminActivities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.AdminNoticeBoardAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.Notice;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.NoticeBoardResponse;
import com.maxlore.edumanage.Models.StatusResponseClass;

import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AdminNoticeBoardActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText titleEdittext, messageEditText;
    private TextView tv_nodata;
    private TextView btnCreate;
    private ArrayList<Notice> responseArraylist;
    private AdminNoticeBoardAdapter adminNoticeBoardAdapter;
    private RecyclerView rvAnnouncements;
    private final Context context = this;
    private int result, pos;
    private EditText etMessage, tvTittle;
    private String currentdate;
    private Date chckdate, enddate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notice_board);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //this.setTheme(R.style.AlertDialogCustom);

        titleEdittext = (EditText) findViewById(R.id.tvTittle);
        messageEditText = (EditText) findViewById(R.id.etMessage);

        /*   errannounce = (TextView) findViewById(R.id.errannounce);*/
        rvAnnouncements = (RecyclerView) findViewById(R.id.rvAnnouncements);
        tv_nodata = (TextView) findViewById(R.id.tv_nodata);
        //  tvDate = (TextView) findViewById(R.id.tvDate);
        // tvDate.setOnClickListener(this);

        btnCreate = (TextView) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);
        currentdate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        try {
            chckdate = new SimpleDateFormat("dd/MM/yyyy")
                    .parse(currentdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        responseArraylist = new ArrayList<>();
        rvAnnouncements.setHasFixedSize(true);
        rvAnnouncements.setLayoutManager(new LinearLayoutManager(this));
        adminNoticeBoardAdapter = new AdminNoticeBoardAdapter(this, responseArraylist);
        rvAnnouncements.setAdapter(adminNoticeBoardAdapter);
        adminNoticeBoardAdapter.notifyDataSetChanged();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        messageEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });


        titleEdittext.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        getNoticeBoard();
        adminNoticeBoardAdapter.SetOnItemClickListener(new AdminNoticeBoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                message(position);
            }

            @Override
            public void onDeleteClick(View view, int position) {
                result = position;
                try {
                    enddate = new SimpleDateFormat("dd/MM/yyyy")
                            .parse(responseArraylist.get(position).getPostedOn());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (enddate.before(chckdate)) {
                    Toast.makeText(getApplicationContext(), "Cannot delete as date exceeded...", Toast.LENGTH_SHORT).show();
                } else {
                    setMessage();
                }

            }

            @Override
            public void onEditClick(View view, int position) {
                pos = position;
                try {
                    enddate = new SimpleDateFormat("dd/MM/yyyy")
                            .parse(responseArraylist.get(position).getPostedOn());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (enddate.before(chckdate)) {
                    Toast.makeText(getApplicationContext(), "Cannot edit as date exceeded...", Toast.LENGTH_SHORT).show();
                } else {
                    editConfirmation(pos);
                }

            }
        });
    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        getNoticeBoard();
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
            startActivity(new Intent(AdminNoticeBoardActivity.this, AdminLandingActivity.class));
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
        }
        if (id == R.id.app_info) {
            alertBox();
        }

        return super.onOptionsItemSelected(item);
    }

    private AlertDialog alertBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
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
        startActivity(new Intent(AdminNoticeBoardActivity.this, AdminLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }


    private void setMessage() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        final EditText edittext = new EditText(context);
        // set title
        alertDialogBuilder.setTitle("Confirmation");
        alertDialogBuilder
                .setMessage("Do You Want to Delete it")
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteMessage();
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

    private void message(int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(false);
        final TextView textView = new TextView(this);
        final Notice notice = responseArraylist.get(position);
        alert.setMessage(notice.getTitle() + "\n");

        textView.setText(notice.getMessage());
        alert.setView(textView);

        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }

    private AlertDialog editConfirmation(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        View promptView = inflater.inflate(R.layout.list_item_admin_noticeboard_dialogbox, null);
        final Notice notice = responseArraylist.get(pos);

        tvTittle = (EditText) promptView.findViewById(R.id.tvTittle);
        etMessage = (EditText) promptView.findViewById(R.id.etMessage);

        tvTittle.setText("" + notice.getTitle());
        etMessage.setText("" + notice.getMessage());

        etMessage.setInputType(InputType.TYPE_CLASS_TEXT);
        tvTittle.setInputType(InputType.TYPE_CLASS_TEXT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        tvTittle.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });


        etMessage.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        builder.setView(promptView)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if ((validateFieldsedit())) {
                            editMessage();
                        } else {
                            editConfirmation(pos);
                        }

                    }
                });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return builder.create();
    }

    private void deleteMessage() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details.");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id", responseArraylist.get(result).getId());
                jsonObject.addProperty("perform", "delete");
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().creteNoticeBoard(jsonObject, new Callback<StatusResponseClass>() {
                    @Override
                    public void success(StatusResponseClass object, Response response) {
                        Log.e("jsonObject", "jsonObject --- " + object.toString());
                        UIUtil.stopProgressDialog(getApplicationContext());
                        try {
                            if (object.getStatus() == null) {
                                Toast.makeText(AdminNoticeBoardActivity.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (object.getStatus() == Constants.SUCCESS) {

                                Toast.makeText(AdminNoticeBoardActivity.this, "Announcement deleted successfully", Toast.LENGTH_SHORT).show();
                                getNoticeBoard();
                                adminNoticeBoardAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(AdminNoticeBoardActivity.this, object.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AdminNoticeBoardActivity.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "Something went wrong, try again.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editMessage() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id", responseArraylist.get(pos).getId());
                jsonObject.addProperty("perform", "update");
                jsonObject.addProperty("title", tvTittle.getText().toString());
                jsonObject.addProperty("message", etMessage.getText().toString());

                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().creteNoticeBoard(jsonObject, new Callback<StatusResponseClass>() {
                    @Override
                    public void success(StatusResponseClass object, Response response) {
                        Log.e("jsonObject", "jsonObject --- " + object.toString());
                        UIUtil.stopProgressDialog(getApplicationContext());
                        try {
                            if (object.getStatus() == null) {
                                Toast.makeText(AdminNoticeBoardActivity.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (object.getStatus() == Constants.SUCCESS) {

                                Toast.makeText(AdminNoticeBoardActivity.this, "Announcement Updated", Toast.LENGTH_SHORT).show();
                                getNoticeBoard();
                                adminNoticeBoardAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(AdminNoticeBoardActivity.this, object.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AdminNoticeBoardActivity.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                    }
                });
            } else {

                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "Something went wrong, try again.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreate:
                createAnnouncement();
                break;
        }
    }

    private boolean validateFields() {
        if (titleEdittext.getText().toString().trim().isEmpty()) {
            titleEdittext.setError("Enter Title");
            return false;
        } else {

            titleEdittext.setError(null);
        }

        if (messageEditText.getText().toString().trim().isEmpty()) {
            messageEditText.setError("Enter Message");
            return false;
        } else {

            messageEditText.setError(null);
        }
        return true;

    }

    private boolean validateFieldsedit() {
        if (tvTittle.getText().toString().trim().isEmpty()) {
            // tvTittle.setError("Enter Title");
            Toast.makeText(this, "Please Enter All The Fields", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            tvTittle.setError(null);
        }
        if (etMessage.getText().toString().trim().isEmpty()) {
            // etMessage.setError("Enter Message");
            Toast.makeText(this, "Please Enter All The Fields ", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etMessage.setError(null);
        }
        return true;
    }

    private void getNoticeBoard() {
        try {
            if (UIUtil.isInternetAvailable(this)) {

                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(this).getApi().getNoticeBoard(params, new Callback<NoticeBoardResponse>() {
                    @Override
                    public void success(NoticeBoardResponse noticeBoardResponse, Response response) {


                        Log.e("Json ", "Hhd --- " + noticeBoardResponse.toString());
                        Log.e("Json ", "Response --- " + response.getBody());
                        try {
                            if (noticeBoardResponse.getStatus() == Constants.SUCCESS) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                responseArraylist.clear();
                                responseArraylist.addAll(noticeBoardResponse.getNotice());
                                if (responseArraylist.size() <= 0) {
                                    rvAnnouncements.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                } else {
                                    rvAnnouncements.setVisibility(View.VISIBLE);
                                    tv_nodata.setVisibility(View.GONE);
                                }
                                adminNoticeBoardAdapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(getApplicationContext(), "" + noticeBoardResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.e("Json ", "Hhd --- " + noticeBoardResponse.toString());
                        Log.e("Json ", "Response --- " + response.getBody());
                        Log.e("spinner1132", "----" + responseArraylist.toString());

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Toast.makeText(getApplicationContext(), "Something went wrong, try again.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createAnnouncement() {
        try {
            if (validateFields()) {

                if (UIUtil.isInternetAvailable(this)) {

                    UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("title", titleEdittext.getText().toString());
                    jsonObject.addProperty("message", messageEditText.getText().toString());
                    if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                        jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                    } else {
                        jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                    }

                    RetrofitAPI.getInstance(this).getApi().creteNoticeBoard(jsonObject, new Callback<StatusResponseClass>() {
                        @Override
                        public void success(StatusResponseClass jsonObj, Response response) {
                            UIUtil.stopProgressDialog(getApplicationContext());

                            Log.e("Json ", "Hhd --- " + jsonObj.toString());
                            Log.e("Json ", "Response --- " + response.getBody());
                            try {

                                if (Constants.SUCCESS == jsonObj.getStatus()) {
                                    UIUtil.stopProgressDialog(getApplicationContext());
                                    Toast.makeText(getApplicationContext(), "Announcement Created successfully", Toast.LENGTH_SHORT).show();

                                    getNoticeBoard();
                                    clearFieldData();

                                } else {
                                    Toast.makeText(getApplicationContext(), jsonObj.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Toast.makeText(getApplicationContext(), "Something went wrong, try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFieldData() {

        titleEdittext.setText("");
        messageEditText.setText("");
    }

}
