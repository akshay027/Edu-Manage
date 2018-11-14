package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maxlore.edumanage.Activities.TeacherActivities.AssignmentDetail;
import com.maxlore.edumanage.Models.TeacherModels.AssignmentNavigationbar.Assignment;
import com.maxlore.edumanage.R;

import java.util.ArrayList;

/**
 * Created by maxlore on 09-Aug-16.
 */
public class AssigmentsectionAdapter extends BaseAdapter implements View.OnClickListener {
    private ArrayList<Assignment> Assignmentarraylist;
    private LayoutInflater inflater;
    private String className = "";
    private ImageButton DetailButton;
    private Activity contextIntent;
    private int currentposition;
    private ArrayList idarraylist = new ArrayList();
    private Boolean session;

    public AssigmentsectionAdapter(Activity context, ArrayList<Assignment> Assignmentarraylist, Boolean session) {
        this.Assignmentarraylist = Assignmentarraylist;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.contextIntent = context;
        this.session = session;
    }

    @Override
    public int getCount() {
        return Assignmentarraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return Assignmentarraylist.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolder holder;
        final Assignment assignment = Assignmentarraylist.get(position);

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.assignment_row_item, null);
            holder = new ViewHolder();

            holder.llTitle = (LinearLayout) convertView.findViewById(R.id.llTitle);
            holder.llBody = (LinearLayout) convertView.findViewById(R.id.llBody);
            holder.tv_assignmentname = (TextView) convertView.findViewById(R.id.tv_assignmentname);
            holder.tv_assignmenttype = (TextView) convertView.findViewById(R.id.tv_assignmenttype);
            holder.tv_duedate = (TextView) convertView.findViewById(R.id.tv_duedate);
            holder.tv_totalstudent = (TextView) convertView.findViewById(R.id.tv_totalstudent);
            holder.tv_studentcompleted = (TextView) convertView.findViewById(R.id.tv_studentcompleted);
            holder.tv_studentremaining = (TextView) convertView.findViewById(R.id.tv_studentremaining);
            holder.standard = (TextView) convertView.findViewById(R.id.tv_standard);
            holder.section = (TextView) convertView.findViewById(R.id.tv_section);

            holder.tvTypeText = (TextView) convertView.findViewById(R.id.tvTypeText);
            holder.tvDateText = (TextView) convertView.findViewById(R.id.tvDateText);
            holder.tvTotalText = (TextView) convertView.findViewById(R.id.tvTotalText);
            holder.tvSubText = (TextView) convertView.findViewById(R.id.tvSubText);
            holder.tvRemText = (TextView) convertView.findViewById(R.id.tvRemText);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }
        holder.llTitle.setClickable(false);

        if (!assignment.isA()) {
            assignment.setA(true);
            if (className.equalsIgnoreCase(Assignmentarraylist.get(position).getStandard() + Assignmentarraylist.get(position).getSection())) {
                holder.llTitle.setVisibility(View.GONE);
            } else {
                className = Assignmentarraylist.get(position).getStandard() + Assignmentarraylist.get(position).getSection();
                holder.llTitle.setVisibility(View.VISIBLE);
                assignment.setB(true);
            }
        } else {
            if (assignment.isB()) {
                holder.llTitle.setVisibility(View.VISIBLE);
            } else {
                holder.llTitle.setVisibility(View.GONE);
            }
        }


        holder.tv_assignmentname.setText("" + Assignmentarraylist.get(position).getName()
                + "  (" + Assignmentarraylist.get(position).getSubject() + ")");
        holder.tv_assignmenttype.setText("" + Assignmentarraylist.get(position).getType());
        holder.tv_duedate.setText("" + Assignmentarraylist.get(position).getDueDate());
        holder.tv_totalstudent.setText("" + Assignmentarraylist.get(position).getStudentCount());
        holder.tv_studentcompleted.setText("" + Assignmentarraylist.get(position).getTotalSubmitted());
        holder.tv_studentremaining.setText("" + Assignmentarraylist.get(position).getStudentLeft());
        holder.section.setText("" + Assignmentarraylist.get(position).getSection());
        holder.standard.setText("" + Assignmentarraylist.get(position).getStandard());

        holder.tv_assignmentname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("sessionnnnn   :", String.valueOf(session));
                gotoNextActivity(position);
            }
        });

        holder.tv_assignmenttype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNextActivity(position);
            }
        });
        holder.tv_duedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNextActivity(position);
            }
        });
        holder.tv_totalstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNextActivity(position);
            }
        });

        holder.tv_studentcompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNextActivity(position);
            }
        });

        holder.tv_studentremaining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNextActivity(position);
            }
        });

        holder.llBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNextActivity(position);
            }
        });

        holder.tvSubText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNextActivity(position);
            }
        });
        holder.tvTotalText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNextActivity(position);
            }
        });
        holder.tvDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNextActivity(position);
            }
        });
        holder.tvTypeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNextActivity(position);
            }
        });
        holder.tvRemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNextActivity(position);
            }
        });


        Assignmentarraylist.set(position, assignment);

        return convertView;

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    static class ViewHolder {
        TextView tv_assignmentname, tv_duedate, tv_assignmentsubject, tv_assignmenttype, tv_totalstudent, tv_studentcompleted, tv_studentremaining, standard, section;
        LinearLayout llTitle, llBody;
        TextView tvTypeText, tvDateText, tvTotalText, tvSubText, tvRemText;
    }

    private void gotoNextActivity(int position) {
        if (session) {
            Intent intent = new Intent(contextIntent, AssignmentDetail.class);
            intent.putExtra("id", String.valueOf(Assignmentarraylist.get(position).getId()));
            intent.putExtra("standard", String.valueOf(Assignmentarraylist.get(position).getStandard()));
            intent.putExtra("section", String.valueOf(Assignmentarraylist.get(position).getSection()));
            contextIntent.startActivity(intent);
        } else {
            Toast.makeText(contextIntent, "Session Over", Toast.LENGTH_SHORT).show();
        }
    }

}

