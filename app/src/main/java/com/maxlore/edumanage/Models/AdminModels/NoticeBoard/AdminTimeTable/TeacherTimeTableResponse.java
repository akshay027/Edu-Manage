package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxlore on 10/4/2016.
 */
public class TeacherTimeTableResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("list")
    @Expose
    private List<TeacherTimeTable> list = new ArrayList<TeacherTimeTable>();
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
     * The list
     */
    public List<TeacherTimeTable> getList() {
        return list;
    }

    /**
     *
     * @param list
     * The list
     */
    public void setList(List<TeacherTimeTable> list) {
        this.list = list;
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
        return "TeacherTimeTableResponse{" +
                "status=" + status +
                ", list=" + list +
                ", message='" + message + '\'' +
                '}';
    }
}
