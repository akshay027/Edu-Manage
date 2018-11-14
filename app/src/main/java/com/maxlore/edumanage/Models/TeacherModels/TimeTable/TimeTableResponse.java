package com.maxlore.edumanage.Models.TeacherModels.TimeTable;

import com.maxlore.edumanage.Models.TeacherModels.database.TimeTableStructure;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxlore on 04-Aug-16.
 */
public class TimeTableResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("time_table_structure")
    @Expose
    private List<TimeTableStructure> timeTableStructure = new ArrayList<TimeTableStructure>();
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<TimeTableStructure> getTimeTableStructure() {
        return timeTableStructure;
    }

    public void setTimeTableStructure(List<TimeTableStructure> timeTableStructure) {
        this.timeTableStructure = timeTableStructure;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TimeTableResponse{" +
                "status=" + status +
                ", timeTableStructure=" + timeTableStructure +
                ", message='" + message + '\'' +
                '}';
    }
}
