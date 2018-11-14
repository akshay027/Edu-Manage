package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAssignSubject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxlore on 9/29/2016.
 */
public class AssignSubjectResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("available_subject_list")
    @Expose
    private List<AssignSubject> availableSubjectList = new ArrayList<AssignSubject>();
    @SerializedName("not_present_list")
    @Expose
    private List<String> notPresentList = new ArrayList<String>();
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
     * @return The availableSubjectList
     */
    public List<AssignSubject> getAvailableSubjectList() {
        return availableSubjectList;
    }

    /**
     * @param availableSubjectList The available_subject_list
     */
    public void setAvailableSubjectList(List<AssignSubject> availableSubjectList) {
        this.availableSubjectList = availableSubjectList;
    }

    /**
     * @return The notPresentList
     */
    public List<String> getNotPresentList() {
        return notPresentList;
    }

    /**
     * @param notPresentList The not_present_list
     */
    public void setNotPresentList(List<String> notPresentList) {
        this.notPresentList = notPresentList;
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

    @Override
    public String toString() {
        return "AssignSubjectResponse{" +
                "status=" + status +
                ", availableSubjectList=" + availableSubjectList +
                ", notPresentList=" + notPresentList +
                ", message='" + message + '\'' +
                '}';
    }
}