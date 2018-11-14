package com.maxlore.edumanage.Models.TeacherModels.Academics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maxlore on 8/14/2016.
 */
public class YearSessionResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("teaching_plan")
    @Expose
    private List<YearSessionPlan> teachingPlan = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<YearSessionPlan> getTeachingPlan() {
        return teachingPlan;
    }

    public void setTeachingPlan(List<YearSessionPlan> teachingPlan) {
        this.teachingPlan = teachingPlan;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "YearSessionResponse{" +
                "status=" + status +
                ", teachingPlan=" + teachingPlan +
                ", message='" + message + '\'' +
                '}';
    }
}
