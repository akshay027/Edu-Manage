package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminCalender;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maxlore on 3/28/2017.
 */

public class AdminEvents {

    @SerializedName("holidays")
    @Expose
    private List<CalenderHoliday> holidays = null;
    @SerializedName("articles")
    @Expose
    private List<Article> articles = null;
    @SerializedName("exam_schedules")
    @Expose
    private List<ExamSchedule> examSchedules = null;
    @SerializedName("fee_types")
    @Expose
    private List<FeeType> feeTypes = null;
    @SerializedName("buses")
    @Expose
    private List<AdminBus> buses = null;

    public List<CalenderHoliday> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<CalenderHoliday> holidays) {
        this.holidays = holidays;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<ExamSchedule> getExamSchedules() {
        return examSchedules;
    }

    public void setExamSchedules(List<ExamSchedule> examSchedules) {
        this.examSchedules = examSchedules;
    }

    public List<FeeType> getFeeTypes() {
        return feeTypes;
    }

    public void setFeeTypes(List<FeeType> feeTypes) {
        this.feeTypes = feeTypes;
    }

    public List<AdminBus> getBuses() {
        return buses;
    }

    public void setBuses(List<AdminBus> buses) {
        this.buses = buses;
    }
}
