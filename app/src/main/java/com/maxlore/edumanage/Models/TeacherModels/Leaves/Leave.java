package com.maxlore.edumanage.Models.TeacherModels.Leaves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikhil on 18-06-2016.
 */
public class Leave {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("leave_category")
    @Expose
    private List<LeaveCategory> leaveCategory = new ArrayList<LeaveCategory>();
    @SerializedName("leave_list_detail")
    @Expose
    private List<LeaveHistory> leaveHistories = new ArrayList<LeaveHistory>();
    @SerializedName("leave_available_detail")
    @Expose
    private List<Object> leaveAvailableDetail = new ArrayList<Object>();
    @SerializedName("leave_dates")
    @Expose
    private List<String> leaveDates = null;
    @SerializedName("student_leave_detail")
    @Expose
    private List<Object> studentLeaveDetail = new ArrayList<Object>();

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The leaveCategory
     */
    public List<LeaveCategory> getLeaveCategory() {
        return leaveCategory;
    }

    /**
     * @param leaveCategory The leave_category
     */
    public void setLeaveCategory(List<LeaveCategory> leaveCategory) {
        this.leaveCategory = leaveCategory;
    }

    /**
     * @return The leaveListDetail
     */
    public List<LeaveHistory> getLeaveListDetail() {
        return leaveHistories;
    }

    /**
     * @param leaveListDetail The leave_list_detail
     */
    public void setLeaveListDetail(List<LeaveHistory> leaveListDetail) {
        this.leaveHistories = leaveListDetail;
    }

    /**
     * @return The leaveAvailableDetail
     */
    public List<Object> getLeaveAvailableDetail() {
        return leaveAvailableDetail;
    }

    /**
     * @param leaveAvailableDetail The leave_available_detail
     */
    public void setLeaveAvailableDetail(List<Object> leaveAvailableDetail) {
        this.leaveAvailableDetail = leaveAvailableDetail;
    }

    /**
     * @return The studentLeaveDetail
     */
    public List<Object> getStudentLeaveDetail() {
        return studentLeaveDetail;
    }

    /**
     * @param studentLeaveDetail The student_leave_detail
     */
    public void setStudentLeaveDetail(List<Object> studentLeaveDetail) {
        this.studentLeaveDetail = studentLeaveDetail;
    }


    public List<LeaveHistory> getLeaveHistories() {
        return leaveHistories;
    }

    public void setLeaveHistories(List<LeaveHistory> leaveHistories) {
        this.leaveHistories = leaveHistories;
    }

    public List<String> getLeaveDates() {
        return leaveDates;
    }

    public void setLeaveDates(List<String> leaveDates) {
        this.leaveDates = leaveDates;
    }

    @Override
    public String toString() {
        return "Leave{" +
                "name='" + name + '\'' +
                ", leaveCategory=" + leaveCategory +
                ", leaveHistories=" + leaveHistories +
                ", leaveAvailableDetail=" + leaveAvailableDetail +
                ", leaveDates=" + leaveDates +
                ", studentLeaveDetail=" + studentLeaveDetail +
                '}';
    }
}