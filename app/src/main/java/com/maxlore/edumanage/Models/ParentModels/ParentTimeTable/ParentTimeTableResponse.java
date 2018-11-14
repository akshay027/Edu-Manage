package com.maxlore.edumanage.Models.ParentModels.ParentTimeTable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maxlore on 28-Aug-16.
 */
public class ParentTimeTableResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("parent_timetable")
    @Expose
    private List<ParentTimeTableStructure> parentTimetable = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ParentTimeTableStructure> getParentTimetable() {
        return parentTimetable;
    }

    public void setParentTimetable(List<ParentTimeTableStructure> parentTimetable) {
        this.parentTimetable = parentTimetable;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "ParentTimeTableResponse{" +
                "status=" + status +
                ", parentTimetable=" + parentTimetable +
                ", message='" + message + '\'' +
                '}';
    }
}
