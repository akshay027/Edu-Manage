package com.maxlore.edumanage.Models.ParentModels.attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikhil on 9/11/2016.
 */
public class MonthYearList {

    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("year_short")
    @Expose
    private String yearShort;
    @SerializedName("month")
    @Expose
    private Integer month;
    @SerializedName("month_name")
    @Expose
    private String monthName;

    private boolean isSelected;


    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getYearShort() {
        return yearShort;
    }

    public void setYearShort(String yearShort) {
        this.yearShort = yearShort;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "MonthYearList{" +
                "year=" + year +
                ", yearShort='" + yearShort + '\'' +
                ", month=" + month +
                ", monthName='" + monthName + '\'' +
                '}';
    }
}
