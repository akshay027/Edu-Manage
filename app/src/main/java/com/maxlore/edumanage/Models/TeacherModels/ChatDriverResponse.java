package com.maxlore.edumanage.Models.TeacherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikhil on 9/13/2016.
 */
public class ChatDriverResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("student")
    @Expose
    private List<ChatDriver> student = new ArrayList<ChatDriver>();
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ChatDriver> getStudent() {
        return student;
    }

    public void setStudent(List<ChatDriver> student) {
        this.student = student;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatDriverResponse{" +
                "status=" + status +
                ", student=" + student +
                ", message='" + message + '\'' +
                '}';
    }
}
