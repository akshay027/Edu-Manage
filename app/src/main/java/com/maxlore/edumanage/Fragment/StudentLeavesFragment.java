package com.maxlore.edumanage.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.API.RetrofitAPI;

import com.maxlore.edumanage.Adapters.TeacherAdapters.StudentLeaveRecyclerAdapter;
import com.maxlore.edumanage.Database.PreferencesManger;
import com.maxlore.edumanage.Models.TeacherModels.Leaves.StudentLeave;
import com.maxlore.edumanage.Models.TeacherModels.Leaves.StudentLeaveResponse;
import com.maxlore.edumanage.R;
import com.maxlore.edumanage.Utility.Constants;
import com.maxlore.edumanage.Utility.UIUtil;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class StudentLeavesFragment extends Fragment {

    //    private OnFragmentInteractionListener mListener;
    private RecyclerView studentLeaveRecyclerView;
    private ArrayList<StudentLeave> leaveArrayList;
    private StudentLeaveRecyclerAdapter leaveRecyclerAdapter;
    private String currentdate;
    private TextView noleave;
    private Date chckdate, enddate;

    public StudentLeavesFragment() {
    }

    public static StudentLeavesFragment newInstance() {
        StudentLeavesFragment fragment = new StudentLeavesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_leaves, container, false);

        studentLeaveRecyclerView = (RecyclerView) view.findViewById(R.id.studentLeaveRecyclerView);
        leaveArrayList = new ArrayList<>();
        noleave = (TextView) view.findViewById(R.id.noleave);
        leaveRecyclerAdapter = new StudentLeaveRecyclerAdapter(getActivity(), leaveArrayList);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        currentdate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        try {
            chckdate = new SimpleDateFormat("dd/MM/yyyy")
                    .parse(currentdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        studentLeaveRecyclerView.setLayoutManager(llm);
        studentLeaveRecyclerView.setAdapter(leaveRecyclerAdapter);
        leaveRecyclerAdapter.notifyDataSetChanged();

        leaveRecyclerAdapter.SetOnItemClickListener(new StudentLeaveRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Log.e("poakjopakopakpoakopa ", "Hhd --- " + chckdate);
                try {
                    enddate = new SimpleDateFormat("dd/MM/yyyy")
                            .parse(leaveArrayList.get(position).getEndDate());
                    Log.e("Json ", "asjhvsdhujvsdajhvds --- " + enddate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (enddate.before(chckdate)) {
                    Toast.makeText(getActivity(), "Date Exceeded...", Toast.LENGTH_SHORT).show();
                } else {
                    acknowledgementPopup(position);
                }
            }
        });
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getStudentLeaveDetails();
        }

    }

    private void getStudentLeaveDetails() {
        try {
            if (UIUtil.isInternetAvailable(getActivity())) {

                UIUtil.startProgressDialog(getActivity(), "Please wait Geting Details.");
                Map<String, String> params = new HashMap<String, String>();
                params.put("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(getActivity()).getApi().studentLeaveApplication(params, new Callback<StudentLeaveResponse>() {
                    @Override
                    public void success(StudentLeaveResponse leaveResponse, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getActivity());

                            Log.e("Json ", "Hhd --- " + leaveResponse.toString());
                            Log.e("Json ", "Response --- " + response.getBody());

                            if (leaveResponse.getStatus() == Constants.SUCCESS) {
                                bindDataForLeave(leaveResponse.getStudentLeave());
                            } else {
                                Toast.makeText(getActivity(), "" + leaveResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.e("Json ", "Hhd --- " + leaveResponse.toString());
                        Log.e("Json ", "Response --- " + response.getBody());

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

    private void bindDataForLeave(List<StudentLeave> studentLeave) {
        try {
            leaveArrayList.clear();
            leaveArrayList.addAll(studentLeave);
            if (leaveArrayList.size() <= 0) {
                noleave.setVisibility(View.VISIBLE);
                studentLeaveRecyclerView.setVisibility(View.GONE);
            } else {
                noleave.setVisibility(View.GONE);
                studentLeaveRecyclerView.setVisibility(View.VISIBLE);
            }
            leaveRecyclerAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    private void acknowledgementPopup(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final EditText edittext = new EditText(getActivity());
        final StudentLeave studentLeave = leaveArrayList.get(position);
        alert.setMessage("Student Name : " + studentLeave.getName() + "\n");
        alert.setTitle("Acknowledgement");


        edittext.setHint("Enter your message here");
        if (!TextUtils.isEmpty(studentLeave.getAcknowledgement()))
            edittext.setText("" + studentLeave.getAcknowledgement());
        alert.setView(edittext);

        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String YouEditTextValue = edittext.getText().toString();
                studentLeave.setAcknowledgement(YouEditTextValue);
                submitAcknowledgement(studentLeave, position);

            }
        });

        alert.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }

    private void submitAcknowledgement(final StudentLeave leave, final int position) {
        try {
            if (UIUtil.isInternetAvailable(getActivity())) {

                UIUtil.startProgressDialog(getActivity(), "Please wait Geting Details.");
                final JsonObject jsonObject = new JsonObject();
                final JsonObject jsonObject1 = new JsonObject();
                jsonObject.addProperty("id", leave.getLeaveId());
                jsonObject1.addProperty("acknowledgement", leave.getAcknowledgement());
                jsonObject1.addProperty("start_date", leaveArrayList.get(position).getStartDate());
                jsonObject1.addProperty("end_date", leaveArrayList.get(position).getEndDate());
                jsonObject.add("student_leave_apply", jsonObject1);
                jsonObject.addProperty("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(getActivity()).getApi().teacherApprovedLeaveApplication(jsonObject, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject object, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getActivity());

                            Log.e("Json ", "Hhd --- " + object.toString());
                            Log.e("Json ", "Response --- " + response.getBody());
                            if (object.get("status").getAsInt() == Constants.SUCCESS) {
                                leaveArrayList.set(position, leave);
                                leaveRecyclerAdapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            }
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
