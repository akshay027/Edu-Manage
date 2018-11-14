package com.maxlore.edumanage.Models.ParentModels.Attnew;

/**
 * Created by maxlore on 12-Apr-17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewStudentDayAttendanceDetail {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("am")
    @Expose
    private Integer am;
    @SerializedName("pm")
    @Expose
    private Integer pm;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAm() {
        return am;
    }

    public void setAm(Integer am) {
        this.am = am;
    }

    public Integer getPm() {
        return pm;
    }

    public void setPm(Integer pm) {
        this.pm = pm;
    }

    @Override
    public String toString() {
        return "NewStudentDayAttendanceDetail{" +
                "date='" + date + '\'' +
                ", am=" + am +
                ", pm=" + pm +
                '}';
    }
}