/*
package com.maxlore.inmegh.Activities.ParentActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.maxlore.inmegh.API.RetrofitAPI;
import com.maxlore.inmegh.Adapters.ParentAdapters.ChatListRecyclerAdapter;
import com.maxlore.inmegh.Models.TeacherModels.ChatDriver;
import com.maxlore.inmegh.Models.TeacherModels.ChatDriverResponse;
import com.maxlore.inmegh.R;
import com.maxlore.inmegh.Utility.Constants;
import com.maxlore.inmegh.Utility.UIUtil;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ChatListActivity extends AppCompatActivity {

    private RecyclerView chatView;
    private ArrayList<ChatDriver> chatArrayList;
    private ChatListRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chatView = (RecyclerView) findViewById(R.id.chatView);
        chatArrayList = new ArrayList<>();
        chatView.setHasFixedSize(true);
        chatView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatListRecyclerAdapter(this, chatArrayList);
        chatView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        getDriverListFromServer();


        adapter.SetOnItemClickListener(new ChatListRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                ChatDriver chatDriver = chatArrayList.get(position);
                Intent intent = new Intent(ChatListActivity.this, ChatActivity.class);
                intent.putExtra("name", chatDriver.getDriverName());
                intent.putExtra("id", chatDriver.getDriverId());
                startActivity(intent);
            }
        });

    }

    private void getDriverListFromServer() {

        if (UIUtil.isInternetAvailable(this)) {
            UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
            RetrofitAPI.getInstance(this).getApi().getDriverListForChat(new Callback<ChatDriverResponse>() {
                @Override
                public void success(ChatDriverResponse chatDriverResponse, Response response) {
                    if (chatDriverResponse.getStatus() == Constants.SUCCESS) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        chatArrayList.clear();
                        chatArrayList.addAll(chatDriverResponse.getStudent());
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), "" + chatDriverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    UIUtil.stopProgressDialog(getApplicationContext());
                    Toast.makeText(getApplicationContext(), "Try After Some time", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Connect to internet.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

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
}*/
