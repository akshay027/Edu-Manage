package com.maxlore.edumanage.Models.TeacherModels.Academics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 8/14/2016.
 */
public class YearSessionPlan {

    @SerializedName("topic_name")
    @Expose
    private String topicName;
    @SerializedName("topic_no")
    @Expose
    private Integer topicNo;
    @SerializedName("month_name")
    @Expose
    private String monthName;


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

    @Override
    public String toString() {
        return "YearSessionPlan{" +
                "topicName='" + topicName + '\'' +
                ", topicNo=" + topicNo +
                ", monthName='" + monthName + '\'' +
                '}';
    }
}
