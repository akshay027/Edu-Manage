package com.maxlore.edumanage.Models.TeacherModels.Academics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 8/14/2016.
 */
public class DailySessionPlan {
    @SerializedName("daily_plan_id")
    @Expose
    private Integer dailyPlanId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("lesson_name")
    @Expose
    private String lessonName;
    @SerializedName("remark")
    @Expose
    private Integer remark;
    @SerializedName("sub_topic_name")
    @Expose
    private String subTopicName;
    @SerializedName("sub_topic_number")
    @Expose
    private String subTopicNumber;
    @SerializedName("week_id")
    @Expose
    private Integer weekId;
    @SerializedName("week")
    @Expose
    private String week;
    @SerializedName("topic_name")
    @Expose
    private String topicName;
    @SerializedName("topic_no")
    @Expose
    private Integer topicNo;
    @SerializedName("month_id")
    @Expose
    private Integer monthId;
    @SerializedName("month_name")
    @Expose
    private String monthName;
    @SerializedName("editable")
    @Expose
    private Boolean editable;

    public Integer getDailyPlanId() {
        return dailyPlanId;
    }

    public void setDailyPlanId(Integer dailyPlanId) {
        this.dailyPlanId = dailyPlanId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public Integer getRemark() {
        return remark;
    }

    public void setRemark(Integer remark) {
        this.remark = remark;
    }

    public String getSubTopicName() {
        return subTopicName;
    }

    public void setSubTopicName(String subTopicName) {
        this.subTopicName = subTopicName;
    }

    public String getSubTopicNumber() {
        return subTopicNumber;
    }

    public void setSubTopicNumber(String subTopicNumber) {
        this.subTopicNumber = subTopicNumber;
    }

    public Integer getWeekId() {
        return weekId;
    }

    public void setWeekId(Integer weekId) {
        this.weekId = weekId;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Integer getTopicNo() {
        return topicNo;
    }

    public void setTopicNo(Integer topicNo) {
        this.topicNo = topicNo;
    }

    public Integer getMonthId() {
        return monthId;
    }

    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    @Override
    public String toString() {
        return "DailySessionPlan{" +
                "dailyPlanId=" + dailyPlanId +
                ", date='" + date + '\'' +
                ", lessonName='" + lessonName + '\'' +
                ", remark=" + remark +
                ", subTopicName='" + subTopicName + '\'' +
                ", subTopicNumber='" + subTopicNumber + '\'' +
                ", weekId=" + weekId +
                ", week='" + week + '\'' +
                ", topicName='" + topicName + '\'' +
                ", topicNo=" + topicNo +
                ", monthId=" + monthId +
                ", monthName='" + monthName + '\'' +
                ", editable=" + editable +
                '}';
    }
}