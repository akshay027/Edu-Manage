package com.maxlore.edumanage.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Activities.AdminActivities.AdminTeacherTimeTableActivity;
import com.maxlore.edumanage.Adapters.AdminAdapters.AdminTeacherTimeTableAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTable.TeacherTimeTable;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTable.TeacherTimeTableResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTableDetails.TeacherTimeTableDetailStructure;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by maxlore on 10/4/2016.
 */
public class AdminTeacherTimeTableFragment extends Fragment {
    private AdminTeacherTimeTableAdapter adminTeacherTimeTableAdapter;
    private ListView lvemployee;
    private ArrayList<TeacherTimeTable> classarraylist, searchArrayList;
    private EditText etSearch;
    private ArrayList<TeacherTimeTableDetailStructure> mylist;
    private Handler handler;
    public static final int TIME_OUT = 1000;
    private int result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_teacher_timetable_fragment, container, false);

        lvemployee = (ListView) view.findViewById(R.id.lvemployee);

        classarraylist = new ArrayList<>();
        searchArrayList = new ArrayList<>();
        mylist = new ArrayList<>();
        handler = new Handler();

        etSearch = (EditText) view.findViewById(R.id.etSearch);
        adminTeacherTimeTableAdapter = new AdminTeacherTimeTableAdapter(getActivity(), classarraylist);
        lvemployee.setAdapter(adminTeacherTimeTableAdapter);
        adminTeacherTimeTableAdapter.notifyDataSetChanged();

        getTimeTableData();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        lvemployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                result = position;
                // getTeacherTimeTableDetails();
                Intent intent = new Intent(getActivity(), AdminTeacherTimeTableActivity.class);
                intent.putExtra("id", String.valueOf(classarraylist.get(position).getEmployeeId()));
                Log.e("possssssssss", id + "");
                intent.putExtra("user", "teacher_timetable");

                startActivity(intent);

            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(s) && s.length() > 0) {
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
                        adminTeacherTimeTableAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;


    }

    private String arrayListToJSON(ArrayList<TeacherTimeTableDetailStructure> al) {
        JSONArray array = new JSONArray();
        try {
            for (int i = 0; i < al.size(); i++) {
                array.put(new JSONObject(al.get(i).toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array.toString();
    }

    private void filterSearch(String search) {
        classarraylist.clear();
        for (int i = 0; i < searchArrayList.size(); i++) {
            TeacherTimeTable teacherTimeTable = searchArrayList.get(i);
            if (teacherTimeTable.getEmployeeName().toLowerCase().contains(search.toLowerCase())) {
                classarraylist.add(teacherTimeTable);
            }
        }
        if (classarraylist.size() <= 0) {
            etSearch.setError("No Record Found");
        }
        adminTeacherTimeTableAdapter.notifyDataSetChanged();
    }

    private void getTimeTableData() {
        try {
            if (UIUtil.isInternetAvailable(getActivity())) {
                UIUtil.startProgressDialog(getActivity(), "Please Wait.. Getting Details");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("user", "teacher_timetable");
                if (PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(getActivity()).getApi().teachertimeTableData(jsonObject, new Callback<TeacherTimeTableResponse>() {
                    @Override
                    public void success(TeacherTimeTableResponse teacherTimeTableResponse, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getActivity());
                            Log.e("jsonObject", "jsonObject --- " + teacherTimeTableResponse.toString());
                            searchArrayList.clear();
                            classarraylist.clear();
                            searchArrayList.addAll(teacherTimeTableResponse.getList());
                            classarraylist.addAll(teacherTimeTableResponse.getList());
                            adminTeacherTimeTableAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        UIUtil.stopProgressDialog(getActivity());
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
