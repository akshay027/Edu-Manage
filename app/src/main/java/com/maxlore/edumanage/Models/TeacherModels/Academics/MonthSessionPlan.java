package com.maxlore.edumanage.Models.TeacherModels.Academics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 8/14/2016.
 */
public class MonthSessionPlan {

    @SerializedName("sub_topic_id")
    @Expose
    private Integer subTopicId;
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
    @SerializedName("remark")
    @Expose
    private Integer remark;
    @SerializedName("topic_name")
    @Expose
    private String topicName;
    @SerializedName("topic_no")
    @Expose
    private Integer topicNo;
    @SerializedName("month_name")
    @Expose
    private String monthName;
    @SerializedName("month_id")
    @Expose
    private Integer monthId;

    public Integer getSubTopicId() {
        return subTopicId;
    }

    public void setSubTopicId(Integer subTopicId) {
        this.subTopicId = subTopicId;
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

    public Integer getRemark() {
        return remark;
    }

    public void setRemark(Integer remark) {
        this.remark = remark;
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

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public Integer getMonthId() {
        return monthId;
    }

    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }

    @Override
    public String toString() {
        return "MonthSessionPlan{" +
                "subTopicId=" + subTopicId +
                ", subTopicName='" + subTopicName + '\'' +
                ", subTopicNumber='" + subTopicNumber + '\'' +
                ", weekId=" + weekId +
                ", week='" + week + '\'' +
                ", remark=" + remark +
                ", topicName='" + topicName + '\'' +
                ", topicNo=" + topicNo +
                ", monthName='" + monthName + '\'' +
                ", monthId=" + monthId +
                '}';
    }
}