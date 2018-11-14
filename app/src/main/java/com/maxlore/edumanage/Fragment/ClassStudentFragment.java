package com.maxlore.edumanage.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Activities.TeacherActivities.StudentObservationActivity;

import com.maxlore.edumanage.Adapters.TeacherAdapters.ClassRecyclerAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.StudentDetails.StudentInfo;
import com.maxlore.edumanage.Models.TeacherModels.StudentDetails.StudentInfoResponse;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by maxlore on 8/18/2016.
 */
public class ClassStudentFragment extends Fragment {

    private RecyclerView classrecyclerView;
    private ArrayList<StudentInfo> classarraylist, searchArrayList;
    private ClassRecyclerAdapter classRecyclerAdapter;
    private EditText searchStudent;
    private Handler handler;
    private ImageView classImageview;
    public static final int TIME_OUT = 1000;
    private LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.class_student_fragment, container, false);

        searchStudent = (EditText) view.findViewById(R.id.et_Search);
        classrecyclerView = (RecyclerView) view.findViewById(R.id.recyclerclassinfo);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        classarraylist = new ArrayList<>();
        searchArrayList = new ArrayList<>();

        handler = new Handler();
        classRecyclerAdapter = new ClassRecyclerAdapter(getActivity(), classarraylist);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        classrecyclerView.setAdapter(classRecyclerAdapter);
        classrecyclerView.setLayoutManager(llm);
        classRecyclerAdapter.notifyDataSetChanged();
        classImageview = (ImageView) view.findViewById(R.id.ivclassMessage);
        Log.e("data=======", "------------------------------" + classarraylist.toString());
     /*   if (classarraylist.size()<=0) {
            expired_tv.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        } else {
            expired_tv.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }*/
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        searchStudent.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        getClassDetails();

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
                    if (searchArrayList.size() > 0) {
                        classarraylist.clear();
                        classarraylist.addAll(searchArrayList);
                        classRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        classRecyclerAdapter.SetOnItemClickListener(new ClassRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(getActivity(), StudentObservationActivity.class);
                intent.putExtra("id", String.valueOf(classarraylist.get(position).getId()));
                intent.putExtra(Constants.NAME, classarraylist.get(position).getName());
                startActivity(intent);
            }

            @Override
            public void onCallClick(View view, int position) {
                confirmationForMakeCall(position);
            }

            @Override
            public void onMessageClick(View view, int position) {
                try {
                    sentMessageConfirmation(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        classImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                final EditText edittext = new EditText(getActivity());
                // set title
                alertDialogBuilder.setTitle("Confirmation");
                edittext.setHint("Enter your message here");
                alertDialogBuilder.setView(edittext);
                alertDialogBuilder
                        .setMessage("Click Discard to exit!")
                        .setCancelable(false)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (TextUtils.isEmpty(edittext.getText().toString())) {
                                    Toast.makeText(getActivity(), "Message Should not be empty", Toast.LENGTH_SHORT).show();
                                } else {
                                    getActivity().finish();
                                    String messageEditText = edittext.getText().toString();
                                    tittleMessage(messageEditText);
                                }
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
        });


        return view;
    }

    private void filterSearch(String search) {
        try {
            classarraylist.clear();
            for (int i = 0; i < searchArrayList.size(); i++) {
                StudentInfo student = searchArrayList.get(i);
                if (student.getName().toLowerCase().contains(search.toLowerCase())) {
                    classarraylist.add(student);
                }
            }
            if (classarraylist.size() <= 0) {
                searchStudent.setError("No Record found");
            }
            classRecyclerAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String stringPass() {
        String numbers = "";
        for (int i = 0; i < classarraylist.size(); i++) {
            numbers = numbers + classarraylist.get(i).getContactNo() + ",";
        }
        Log.e("Strimnf", "=========" + numbers);
        return numbers;

    }

    private void makeCall(String number) {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        startActivity(intent);
    }

    private void sentMessage(String number, String message) {
        try {
            if (UIUtil.isInternetAvailable(getActivity())) {
                UIUtil.startProgressDialog(getActivity(), "Please Wait.. Getting Details");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("message", message);
                jsonObject.addProperty("mobile_no", number);
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(getActivity()).getApi().setMessage(jsonObject, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject object, Response response) {
                        Log.e("jsonObject", "jsonObject --- " + object.toString());


                        try {
                            if (object == null) {
                                Toast.makeText(getActivity(), "Something went worng, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (object.get("status").getAsInt() == Constants.SUCCESS) {
                                Toast.makeText(getActivity(), object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Something went worng, try after sometime...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            } else

            {
                Toast.makeText(getActivity(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void tittleMessage(String message) {
        try {
            if (UIUtil.isInternetAvailable(getActivity())) {
                UIUtil.startProgressDialog(getActivity(), "Please Wait.. Getting Details");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("message", message);
                jsonObject.addProperty("mobile_no", stringPass());
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(getActivity()).getApi().setMessage(jsonObject, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject object, Response response) {
                        Log.e("jsonObject", "jsonObject --- " + object.toString());


                        try {
                            if (object == null) {
                                Toast.makeText(getActivity(), "Something went worng, try after sometime...", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (object.get("status").getAsInt() == Constants.SUCCESS) {
                                Toast.makeText(getActivity(), object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Something went worng, try after sometime...", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            } else

            {
                Toast.makeText(getActivity(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getClassDetails() {
        try {
            if (UIUtil.isInternetAvailable(getActivity())) {
                UIUtil.startProgressDialog(getActivity(), "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(getActivity()).getApi().getClassInfo(params, new Callback<StudentInfoResponse>() {
                    @Override
                    public void success(StudentInfoResponse classResponse, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getActivity());
                            classarraylist.clear();
                            searchArrayList.clear();
                            classarraylist.addAll(classResponse.getStudent());
                            searchArrayList.addAll(classResponse.getStudent());

                            classRecyclerAdapter.notifyDataSetChanged();
                            Log.e("data=======", "------------------------------" + classarraylist.toString());
                /* sectionArrayList= new ArrayList<Section>();
                arrayList.clear();
                searchArrayList.clear();
                searchArrayList.addAll(timeTableResponse);
                arrayList.addAll(students);
                Log.e("Example", " --" + students.toString());
                timeTableAdapter.notifyDataSetChanged();*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        UIUtil.stopProgressDialog(getActivity());
                    }
                });
            } else

            {
                Toast.makeText(getActivity(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void confirmationForMakeCall(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

//        AlertDialog.Builder builder =
//                new AlertDialog.Builder(this, R.style.AppTheme_AppBarOverlay);
        final StudentInfo student = classarraylist.get(position);
        builder.setTitle("Confirmation");
        String message = "Do you want to Call " + student.getName() + " on this number " + student.getContactNo() + " ?";
        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                makeCall(student.getContactNo());

            }
        });
        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void sentMessageConfirmation(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final EditText edittext = new EditText(getActivity());

        final StudentInfo student = classarraylist.get(position);

        alert.setMessage("Student Name : " + student.getName() + "\n");
        alert.setTitle("Confirmation");

        edittext.setHint("Enter your message here");
//        if (!TextUtils.isEmpty(studentLeave.getAcknowledgement()))
//            edittext.setText("" + studentLeave.getAcknowledgement());
        alert.setView(edittext);

        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (TextUtils.isEmpty(edittext.getText().toString())) {
                    Toast.makeText(getActivity(), "Message Should not be empty", Toast.LENGTH_SHORT).show();
                } else {
                    String messageEditText = edittext.getText().toString();
                    sentMessage(student.getContactNo(), messageEditText);
                }
            }
        });

        alert.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }


}
