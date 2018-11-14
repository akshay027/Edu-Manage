package com.maxlore.edumanage.Models.ParentModels.ParentLeaves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 8/26/2016.
 */
public class ParentLeaveHistory {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("from_date")
    @Expose
    private String fromDate;
    @SerializedName("to_date")
    @Expose
    private String toDate;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("acknowledgement")
    @Expose
    private String acknowledgement;
    @SerializedName("is_half_day")
    @Expose
    private Boolean isHalfDay;
    @SerializedName("meridiem")
    @Expose
    private Object meridiem;
    @SerializedName("class_teacher_name")
    @Expose
    private String classTeacherName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAcknowledgement() {
        return acknowledgement;
    }

    public void setAcknowledgement(String acknowledgement) {
        this.acknowledgement = acknowledgement;
    }

    public Boolean getIsHalfDay() {
        return isHalfDay;
    }

    public void setIsHalfDay(Boolean isHalfDay) {
        this.isHalfDay = isHalfDay;
    }

    public Object getMeridiem() {
        return meridiem;
    }

    public void setMeridiem(Object meridiem) {
        this.meridiem = meridiem;
    }

    public String getClassTeacherName() {
        return classTeacherName;
    }

    public void setClassTeacherName(String classTeacherName) {
        this.classTeacherName = classTeacherName;
    }

}
