package com.maxlore.edumanage.Activities.ParentActivities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.maxlore.edumanage.R;
import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.TeacherAdapters.ChatRecyclerAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.database.Chat;

import com.maxlore.edumanage.Utility.Constants;
import com.google.gson.JsonObject;
import com.orm.SugarContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView chatView;
    private ArrayList<Chat> chatArrayList;
    private ChatRecyclerAdapter chatRecyclerAdapter;
    private String name = "";
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        SugarContext.init(this);
        try {
            name = getIntent().getStringExtra("name");
            id = getIntent().getIntExtra("id", 67);
            Log.e("User", "Name : " + name + "  Id : " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        chatView = (RecyclerView) findViewById(R.id.chatView);
        chatArrayList = new ArrayList<>();
        chatView.setHasFixedSize(true);
        chatView.setLayoutManager(new LinearLayoutManager(this));

        chatRecyclerAdapter = new ChatRecyclerAdapter(this, chatArrayList);

        Date date = new Date();
        Log.e("Date", date.toString());

//        Chat chat = new Chat();
//        chat.setType(1);
//        chat.setMessage("Handle action bar item clicks here. The action bar will");
//        chat.setTime("12:13 PM");
//        chatArrayList.add(chat);
//
//        Chat chat1 = new Chat();
//        chat1.setType(0);
//        chat1.setMessage("I'd hate to be resurrecting old threads but this is a problem that is not answered correctly and moreover I've ran into this problem myself.");
//        chat1.setTime("12:14 PM");
//        chatArrayList.add(chat1);
//        chatArrayList.add(chat);
//        chatArrayList.add(chat1);

        chatArrayList.addAll(Chat.find(Chat.class, "user_id = ?", new String[]{String.valueOf(id)}));
        Log.e("chatArrayList", "chatArrayList : " + chatArrayList.toString());

        chatView.setAdapter(chatRecyclerAdapter);
        chatView.scrollToPosition(chatArrayList.size() - 1);
        chatRecyclerAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferencesManger.addBooleanFields(this, Constants.Pref.KEY_CHAT_OPEN, true);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("Attendance"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreferencesManger.addBooleanFields(this, Constants.Pref.KEY_CHAT_OPEN, false);
//        unregisterReceiver(mMessageReceiver);

    }

    /*    private void updateChat() {
            if (UIUtil.isInternetAvailable(this)) {

                RetrofitAPI.getInstance(this).getApi().getTripList(new Callback<TripListResponse>() {
                    @Override
                    public void success(TripListResponse tripListResponse, Response response) {
                        if (tripListResponse == null)
                            return;
                        if (tripListResponse.getStatus() == Constants.SUCCESS) {
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                    }
                });
            }
        }*/
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

    private void sendChatMessage() {
        Chat chat = new Chat();
        chat.setMessageId((int) System.currentTimeMillis());
        chat.setType(0);
        chat.setUserId(id);
        chat.setTime(new SimpleDateFormat("hh:mm a").format(new Date()).toString());
        chat.setDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()).toString());

        Log.e("chat", "Chat : " + chat.toString());
        chat.save();
        chatArrayList.add(chat);

        chatRecyclerAdapter.notifyDataSetChanged();
//        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.scrollToPosition(true);
//        chatView.setLayoutManager(linearLayoutManager);
        chatView.scrollToPosition(chatArrayList.size() - 1);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("receiver_id", 127);
        jsonObject.addProperty("time", new Date().toString());
        jsonObject.addProperty("user_type", PreferencesManger.getStringFields(this, Constants.Pref.KEY_USER_TYPE));
        jsonObject.addProperty("position", chat.getMessageId());

        Log.e("jsonObject", "jsonObject : " + jsonObject.toString());

        RetrofitAPI.getInstance(this).getApi().sentChatMessage(jsonObject, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {

                if (jsonObject.get("status").getAsInt() == Constants.SUCCESS) {
                    int pos = jsonObject.get("position").getAsInt();
                    updateMessageStatus(pos, jsonObject.get("message_id").getAsInt());
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void updateMessageStatus(int pos, int messageId) {
        List<Chat> chat2 = Chat.find(Chat.class, "message_id = ?", new String[]{String.valueOf(pos)});

        if (chat2.size() > 0) {

            Chat chat = chat2.get(0);
            Log.e("Chat", "Chat : " + chat.toString());

            chat.setMessageId(messageId);
            chat.setStatus(Constants.SENT);
            chat.save();

            for (int i = 0; i < chatArrayList.size(); i++) {
                if (chatArrayList.get(i).getMessageId() == pos) {
                    chatArrayList.set(i, chat);
                    break;
                }
            }

            chatRecyclerAdapter.notifyDataSetChanged();
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            int messageId = intent.getIntExtra("message", 0);

            Log.d("receiver", "Got message: " + String.valueOf(messageId));

            List<Chat> chat2 = Chat.find(Chat.class, "message_id = ?", new String[]{String.valueOf(messageId)});

            if (chat2.size() > 0) {
                Chat chat = chat2.get(0);
                Log.e("Chat", "Chat : " + chat.toString());
                chatArrayList.add(chat);
                chatView.scrollToPosition(chatArrayList.size() - 1);
                chatRecyclerAdapter.notifyDataSetChanged();
            }
        }
    };
}