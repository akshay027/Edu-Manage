package com.maxlore.edumanage.Fragment;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.ListView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;
import com.maxlore.edumanage.Adapters.AdminAdapters.DefaulterAdminAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels.DefaulterDatamodel;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels.DefaulterResponseModel;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DefaulterFragment extends Fragment {
    private DefaulterAdminAdapter defaulterAdminAdapter;
    private ListView listviewdefaulter;
    private EditText etSearchdefaulters;
    private Handler handler;
    private String pos;
    public static final int TIME_OUT = 1000;
    private ArrayList<DefaulterDatamodel> defaulterDatamodels, searchArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_defaulter_fragment, container, false);

        listviewdefaulter = (ListView) view.findViewById(R.id.listviewdefaulter);

        defaulterDatamodels = new ArrayList<>();
        searchArrayList = new ArrayList<>();
        etSearchdefaulters = (EditText) view.findViewById(R.id.etSearchdefaulters);
        defaulterAdminAdapter = new DefaulterAdminAdapter(getActivity(), defaulterDatamodels);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        listviewdefaulter.setAdapter(defaulterAdminAdapter);
        defaulterAdminAdapter.notifyDataSetChanged();
        handler = new Handler();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etSearchdefaulters.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        getPaymentData();

        etSearchdefaulters.addTextChangedListener(new TextWatcher() {
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
                        defaulterDatamodels.clear();
                        defaulterDatamodels.addAll(searchArrayList);
                        defaulterAdminAdapter.notifyDataSetChanged();
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
        try {
            defaulterDatamodels.clear();
            for (int i = 0; i < searchArrayList.size(); i++) {
                DefaulterDatamodel defaulterDatamodel = searchArrayList.get(i);
                if (defaulterDatamodel.getStudentName().toLowerCase().contains(search.toLowerCase()) || defaulterDatamodel.getStudentClass().toLowerCase().contains(search.toLowerCase())) {
                    defaulterDatamodels.add(defaulterDatamodel);
                }
            }
            if (defaulterDatamodels.size() <= 0) {
                etSearchdefaulters.setError("No Record found");
            }
            defaulterAdminAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPaymentData() {
        try {
            if (UIUtil.isInternetAvailable(getActivity())) {
                UIUtil.startProgressDialog(getActivity(), "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                if (PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Branch Admin")) {
                    params.put("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_BRANCH_ID));
                } else {
                    params.put("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_SUPER_BRANCH_ID));
                }
                RetrofitAPI.getInstance(getActivity()).getApi().getdefaulterdata(params, new Callback<DefaulterResponseModel>() {
                    @Override
                    public void success(DefaulterResponseModel defaulterResponseModel, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getActivity());
                            Log.e("jsonObject", "jsonObject --- " + defaulterResponseModel.toString());
                            searchArrayList.clear();
                            defaulterDatamodels.clear();
                            searchArrayList.addAll(defaulterResponseModel.getDefaulters());
                            defaulterDatamodels.addAll(defaulterResponseModel.getDefaulters());
                            defaulterAdminAdapter.notifyDataSetChanged();
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
