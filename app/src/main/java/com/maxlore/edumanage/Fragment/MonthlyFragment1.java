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
import com.maxlore.inmegh.Activities.TeacherActivities.AcademicsMonthDataActivity;
import com.maxlore.inmegh.Adapters.TeacherAdapters.MonthlvAdapter;
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

*/
/**
 * Created by maxlore on 10/21/2016.
 *//*


public class MonthlyFragment1 extends Fragment {
    private ListView lvMonthdetail;
    private MonthlvAdapter monthAdapter;
    private ArrayList<Session> monthlyarrayList;
    private List<Session> sessionArrayList;
    private String standard, section, subject;
    private int result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month, container, false);
        lvMonthdetail = (ListView) view.findViewById(R.id.lvMonthdetail);
        monthlyarrayList = new ArrayList<>();
        sessionArrayList = new ArrayList<>();

        monthlyarrayList = new ArrayList<>();
        monthAdapter = new MonthlvAdapter(getActivity(), monthlyarrayList);

        lvMonthdetail.setAdapter(monthAdapter);

        monthAdapter.notifyDataSetChanged();

        lvMonthdetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                result = position;
                Intent intent = new Intent(getActivity(), AcademicsMonthDataActivity.class);
                intent.putExtra("secid", String.valueOf(monthlyarrayList.get(position).getSectionId()));
                intent.putExtra("stdid", String.valueOf(monthlyarrayList.get(position).getStandardId()));
                intent.putExtra("subid", String.valueOf(monthlyarrayList.get(position).getSubjectId()));
                startActivity(intent);
            }
        });
        getSessionDetail();
        return view;
    }

    public void onCreateButtonSelect() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_BRANCH_ID));
        params.put("type", Constants.MONTHFRAGMENT);
        params.put("standard_id", String.valueOf(monthlyarrayList.get(result).getStandardId()));
        params.put("section_id", String.valueOf(monthlyarrayList.get(result).getSectionId()));
        params.put("subject_id", String.valueOf(monthlyarrayList.get(result).getSubjectId()));
        RetrofitAPI.getInstance(getActivity()).getApi().getDailyAcademicPlan(params, new Callback<DailySessionResponse>() {
            @Override
            public void success(DailySessionResponse dailySessionResponse, Response response) {

                if (dailySessionResponse.getStatus() == Constants.SUCCESS) {
                    //dailyarrayList.clear();
                    //dailyarrayList.addAll(dailySessionResponse.getSessionPlan());
                    //Log.e("Example", "--------------- --" + dailyarrayList.toString());
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
            RetrofitAPI.getInstance(getActivity()).getApi().getSession(params, new Callback<SessionResponse>() {
                @Override
                public void success(SessionResponse sessionResponse, Response response) {
                    UIUtil.stopProgressDialog(getActivity());
                    if (sessionResponse.getStatus() == Constants.SUCCESS) {
                        monthlyarrayList.clear();
                        monthlyarrayList.addAll(sessionResponse.getSession());
                        monthAdapter = new MonthlvAdapter(getActivity(), monthlyarrayList);
                        lvMonthdetail.setAdapter(monthAdapter);
                        Log.e("List data---- ", monthlyarrayList.toString());

                    } else {
                        Toast.makeText(getContext(), sessionResponse.getMessage(), Toast.LENGTH_LONG).show();
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
    }
}
*/
