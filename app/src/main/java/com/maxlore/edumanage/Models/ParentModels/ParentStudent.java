package com.maxlore.edumanage.Models.ParentModels;

/**
 * Created by maxlore on 26-Aug-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class ParentStudent {


    @SerializedName("attendance_percent")
    @Expose
    private Double attendancePercent;
    @SerializedName("assignment")
    @Expose
    private List<ParentAssignment> assignment = new ArrayList<ParentAssignment>();
    @SerializedName("leave_apply")
    @Expose
    private Integer leaveApply;

    /**
     * @return The studentId
     */

    /**
     * @param studentId The student_id
     */

    /**
     * @return The studentName
     */

    /**
     * @param studentName The student_name
     */

    /**
     * @return The attendancePercent
     */
    public Double getAttendancePercent() {
        return attendancePercent;
    }

    /**
     * @param attendancePercent The attendance_percent
     */
    public void setAttendancePercent(Double attendancePercent) {
        this.attendancePercent = attendancePercent;
    }

    /**
     * @return The assignment
     */
    public List<ParentAssignment> getAssignment() {
        return assignment;
    }

    /**
     * @param assignment The assignment
     */
    public void setAssignment(List<ParentAssignment> assignment) {
        this.assignment = assignment;
    }

    /**
     * @return The leaveApply
     */
    public Integer getLeaveApply() {
        return leaveApply;
    }

    /**
     * @param leaveApply The leave_apply
     */
    public void setLeaveApply(Integer leaveApply) {
        this.leaveApply = leaveApply;
    }

    @Override
    public String toString() {
        return "ParentStudent{" +
                "attendancePercent=" + attendancePercent +
                ", assignment=" + assignment +
                ", leaveApply=" + leaveApply +
                '}';
    }
}