package com.maxlore.edumanage.Activities.TeacherActivities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Activities.ParentActivities.SchoolInfoImageviewActivity;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.StudentInfor.StudentInformation;
import com.maxlore.edumanage.Models.TeacherModels.StudentInfor.StudentInformationResponse;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SchoolinfoActivity extends AppCompatActivity {
    private TextView tvSchoolName, tvAddress1, tvAddress2, tvCountry, tvState, tvDistrict, tvPinCode,
            tvCity, tvPhoneNo, tvUgcBoardname, tvTaxType;
    private Toolbar toolbar;
    private ImageView schoolImage;
    private ImageView poster;
    CollapsingToolbarLayout collapsingToolbarLayout;
    String image, schoolname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolinfo);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        schoolImage = (ImageView) findViewById(R.id.image);

        poster = (ImageView) findViewById(R.id.poster);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbarlayout);
        tvSchoolName = (TextView) findViewById(R.id.tvschoolName);
        tvAddress1 = (TextView) findViewById(R.id.tvaddress1);
        tvAddress2 = (TextView) findViewById(R.id.tvaddress2);
        tvCountry = (TextView) findViewById(R.id.tvcountry);
        tvState = (TextView) findViewById(R.id.tvstate);
        tvDistrict = (TextView) findViewById(R.id.tvdistrict);
        tvPinCode = (TextView) findViewById(R.id.tvpincode);
        tvCity = (TextView) findViewById(R.id.tvcity);
        tvPhoneNo = (TextView) findViewById(R.id.tvphoneno);
        tvUgcBoardname = (TextView) findViewById(R.id.tvugcboardname);
        //tvTaxType = (TextView) findViewById(R.id.tvtaxType);

        getStudentInfo();
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SchoolinfoActivity.this, SchoolInfoImageviewActivity.class);
                intent.putExtra("image", image);
                intent.putExtra("schoolname", schoolname);
                startActivity(intent);
            }
        });
    }


    private void getStudentInfo() {
        if (UIUtil.isInternetAvailable(this)) {
            UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
            Map<String, String> params = new HashMap<String, String>();
            params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
            params.put("user_type", "Employee");
            RetrofitAPI.getInstance(this).getApi().getSchoolProfile(params, new Callback<StudentInformationResponse>() {
                @Override
                public void success(StudentInformationResponse studentInfoResponse, Response response) {

                    UIUtil.stopProgressDialog(getApplicationContext());
                    if (studentInfoResponse == null)
                        return;
                    if (studentInfoResponse.getStatus() == Constants.SUCCESS) {
                        bindData(studentInfoResponse.getInfo());
                    } else {
                        Toast.makeText(getApplicationContext(), "" + studentInfoResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    UIUtil.stopProgressDialog(getApplicationContext());

                    Toast.makeText(getApplicationContext(), "Server is not responding, Try after some time.", Toast.LENGTH_SHORT).show();

                }
            });
        } else {

            Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
        }

    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            Log.e("image", "JJJJJJJJJJJJJJJJJ");
            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
            ((AppBarLayout) findViewById(R.id.app_bar)).setBackgroundDrawable(drawable);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    private void bindData(StudentInformation profile) {


        collapsingToolbarLayout.setTitle(profile.getName());
        if (profile.getLogoFileName().isEmpty()) {
            poster.setImageResource(R.drawable.photo);
        } else {
            Picasso.with(this).load(profile.getLogoFileName()).fit().into(poster);
          /*  Picasso.with(context).load(Constants.HostImage + profile.getLogoFileName()).resize(50, 50)
                    .fit().into(holder.imageview);*/
        }

        image = profile.getLogoFileName();
        Log.e("image=============", profile.getLogoFileName());

/*
        if (TextUtils.isEmpty(profile.getName())) {
            toolbar.setTitle("NA");
        } else {
            toolbar.setTitle(profile.getName());
        }*/
        schoolname = profile.getName();
        if (TextUtils.isEmpty(profile.getName())) {
            tvSchoolName.setText("N/A");
        } else {
            tvSchoolName.setText(profile.getName());
        }

        if (TextUtils.isEmpty(profile.getAddLine1())) {
            tvAddress1.setText("N/A");
        } else {
            tvAddress1.setText(profile.getAddLine1());
        }

        if (TextUtils.isEmpty(profile.getAddLine2())) {
            tvAddress2.setText("N/A");
        } else {
            tvAddress2.setText(profile.getAddLine2());
        }

        if (TextUtils.isEmpty(profile.getCountry())) {
            tvCountry.setText("N/A");
        } else {
            tvCountry.setText(profile.getCountry());
        }

        if (TextUtils.isEmpty(profile.getState())) {
            tvState.setText("N/A");
        } else {
            tvState.setText(profile.getState());
        }

        if (TextUtils.isEmpty(profile.getDistrict())) {
            tvDistrict.setText("N/A");
        } else {
            tvDistrict.setText(profile.getDistrict());
        }

        if (TextUtils.isEmpty(profile.getPincode())) {
            tvPinCode.setText("N/A");
        } else {
            tvPinCode.setText(profile.getPincode());
        }

        if (TextUtils.isEmpty(profile.getCity())) {
            tvCity.setText("N/A");
        } else {
            tvCity.setText(profile.getCity());
        }

        if (TextUtils.isEmpty(profile.getPhNumber())) {
            tvPhoneNo.setText("N/A");
        } else {
            tvPhoneNo.setText(profile.getPhNumber());
        }

        if (TextUtils.isEmpty(profile.getUgcBoardName())) {
            tvUgcBoardname.setText("N/A");
        } else {
            tvUgcBoardname.setText(profile.getUgcBoardName());
        }

      /*  if (TextUtils.isEmpty(profile.getTaxType())) {
            tvTaxType.setText("N/A");
        } else {
            tvTaxType.setText(profile.getTaxType());
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }
}

