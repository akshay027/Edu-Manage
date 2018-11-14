/*
package com.maxlore.inmegh.Fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.maxlore.inmegh.API.RetrofitAPI;
import com.maxlore.inmegh.Activities.TeacherActivities.AcademicsYearDataActivity;
import com.maxlore.inmegh.Adapters.TeacherAdapters.YearlvAdapter;
import com.maxlore.inmegh.Database.PreferencesManger;
import com.maxlore.inmegh.Models.TeacherModels.Academics.DailySessionResponse;
import com.maxlore.inmegh.Models.TeacherModels.Academics.Session;
import com.maxlore.inmegh.Models.TeacherModels.Academics.SessionResponse;
import com.maxlore.inmegh.R;
import com.maxlore.inmegh.Utility.Constants;
import com.maxlore.inmegh.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static java.security.AccessController.getContext;

*
 * Created by maxlore on 10/21/2016.



public class YearlyFragment1 extends Fragment {
    private ListView lvYeardetail;
    private YearlvAdapter yearAdapter;
    private ArrayList<Session> yeararrayList;
    private List<Session> sessionArrayList;
    private String standard, section, subject;
    private int result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_year, container, false);
        lvYeardetail = (ListView) view.findViewById(R.id.lvYeardetail);
        yeararrayList = new ArrayList<>();
        sessionArrayList = new ArrayList<>();


        yeararrayList = new ArrayList<>();
        yearAdapter = new YearlvAdapter(getActivity(), yeararrayList);

        lvYeardetail.setAdapter(yearAdapter);

        yearAdapter.notifyDataSetChanged();
        lvYeardetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                result = position;
                Intent intent = new Intent(getActivity(), AcademicsYearDataActivity.class);
                intent.putExtra("secid", String.valueOf(yeararrayList.get(position).getSectionId()));
                intent.putExtra("stdid", String.valueOf(yeararrayList.get(position).getStandardId()));
                intent.putExtra("subid", String.valueOf(yeararrayList.get(position).getSubjectId()));
                startActivity(intent);
            }
        });

        getSessionDetail();
        return view;
    }

    public void onCreateButtonSelect() {

        //  dailyarrayList.clear();
        JsonObject jsonObject = new JsonObject();

        Map<String, String> params = new HashMap<String, String>();
        params.put("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_BRANCH_ID));
        params.put("type", Constants.YEARFRAGMENT);
        params.put("standard_id", String.valueOf(yeararrayList.get(result).getStandardId()));
        params.put("section_id", String.valueOf(yeararrayList.get(result).getSectionId()));
        params.put("subject_id", String.valueOf(yeararrayList.get(result).getSubjectId()));


        RetrofitAPI.getInstance(getActivity()).getApi().getDailyAcademicPlan(params, new Callback<DailySessionResponse>() {
            @Override
            public void success(DailySessionResponse dailySessionResponse, Response response) {

                if (dailySessionResponse.getStatus() == Constants.SUCCESS) {
                    //  dailyarrayList.clear();
                    //dailyarrayList.addAll(dailySessionResponse.getSessionPlan());
                    //   Log.e("Example", "--------------- --" + dailyarrayList.toString());
                    //dailyRecyclerAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getActivity(), "" + dailySessionResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void getSessionDetail() {
        if (UIUtil.isInternetAvailable(getActivity())) {
            UIUtil.startProgressDialog(getActivity(), "Please Wait.. Getting Details");
            Map<String, String> params = new HashMap<String, String>();
            params.put("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_BRANCH_ID));
            RetrofitAPI.getInstance(getActivity()).getApi().getSession(params,new Callback<SessionResponse>() {
                @Override
                public void success(SessionResponse sessionResponse, Response response) {
                    UIUtil.stopProgressDialog(getActivity());
                    if (sessionResponse.getStatus() == Constants.SUCCESS) {
                        yeararrayList.clear();
                        yeararrayList.addAll(sessionResponse.getSession());
                        lvYeardetail.setAdapter(yearAdapter);

                        yearAdapter.notifyDataSetChanged();
                        Log.e("List data---- ", yeararrayList.toString());

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
