package com.maxlore.edumanage.Activities.ParentActivities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.ParentAdapters.ParentTransferCertificatelistItemAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.ParentModels.ParentTransfercertificate.ParentTansfercertiResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentTransfercertificate.TransferList;
import com.maxlore.edumanage.Models.ParentModels.ParentTransfercertificate.TransferCertificate;

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

public class ParentTransferCertificateListItemActivity extends AppCompatActivity {
    
    private ParentTransferCertificatelistItemAdapter parentTransferCertificatelistItemAdapter;
    private List<TransferList> transfercertificateArraylist;
    private List<TransferCertificate> transferinfoArraylist;
    private RecyclerView transferRecyclerview;
    private ArrayAdapter<String> spinneradapter;
    private ArrayList<String> categories;
    private String spiner;
    private int result=0;
    private TextView tvStudentname, tv_nodata;
    private ArrayList<String> applyArraylist;
    private LinearLayout llleaveAval, llApply;
    private ParentTansfercertiResponse response;
    private int Response;
    private boolean c = true;
    private final Context context = this;
    private TransferCertificate transferCertificate;
    private String daysleft;


    TextView btnApply, applybutton, daylefttv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_certificate_list_item);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        llleaveAval = (LinearLayout) findViewById(R.id.llleaveAval);
        llApply = (LinearLayout) findViewById(R.id.llApply);
        btnApply = (TextView) findViewById(R.id.btnApply);

        transferinfoArraylist = new ArrayList<>();
        tv_nodata = (TextView) findViewById(R.id.tv_nodata);
        applybutton = (TextView) findViewById(R.id.applybutton);
        daylefttv = (TextView) findViewById(R.id.daylefttv);
        transferRecyclerview = (RecyclerView) findViewById(R.id.rvTransfercerti);
        transfercertificateArraylist = new ArrayList<>();
        categories = new ArrayList<>();

        transferRecyclerview.setHasFixedSize(true);
        transferRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        parentTransferCertificatelistItemAdapter = new ParentTransferCertificatelistItemAdapter(this, transfercertificateArraylist);
        transferRecyclerview.setAdapter(parentTransferCertificatelistItemAdapter);
        parentTransferCertificatelistItemAdapter.notifyDataSetChanged();


        applyArraylist = new ArrayList();
        try {

            Intent intent = getIntent();
            Response = Integer.parseInt(getIntent().getStringExtra("response"));

            if (Constants.SUCCESS == Response) {
                getParentTransferDetails();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //getParentTransferDetails();

        parentTransferCertificatelistItemAdapter.SetOnItemClickListener(new ParentTransferCertificatelistItemAdapter.OnItemClickListener() {
            @Override
            public void onAckClick(View s, int position) {
                setMessage(position);

            }

        });


        applybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ParentTransferCertificateActivity.class);
                // intent.putExtra("id", String.valueOf(transfercertificateArraylist.get(result).getStudentId()));
                // intent.putExtra(Constants.NAME, (transfercertificateArraylist.get(result)).getStudentName());
                intent.putExtra(Constants.STANDARD, (transferinfoArraylist.get(result).getStandard()));
                intent.putExtra(Constants.SECTION, (transferinfoArraylist.get(result).getSection()));
                intent.putExtra("admission id", (transferinfoArraylist.get(result).getAdmissionNumber()));
                intent.putExtra(Constants.PARENT, (transferinfoArraylist.get(result).getParentName()));
                Log.e("std", "---" + transferinfoArraylist.get(result).getStandard());
                Log.e("adm", "---" + transferinfoArraylist.get(result).getAdmissionNumber());
                Log.e("add", "---" + transferinfoArraylist.get(result).getParentName()
                );
                Log.e("sec", "---" + transferinfoArraylist.get(result).getSection());
                startActivity(intent);
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ParentTransferCertificateActivity.class);
                // intent.putExtra("id", String.valueOf(transfercertificateArraylist.get(result).getStudentId()));
                // intent.putExtra(Constants.NAME, (transfercertificateArraylist.get(result)).getStudentName());
                intent.putExtra(Constants.STANDARD, (transferinfoArraylist.get(result).getStandard()));
                intent.putExtra(Constants.SECTION, (transferinfoArraylist.get(result).getSection()));
                intent.putExtra("admission id", (transferinfoArraylist.get(result).getAdmissionNumber()));
                intent.putExtra(Constants.PARENT, (transferinfoArraylist.get(result).getParentName()));
                Log.e("std", "---" + transferinfoArraylist.get(result).getStandard());
                Log.e("adm", "---" + transferinfoArraylist.get(result).getAdmissionNumber());
                Log.e("parent", "---" + transferinfoArraylist.get(result).getParentName());
                Log.e("sec", "---" + transferinfoArraylist.get(result).getSection());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        getParentTransferDetails();
    }

    private void setMessage(final int pos) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        final EditText edittext = new EditText(context);
        // set title
        alertDialogBuilder.setTitle("Confirmation");
        alertDialogBuilder
                .setMessage("Do You Want to Cancel it ?")
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String YouEditTextValue = edittext.getText().toString();
                        deleteTc(pos);
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

    private void deleteTc(int position) {
        try{if (UIUtil.isInternetAvailable(this)) {
            UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("transfer_id", transfercertificateArraylist.get(position).getTransferId());
            jsonObject.addProperty("branch_id",PreferencesManger.getStringFields(getApplicationContext(),Constants.Pref.KEY_BRANCH_ID));
            RetrofitAPI.getInstance(this).getApi().canceTc(jsonObject, new Callback<JsonObject>() {
                @Override
                public void success(JsonObject object, Response response) {
                    UIUtil.stopProgressDialog(getApplicationContext());
                    Log.e("jsonObject", "jsonObject --- " + object.toString());
                    getParentTransferDetails();

                    try {
                        if (object == null) {
                            Toast.makeText(ParentTransferCertificateListItemActivity.this, "Something went worng, try after sometime...", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (object.get("status").getAsInt() == Constants.SUCCESS) {
                            Toast.makeText(ParentTransferCertificateListItemActivity.this, object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(ParentTransferCertificateListItemActivity.this, object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(ParentTransferCertificateListItemActivity.this, "Something went worng, try after sometime...", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    UIUtil.stopProgressDialog(getApplicationContext());
                }
            });
        } else {

            Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
        }} catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.parentleavespinner, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        spinneradapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinneradapter);
        // getParentTransferDetails();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spiner = categories.get(position);
                updateListItem(spiner);
                result = position;
                spiner = (categories.get(position));
                updateListItem(spiner);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return true;
    }*/

   /* private void updateSpinner() {
        categories.clear();
        for (int i = 0; i < transfercertificateArraylist.size(); i++) {
            TransferCertificate transferCertificate = transfercertificateArraylist.get(i);
            categories.add(transferCertificate.getStudentName());
        }
        spinneradapter.notifyDataSetChanged();
        updateListItem(String.valueOf(spiner));
    }
*/
   /* private void updateListItem() {
        for (int i = 0; i < transfercertificateArraylist.size(); i++) {
                updateData(transfercertificateArraylist.get(i));
                break;
            }

    }


    private void updateData(TransferCertificate parenttransferinfoitem) {
        transferinfoArraylist.clear();
        transferinfoArraylist.addAll(parenttransferinfoitem.getTransferList());
        // if (transferinfoArraylist.size() > 0) {
        Log.e("transferinfoArraylist", "transferinfoArraylist -- " + transferinfoArraylist);
        parentTransferCertificatelistItemAdapter = new ParentTransferCertificatelistItemAdapter(this, transferinfoArraylist);
        transferRecyclerview.setAdapter(parentTransferCertificatelistItemAdapter);
        parentTransferCertificatelistItemAdapter.notifyDataSetChanged();

        if (parenttransferinfoitem.getApplyAgain()) {
            btnApply.setVisibility(View.VISIBLE);
        } else {
            btnApply.setVisibility(View.GONE);
        }

            *//*llleaveAval.setVisibility(View.VISIBLE);
            llApply.setVisibility(View.GONE);
        } else {
            llleaveAval.setVisibility(View.GONE);
            llApply.setVisibility(View.VISIBLE);*//*


    }
*/

    private void getParentTransferDetails() {
       try{ if (UIUtil.isInternetAvailable(this)) {
            UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
            Map<String, String> params = new HashMap<String, String>();
            params.put("student_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_Id));
            params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));

            RetrofitAPI.getInstance(this).getApi().getParentTransferDetails(params, new Callback<ParentTansfercertiResponse>() {
                @Override
                public void success(ParentTansfercertiResponse parentTansfercertiResponse, Response response) {
                    UIUtil.stopProgressDialog(getApplicationContext());
                    Log.e("API", "dashboardResponses" + parentTansfercertiResponse.toString());
                    Log.e("API", "dashboardResponses" + response.getBody());
                    try {
                        if (parentTansfercertiResponse.getStatus() == Constants.SUCCESS) {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            transfercertificateArraylist.clear();
                            transferinfoArraylist.clear();
                            transferinfoArraylist.add(parentTansfercertiResponse.getStudentDetails());
                            transfercertificateArraylist.addAll(parentTansfercertiResponse.getTransferCertificate());
                            parentTransferCertificatelistItemAdapter.notifyDataSetChanged();
                            if (parentTansfercertiResponse.getApplyAgain()) {
                                btnApply.setVisibility(View.VISIBLE);
                            } else {
                                btnApply.setVisibility(View.GONE);
                            }
                            if (transfercertificateArraylist.size() <= 0) {
                                llApply.setVisibility(View.VISIBLE);
                                llleaveAval.setVisibility(View.GONE);
                            } else {
                                llApply.setVisibility(View.GONE);
                                llleaveAval.setVisibility(View.VISIBLE);
                            }
                            daysleft = parentTansfercertiResponse.getDaysLeft();
                            for (int i = 0; i < transfercertificateArraylist.size(); i++) {
                                transfercertificateArraylist.get(i).getStatus();
                                if (transfercertificateArraylist.get(i).getStatus().equalsIgnoreCase("approved")) {
                                    daylefttv.setText("Your Account will be Deactivated Within" + " " + parentTansfercertiResponse.getDaysLeft() + " " + "Days");
                                } else {
                                    daylefttv.setVisibility(View.GONE);
                                }
                            }

                            Log.e("list item", "=======" + transfercertificateArraylist);
                            //updateSpinner();

                        } else {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Toast.makeText(getApplicationContext(), "" + parentTansfercertiResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
        }} catch (Exception e) {
           e.printStackTrace();
       }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            startActivity(new Intent(ParentTransferCertificateListItemActivity.this, ParentLandingActivity.class));
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(ParentTransferCertificateListItemActivity.this, ParentLandingActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }
}
