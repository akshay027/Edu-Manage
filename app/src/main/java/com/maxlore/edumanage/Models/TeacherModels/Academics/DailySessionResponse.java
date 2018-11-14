package com.maxlore.edumanage.Models.TeacherModels.Academics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxlore on 8/14/2016.
 */
public class DailySessionResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("teaching_plan")
    @Expose
    private List<DailySessionPlan> sessionPlan = new ArrayList<DailySessionPlan>();
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * @return The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return The sessionPlan
     */
    public List<DailySessionPlan> getSessionPlan() {
        return sessionPlan;
    }

    /**
     * @param sessionPlan The session_plan
     */
    public void setSessionPlan(List<DailySessionPlan> sessionPlan) {
        this.sessionPlan = sessionPlan;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}