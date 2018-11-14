package com.maxlore.edumanage.Models.TeacherModels.Leaves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikhil on 19-06-2016.
 */
public class StudentLeave {

    @SerializedName("student_id")
    @Expose
    private Integer studentId;
    @SerializedName("leave_id")
    @Expose
    private Integer leaveId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("acknowledgement")
    @Expose
    private String acknowledgement;
    @SerializedName("photo")
    @Expose
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @return The studentId
     */
    public Integer getStudentId() {
        return studentId;
    }

    /**
     * @param studentId The student_id
     */
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    /**
     * @return The leaveId
     */
    public Integer getLeaveId() {
        return leaveId;
    }

    /**
     * @param leaveId The leave_id
     */
    public void setLeaveId(Integer leaveId) {
        this.leaveId = leaveId;
    }

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
     * @return The startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate The start_date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return The endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate The end_date
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return The reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason The reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return The acknowledgement
     */
    public String getAcknowledgement() {
        return acknowledgement;
    }

    /**
     * @param acknowledgement The acknowledgement
     */
    public void setAcknowledgement(String acknowledgement) {
        this.acknowledgement = acknowledgement;
    }
}
