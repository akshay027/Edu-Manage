/*
package com.maxlore.inmegh.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.maxlore.inmegh.API.RetrofitAPI;
import com.maxlore.inmegh.Activities.TeacherActivities.AcademicsDailyDataActivity;
import com.maxlore.inmegh.Adapters.TeacherAdapters.DailylvAdapter;
import com.maxlore.inmegh.Database.PreferencesManger;
import com.maxlore.inmegh.Models.TeacherModels.Academics.DailySessionResponse;
import com.maxlore.inmegh.Models.TeacherModels.Academics.Session;
import com.maxlore.inmegh.Models.TeacherModels.Academics.SessionResponse;
import com.maxlore.inmegh.R;
import com.maxlore.inmegh.Utility.Constants;
import com.maxlore.inmegh.Utility.UIUtil;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

*/
/**
 * Created by maxlore on 10/21/2016.
 *//*


public class DailyFragment1 extends Fragment {
    private ListView lvDailydetail;
    private DailylvAdapter dailyAdapter;
    private ArrayList<Session> dailyarrayList;
    private List<Session> sessionArrayList;
    private int result;
    private String standard, section, subject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily, container, false);

        lvDailydetail = (ListView) view.findViewById(R.id.lvDailydetail);
        dailyarrayList = new ArrayList<>();
        sessionArrayList = new ArrayList<>();

        dailyarrayList = new ArrayList<>();
        dailyAdapter = new DailylvAdapter(getActivity(), dailyarrayList);

        lvDailydetail.setAdapter(dailyAdapter);

        dailyAdapter.notifyDataSetChanged();
        lvDailydetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), AcademicsDailyDataActivity.class);
                intent.putExtra("secid", String.valueOf(dailyarrayList.get(position).getSectionId()));
                intent.putExtra("stdid", String.valueOf(dailyarrayList.get(position).getStandardId()));
                intent.putExtra("subid", String.valueOf(dailyarrayList.get(position).getSubjectId()));
                startActivity(intent);
                result = position;
            }
        });
        getSessionDetail();
        return view;
    }

    public void onCreateButtonSelect() {
        dailyarrayList.clear();

        Map<String, String> params = new HashMap<String, String>();
        params.put("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_BRANCH_ID));
        params.put("type", Constants.YEARFRAGMENT);
        params.put("standard_id", String.valueOf(dailyarrayList.get(result).getStandardId()));
        params.put("section_id", String.valueOf(dailyarrayList.get(result).getSectionId()));
        params.put("subject_id", String.valueOf(dailyarrayList.get(result).getSubjectId()));

        RetrofitAPI.getInstance(getActivity()).getApi().getDailyAcademicPlan(params, new Callback<DailySessionResponse>() {
            @Override
            public void success(DailySessionResponse dailySessionResponse, Response response) {
                UIUtil.stopProgressDialog(getActivity());
                if (dailySessionResponse.getStatus() == Constants.SUCCESS) {
                    dailyarrayList.clear();
                    //dailyarrayList.addAll(dailySessionResponse.getSessionPlan());
                    Log.e("Example", "--------------- --" + dailyarrayList.toString());
                    dailyAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getActivity(), "" + dailySessionResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                UIUtil.stopProgressDialog(getActivity());
            }
        });
    }


    public void getSessionDetail() {
        if (UIUtil.isInternetAvailable(getActivity())) {
            UIUtil.startProgressDialog(getActivity(), "Please Wait.. Getting Details");
            Map<String, String> params = new HashMap<String, String>();
            params.put("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_BRANCH_ID));
            RetrofitAPI.getInstance(getActivity()).getApi().getSession(params, new Callback<SessionResponse>() {
                @Override
                public void success(SessionResponse sessionResponse, Response response) {
                    UIUtil.stopProgressDialog(getActivity());
                    if (sessionResponse.getStatus() == Constants.SUCCESS) {
                        dailyarrayList.clear();
                        dailyarrayList.addAll(sessionResponse.getSession());
                        dailyAdapter = new DailylvAdapter(getActivity(), dailyarrayList);
                        lvDailydetail.setAdapter(dailyAdapter);
                        Log.e("List data---- ", dailyarrayList.toString());

                    } else {
                        Toast.makeText(getContext(), sessionResponse.getMessage(), Toast.LENGTH_LONG).show();
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
    }
}
*/
