package com.maxlore.edumanage.Models.ParentModels.ParentProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 9/1/2016.
 */
public class ParentProfileResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("student")
    @Expose
    private ParentProfile parentProfile;
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
     * The student
     */

    /**
     *
     * @param student
     * The student
     */
    public ParentProfile getParentProfile() {
        return parentProfile;
    }

    public void setParentProfile(ParentProfile parentProfile) {
        this.parentProfile = parentProfile;
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
        return "ParentProfileResponse{" +
                "status=" + status +
                ", parentProfile=" + parentProfile +
                ", message='" + message + '\'' +
                '}';
    }
}
