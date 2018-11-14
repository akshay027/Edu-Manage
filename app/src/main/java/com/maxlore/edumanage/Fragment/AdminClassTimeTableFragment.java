package com.maxlore.edumanage.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Activities.AdminActivities.AdminClassTimeTableActivity;
import com.maxlore.edumanage.Adapters.AdminAdapters.AdminClassTimeTableAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTable.ClassTimeTable;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTable.ClassTimeTableResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTableDetails.ClassTimeTableDetailStructure;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AdminClassTimeTableFragment extends Fragment {
    private AdminClassTimeTableAdapter adminClassTimeTableAdapter;
    private RecyclerView recyclerClass;
    private ArrayList<ClassTimeTable> classarraylist, searchArrayList;
    private EditText etSearch;
    private Handler handler;
    public static final int TIME_OUT = 1000;
    private int result;
    private ArrayList<ClassTimeTableDetailStructure> structArraylist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_class_timetable_fragment, container, false);

        recyclerClass = (RecyclerView) view.findViewById(R.id.recyclerClass);

        classarraylist = new ArrayList<>();
        searchArrayList = new ArrayList<>();
        structArraylist = new ArrayList<>();
        handler = new Handler();

        etSearch = (EditText) view.findViewById(R.id.etSearch);

        adminClassTimeTableAdapter = new AdminClassTimeTableAdapter(getActivity(), classarraylist);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        recyclerClass.setAdapter(adminClassTimeTableAdapter);
        recyclerClass.setLayoutManager(llm);
        adminClassTimeTableAdapter.notifyDataSetChanged();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        if (!PreferencesManger.getBooleanFields(getActivity(), Constants.Pref.KEY_TIME_TABLE_DOWNLOAD_CLASS))

            getTimeTableData();
        adminClassTimeTableAdapter.SetOnItemClickListener(new AdminClassTimeTableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                result = position;
                // getClassTimeTableDetails();
                Intent intent = new Intent(getActivity(), AdminClassTimeTableActivity.class);
                intent.putExtra("id", String.valueOf(classarraylist.get(position).getSectionId()));
                intent.putExtra("user", "class_timetable");
                intent.putExtra("key", structArraylist);
                startActivity(intent);
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
                    if (searchArrayList.size() > 0) {
                        classarraylist.clear();
                        classarraylist.addAll(searchArrayList);
                        adminClassTimeTableAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void filterSearch(String search) {
        classarraylist.clear();
        for (int i = 0; i < searchArrayList.size(); i++) {
            ClassTimeTable classTimeTable = searchArrayList.get(i);
            if (classTimeTable.getClassTeacherName().toLowerCase().contains(search.toLowerCase()) || classTimeTable.getSectionName().toLowerCase().contains(search.toLowerCase()) ||
                    classTimeTable.getStandardName().toLowerCase().contains(search.toLowerCase())) {
                classarraylist.add(classTimeTable);
            }
        }
        if (classarraylist.size() <= 0) {
            etSearch.setError("No Record Found");
        }
        adminClassTimeTableAdapter.notifyDataSetChanged();
    }

    /* private void  getClassTimeTableDetails() {
         if (UIUtil.isInternetAvailable(getActivity())) {

             JsonObject jsonObject = new JsonObject();
             jsonObject.addProperty("user", "class_timetable");
             jsonObject.addProperty("section_id",classarraylist.get(result).getSectionId());

             RetrofitAPI.getInstance(getActivity()).getApi().getClassTimeTableDetails(jsonObject, new Callback<ClassTimeTableDetailResponse>() {
                 @Override
                 public void success(ClassTimeTableDetailResponse classTimeTableDetailResponse, Response response) {
                     Log.e("jsonObject", "jsonObject --- " + classTimeTableDetailResponse.toString());
                      structArraylist.clear();
                     // classarraylist.clear();
                     structArraylist.addAll(classTimeTableDetailResponse.getList());
                     // classarraylist.addAll(teacherTimeTableDetailResponse.getList());
                     // adminTeacherTimeTableAdapter.notifyDataSetChanged();

                 }

                 @Override
                 public void failure(RetrofitError error) {

                 }
             });
         } else {
             Toast.makeText(getActivity(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
         }
     }*/
    private void getTimeTableData() {
        try {
            if (UIUtil.isInternetAvailable(getActivity())) {
                UIUtil.startProgressDialog(getActivity(), "Please Wait.. Getting Details");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("user", "class_timetable");
                if (PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(getActivity()).getApi().classtimeTableData(jsonObject, new Callback<ClassTimeTableResponse>() {
                    @Override
                    public void success(ClassTimeTableResponse classTimeTableResponse, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getActivity());
                            Log.e("jsonObject", "jsonObject --- " + classTimeTableResponse.toString());
                            searchArrayList.clear();
                            classarraylist.clear();
                            searchArrayList.addAll(classTimeTableResponse.getList());
                            classarraylist.addAll(classTimeTableResponse.getList());
                            adminClassTimeTableAdapter.notifyDataSetChanged();

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
