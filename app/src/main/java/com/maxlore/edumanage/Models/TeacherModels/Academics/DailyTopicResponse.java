package com.maxlore.edumanage.Models.TeacherModels.Academics;

/**
 * Created by maxlore on 23-Oct-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DailyTopicResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("daily_topics")
    @Expose
    private List<DailyTopic> dailyTopics = new ArrayList<DailyTopic>();
    @SerializedName("message")
    @Expose
    private String message;

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate
     * The start_date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return
     * The endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate
     * The end_date
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return
     * The dailyTopics
     */
    public List<DailyTopic> getDailyTopics() {
        return dailyTopics;
    }

    /**
     *
     * @param dailyTopics
     * The daily_topics
     */
    public void setDailyTopics(List<DailyTopic> dailyTopics) {
        this.dailyTopics = dailyTopics;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DailyTopicResponse{" +
                "status=" + status +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", dailyTopics=" + dailyTopics +
                ", message='" + message + '\'' +
                '}';
    }
}