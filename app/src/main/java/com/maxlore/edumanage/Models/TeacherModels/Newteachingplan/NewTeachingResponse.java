package com.maxlore.edumanage.Models.TeacherModels.Newteachingplan;

/**
 * Created by Shrey on 11-Sep-17.
 */


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewTeachingResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("teaching_plans")
    @Expose
    private List<NewTeachingPlanData> teachingPlans = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<NewTeachingPlanData> getTeachingPlans() {
        return teachingPlans;
    }

    public void setTeachingPlans(List<NewTeachingPlanData> teachingPlans) {
        this.teachingPlans = teachingPlans;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "NewTeachingResponse{" +
                "status=" + status +
                ", teachingPlans=" + teachingPlans +
                ", message='" + message + '\'' +
                '}';
    }
}