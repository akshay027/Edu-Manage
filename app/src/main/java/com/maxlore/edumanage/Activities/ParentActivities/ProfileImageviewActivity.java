package com.maxlore.edumanage.Activities.ParentActivities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.squareup.picasso.Picasso;

public class ProfileImageviewActivity extends AppCompatActivity {
    private ImageView poster;
    private String name;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar = getSupportActionBar();
        poster = (ImageView) findViewById(R.id.poster);
        try {

            Intent intent = getIntent();
            name = getIntent().getStringExtra("name");
            //  image = getIntent().getStringExtra("image");
            // Log.e("profile","-----"+profile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        actionBar.setTitle(name);
        Picasso.with(this).load(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_IMAGE)).fit().into(poster);
        Log.e("profile", "-----" + name);

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
