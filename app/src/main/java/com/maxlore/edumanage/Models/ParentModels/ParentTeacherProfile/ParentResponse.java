package com.maxlore.edumanage.Models.ParentModels.ParentTeacherProfile;

/**
 * Created by maxlore on 29-Aug-16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ParentResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("teacher_profile")
    @Expose
    private List<TeacherProfile> teacherProfile = new ArrayList<TeacherProfile>();
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
     * The teacherProfile
     */
    public List<TeacherProfile> getTeacherProfile() {
        return teacherProfile;
    }

    /**
     *
     * @param teacherProfile
     * The teacher_profile
     */
    public void setTeacherProfile(List<TeacherProfile> teacherProfile) {
        this.teacherProfile = teacherProfile;
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
        return "ParentResponse{" +
                "status=" + status +
                ", teacherProfile=" + teacherProfile +
                ", message='" + message + '\'' +
                '}';
    }
}