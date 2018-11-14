package com.maxlore.edumanage.Models.TeacherModels.Academics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 8/14/2016.
 */
public class WeekSessionPlan {


    @SerializedName("sub_topic_id")
    @Expose
    private Integer subTopicId;
    @SerializedName("sub_topic_name")
    @Expose
    private String subTopicName;
    @SerializedName("week")
    @Expose
    private String week;
    @SerializedName("sub_topic_number")
    @Expose
    private String subtopicnumber;
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

    /**
     * @return The subTopicId
     */
    public Integer getSubTopicId() {
        return subTopicId;
    }

    /**
     * @param subTopicId The sub_topic_id
     */
    public void setSubTopicId(Integer subTopicId) {
        this.subTopicId = subTopicId;
    }

    /**
     * @return The subTopicName
     */
    public String getSubTopicName() {
        return subTopicName;
    }

    /**
     * @param subTopicName The sub_topic_name
     */
    public void setSubTopicName(String subTopicName) {
        this.subTopicName = subTopicName;
    }

    /**
     * @return The week
     */
    public String getWeek() {
        return week;
    }

    /**
     * @param week The week
     */
    public void setWeek(String week) {
        this.week = week;
    }

    /**
     * @return The remark
     */
    public Integer getRemark() {
        return remark;
    }

    /**
     * @param remark The remark
     */
    public void setRemark(Integer remark) {
        this.remark = remark;
    }

    /**
     * @return The topicName
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * @param topicName The topic_name
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    /**
     * @return The topicNo
     */
    public Integer getTopicNo() {
        return topicNo;
    }

    /**
     * @param topicNo The topic_no
     */
    public void setTopicNo(Integer topicNo) {
        this.topicNo = topicNo;
    }

    /**
     * @return The monthName
     */
    public String getMonthName() {
        return monthName;
    }

    /**
     * @param monthName The month_name
     */
    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getSubtopicnumber() {
        return subtopicnumber;
    }

    public void setSubtopicnumber(String subtopicnumber) {
        this.subtopicnumber = subtopicnumber;
    }

    @Override
    public String toString() {
        return "WeekSessionPlan{" +
                "subTopicId=" + subTopicId +
                ", subTopicName='" + subTopicName + '\'' +
                ", week='" + week + '\'' +
                ", subtopicnumber=" + subtopicnumber +
                ", remark=" + remark +
                ", topicName='" + topicName + '\'' +
                ", topicNo=" + topicNo +
                ", monthName='" + monthName + '\'' +
                '}';
    }
}